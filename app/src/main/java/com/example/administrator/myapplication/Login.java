package com.example.administrator.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Login extends AppCompatActivity {

    EditText et1,et2;
    Button btn;
    String idcheck=null,pwdcheck=null;
    CheckBox Auto_LogIn;
    SharedPreferences setting,setting2;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        setting = getSharedPreferences("setting",0);

        et1=(EditText)findViewById(R.id.editText);
        et2=(EditText)findViewById(R.id.editText2);
        btn=(Button)findViewById(R.id.button2);

        Auto_LogIn = (CheckBox) findViewById(R.id.logcheck);



        setting2 = getSharedPreferences("setting", 0);
        editor= setting2.edit();

        if(setting.getBoolean("Auto_Login_enabled", false)){
           et1.setText(setting.getString("ID", ""));
            et2.setText(setting.getString("PW", ""));
            Auto_LogIn.setChecked(true);
        }

        Auto_LogIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    String ID = et1.getText().toString();
                    String PW = et2.getText().toString();

                    editor.putString("ID", ID);
                    editor.putString("PW", PW);
                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.commit();
                }else{
                    editor.clear();
                    editor.commit();
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String URL = "http://203.234.28.137:8080/sdasd/ex.jsp";
                    // String simplData = "?id=1&password=1";
                    String simplData = "?id="+et1.getText()+"&password="+et2.getText();
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
                    idcheck = json.getString("idCheck");
                    pwdcheck = json.getString("pwdCheck");


                    if (idcheck.equals("true")) {
                        if (pwdcheck.equals("true")) {

                            long now = System.currentTimeMillis();
                            Date date = new Date(now);

                            final SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");

                            String strNow = sdfNow.format(date);

                            Calendar cal2 = Calendar.getInstance();



                            //cal2.get(Calendar.YEAR)+"/"+(cal2.get(Calendar.MONTH)+1)+"/"+cal2.getMaximum(Calendar.DAY_OF_MONTH)
                            SharedPreferences.Editor editor;
                            editor=setting.edit();
                            editor.putString("id",et1.getText().toString());
                            editor.remove("firstTime");
                            editor.remove("lastTime");
                            editor.putString("firstTime",strNow);
                            editor.putString("lastTime",cal2.get(Calendar.YEAR)+"/"+(cal2.get(Calendar.MONTH)+1)+"/"+cal2.getMaximum(Calendar.DAY_OF_MONTH));
                            editor.putString("loginid",et1.getText().toString());
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), main.class);

                            startActivity(intent);
                            finish();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "id가 틀렸습니다", Toast.LENGTH_LONG).show();
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
    }


   public void signupClick(View V){
       Intent intent = new Intent(getApplicationContext(), Signup.class);
       startActivity(intent);
   }
    public void forgotClick(View V){
        Intent intent = new Intent(getApplicationContext(), Forgotidpw.class);
        startActivity(intent);
    }
}
