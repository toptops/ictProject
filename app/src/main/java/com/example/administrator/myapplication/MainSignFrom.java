package com.example.administrator.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainSignFrom extends AppCompatActivity {

    String idCheck2="",passwordsave,passwordsave2;
    Button idCheck,bt6;
    EditText et3,et4,et5,et6,et7,et8,et10,et11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainsignform);

        et3 = (EditText)findViewById(R.id.editText3);
        idCheck = (Button)findViewById(R.id.button5);
        idCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String URL = "http://203.234.28.137:8080/sdasd/ex3.jsp";
                            // String simplData = "?id=1&password=1";
                            String simplData = "?id="+et3.getText();
                            HttpPost post = new HttpPost(URL+simplData);
                            HttpClient client = new DefaultHttpClient();
                            client.execute(post);

                            HttpResponse respons = client.execute(post);
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(respons.getEntity().getContent(), "EUC-KR"));
                            String line = null;
                            String page = "";
                            while ((line = bufferedReader.readLine()) != null) {
                                page += line;
                            }

                            JSONObject json = new JSONObject(page);
                            JSONArray jArr = json.getJSONArray("Check");

                            json = jArr.getJSONObject(0);
                            idCheck2 = json.getString("Check");

                            if (idCheck2.equals("true")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"아이디가 중복되었습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if(idCheck2.equals("false")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"아이디를 사용하실수 있습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });

        et5 = (EditText)findViewById(R.id.editText5);
        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordsave=charSequence.toString();
                if(passwordsave.equals(passwordsave2)==false){
                    et5.setBackgroundColor(Color.WHITE);
                    et6.setBackgroundColor(Color.RED);
                }else{
                    et5.setBackgroundColor(Color.GREEN);
                    et6.setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et6 = (EditText)findViewById(R.id.editText6);
        et6.addTextChangedListener(new TextWatcher() {
            String s;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordsave2=charSequence.toString();
                if(passwordsave.equals(passwordsave2)==false){
                    et5.setBackgroundColor(Color.WHITE);
                    et6.setBackgroundColor(Color.RED);
                }else{
                    et5.setBackgroundColor(Color.GREEN);
                    et6.setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et8 = (EditText)findViewById(R.id.editText8);
        et4 = (EditText)findViewById(R.id.editText4);
        et7 = (EditText)findViewById(R.id.editText7);
        et10 = (EditText)findViewById(R.id.editText10);
        et11 = (EditText)findViewById(R.id.textView8);

        bt6 = (Button)findViewById(R.id.button6);
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String URL = "http://203.234.28.137:8080/sdasd/ex2.jsp";
                            // String simplData = "?id=1&password=1";
                            String simplData = "?id="+et3.getText()+"&password="+et5.getText()+"&name="+et11.getText()+"&birth="+et8.getText()+"&phone="+et4.getText()+"&email="+et7.getText()+"&address="+et10.getText();
                            HttpPost post = new HttpPost(URL+simplData);
                            HttpClient client = new DefaultHttpClient();
                            client.execute(post);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }

        });

    }
}
