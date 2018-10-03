package com.university.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {


    TextView time;
    EditText title;
    EditText desc;
    Spinner spinner;
    ImageView img;


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
        img = findViewById(R.id.createTaskImg);
        findViewById(R.id.createTaskBackBtn).setOnClickListener(this);
        findViewById(R.id.createTaskBtn).setOnClickListener(this);
        time.setOnClickListener(this);
        spinner = findViewById(R.id.createTaskSpinner);
        spinner.setAdapter(new TaskTypeAdapter());
        img.setImageResource(R.drawable.ic_important);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case TaskTypeAdapter.IMPORTANT_CODE:
                        img.setImageResource(R.drawable.ic_important);break;
                    case TaskTypeAdapter.MEDIUM_CODE:
                        img.setImageResource(R.drawable.ic_medium);break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.createTaskBackBtn:
                onBackPressed();
                break;
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
        model.setPriority((int)spinner.getSelectedItemId());

        Long insert = Db.getInstance(this).tasksDao().insert(model);

        getIntent().putExtra(TaskListActivity.BUNDLE_ID,insert.intValue());
        Log.d("insert", "createNewTaskInDb: id=" + insert);

        setAlarm(model);


        setResult(RESULT_OK,getIntent());
        finish();
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


    private void setAlarm(TaskModel model){

        AlarmManager alarmM = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String[] time = model.getTime().split(":");
        Log.d("alarm", "setAlarm: " + time);
        if (time.length >= 1)
            calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(time[0]));
        if (time.length >= 2)
            calendar.set(Calendar.MINUTE,Integer.valueOf(time[1]));

        //create intent
        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra(MyReceiver.BUNDLE_TASK_MODEL,model);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,
                intent
                ,0);

        if (model.getPriority() == TaskTypeAdapter.MEDIUM_CODE)
            alarmM.set(AlarmManager.RTC,
                    calendar.getTimeInMillis(),
                    pendingIntent);
        else
            alarmM.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    10 * 60 * 1000,
                    pendingIntent);
    }
}
