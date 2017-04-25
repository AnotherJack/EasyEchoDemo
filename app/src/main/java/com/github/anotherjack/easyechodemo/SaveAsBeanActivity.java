package com.github.anotherjack.easyechodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.anotherjack.easyechodemo.bean.Student;
import com.github.anotherjack.easyechodemo.easyEcho.EasyEcho;
import com.google.gson.Gson;


public class SaveAsBeanActivity extends AppCompatActivity {
    private Button save;
    private TextView result;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_as_bean);
        save = (Button) findViewById(R.id.save);
        result = (TextView) findViewById(R.id.result);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = EasyEcho.saveAsBean(Student.class, SaveAsBeanActivity.this, new EasyEcho.IdStrConverterForBean() {
                    @Override
                    public String convert(String fieldName, Class<?> clazz) {
                        if(clazz.equals(Student.GradesBean.class)){
                            return "grades_"+fieldName;
                        }else {
                            return fieldName;
                        }
                    }
                });
                result.setText(gson.toJson(student));
            }
        });
    }

}
