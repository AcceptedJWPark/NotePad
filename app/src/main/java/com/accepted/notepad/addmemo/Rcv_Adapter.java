package com.accepted.notepad.addmemo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.accepted.notepad.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Rcv_Adapter extends RecyclerView.Adapter<Rcv_Adapter.CustomViewHolder> {

    private ArrayList<String> mItems;
    private EditText fakeTitle;
    private EditText fakeContents;
    private ArrayList<String> fakeTitleList;
    private ArrayList<String> fakeContentsList;
    private int selectedPosition = 0;

    GradientDrawable shape3;
    GradientDrawable shape4;

    String color4;



    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView fake;

        public CustomViewHolder(View view){
            super(view);
            this.fake = view.findViewById(R.id.tv_faketap);
        }
        void onBind(String str) {
            fake.setText(str);
        }


    }

    public Rcv_Adapter(ArrayList<String> list, EditText fakeTitle, EditText fakeContents, ArrayList<String> fakeTitleList, ArrayList<String> fakeContentsList, GradientDrawable shape3, GradientDrawable shape4, String color4){
        this.mItems = list;
        this.fakeTitle = fakeTitle;
        this.fakeContents = fakeContents;
        this.fakeTitleList = fakeTitleList;
        this.fakeContentsList = fakeContentsList;

        this.shape3 = shape3;
        this.shape4 = shape4;
        this.color4 = color4;
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addmemo_rvfake_set, parent, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {

        holder.onBind(mItems.get(position));
        if(selectedPosition == position)
        {
            holder.fake.setBackground(shape3);
            holder.fake.setTextColor(Color.parseColor(color4));
            holder.fake.setTypeface(Typeface.DEFAULT_BOLD);
        }else
        {
            holder.fake.setBackground(shape4);
            holder.fake.setTextColor(Color.parseColor("#AEAEAE"));
            holder.fake.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }

        holder.fake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;

                fakeTitle.setText(fakeTitleList.get(position));
                fakeContents.setText(fakeContentsList.get(position));

                fakeTitle.clearFocus();
                fakeContents.clearFocus();

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != mItems ? mItems.size() : 0);
    }

}