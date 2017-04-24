package com.github.anotherjack.easyechodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import com.github.anotherjack.easyechodemo.easyEcho.EasyEcho;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EchoMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echo_map);
        String data_str = getString(R.string.data_str);


        Map studentMap = new HashMap();
        studentMap.put("name","小明");
        studentMap.put("age",15);
        studentMap.put("sex","男");
        studentMap.put("home","北京");
        studentMap.put("hobby","看电影");
        studentMap.put("extra","其他");

        Map gradesMap = new HashMap();
        gradesMap.put("chinese",95);
        gradesMap.put("math",100);
        gradesMap.put("english",90);
        gradesMap.put("level","优秀");

        studentMap.put("grades",gradesMap);

        EasyEcho.echoMap(studentMap, this);
    }




    private void echoMap(Map map){
        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()){
            Map.Entry entry = (Map.Entry) entries.next();
            //获取到键
            String key = entry.getKey().toString();
            //获取到值
            String value = entry.getValue().toString();


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
