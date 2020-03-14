package com.example.android.examdutyalterationhelper;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RescheduleDutyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public ItemClickListener listener;
    public TextView dutyName,dutyDate,startTime,endtime,incharge,phone;

    public RescheduleDutyViewHolder(@NonNull View itemView) {
        super(itemView);

        dutyName = (TextView) itemView.findViewById(R.id.display_duty_name_reschedule);
        dutyDate = (TextView) itemView.findViewById(R.id.display_duty_date_reschedule);
        startTime = (TextView) itemView.findViewById(R.id.display_start_time_reschedule);
        endtime = (TextView) itemView.findViewById(R.id.display_end_time_reschedule);
        incharge = (TextView) itemView.findViewById(R.id.display_incharge_reschedule);
        phone = (TextView) itemView.findViewById(R.id.display_phone_reschedule);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);
    }
}
