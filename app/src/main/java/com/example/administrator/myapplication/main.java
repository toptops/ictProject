package com.example.administrator.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

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

public class main extends AppCompatActivity {
    String idCheck2="",passwordsave,passwordsave2;
    String k1,k2,k3,k4,k5,k6;
    private TextView carName, carNum;
    private Button button1,button2;
    private int check=0;
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private int hap=0,count=0,moneyCount=0;
    private SharedPreferences setting,setting2;
    private    SharedPreferences.Editor editor;
    private TextView txmoney,txcount,txefficiency;
    private int oilcount=0,money=1,litermoney=1,oildistance=1;
    LayoutInflater inflater;
    View dialogView;

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.white));
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView tv =(TextView)findViewById(R.id.ex);

        button1 = (Button)findViewById(R.id.Button_Main_Date1);
        button2 = (Button)findViewById(R.id.Button_Main_Date2);


        long now = System.currentTimeMillis();
        Date date = new Date(now);

       final SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");

        String strNow = sdfNow.format(date);

        Calendar cal2 = Calendar.getInstance();

        setting = getSharedPreferences("setting",0);
        setting2 = getSharedPreferences("setting",MODE_PRIVATE);



        editor=setting.edit();

        button1.setText(setting2.getString("firstTime",""));
        button2.setText(setting2.getString("lastTime",""));

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        caldroidFragment = new CaldroidFragment();

        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                if(check==1) {
                    String buf = sdfNow.format(date);
                    button1.setText(buf);
                    editor.remove("firstTime");
                    editor.putString("firstTime",buf);
                    editor.commit();
                    //editor.putString("lastTime",cal2.get(Calendar.YEAR)+"/"+(cal2.get(Calendar.MONTH)+1)+"/"+cal2.getMaximum(Calendar.DAY_OF_MONTH));
                }else if(check == 2){
                    String buf= sdfNow.format(date);
                    editor.remove("lastTime");
                    editor.putString("lastTime",buf);
                    editor.commit();
                    button2.setText(buf);
                }
                dialogCaldroidFragment.dismiss();
            }

            @Override
            public void onChangeMonth(int month, int year) {

            }




            @Override
            public void onLongClickDate(Date date, View view) {
                if(check==1) {
                    String buf = sdfNow.format(date);
                    button1.setText(buf);
                }else if(check == 2){
                    String buf= sdfNow.format(date);
                    button2.setText(buf);
                }
                dialogCaldroidFragment.dismiss();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                }
            }

        };

        Button showDialogButton = (Button) findViewById(R.id.Button_Main_Date1);

        final Bundle state = savedInstanceState;
        showDialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Setup caldroid to use as dialog
                dialogCaldroidFragment = new CaldroidFragment();
                dialogCaldroidFragment.setCaldroidListener(listener);

                // If activity is recovered from rotation
                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
                if (state != null) {
                    dialogCaldroidFragment.restoreDialogStatesFromKey(
                            getSupportFragmentManager(), state,
                            "DIALOG_CALDROID_SAVED_STATE", dialogTag);
                    Bundle args = dialogCaldroidFragment.getArguments();
                    if (args == null) {
                        args = new Bundle();
                        dialogCaldroidFragment.setArguments(args);
                    }
                } else {
                    // Setup arguments
                    Bundle bundle = new Bundle();
                    // Setup dialogTitle
                    dialogCaldroidFragment.setArguments(bundle);
                }

                dialogCaldroidFragment.show(getSupportFragmentManager(),
                        dialogTag);
                check=1;
            }
        });


        Button showDialogButton2 = (Button) findViewById(R.id.Button_Main_Date2);
        final Bundle state2 = savedInstanceState;
        showDialogButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Setup caldroid to use as dialog
                dialogCaldroidFragment = new CaldroidFragment();
                dialogCaldroidFragment.setCaldroidListener(listener);

                // If activity is recovered from rotation
                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
                if (state2 != null) {
                    dialogCaldroidFragment.restoreDialogStatesFromKey(
                            getSupportFragmentManager(), state2,
                            "DIALOG_CALDROID_SAVED_STATE", dialogTag);
                    Bundle args = dialogCaldroidFragment.getArguments();
                    if (args == null) {
                        args = new Bundle();
                        dialogCaldroidFragment.setArguments(args);
                    }
                } else {
                    // Setup arguments
                    Bundle bundle = new Bundle();
                    // Setup dialogTitle
                    dialogCaldroidFragment.setArguments(bundle);
                }

                dialogCaldroidFragment.show(getSupportFragmentManager(),
                        dialogTag);
                check=2;
            }
        });

        TableLayout register = (TableLayout)findViewById(R.id.TableLayout_Main_Car);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), carRegister.class);
                startActivity(intent);
            }
        });

        Intent intent =getIntent();

        carName = (TextView)findViewById(R.id.TextView_Main_CarName);
        carNum = (TextView)findViewById(R.id.TextView_Main_CarNumber);

        carName.setText(intent.getStringExtra("name"));
        carNum.setText(intent.getStringExtra("num"));

        editor.putString("saveName",intent.getStringExtra("name"));
        editor.putString("saveNum",intent.getStringExtra("num"));

        final TableLayout distance = (TableLayout)findViewById(R.id.TableLayout_Main_Tab1);
        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),popup_distance.class);
                intent.putExtra("name",carName.getText());
                intent.putExtra("num",carNum.getText());
                startActivity(intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                    String id = setting.getString("id","");

                    String URL = "http://203.234.28.137:8080/sdasd/ex12.jsp";
                    String simplData = "?id="+id+"&carName="+carName.getText()+"&carNum="+carNum.getText();
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

                    for(int i=0;i<jArr.length();i++){
                        json = jArr.getJSONObject(i);
                        hap+=Integer.parseInt(json.getString("distance"));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tx = (TextView)findViewById(R.id.TextView_Main_Count1);
                            tx.setText(hap+"km");
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                    String id = setting.getString("id","");
                    String URL = "http://203.234.28.137:8080/sdasd/ex14.jsp";
                    String simplData = "?id="+id+"&carName="+carName.getText()+"&carNum="+carNum.getText();
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
                    final JSONArray jArr = json.getJSONArray("Check");

                    for(int i=0;i<jArr.length();i++){
                        json = jArr.getJSONObject(i);
                        count++;
                       moneyCount+=Integer.parseInt(json.getString("money").substring(0,json.getString("money").length()-1));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tx = (TextView)findViewById(R.id.TextView_Main_Count3);
                            if(count==0) {
                                tx.setText(count  + "건");
                            }else if(count>0){
                                tx.setText((count) + "건");
                            }
                            TextView tx2 = (TextView)findViewById(R.id.TextView_Main_Money3);
                            tx2.setText(moneyCount+"원");
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                    String id = setting.getString("id","");
                    String URL = "http://203.234.28.137:8080/sdasd/ex18.jsp";
                    String simplData = "?id="+id+"&carName="+carName.getText()+"&carNum="+carNum.getText();
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


                    for(int i=0;i<jArr.length();i++) {
                        json = jArr.getJSONObject(i);
                        oilcount++;
                        money+=Integer.parseInt(json.getString("money").substring(0,json.getString("money").length()-1));
                        litermoney+=Integer.parseInt(json.getString("litermoney").substring(0,json.getString("litermoney").length()-1));
                        oildistance+=Integer.parseInt(json.getString("distance").substring(0,json.getString("distance").length()-2));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txcount =(TextView)findViewById(R.id.TextView_Main_Count2);
                            txefficiency=(TextView)findViewById(R.id.TextView_Main_Money2);

                            if(oilcount==0 ) {
                                txcount.setText("연비 : " + (oildistance / (money / litermoney) - 1) + "km");
                                txefficiency.setText((oilcount) + "건 / " + (money - 1) + "원");
                            }else if(oilcount>0){
                                txcount.setText("연비 : " + (oildistance / (money / litermoney) ) + "km");
                                txefficiency.setText((oilcount) + "건 / " + (money ) + "원");
                            }
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        TableLayout repairBtn = (TableLayout)findViewById(R.id.TableLayout_Main_Tab3);
        repairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Popup_repair.class);
                intent.putExtra("name",carName.getText());
                intent.putExtra("num",carNum.getText());
                startActivity(intent); ;
            }
        });
        TableLayout gas = (TableLayout)findViewById(R.id.TableLayout_Main_Tab2);
        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),popup_gas.class);
                intent.putExtra("name",carName.getText());
                intent.putExtra("num",carNum.getText());
                startActivity(intent); ;
            }
        });
        Button button = (Button)findViewById(R.id.Button_Main_Settings);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.remainsignform,null);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    SharedPreferences setting,setting2;
                    setting =getSharedPreferences("setting",MODE_PRIVATE);


                    String URL = "http://203.234.28.137:8080/sdasd/ex23.jsp";
                    // String simplData = "?id=1&password=1";
                    String simplData = "?id="+setting.getString("loginid","");
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

                    for(int i=0;i<jArr.length();i++){
                        json = jArr.getJSONObject(i);
                    }
                    //pw text6 =>비밀번호 확인 Text4 전화번호 Text7 이멜 Text8 주소
                    final EditText tv1,tv2,tv3,tv4,tv5;
                    TextView tv6,tv7,tv8;

                    //pw text6 =>비밀번호 확인 Text4 전화번호 Text7 이멜 Text8 주소
                    tv1 = (EditText)dialogView.findViewById(R.id.pw);
                    tv2 = (EditText)dialogView.findViewById(R.id.Text6);
                    tv3 = (EditText)dialogView.findViewById(R.id.Text4);
                    tv4 = (EditText)dialogView.findViewById(R.id.Text7);
                    tv5 = (EditText)dialogView.findViewById(R.id.Text10);
                    //아이디 이름 생년월일
                    tv6 = (TextView)dialogView.findViewById(R.id.ID_1);
                    tv7 = (TextView)dialogView.findViewById(R.id.Text8);
                    tv8 = (TextView)dialogView.findViewById(R.id.Etext8);

                    tv1.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            passwordsave=charSequence.toString();
                            if(passwordsave.equals(passwordsave2)==false){
                                tv1.setBackgroundColor(Color.WHITE);
                                tv2.setBackgroundColor(Color.RED);
                            }else{
                                tv1.setBackgroundColor(Color.GREEN);
                                tv2.setBackgroundColor(Color.GREEN);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    tv2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            passwordsave2=charSequence.toString();
                            if(passwordsave.equals(passwordsave2)==false){
                                tv1.setBackgroundColor(Color.WHITE);
                                tv2.setBackgroundColor(Color.RED);
                            }else{
                                tv1.setBackgroundColor(Color.GREEN);
                                tv2.setBackgroundColor(Color.GREEN);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });



                    tv1.setText(json.getString("password"));
                    tv3.setText(json.getString("phone"));
                    tv4.setText(json.getString("email"));
                    tv5.setText(json.getString("address"));

                    tv6.setText(json.getString("id"));
                    tv7.setText(json.getString("name"));
                    tv8.setText(json.getString("birth"));


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();



        //pw text6 =>비밀번호 확인 Text4 전화번호 Text7 이멜 Text8 주소
        Button button3 =(Button)findViewById(R.id.s);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder ;
                builder= new AlertDialog.Builder(main.this);


                builder.setTitle("My Information")
                        .setIcon(R.drawable.icon)
                        .setView(dialogView)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                                            String id = setting.getString("id","");
                                            final EditText tv1,tv2,tv3,tv4,tv5;
                                            TextView tv6,tv7,tv8;

                                            tv1 = (EditText)dialogView.findViewById(R.id.pw);
                                            tv2 = (EditText)dialogView.findViewById(R.id.Text6);
                                            tv3 = (EditText)dialogView.findViewById(R.id.Text4);
                                            tv4 = (EditText)dialogView.findViewById(R.id.Text7);
                                            tv5 = (EditText)dialogView.findViewById(R.id.Text10);
                                            //아이디 이름 생년월일
                                            tv6 = (TextView)dialogView.findViewById(R.id.ID_1);
                                            tv7 = (TextView)dialogView.findViewById(R.id.Text8);
                                            tv8 = (TextView)dialogView.findViewById(R.id.Etext8);

                                            k1=tv6.getText().toString();
                                            k2=tv1.getText().toString();
                                            k3=tv3.getText().toString();
                                            k4=tv4.getText().toString();
                                            k5=tv5.getText().toString();

                                            String URL = "http://203.234.28.137:8080/sdasd/ex22.jsp";
                                            String simplData = "?id="+k1+"&pwd="+k2+"&phone="+k3+"&email="+k4+"&address="+k5;
                                            HttpPost post = new HttpPost(URL+simplData);
                                            HttpClient client = new DefaultHttpClient();
                                            client.execute(post);
                                            ((ViewGroup)dialogView.getParent()).removeView(dialogView);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                inflater = getLayoutInflater();
                                dialogView = inflater.inflate(R.layout.remainsignform,null);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            SharedPreferences setting,setting2;
                                            setting =getSharedPreferences("setting",MODE_PRIVATE);


                                            String URL = "http://203.234.28.137:8080/sdasd/ex23.jsp";
                                            // String simplData = "?id=1&password=1";
                                            String simplData = "?id="+setting.getString("loginid","");
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

                                            for(int i=0;i<jArr.length();i++){
                                                json = jArr.getJSONObject(i);
                                            }
                                            //pw text6 =>비밀번호 확인 Text4 전화번호 Text7 이멜 Text8 주소
                                            final EditText tv1,tv2,tv3,tv4,tv5;
                                            TextView tv6,tv7,tv8;

                                            //pw text6 =>비밀번호 확인 Text4 전화번호 Text7 이멜 Text8 주소
                                            tv1 = (EditText)dialogView.findViewById(R.id.pw);
                                            tv2 = (EditText)dialogView.findViewById(R.id.Text6);
                                            tv3 = (EditText)dialogView.findViewById(R.id.Text4);
                                            tv4 = (EditText)dialogView.findViewById(R.id.Text7);
                                            tv5 = (EditText)dialogView.findViewById(R.id.Text10);
                                            //아이디 이름 생년월일
                                            tv6 = (TextView)dialogView.findViewById(R.id.ID_1);
                                            tv7 = (TextView)dialogView.findViewById(R.id.Text8);
                                            tv8 = (TextView)dialogView.findViewById(R.id.Etext8);

                                            tv1.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                    passwordsave=charSequence.toString();
                                                    if(passwordsave.equals(passwordsave2)==false){
                                                        tv1.setBackgroundColor(Color.WHITE);
                                                        tv2.setBackgroundColor(Color.RED);
                                                    }else{
                                                        tv1.setBackgroundColor(Color.GREEN);
                                                        tv2.setBackgroundColor(Color.GREEN);
                                                    }
                                                }

                                                @Override
                                                public void afterTextChanged(Editable editable) {

                                                }
                                            });
                                            tv2.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                    passwordsave2=charSequence.toString();
                                                    if(passwordsave.equals(passwordsave2)==false){
                                                        tv1.setBackgroundColor(Color.WHITE);
                                                        tv2.setBackgroundColor(Color.RED);
                                                    }else{
                                                        tv1.setBackgroundColor(Color.GREEN);
                                                        tv2.setBackgroundColor(Color.GREEN);
                                                    }
                                                }

                                                @Override
                                                public void afterTextChanged(Editable editable) {

                                                }
                                            });



                                            tv1.setText(json.getString("password"));
                                            tv3.setText(json.getString("phone"));
                                            tv4.setText(json.getString("email"));
                                            tv5.setText(json.getString("address"));

                                            tv6.setText(json.getString("id"));
                                            tv7.setText(json.getString("name"));
                                            tv8.setText(json.getString("birth"));


                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                ((ViewGroup)dialogView.getParent()).removeView(dialogView);
                                inflater = getLayoutInflater();
                                dialogView = inflater.inflate(R.layout.remainsignform,null);

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            SharedPreferences setting,setting2;
                                            setting =getSharedPreferences("setting",MODE_PRIVATE);


                                            String URL = "http://203.234.28.137:8080/sdasd/ex23.jsp";
                                            // String simplData = "?id=1&password=1";
                                            String simplData = "?id="+setting.getString("loginid","");
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

                                            for(int i=0;i<jArr.length();i++){
                                                json = jArr.getJSONObject(i);
                                            }
                                            //pw text6 =>비밀번호 확인 Text4 전화번호 Text7 이멜 Text8 주소
                                            final EditText tv1,tv2,tv3,tv4,tv5;
                                            TextView tv6,tv7,tv8;

                                            //pw text6 =>비밀번호 확인 Text4 전화번호 Text7 이멜 Text8 주소
                                            tv1 = (EditText)dialogView.findViewById(R.id.pw);
                                            tv2 = (EditText)dialogView.findViewById(R.id.Text6);
                                            tv3 = (EditText)dialogView.findViewById(R.id.Text4);
                                            tv4 = (EditText)dialogView.findViewById(R.id.Text7);
                                            tv5 = (EditText)dialogView.findViewById(R.id.Text10);
                                            //아이디 이름 생년월일
                                            tv6 = (TextView)dialogView.findViewById(R.id.ID_1);
                                            tv7 = (TextView)dialogView.findViewById(R.id.Text8);
                                            tv8 = (TextView)dialogView.findViewById(R.id.Etext8);

                                            tv1.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                    passwordsave=charSequence.toString();
                                                    if(passwordsave.equals(passwordsave2)==false){
                                                        tv1.setBackgroundColor(Color.WHITE);
                                                        tv2.setBackgroundColor(Color.RED);
                                                    }else{
                                                        tv1.setBackgroundColor(Color.GREEN);
                                                        tv2.setBackgroundColor(Color.GREEN);
                                                    }
                                                }

                                                @Override
                                                public void afterTextChanged(Editable editable) {

                                                }
                                            });
                                            tv2.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                    passwordsave2=charSequence.toString();
                                                    if(passwordsave.equals(passwordsave2)==false){
                                                        tv1.setBackgroundColor(Color.WHITE);
                                                        tv2.setBackgroundColor(Color.RED);
                                                    }else{
                                                        tv1.setBackgroundColor(Color.GREEN);
                                                        tv2.setBackgroundColor(Color.GREEN);
                                                    }
                                                }

                                                @Override
                                                public void afterTextChanged(Editable editable) {

                                                }
                                            });



                                            tv1.setText(json.getString("password"));
                                            tv3.setText(json.getString("phone"));
                                            tv4.setText(json.getString("email"));
                                            tv5.setText(json.getString("address"));

                                            tv6.setText(json.getString("id"));
                                            tv7.setText(json.getString("name"));
                                            tv8.setText(json.getString("birth"));


                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

                            }
                        }).setCancelable(false).create().show();
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("종료 알림")
                .setMessage("종료하시겠습니까?")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences setting,setting2;
                        setting = getSharedPreferences("setting",0);
                        SharedPreferences.Editor editor;
                        editor=setting.edit();
                        editor.remove("loginid");
                        editor.commit();
                        finish();
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
        //super.onBackPressed();
    }
}
