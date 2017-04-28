package com.loadtracker.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableRow;

import com.loadtracker.R;
import com.loadtracker.activity.CustomAdapter;


public class OneFragment extends Fragment {
    ListView lv;
    String[] values = new String[]{"India", "java", "c++", "Ad.Java", "Linux", "Unix","aaa","bbb"};
    String[] values1 = new String[]{"I", "j", "c", "A", "L", "U","a","b"};
    String[] values2 = new String[]{"India", "java", "c++", "Ad.Java", "Linux", "Unix","aaa","bbb"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        perform(v);

        return v;
    }

    public void perform(View v) {
        lv = (ListView) v.findViewById(R.id.list);
        lv.setAdapter(new CustomAdapter(getActivity(), values,values1,values2));
    }
}
