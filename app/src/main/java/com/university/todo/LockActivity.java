package com.university.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class LockActivity extends AppCompatActivity implements View.OnClickListener {


    EditText passwordET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);



        config();

    }

    private void config() {

        passwordET = findViewById(R.id.password);
        //set listeners
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btnC).setOnClickListener(this);
        findViewById(R.id.btnEnter).setOnClickListener(this);
        findViewById(R.id.btnClose).setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1: passwordET.append("1");break;
            case R.id.btn2: passwordET.append("2");break;
            case R.id.btn3: passwordET.append("3");break;
            case R.id.btn4: passwordET.append("4");break;
            case R.id.btn5: passwordET.append("5");break;
            case R.id.btn6: passwordET.append("6");break;
            case R.id.btn7: passwordET.append("7");break;
            case R.id.btn8: passwordET.append("8");break;
            case R.id.btn9: passwordET.append("9");break;
            case R.id.btn0: passwordET.append("0");break;
            case R.id.btnC: passwordET.setText("");break;
            case R.id.btnEnter:{
                //check and open app landing screen

                Intent intent = new Intent(this, TaskListActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btnClose:finish();break;
        }
    }
}
