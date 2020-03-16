package com.example.android.examdutyalterationhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddNewDutyActivity extends AppCompatActivity {

    EditText startTime,endTime,examDetail,remarks,date;
    Spinner incharge1,incharge2;
    Button addNewDutyBtn;
    private DatabaseReference databaseDuty,databaseUsers;
    private Calendar myCalendar = Calendar.getInstance();
    Boolean edit = false;
    String dname = "",stime="",etime="",dte="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_duty);

        examDetail = (EditText) findViewById(R.id.add_duty_name);
        date = (EditText) findViewById(R.id.add_duty_date);
        startTime = (EditText) findViewById(R.id.add_duty_start_time);
        endTime = (EditText) findViewById(R.id.add_duty_end_time);
        incharge1 = (Spinner) findViewById(R.id.add_duty_incharge1);
        incharge2 = (Spinner) findViewById(R.id.add_duty_incharge2);
        remarks = (EditText) findViewById(R.id.add_duty_remarks);

        databaseDuty = FirebaseDatabase.getInstance().getReference("Duty");
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey("Edit")){
            edit = bundle.getBoolean("Edit");
            dname = bundle.getString("Dname");
            stime = bundle.getString("STime");
            etime = bundle.getString("ETime");
            dte = bundle.getString("Date");
        }

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> usersList1 = new ArrayList<String>();
                final List<String> usersList2 = new ArrayList<String>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String name = dataSnapshot1.child("name").getValue(String.class);
                    if (!name.equals("Admin")){
                        usersList1.add(name);
                        usersList2.add(name);
                    }
                }

                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(AddNewDutyActivity.this, android.R.layout.simple_spinner_item, usersList1);
                arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                incharge1.setAdapter(arrayAdapter1);

                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(AddNewDutyActivity.this, android.R.layout.simple_spinner_item, usersList2);
                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                incharge2.setAdapter(arrayAdapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddNewDutyActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddNewDutyActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date.setText(sdf.format(myCalendar.getTime()));
            }

        };


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddNewDutyActivity.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        addNewDutyBtn = (Button) findViewById(R.id.add_new_duty_btn);

        if(edit){
            addNewDutyBtn.setText("Update Duty");
            examDetail.setText(dname);
            examDetail.setEnabled(false);
            startTime.setText(stime);
            startTime.setEnabled(false);
            endTime.setText(etime);
            endTime.setEnabled(false);
            date.setText(dte);
            date.setEnabled(false);

        }
        addNewDutyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDuty();
            }
        });
    }

    private void addDuty() {
        final String dutyDetailEditText,dutyDateEditText,startTimeEditText,endtimeEditText,incharge1EditText,incharge2EditText,remarksEditText;
        dutyDetailEditText = examDetail.getText().toString().trim();
        dutyDateEditText = date.getText().toString().trim();
        startTimeEditText = startTime.getText().toString().trim();
        endtimeEditText = endTime.getText().toString().trim();
        incharge1EditText = incharge1.getSelectedItem().toString().trim();
        incharge2EditText = incharge2.getSelectedItem().toString().trim();
        remarksEditText = remarks.getText().toString().trim();

        if(dutyDetailEditText.equals("")){
            examDetail.setError("Name is Required");
            examDetail.requestFocus();
            return;
        }

        if(dutyDateEditText.equals("")){
            date.setError("Date is Required");
            date.requestFocus();
            return;
        }

        if(startTimeEditText.equals("")){
            startTime.setError("Start Time is Required");
            startTime.requestFocus();
            return;
        }

        if(endtimeEditText.equals("")){
            endTime.setError("End Time is Required");
            endTime.requestFocus();
            return;
        }

        if(incharge1EditText.equals(incharge2EditText) || incharge2EditText.equals(incharge1EditText)){

            Toast.makeText(getApplicationContext(),"Select Different Invigilators",Toast.LENGTH_LONG).show();
            return;
        }

        if(remarksEditText.equals("")){
            remarks.setError("Name is Required");
            remarks.requestFocus();
            return;
        }

        Duties duties = new Duties(startTimeEditText,endtimeEditText,dutyDetailEditText,incharge1EditText,incharge2EditText,remarksEditText,dutyDateEditText);

         databaseDuty.child(dutyDetailEditText).setValue(duties).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful()) {
                     Toast.makeText(getApplicationContext(), "Duty Added Successfully", Toast.LENGTH_SHORT).show();
                     Intent i = new Intent(AddNewDutyActivity.this, HomeActivity.class);
                     i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(i);
                 } else {
                     Toast.makeText(getApplicationContext(), "Duty Not Added", Toast.LENGTH_LONG).show();
                 }
             }
         });

    }
}
