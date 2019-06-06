package com.example.administrator.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by top on 2016-12-07.
 */

public class car_edit extends AppCompatActivity {

    TableLayout carName,carNum;
    TextView textView,textView2;
    String firstname,firstnum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_setting_cars_add);

        carName = (TableLayout) findViewById(R.id.TableLayout_Setting_Cars_Add_Tab1);
        carNum = (TableLayout) findViewById(R.id.TableLayout_Setting_Cars_Add_Tab2);

        carName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),reg_Name.class);
                startActivityForResult(intent,0);
            }
        });
        carNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),reg_Num.class);
                startActivityForResult(intent,0);
            }
        });
        Intent intent =getIntent();

        textView = (TextView)findViewById(R.id.TextView_Setting_Cars_Add1);
        textView2 = (TextView)findViewById(R.id.TextView_Setting_Cars_Add2);

        firstname=intent.getStringExtra("name");
        firstnum=intent.getStringExtra("num");

        textView.setText(intent.getStringExtra("name"));
        textView2.setText(intent.getStringExtra("num"));

        Button button = (Button)findViewById(R.id.Button_Setting_Cars_Add_Delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                            String id = setting.getString("id","");
                            String URL = "http://203.234.28.137:8080/sdasd/ex7.jsp";
                            String simplData = "?id="+id+"&firstName="+firstname+"&firstNum="+firstnum;
                            HttpPost post = new HttpPost(URL+simplData);
                            HttpClient client = new DefaultHttpClient();
                            client.execute(post);

                            Intent intent = new Intent(getApplicationContext(),carRegister.class);
                            startActivity(intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        Button button2 = (Button)findViewById(R.id.Button_Setting_Cars_Add_OK);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                            String id = setting.getString("id","");
                            String URL = "http://203.234.28.137:8080/sdasd/ex8.jsp";
                            String simplData = "?id="+id+"&firstName="+firstname+"&firstNum="+firstnum+"&editName="+textView.getText()+"&editNum="+textView2.getText();
                            HttpPost post = new HttpPost(URL+simplData);
                            HttpClient client = new DefaultHttpClient();
                            client.execute(post);

                            Intent intent = new Intent(getApplicationContext(),carRegister.class);
                            startActivity(intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0){
            textView.setText(data.getStringExtra("Name"));
        }
        else if(resultCode==1){
            textView2.setText(data.getStringExtra("Num"));
        }else if(resultCode==3){

        }
    }
}
