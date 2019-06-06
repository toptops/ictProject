package com.example.administrator.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

public class popup_gas extends AppCompatActivity { //누적주행거리 xml class
    Button button;
    String carName,carNum;
    ListView listView;
    popup_gas_adapter adapter;
    private Button button1,button2;
    private int check=0;
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private int hap=0,count=0,moneyCount=0;
    private SharedPreferences setting,setting2;
    private    SharedPreferences.Editor editor;
    private TextView txcount,txmoney,txoil;
    private int intcount=0,intmoney=0,intoil=0;

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
        setContentView(R.layout.popup_gas);

        listView=(ListView)findViewById(R.id.ListView_Gas);
        adapter=new popup_gas_adapter();
        listView.setAdapter(adapter);



        button1 = (Button)findViewById(R.id.Button_Gas_Date1);
        button2 = (Button)findViewById(R.id.Button_Gas_Date2);


        long now = System.currentTimeMillis();
        Date date = new Date(now);

        final SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");

        String strNow = sdfNow.format(date);

        Calendar cal2 = Calendar.getInstance();

        setting = getSharedPreferences("setting",0);
        setting2 = getSharedPreferences("setting",MODE_PRIVATE);
        editor=setting.edit();
        editor.putString("firstTime",strNow);
        editor.putString("lastTime",cal2.get(Calendar.YEAR)+"/"+(cal2.get(Calendar.MONTH)+1)+"/"+cal2.getMaximum(Calendar.DAY_OF_MONTH));
        editor.commit();


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

                    setting = getSharedPreferences("setting",0);
                    editor=setting.edit();
                    editor.remove("firstTime");
                    editor.putString("firstTime",buf);
                    button1.setText(buf);
                    //editor.putString("lastTime",cal2.get(Calendar.YEAR)+"/"+(cal2.get(Calendar.MONTH)+1)+"/"+cal2.getMaximum(Calendar.DAY_OF_MONTH));
                }else if(check == 2){
                    String buf= sdfNow.format(date);
                    setting = getSharedPreferences("setting",0);
                    editor=setting.edit();
                    editor.remove("firstTime");
                    editor.putString("firstTime",buf);
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

        Button showDialogButton = (Button) findViewById(R.id.Button_Gas_Date1);

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


        Button showDialogButton2 = (Button) findViewById(R.id.Button_Gas_Date2);
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





        Intent intent = getIntent();
        carName=intent.getStringExtra("name");
        carNum=intent.getStringExtra("num");

        button=(Button)findViewById(R.id.Button_Gas_Add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),popup_gas_new_add.class);
                intent.putExtra("name",carName);
                intent.putExtra("num",carNum);
                startActivityForResult(intent,0);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                    String id = setting.getString("id","");
                    String URL = "http://203.234.28.137:8080/sdasd/ex18.jsp";
                    String simplData = "?id="+id+"&carName="+carName+"&carNum="+carNum;
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

                    //    private TextView txcount,txmoney,txoil;
                   // private int intcount=0,intmoney=0,intoil=0;
                    for(int i=0;i<jArr.length();i++){
                        json = jArr.getJSONObject(i);
                        intcount=i;
                        intmoney+=Integer.parseInt(json.getString("money").substring(0,json.getString("money").length()-1));
                        intoil+=Integer.parseInt(json.getString("amount").substring(0,json.getString("amount").length()-1));
                       // 2. 주유금액 3. 리터당 금액 4. 연비 5. 주유량 메모 6주행거리
                        adapter.addItem(json.getString("dcDate"),json.getString("money"),json.getString("litermoney"),json.getString("efficiency"),json.getString("amount"),json.getString("memo"),json.getString("distance"));
                    }
                    intcount=intcount+1; //횟수
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            txcount=(TextView)findViewById(R.id.TextView_Gas_Total1);
                            txmoney=(TextView)findViewById(R.id.TextView_Gas_Total2);
                            txoil=(TextView)findViewById(R.id.TextView_Gas_Total3);

                            txcount.setText(intcount+"건");
                            txmoney.setText(intmoney+"원");
                            txoil.setText(intoil+"L");

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    popup_gas_ListViewItem item = (popup_gas_ListViewItem)adapterView.getItemAtPosition(i);
                                    Intent intent = new Intent(getApplicationContext(),popup_gas_edit_add.class);


                                    intent.putExtra("name",carName);
                                    intent.putExtra("num",carNum);
                                    intent.putExtra("money",item.getGastotal());
                                    intent.putExtra("litermoney",item.getGasprice());
                                    intent.putExtra("amount",item.getGasliter());
                                    intent.putExtra("efficiency",item.getMileage());
                                    intent.putExtra("distance",item.getDistance());
                                    intent.putExtra("memo",item.getMemo());

                                    startActivityForResult(intent,0);
                                }
                            });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0){
            if(carName==null)
                carName=data.getStringExtra("name");

            if(carNum==null)
                carNum=data.getStringExtra("num");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                        String id = setting.getString("id","");
                        String URL = "http://203.234.28.137:8080/sdasd/ex18.jsp";
                        String simplData = "?id="+id+"&carName="+carName+"&carNum="+carNum;
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


                        final popup_gas_adapter adapter;

                        adapter = new popup_gas_adapter();

                        listView = (ListView)findViewById(R.id.ListView_Gas);

                        for(int i=0;i<jArr.length();i++) {
                            json = jArr.getJSONObject(i);
                            intcount=i;
                            intmoney+=Integer.parseInt(json.getString("money").substring(0,json.getString("money").length()-1));
                            intoil+=Integer.parseInt(json.getString("amount").substring(0,json.getString("amount").length()-1));
                            adapter.addItem(json.getString("dcDate"),json.getString("money"),json.getString("litermoney"),json.getString("efficiency"),json.getString("amount"),json.getString("memo"),json.getString("distance"));
                        }
                        intcount=intcount+1; //횟수
                            adapter.notifyDataSetChanged();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView.setAdapter(adapter);

                                txcount=(TextView)findViewById(R.id.TextView_Gas_Total1);
                                txmoney=(TextView)findViewById(R.id.TextView_Gas_Total2);
                                txoil=(TextView)findViewById(R.id.TextView_Gas_Total3);

                                txcount.setText(intcount+"건");
                                txmoney.setText(intmoney+"원");
                                txoil.setText(intoil+"L");
                            }
                        });
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                popup_gas_ListViewItem item = (popup_gas_ListViewItem)adapterView.getItemAtPosition(i);
                                Intent intent = new Intent(getApplicationContext(),popup_gas_edit_add.class);


                                intent.putExtra("name",carName);
                                intent.putExtra("num",carNum);
                                intent.putExtra("money",item.getGastotal());
                                intent.putExtra("litermoney",item.getGasprice());
                                intent.putExtra("amount",item.getGasliter());
                                intent.putExtra("efficiency",item.getMileage());
                                intent.putExtra("distance",item.getDistance());
                                intent.putExtra("memo",item.getMemo());
                                startActivityForResult(intent,1);
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
        else if(resultCode==1){

        }else if(resultCode==2){

        }else if(resultCode==3){}
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = getIntent();
        carName=intent2.getStringExtra("name");
        carNum=intent2.getStringExtra("num");

        Intent intent = new Intent(getApplicationContext(),main.class);
        intent.putExtra("name",carName);
        intent.putExtra("num",carNum);
        startActivity(intent);
        //super.onBackPressed();
    }
}