package com.example.android.examdutyalterationhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RescheduleDutyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference rescheduleDutiesRef,databaseDuty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_duty);

        rescheduleDutiesRef = FirebaseDatabase.getInstance().getReference().child("Reschedule Duty");
        databaseDuty = FirebaseDatabase.getInstance().getReference("Duty");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_menu_reschedule);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();


        final FirebaseRecyclerOptions<RescheduleDuties> options = new FirebaseRecyclerOptions.Builder<RescheduleDuties>()
                .setQuery(rescheduleDutiesRef,RescheduleDuties.class)
                .build();

        FirebaseRecyclerAdapter<RescheduleDuties, RescheduleDutyViewHolder> adapter = new FirebaseRecyclerAdapter<RescheduleDuties, RescheduleDutyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RescheduleDutyViewHolder holder, int position, @NonNull final RescheduleDuties model) {
                holder.dutyName.setText(model.getDutyName());
                holder.dutyDate.setText(model.getDutyDate());
                holder.startTime.setText(model.getStartTime());
                holder.endtime.setText(model.getEndtime());
                holder.incharge.setText(model.getIncharge());
                holder.phone.setText(model.getPhone());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence option[] = new CharSequence[]{
                                "Edit",
                                "No"
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(RescheduleDutyActivity.this);
                        builder.setTitle("Accept Reschedule");

                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){
                                    rescheduleDutiesRef.child(model.getDutyName()+model.getIncharge()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Intent i = new Intent(RescheduleDutyActivity.this, AddNewDutyActivity.class);
                                                i.putExtra("Edit",true);
                                                i.putExtra("Dname",model.getDutyName());
                                                i.putExtra("STime",model.getStartTime());
                                                i.putExtra("ETime",model.getEndtime());
                                                i.putExtra("Date",model.getDutyDate());
                                                startActivity(i);

                                                Toast.makeText(RescheduleDutyActivity.this,"Request Accepted",Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(RescheduleDutyActivity.this,"Request Not Accepted",Toast.LENGTH_SHORT).show();
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

            @NonNull
            @Override
            public RescheduleDutyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reschedule_duty_layout,viewGroup,false);
                RescheduleDutyViewHolder holder = new RescheduleDutyViewHolder(view);
                return holder;
            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
