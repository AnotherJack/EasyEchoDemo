package com.github.anotherjack.easyechodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.anotherjack.easyechodemo.easyEcho.EasyEcho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaveAsMapActivity extends AppCompatActivity {
    private Button save;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_as_map);



        save = (Button) findViewById(R.id.save);
        result = (TextView) findViewById(R.id.result);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个模板map，也就是默认map，可以存储一个json字符串，每次用到的时候解析为map来用
                Map defaultMap = new HashMap();
                defaultMap.put("name",null);
                defaultMap.put("age",0);
                defaultMap.put("sex","");
                defaultMap.put("home","");
                defaultMap.put("hobby","");
                defaultMap.put("extra","其他");

                Map gradesMap = new HashMap();
                gradesMap.put("chinese",0);
                gradesMap.put("math",60);
                gradesMap.put("english",0);
                gradesMap.put("level","及格");

                defaultMap.put("grades",gradesMap);

                ArrayList<Map> friendsList = new ArrayList<>();
                Map friendMap1 = new HashMap();
                friendMap1.put("friendName","");
                Map friendMap2 = new HashMap();
                friendMap2.put("friendName","");
                Map friendMap3 = new HashMap();
                friendMap3.put("friendName","");
                friendsList.add(friendMap1);
                friendsList.add(friendMap2);
                friendsList.add(friendMap3);

                defaultMap.put("friends",friendsList);

                //注意，调用EasyEcho.saveAsMap之后，传进去的defaultMap也会改变，这是Java地址传递的问题，考虑过深度复制一份map，但是查了资料后就HashMap好弄一点，所以目前没弄
                Map resultMap = EasyEcho.saveAsMap(defaultMap,SaveAsMapActivity.this);
                result.setText("resultMap.toString() = "+resultMap.toString());
            }
        });
    }
}
