package com.github.anotherjack.easyechodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.anotherjack.easyechodemo.bean.Student;
import com.github.anotherjack.easyechodemo.easyEcho.EasyEcho;
import com.google.gson.Gson;

import java.lang.reflect.Field;

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

    private <T> T saveAsBean(Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //1.获取属性
            String key = field.getName();

            int id = 0;
            //2.通过反射获取到控件id
            try {
                Field idField = R.id.class.getDeclaredField(key);
                id = idField.getInt(R.id.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //3.拿到控件，获取的text就是对应bean的值（value）
            TextView tv = (TextView) findViewById(id);
            String value;
            if (tv != null) {
                value = tv.getText().toString();
            } else {
                value = "";
            }
            Class<?> typeClass = field.getType();
            Object valueObj = value;
            if (typeClass.equals(byte.class) || typeClass.equals(Byte.class)) {
                valueObj = Byte.parseByte(value);
            }
            if (typeClass.equals(short.class) || typeClass.equals(Short.class)) {
                valueObj = Short.parseShort(value);
            }
            if (typeClass.equals(int.class) || typeClass.equals(Integer.class)) {
                valueObj = Integer.parseInt(value);
            }
            if (typeClass.equals(long.class) || typeClass.equals(Long.class)) {
                valueObj = Long.parseLong(value);
            }
            if (typeClass.equals(float.class) || typeClass.equals(Float.class)) {
                valueObj = Float.parseFloat(value);
            }
            if (typeClass.equals(double.class) || typeClass.equals(Double.class)) {
                valueObj = Double.parseDouble(value);
            }
            if (typeClass.equals(boolean.class) || typeClass.equals(Boolean.class)) {
                valueObj = Boolean.parseBoolean(value);
            }


            //4.给result赋值
            try {
                field.set(result, valueObj);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return result;
    }
}
