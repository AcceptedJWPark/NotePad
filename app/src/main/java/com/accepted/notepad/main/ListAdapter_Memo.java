package com.accepted.notepad.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.ListPreference;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accepted.notepad.LongPressChecker;
import com.accepted.notepad.R;
import com.accepted.notepad.papermemo.Papermemo_MainActivity;
import com.accepted.notepad.password.Password_MainActivity;
import com.accepted.notepad.tutorial.Tutorial_MainActivity;

import java.util.ArrayList;


public class ListAdapter_Memo extends BaseAdapter {


    private Context mContext;
    private ArrayList<Listitem_Memo> arrayList;
    private long btnPressTime = 0;
    String backColor1;
    String backColor2;
    String txtcolor;
    String btncolor;
    boolean ismemo;
    boolean isdate;

    public ListAdapter_Memo(Context mContext, ArrayList<Listitem_Memo> arrayList, String backColor1, String backColor2, String txtcolor, String btncolor,boolean ismemo, boolean isdate) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.backColor1 = backColor1;
        this.backColor2 = backColor2;
        this.txtcolor = txtcolor;
        this.btncolor = btncolor;
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
            holder.iv_delete = convertView.findViewById(R.id.iv_delete);

            convertView.setBackgroundColor(Color.parseColor(backColor1));
            holder.ll_container.setBackgroundColor(Color.parseColor(backColor2));
            holder.tv_title.setTextColor(Color.parseColor(txtcolor));
            holder.tv_contents.setTextColor(Color.parseColor(txtcolor));
            holder.tv_date.setTextColor(Color.parseColor(txtcolor));
            holder.iv_delete.setColorFilter(Color.parseColor(btncolor));

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

        setClickTypeEvent(item, convertView);

        return convertView;
    }

    static class ViewHolder {
        LinearLayout ll_container;
        TextView tv_title;
        TextView tv_contents;
        TextView tv_date;
        ImageView iv_delete;
    }

    public void setClickTypeEvent(Listitem_Memo item, View convertView ) {
        String secureType = item.getSecureType();
        String clickType = item.getClickType();
        GestureDetector gd; GestureDetector.OnDoubleTapListener listener;

        gd = new GestureDetector(mContext, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                Log.d("press", "down");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.d("press", "press");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d("press", "up");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }

        });

        listener = new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d("press", "single");
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d("press", "double");
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        };

        LongPressChecker mLongPressChecker = new LongPressChecker(mContext, 3000);
        mLongPressChecker.setOnLongPressListener( new LongPressChecker.OnLongPressListener() {
            @Override
            public void onLongPressed() {
                Log.d("press", "longpress");
            }
        });

        gd.setOnDoubleTapListener(listener);
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mLongPressChecker.deliverMotionEvent( v, event );

                if(gd != null) {
                    gd.onTouchEvent(event);
                    return true;
                }

                return false;
            }
        });





