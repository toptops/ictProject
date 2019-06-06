package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

/**
 * Created by top on 2016-12-08.
 */

public class Forgotidpw extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5;
    String realid,realpwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotidpw);

        et1 =(EditText)findViewById(R.id.idet1);
        et2 =(EditText)findViewById(R.id.idet2);

        et3 =(EditText)findViewById(R.id.editText11);
        et4 =(EditText)findViewById(R.id.editText12);
        et5 =(EditText)findViewById(R.id.editText9);


        Button id = (Button)findViewById(R.id.id);
        Button password = (Button)findViewById(R.id.password);

        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String URL = "http://203.234.28.137:8080/sdasd/ex21.jsp";
                            HttpPost post = new HttpPost(URL);
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


                            for(int i=0;i<jArr.length();i++) {
                                json = jArr.getJSONObject(i);
                                if(et1.getText().toString().equals(json.getString("name")) && et2.getText().toString().equals(json.getString("birth"))){
                                    realid=json.getString("id");
                                }
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder al = new AlertDialog.Builder(Forgotidpw.this);
                                    al.setTitle("당신의 아이디")
                                            .setMessage("아이디는 = "+realid)
                                            .setPositiveButton("종료",null).create().show();
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
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String URL = "http://203.234.28.137:8080/sdasd/ex21.jsp";
                            HttpPost post = new HttpPost(URL);
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


                            for(int i=0;i<jArr.length();i++) {
                                json = jArr.getJSONObject(i);
                                if(et3.getText().toString().equals(json.getString("id")) && et4.getText().toString().equals(json.getString("name")) && et5.getText().toString().equals(json.getString("birth")) ){
                                    realpwd=json.getString("pwd");
                                }

                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder al2 = new AlertDialog.Builder(Forgotidpw.this);
                                    al2.setTitle("당신의 비밀번호")
                                            .setMessage("비밀번호는 : "+realpwd)
                                            .setPositiveButton("종료",null).create().show();
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
        });
    }

}
