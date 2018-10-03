package com.university.todo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gg on 9/28/2018.
 */

public class TaskTypeAdapter extends BaseAdapter {


    public static final int IMPORTANT_CODE = 0;
    public static final int MEDIUM_CODE = 1;
    List<String> stringList = new ArrayList<>();
    private String TAG = "adapter";


    public TaskTypeAdapter() {
        stringList.add("Important"); //0
        stringList.add("medium"); //1

    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        Log.d(TAG, "getDropDownView: ");
        if (view == null)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_type_item_drop,parent,false);

        TextView tv = view.findViewById(R.id.item);
        tv.setText(stringList.get(position));
        return view;
//                super.getDropDownView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return stringList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d(TAG, "getView: ");
        if (view == null)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_type_item,viewGroup,false);
        TextView tv = view.findViewById(R.id.item);
        tv.setText(stringList.get(i));
        return view;
    }
}
