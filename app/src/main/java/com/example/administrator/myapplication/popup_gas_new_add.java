package com.example.administrator.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class popup_gas_new_add extends AppCompatActivity { //가스 add xml class

    TableLayout t1,t2,t3,t4;
    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    String str1;
    String str2;
    String str3;
    int num1=0,num2=0,num3=0;
    private boolean b1=false,b2=false,b3=false;
    private Button button1,button2;
    private int check=0;
    private boolean undo = false;
    String carName,carNum;
    private SharedPreferences setting,setting2;
    private    SharedPreferences.Editor editor;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;

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
        setContentView(R.layout.popup_gas_new_add);

        button1 = (Button)findViewById(R.id.Button_Gas_Add_Date);


        long now = System.currentTimeMillis();
        Date date = new Date(now);

        final SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");

        String strNow = sdfNow.format(date);

        Calendar cal2 = Calendar.getInstance();
        setting2 = getSharedPreferences("setting",MODE_PRIVATE);

        button1.setText(setting2.getString("firstTime",""));
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
                String buf = sdfNow.format(date);
                setting = getSharedPreferences("setting",0);
                editor=setting.edit();
                editor.remove("firstTime");
                editor.putString("firstTime",buf);
                editor.commit();
                button1.setText(buf);

                dialogCaldroidFragment.dismiss();
            }

            @Override
            public void onChangeMonth(int month, int year) {

            }

            @Override
            public void onLongClickDate(Date date, View view) {
                String buf = sdfNow.format(date);
                button1.setText(buf);

                dialogCaldroidFragment.dismiss();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                }
            }

        };

        Button showDialogButton = (Button) findViewById(R.id.Button_Gas_Add_Date);

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
            }
        });


        Intent intent = getIntent();
        carName=intent.getStringExtra("name");
        carNum=intent.getStringExtra("num");

        t1=(TableLayout)findViewById(R.id.TableLayout_Gas_Add_Tab1);
        t2=(TableLayout)findViewById(R.id.TableLayout_Gas_Add_Tab2);
        t3=(TableLayout)findViewById(R.id.TableLayout_Gas_Add_Tab3);
        t4=(TableLayout)findViewById(R.id.TableLayout_Gas_Add_Tab4);

        tv1 = (TextView)findViewById(R.id.TextView_Gas_Add1);
        tv2 = (TextView)findViewById(R.id.TextView_Gas_Add2);
        tv3 = (TextView)findViewById(R.id.TextView_Gas_Add3);
        tv4 = (TextView)findViewById(R.id.TextView_Gas_Add4);
        tv5 = (TextView)findViewById(R.id.TextView_Gas_Add5);
        tv6 = (TextView)findViewById(R.id.TextView_Gas_Add6);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),keyboard_gas.class);
                startActivityForResult(intent,0);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),keyboard_GO.class);
                startActivityForResult(intent,4);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),keyboard_gas2.class);
                startActivityForResult(intent,5);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),reg_Name.class);
                startActivityForResult(intent,0);
            }
        });

        Button button = (Button)findViewById(R.id.Button_Gas_Add_OK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                            String id = setting.getString("id","");
                            String URL = "http://203.234.28.137:8080/sdasd/ex17.jsp";

                            String simplData = "?id="+id+"&carName="+carName+"&carNum="+carNum+"&dcDate="+button1.getText()+"&money="+tv1.getText()+"&litermoney="+tv2.getText()+"&amount="+tv5.getText()+"&efficiency="+tv6.getText()+"&distance="+tv3.getText()+"&memo="+tv4.getText();
                            HttpPost post = new HttpPost(URL+simplData);
                            HttpClient client = new DefaultHttpClient();
                            client.execute(post);

                            Intent intent = new Intent();
                            intent.putExtra("name",carName);
                            intent.putExtra("num",carNum);
                            setResult(0,intent);
                            finish();
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
        if (resultCode == 0) {
            tv4.setText(data.getStringExtra("Name"));
        } else if (resultCode == 1) {
        } else if (resultCode == 2) {
            tv1.setText(data.getStringExtra("gastotal") + "원");
            b1=true;
        } else if (resultCode == 3) {

        } else if (resultCode == 4) {
            tv2.setText(data.getStringExtra("go") + "원");
            b2=true;
        } else if (resultCode ==5) {
            tv3.setText(data.getStringExtra("gastotal2") + "km");
            b3=true;
        }

        if(b1==true && b2==true){
            str1=tv1.getText().toString();
            str1=onlyNum(str1);
            str2=tv2.getText().toString();
            str2=onlyNum(str2);

            num1=Integer.parseInt(str1);
            num2=Integer.parseInt(str2);
            tv5.setText(Integer.toString((int)(num1/num2)));
            tv5.setText(tv5.getText().toString()+"L");
        }
        if(b1 ==true  && b2 ==true && b3 ==true){
            str1=tv1.getText().toString();
            str1=onlyNum(str1);
            str2=tv2.getText().toString();
            str2=onlyNum(str2);
            str3=tv3.getText().toString();
            str3=onlyNum(str3);

            num1=Integer.parseInt(str1);
            num2=Integer.parseInt(str2);
            num3=Integer.parseInt(str3);
            tv5.setText(Integer.toString((int)(num1/num2)));
            tv5.setText(tv5.getText().toString()+"L");
            tv6.setText(Integer.toString((int)(num3/(num1/num2))));
            tv6.setText(tv6.getText().toString()+"km/L");
        }
    }
    public static String onlyNum(String str) {
        if ( str == null ) return "";

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < str.length(); i++){
            if( Character.isDigit( str.charAt(i) ) ) {
                sb.append( str.charAt(i) );
            }
        }
        return sb.toString();
    }
}