package com.example.android.examdutyalterationhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    String currentUser;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference dutiesRef,rescheduleDutiesRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        currentUser = Prevalent.currentOnlineUser.getName();

        dutiesRef = FirebaseDatabase.getInstance().getReference().child("Duty");
        rescheduleDutiesRef = FirebaseDatabase.getInstance().getReference().child("Reschedule Duty");
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.homeactivitymenu, menu);
        MenuItem item = menu.findItem(R.id.add_duty);
        MenuItem item1 = menu.findItem(R.id.view_reschedule_requests);

        if(currentUser.equals("Admin")){
            item.setVisible(true);
            item1.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_duty:
                Intent x = new Intent(this, AddNewDutyActivity.class);
                x.putExtra("Edit",false);
                startActivity(x);
                return true;
            case R.id.view_reschedule_requests:
                startActivity(new Intent(this, RescheduleDutyActivity.class));
                return true;
            case R.id.sign_out:
                mAuth.signOut();
                Intent i = new Intent(this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseRecyclerOptions<Duties> options = new FirebaseRecyclerOptions.Builder<Duties>()
                .setQuery(dutiesRef,Duties.class)
                .build();

        FirebaseRecyclerAdapter<Duties, DutyViewHolder> adapter = new FirebaseRecyclerAdapter<Duties, DutyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DutyViewHolder holder, int position, @NonNull Duties model) {
                holder.dutyName.setText(model.getExamDetail());
                holder.dutyDate.setText(model.getDate());
                holder.startTime.setText(model.getStartTime());
                holder.endtime.setText(model.getEndTime());
                holder.incharge1.setText(model.getIncharge1());
                holder.incharge2.setText(model.getIncharge2());
                holder.remarks.setText(model.getRemarks());



                if(holder.incharge1.getText().toString().trim().equals(currentUser) || holder.incharge2.getText().toString().trim().equals(currentUser)){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            CharSequence option[] = new CharSequence[]{
                                    "Yes",
                                    "No"
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                            builder.setTitle("Do you want to request for a reschedule ?");

                            builder.setItems(option, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(which == 0){
                                        String dutyname,dutydate,stime,etime,incharge,phone;
                                        Boolean accepted = false;
                                        dutyname = holder.dutyName.getText().toString().trim();
                                        dutydate  = holder.dutyDate.getText().toString().trim();
                                        stime = holder.startTime.getText().toString().trim();
                                        etime = holder.endtime.getText().toString().trim();
                                        incharge = Prevalent.currentOnlineUser.getName();;
                                        phone = Prevalent.currentOnlineUser.getPhone();

                                        RescheduleDuties rDuties = new RescheduleDuties(dutyname,dutydate,stime,etime,incharge,phone);
                                        rescheduleDutiesRef.child(dutyname+incharge).setValue(rDuties).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(HomeActivity.this,"Request Has Been Placed Successfully",Toast.LENGTH_SHORT).show();
                                                }else{
                                                    Toast.makeText(HomeActivity.this,"Request Not Placed",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }else {
                                    }
                                }
                            });
                            builder.show();
                        }
                    });
                }

            }

            @NonNull
            @Override
            public DutyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.duties_layout,viewGroup,false);
                DutyViewHolder holder = new DutyViewHolder(view);
                return holder;
            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
