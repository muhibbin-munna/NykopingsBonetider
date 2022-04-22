package com.nykopings.bonetider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MonthViewHolder>  {
    private Context mContext;
    private List<Upload> mUploads;

    public MyAdapter(Context context, List<Upload> mUploads) {
        this.mContext = context;
        this.mUploads = mUploads;
    }

    @NonNull
    @Override
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.daily_item, parent, false);
        return new MonthViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, int position) {

//        if(position!=0){
            Upload uploadCurrent = mUploads.get(position);
            holder.date.setText(String.valueOf(uploadCurrent.getDate()));
            holder.day.setText(String.valueOf(uploadCurrent.getDay()));
            holder.fajr.setText(String.valueOf(uploadCurrent.getFazr()));
            holder.shorook.setText(String.valueOf(uploadCurrent.getShorook()));
            holder.zuhur.setText(String.valueOf(uploadCurrent.getZuhur()));
            holder.asr.setText(String.valueOf(uploadCurrent.getAsr()));
            holder.maghirb.setText(String.valueOf(uploadCurrent.getMaghrib()));
            holder.isha.setText(String.valueOf(uploadCurrent.getIsha()));

            if(position%2==0){
                holder.date.setBackgroundColor(Color.parseColor("#ffffcd"));
                holder.day.setBackgroundColor(Color.parseColor("#ffffcd"));
                holder.fajr.setBackgroundColor(Color.parseColor("#ffffcd"));
                holder.shorook.setBackgroundColor(Color.parseColor("#ffffcd"));
                holder.zuhur.setBackgroundColor(Color.parseColor("#ffffcd"));
                holder.asr.setBackgroundColor(Color.parseColor("#ffffcd"));
                holder.maghirb.setBackgroundColor(Color.parseColor("#ffffcd"));
                holder.isha.setBackgroundColor(Color.parseColor("#ffffcd"));
            }
            else {
                holder.date.setBackgroundColor(Color.parseColor("#e7fbff"));
                holder.day.setBackgroundColor(Color.parseColor("#e7fbff"));
                holder.fajr.setBackgroundColor(Color.parseColor("#e7fbff"));
                holder.shorook.setBackgroundColor(Color.parseColor("#e7fbff"));
                holder.zuhur.setBackgroundColor(Color.parseColor("#e7fbff"));
                holder.asr.setBackgroundColor(Color.parseColor("#e7fbff"));
                holder.maghirb.setBackgroundColor(Color.parseColor("#e7fbff"));
                holder.isha.setBackgroundColor(Color.parseColor("#e7fbff"));
            }
//        }
//        else {
//            holder.date.setTypeface(holder.date.getTypeface(), Typeface.BOLD);
//            holder.day.setTypeface(holder.day.getTypeface(), Typeface.BOLD);
//            holder.fajr.setTypeface(holder.fajr.getTypeface(), Typeface.BOLD);
//            holder.shorook.setTypeface(holder.shorook.getTypeface(), Typeface.BOLD);
//            holder.zuhur.setTypeface(holder.zuhur.getTypeface(), Typeface.BOLD);
//            holder.asr.setTypeface(holder.asr.getTypeface(), Typeface.BOLD);
//            holder.maghirb.setTypeface(holder.maghirb.getTypeface(), Typeface.BOLD);
//            holder.isha.setTypeface(holder.isha.getTypeface(), Typeface.BOLD);
//        }
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public static class MonthViewHolder extends RecyclerView.ViewHolder {
        TextView date,day,fajr,zuhur,asr,maghirb,isha,shorook;
        public MonthViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateTv);
            day = itemView.findViewById(R.id.dayTv);
            fajr = itemView.findViewById(R.id.fajrTv);
            shorook = itemView.findViewById(R.id.shorookTv);
            zuhur = itemView.findViewById(R.id.zuhurTv);
            asr = itemView.findViewById(R.id.asrTv);
            maghirb = itemView.findViewById(R.id.maghribTv);
            isha = itemView.findViewById(R.id.ishaTv);

        }
    }
}
