package com.accepted.notepad.main;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accepted.notepad.R;

import java.util.ArrayList;


public class ListAdapter_Memo extends BaseAdapter {


    private Context mContext;
    private ArrayList<Listitem_Memo> arrayList;
    String backColor1;
    String backColor2;
    String txtcolor;
    boolean ismemo;
    boolean isdate;

    public ListAdapter_Memo(Context mContext, ArrayList<Listitem_Memo> arrayList, String backColor1, String backColor2, String txtcolor, boolean ismemo, boolean isdate) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.backColor1 = backColor1;
        this.backColor2 = backColor2;
        this.txtcolor = txtcolor;
        this.ismemo = ismemo;
        this.isdate = isdate;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.memolist_set, parent, false);

            holder = new ViewHolder();

            holder.tv_title = convertView.findViewById(R.id.tv_title_memo);
            holder.tv_contents = convertView.findViewById(R.id.tv_contents_memo);
            holder.tv_date = convertView.findViewById(R.id.tv_date_memo);
            holder.ll_container = convertView.findViewById(R.id.ll_container);

            convertView.setBackgroundColor(Color.parseColor(backColor1));
            holder.ll_container.setBackgroundColor(Color.parseColor(backColor2));
            holder.tv_title.setTextColor(Color.parseColor(txtcolor));
            holder.tv_contents.setTextColor(Color.parseColor(txtcolor));
            holder.tv_date.setTextColor(Color.parseColor(txtcolor));

            if(isdate)
            {
                holder.tv_date.setVisibility(View.VISIBLE);
            }else
            {
                holder.tv_date.setVisibility(View.GONE);
            }

            if(ismemo)
            {
                holder.tv_contents.setVisibility(View.VISIBLE);
            }else
            {
                holder.tv_contents.setVisibility(View.GONE);
            }

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Listitem_Memo item = arrayList.get(position);
        if (item.getSecureType().equals("1")) {
            holder.tv_title.setText(item.getRTitle());
            holder.tv_contents.setText(item.getRContent());
        } else {
            holder.tv_title.setText(item.getFTitle());
            holder.tv_contents.setText(item.getFContent());
        }
        holder.tv_date.setText(item.getDate());

        return convertView;
    }

    static class ViewHolder {
        LinearLayout ll_container;
        TextView tv_title;
        TextView tv_contents;
        TextView tv_date;
    }
}
