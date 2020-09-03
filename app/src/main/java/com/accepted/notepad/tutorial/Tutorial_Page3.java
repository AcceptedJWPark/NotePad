package com.accepted.notepad.tutorial;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.accepted.notepad.R;

import androidx.fragment.app.Fragment;

public class Tutorial_Page3 extends Fragment {


    Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext=getActivity();

        View view = inflater.inflate(R.layout.tutorial_page3, container, false);

        ImageView iv_addmemo = (ImageView)view.findViewById(R.id.iv_addmemo_tutorial);
        iv_addmemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tutorial_MainActivity mainActivity = (Tutorial_MainActivity) getActivity();
                mainActivity.nextPage(2);
            }
        });


        return view;
    }

}
