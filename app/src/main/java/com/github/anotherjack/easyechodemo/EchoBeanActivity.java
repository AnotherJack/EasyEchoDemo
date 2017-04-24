package com.github.anotherjack.easyechodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.anotherjack.easyechodemo.bean.Student;
import com.github.anotherjack.easyechodemo.easyEcho.EasyEcho;
import com.google.gson.Gson;

import java.lang.reflect.Field;

public class EchoBeanActivity extends AppCompatActivity {
    private String data_str;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echo_bean);
        data_str = getString(R.string.data_str);
        Student student = gson.fromJson(data_str,Student.class);

        EasyEcho.echoBean(student, this);
    }


    private void echoBean(Object obj){
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            //获取属性
            String key = field.getName();
            //获取值
            String value = null;
            try {
                value = field.get(obj).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


            int id = 0;
            //通过反射获取到控件id
            try {
                Field idField = R.id.class.getDeclaredField(key);
                id = idField.getInt(R.id.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //最后控件赋值，大功告成
            TextView tv = (TextView) findViewById(id);
            if(tv!=null){
                tv.setText(value);
            }

        }
    }
}
