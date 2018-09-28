package com.university.todo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Gg on 9/23/2018.
 */

public class TaskListActivity extends AppCompatActivity
        implements TaskListAdapter.ItemClickCallback<TaskModel> {

    TaskListAdapter adapter;
    RecyclerView recycle;
    TextView emptyView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        recycle = findViewById(R.id.recycle);
        emptyView = findViewById(R.id.emptyView);


        config();


    }

    /**
     * prepare things...
     * */
    private void config() {

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskListActivity.this,CreateTaskActivity.class));

            }
        });


        //config recycle view
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setHasFixedSize(true);

        //config with adapter
        adapter = new TaskListAdapter();
        //TODO remove below comment to enable item click callback
//        adapter?.mCallback = this
        recycle.setAdapter(adapter);


        //update adapter with your list
        adapter.mList = getTasks("");
        adapter.notifyDataSetChanged();

        //don't forgot to use Empty view

        if (adapter.mList.isEmpty()){
            emptyView.setText("No tasks available");
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }

    private List<TaskModel> getTasks(String date) {
        return Db.getInstance(this)
                .tasksDao()
                .loadAllTasksForToday();
    }


    /**
     * handle on any item of list clicked
     * */
    @Override
    public void onItemClick(View view, TaskModel model, int position, String data) {

    }
}
