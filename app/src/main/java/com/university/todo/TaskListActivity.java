package com.university.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
        implements TaskListAdapter.ItemClickCallback<TaskModel>, SwipeRefreshLayout.OnRefreshListener, TaskListAdapter.ItemClickLongCallback<TaskModel> {

    TaskListAdapter adapter;
    RecyclerView recycle;
    TextView emptyView;
    SwipeRefreshLayout refresh;

    public static final String BUNDLE_ID = "bun_id";
    private final int CREATE_TASK_CODE = 8;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        recycle = findViewById(R.id.recycle);
        emptyView = findViewById(R.id.emptyView);
        refresh = findViewById(R.id.refresh);


        config();


    }

    /**
     * prepare things...
     * */
    private void config() {

        refresh.setOnRefreshListener(this);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(TaskListActivity.this,CreateTaskActivity.class),CREATE_TASK_CODE);

            }
        });


        //config recycle view
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setHasFixedSize(true);

        //config with adapter
        adapter = new TaskListAdapter();
        //TODO remove below comment to enable item click callback
        adapter.mCallback = this;
        adapter.mLongCallback = this;
        recycle.setAdapter(adapter);
        callTaskesFromDb();


        //don't forgot to use Empty view

        if (adapter.mList.isEmpty()){
            emptyView.setText("No tasks available");
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }

    private void callTaskesFromDb() {
        //update adapter with your list
        adapter.mList = getTasks("");
        adapter.notifyDataSetChanged();
        refresh.setRefreshing(false);
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
    public void onItemClick(View view, final TaskModel model, int position, String data) {
       if (model.isDone()) return;
       //convert to done
        new AlertDialog.Builder(this,R.style.DialogTheme)
                .setMessage("convert to done task?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.setDone(true);
                        updateTask(model);
                    }
                })
                .setNegativeButton("cancel",null)
                .show();
    }

    private void updateTask(TaskModel model) {
        Db.getInstance(TaskListActivity.this)
                .tasksDao()
                .update(model);
        startRefresh();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callTaskesFromDb();
            }
        }, 1000);
    }


    private void startRefresh(){
        if (refresh != null && !refresh.isRefreshing())
            refresh.setRefreshing(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            switch (requestCode){
                case CREATE_TASK_CODE:
                    startRefresh();
                    callTaskesFromDb();
                    break;
                    default:
                        //nothing
            }
        }
    }

    @Override
    public void onItemLongClick(View view, TaskModel model, int position, String data) {
        Intent intent = new Intent(this,EditTaskActivity.class);
        intent.putExtra(BUNDLE_ID,model.getId());
        startActivity(intent);
    }
}
