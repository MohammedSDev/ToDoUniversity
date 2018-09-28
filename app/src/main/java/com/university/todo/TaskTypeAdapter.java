package com.university.todo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

/**
 * Created by Gg on 9/28/2018.
 */

public class TaskTypeAdapter extends BaseAdapter {


    private String TAG = "adapter";


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getDropDownView: ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_type_item,parent,false);

        return view;
//                super.getDropDownView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d(TAG, "getView: ");
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_type_item_drop,viewGroup,false);
        return view;
    }
}
