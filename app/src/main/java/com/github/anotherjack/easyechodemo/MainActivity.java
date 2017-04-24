package com.github.anotherjack.easyechodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()){
            case R.id.btn1:
                i = new Intent(MainActivity.this,EchoMapActivity.class);
                break;
            case R.id.btn2:
                i = new Intent(MainActivity.this,EchoBeanActivity.class);
                break;
            case R.id.btn3:
                i = new Intent(MainActivity.this,SaveAsMapActivity.class);
                break;
            case R.id.btn4:
                i = new Intent(MainActivity.this,SaveAsBeanActivity.class);
                break;
        }

        if(i!=null){
            startActivity(i);
        }
    }
}
