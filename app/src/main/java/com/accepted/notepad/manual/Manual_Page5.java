package com.accepted.notepad.manual;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accepted.notepad.R;

import androidx.fragment.app.Fragment;

public class Manual_Page5 extends Fragment {

    Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext=getActivity();
        View view = inflater.inflate(R.layout.manual_page5, container, false);


        return view;
    }
}
