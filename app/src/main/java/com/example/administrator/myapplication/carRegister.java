package com.example.administrator.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by top on 2016-12-07.
 */

public class carRegister extends AppCompatActivity {

    private ArrayList<String> name, num;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_setting_cars);

        name = new ArrayList<String>();
        num = new ArrayList<String>();




       // adapter.addItem("1","2");
       // adapter.addItem("2","3");

        Button regButton = (Button)findViewById(R.id.Button_Setting_Cars_Add);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),carRegAdd.class);
                startActivity(intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                    String id = setting.getString("id","");
                    String URL = "http://203.234.28.137:8080/sdasd/ex6.jsp";
                    String simplData = "?id="+id;
                    HttpPost post = new HttpPost(URL+simplData);
                    HttpClient client = new DefaultHttpClient();

                    HttpResponse respons = client.execute(post);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(respons.getEntity().getContent(), "EUC-KR"));
                    String line = null;
                    String page = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        page += line;
                    }
                    JSONObject json = new JSONObject(page);
                    JSONArray jArr = json.getJSONArray("Check");


                    final caraddListView adapter;

                    adapter = new caraddListView();

                    listView = (ListView)findViewById(R.id.ListView_Setting_Cars);

                    for(int i=0;i<jArr.length();i++){
                        json = jArr.getJSONObject(i);
                        adapter.addItem(json.getString("carName"),json.getString("carNum"));
                    }
                        adapter.notifyDataSetChanged();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(adapter);
                            TextView textView = (TextView)findViewById(R.id.TextView_Setting_Cars_Count);
                            textView.setText("등록된 챠량: "+adapter.getCount()+"대");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }
}
