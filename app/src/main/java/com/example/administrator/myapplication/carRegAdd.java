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

public class carRegAdd extends AppCompatActivity {
    TextView textView,textView2;
    String Name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_add_cars);

        final TableLayout regName=(TableLayout)findViewById(R.id.TableLayout_Setting_Cars_Add_Tab1);
        TableLayout regNum=(TableLayout)findViewById(R.id.TableLayout_Setting_Cars_Add_Tab2);

        regName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),reg_Name.class);
                startActivityForResult(intent,0);
            }
        });
        regNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),reg_Num.class);
                startActivityForResult(intent,0);
            }
        });
        textView = (TextView)findViewById(R.id.TextView_Setting_Cars_Add1);
        textView2 = (TextView)findViewById(R.id.TextView_Setting_Cars_Add2);

        Button button = (Button)findViewById(R.id.Button_Setting_Cars_Add_OK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                            String id = setting.getString("id","");
                            String URL = "http://203.234.28.137:8080/sdasd/ex5.jsp";
                            String simplData = "?id="+id+"&carName="+textView.getText()+"&carNum="+textView2.getText();
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
