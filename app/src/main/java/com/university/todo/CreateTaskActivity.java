package com.university.todo;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {


    TextView time;
    EditText title;
    EditText desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);


        config();

    }

    private void config() {
        title = findViewById(R.id.createTaskTitle);
        desc = findViewById(R.id.createTaskDesc);
        time = findViewById(R.id.createTaskTime);
        findViewById(R.id.createTaskBtn).setOnClickListener(this);
        time.setOnClickListener(this);
        Spinner spinner = findViewById(R.id.createTaskSpinner);
        spinner.setAdapter(new TaskTypeAdapter());



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.createTaskTime:
                showTimerPicker();
                break;
            case R.id.createTaskBtn:
                createNewTaskInDb();
                break;
                default:
                    //

        }
    }

    private void createNewTaskInDb() {
        TaskModel model = new TaskModel();
        model.setDescription(desc.getText().toString());
        model.setTitle(title.getText().toString());
        model.setTime(time.getText().toString());

        Db.getInstance(this).tasksDao().insert(model);
    }

    private void showTimerPicker() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        new TimePickerDialog(this, this, hour, minute,DateFormat.is24HourFormat(this))
        .show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
        time.setText(hourOfDay + ":" + minutes);
    }
}
