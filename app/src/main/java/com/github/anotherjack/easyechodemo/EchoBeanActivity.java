package com.github.anotherjack.easyechodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.anotherjack.easyechodemo.bean.Student;
import com.github.anotherjack.easyechodemo.easyEcho.EasyEcho;
import com.google.gson.Gson;


public class EchoBeanActivity extends AppCompatActivity {
    private String data_str;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echo_bean);
        data_str = getString(R.string.data_str);
        Student student = gson.fromJson(data_str,Student.class);

        EasyEcho.echoBean(student, this, new EasyEcho.IdStrConverterForBean() {
            @Override
            public String convert(String fieldName, Class<?> clazz) {
                if(clazz.equals(Student.GradesBean.class)){
                    return "grades_"+fieldName;
                }else {
                    return fieldName;
                }
            }
        });
    }

}
