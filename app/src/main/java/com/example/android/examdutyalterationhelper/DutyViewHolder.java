package com.example.android.examdutyalterationhelper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DutyViewHolder  extends RecyclerView.ViewHolder {

    public TextView dutyName,dutyDate,startTime,endtime,incharge1,incharge2,remarks;
    public ItemClickListener listener;

    public DutyViewHolder(@NonNull View itemView) {
        super(itemView);

        dutyName = (TextView) itemView.findViewById(R.id.display_duty_name);
        dutyDate = (TextView) itemView.findViewById(R.id.display_duty_date);
        startTime = (TextView) itemView.findViewById(R.id.display_start_time);
        endtime = (TextView) itemView.findViewById(R.id.display_end_time);
        incharge1 = (TextView) itemView.findViewById(R.id.display_incharge1);
        incharge2 = (TextView) itemView.findViewById(R.id.display_incharge2);
        remarks = (TextView) itemView.findViewById(R.id.display_remarks);
    }

//    public void setItemClickListener(ItemClickListener listener){
//        this.listener = listener;
//    }
//
//    @Override
//    public void onClick(View v) {
//        listener.onClick(v,getAdapterPosition(),false);
//    }
}