//        if (secureType.equals("3")) {
//            switch (clickType) {
//                case "1":
//                    // 더블 클릭
//                    convertView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (System.currentTimeMillis() > btnPressTime + 1000) {
//                                btnPressTime = System.currentTimeMillis();
//                                return;
//                            }
//                            if (System.currentTimeMillis() <= btnPressTime + 1000) {
//                                Intent intent = new Intent(mContext, Password_MainActivity.class);
//                                intent.putExtra("isTutorial",true);
//                                mContext.startActivity(intent);
//                            }
//                        }
//                    });
//                    break;
//                case "2":
//                    // 롱 클릭 1초
//                    convertView.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            switch (event.getAction()) {
//                                case MotionEvent.ACTION_DOWN:
//                                    btnPressTime = (Long) System.currentTimeMillis();
//                                    return true;
//                                case MotionEvent.ACTION_UP:
//                                    if(((Long) System.currentTimeMillis() - btnPressTime) > 1000){
//                                        // 진짜 켜기
//                                        Intent intent = new Intent(mContext, Password_MainActivity.class);
//                                        intent.putExtra("MemoCode", item.getMemoCode());
//                                        intent.putExtra("Title", item.getFTitle());
//                                        intent.putExtra("Content", item.getFContent());
//                                        intent.putExtra("RTitle", item.getRTitle());
//                                        intent.putExtra("RContent", item.getRContent());
//                                        intent.putExtra("SecureType", Integer.parseInt(item.getSecureType()));
//                                        intent.putExtra("ClickType", Integer.parseInt(item.getClickType()));
//                                        intent.putExtra("isTutorial",true);
//                                        mContext.startActivity(intent);
//                                    } else {
//                                        // 가짜 켜기
//                                        Intent intent = new Intent(mContext, Papermemo_MainActivity.class);
//                                        intent.putExtra("MemoCode", item.getMemoCode());
//                                        intent.putExtra("Title", item.getFTitle());
//                                        intent.putExtra("Content", item.getFContent());
//                                        intent.putExtra("RTitle", item.getRTitle());
//                                        intent.putExtra("RContent", item.getRContent());
//                                        intent.putExtra("SecureType", Integer.parseInt(item.getSecureType()));
//                                        intent.putExtra("ClickType", Integer.parseInt(item.getClickType()));
//                                        intent.putExtra("isReal", 3);
//                                        mContext.startActivity(intent);
//                                    }
//                                    break;
//                            }
//                            return false;
//                        }
//                    });
//                    break;
//                case "3":
//                    // 롱 클릭 3초
//                    convertView.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            switch (event.getAction()) {
//                                case MotionEvent.ACTION_DOWN:
//                                    btnPressTime = (Long) System.currentTimeMillis();
//                                    return true;
//                                case MotionEvent.ACTION_UP:
//                                    if(((Long) System.currentTimeMillis() - btnPressTime) > 3000){
//                                        // 진짜 켜기
//                                        Intent intent = new Intent(mContext, Password_MainActivity.class);
//                                        intent.putExtra("MemoCode", item.getMemoCode());
//                                        intent.putExtra("Title", item.getFTitle());
//                                        intent.putExtra("Content", item.getFContent());
//                                        intent.putExtra("RTitle", item.getRTitle());
//                                        intent.putExtra("RContent", item.getRContent());
//                                        intent.putExtra("SecureType", Integer.parseInt(item.getSecureType()));
//                                        intent.putExtra("ClickType", Integer.parseInt(item.getClickType()));
//                                        intent.putExtra("isTutorial",true);
//                                        mContext.startActivity(intent);
//                                    } else {
//                                        // 가짜 켜기
//                                        Intent intent = new Intent(mContext, Papermemo_MainActivity.class);
//                                        intent.putExtra("MemoCode", item.getMemoCode());
//                                        intent.putExtra("Title", item.getFTitle());
//                                        intent.putExtra("Content", item.getFContent());
//                                        intent.putExtra("RTitle", item.getRTitle());
//                                        intent.putExtra("RContent", item.getRContent());
//                                        intent.putExtra("SecureType", Integer.parseInt(item.getSecureType()));
//                                        intent.putExtra("ClickType", Integer.parseInt(item.getClickType()));
//                                        intent.putExtra("isReal", 3);
//                                        mContext.startActivity(intent);
//                                    }
//                                    break;
//                            }
//                            return false;
//                        }
//                    });
//                    break;
//            }
//        } else {
//            // 보안이 없는 일반 메모
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, Papermemo_MainActivity.class);
//                    intent.putExtra("MemoCode", item.getMemoCode());
//                    intent.putExtra("Title", item.getFTitle());
//                    intent.putExtra("Content", item.getFContent());
//                    intent.putExtra("RTitle", item.getRTitle());
//                    intent.putExtra("RContent", item.getRContent());
//                    intent.putExtra("SecureType", Integer.parseInt(item.getSecureType()));
//                    intent.putExtra("ClickType", Integer.parseInt(item.getClickType()));
//                    mContext.startActivity(intent);
//                }
//            });
//        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    Intent intent = new Intent(mContext, Password_MainActivity.class);
                    intent.putExtra("isTutorial",true);
                    mContext.startActivity(intent);
                    break;
                case 0:
                    Toast.makeText(mContext, "해당 이벤트가 아닐때", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
}
