package com.university.todo;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {


    TextView time;
    EditText title;
    EditText desc;
    SwitchCompat done;
    Spinner spinner;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);


        if (getIdFromBundle().isEmpty())
            finish();

        config();

    }


    private String getIdFromBundle() {
        return getIntent().getIntExtra(TaskListActivity.BUNDLE_ID, 0) + "";
    }


    private void config() {
        title = findViewById(R.id.editTaskTitle);
        desc = findViewById(R.id.editTaskDesc);
        time = findViewById(R.id.editTaskTime);
        done = findViewById(R.id.editTaskDone);
        img = findViewById(R.id.editTaskImg);
        findViewById(R.id.editTaskBackBtn).setOnClickListener(this);
        findViewById(R.id.editTaskBtn).setOnClickListener(this);
        time.setOnClickListener(this);
        spinner = findViewById(R.id.editTaskSpinner);
        spinner.setAdapter(new TaskTypeAdapter());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case TaskTypeAdapter.IMPORTANT_CODE:
                        img.setImageResource(R.drawable.ic_important);
                        break;
                    case TaskTypeAdapter.MEDIUM_CODE:
                        img.setImageResource(R.drawable.ic_medium);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //get task
        TaskModel task = getTask(getIdFromBundle());
        setData(task);

    }


    private void setData(TaskModel model) {
        title.setText(model.getTitle());
        desc.setText(model.getDescription());
        time.setText(model.getTime());
        spinner.setSelection(model.getPriority());
        done.setChecked(model.isDone());
        if (model.isDone())
            img.setImageResource(R.drawable.done);
    }


    private TaskModel getTask(String id) {
        return Db.getInstance(this)
                .tasksDao()
                .loadTask(id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editTaskBackBtn:
                onBackPressed();
                break;
            case R.id.editTaskTime:
                showTimerPicker();
                break;
            case R.id.editTaskBtn:
                updateNewTaskInDb();
                break;
            default:
                //

        }
    }

    private void updateNewTaskInDb() {
        TaskModel model = getTask(getIdFromBundle());
        model.setDescription(desc.getText().toString());
        model.setTitle(title.getText().toString());
        model.setTime(time.getText().toString());
        model.setDone(done.isChecked());
        model.setPriority((int) spinner.getSelectedItemId());

        Db.getInstance(this).tasksDao().update(model);

        setResult(RESULT_OK, getIntent());
        finish();
    }

    private void showTimerPicker() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        new TimePickerDialog(this, this, hour, minute, DateFormat.is24HourFormat(this))
                .show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
        time.setText(hourOfDay + ":" + minutes);
    }
}
