package com.example.administrator.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class popup_distance_new_add extends AppCompatActivity  {
    public Button btn0;
    public Button btn1;
    public Button btn2;
    public Button btn3;
    public Button btn4;
    public Button btn5;
    public Button btn6;
    public Button btn7;
    public Button btn8;
    public Button btn9;
    public Button delete;
    public Button backspace;
    public Button save;
    TextView text;
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
        setContentView(R.layout.popup_distance_new_add);

        button1 = (Button)findViewById(R.id.Button_Distance_Add_Date);


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
                setting = getSharedPreferences("setting",0);
                editor=setting.edit();
                editor.remove("firstTime");
                editor.putString("firstTime",buf);
                editor.commit();
                button1.setText(buf);

                dialogCaldroidFragment.dismiss();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                }
            }

        };

        Button showDialogButton = (Button) findViewById(R.id.Button_Distance_Add_Date);

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

        final TextView tx = (TextView)findViewById(R.id.TextView_Distance_Add_Distance);
        Intent intent = getIntent();
        carName=intent.getStringExtra("name");
        carNum=intent.getStringExtra("num");

        ////////////////////////////////////////달력 추가

        //저장 버튼 클리시 이벤트 추가 해야됨  자료는 text에 저장되어 있음.
        save=(Button)findViewById(R.id.Button_Distance_Add_OK);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
                            String id = setting.getString("id","");
                            String URL = "http://203.234.28.137:8080/sdasd/ex9.jsp";

                            String simplData = "?id="+id+"&carName="+carName+"&carNum="+carNum+"&dcDate="+button1.getText()+"&distance="+tx.getText();
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


        View.OnClickListener clickListener=new View.OnClickListener(){
            @Override
            public void onClick(View v){
                text=(TextView)findViewById(R.id.TextView_Distance_Add_Distance);
                switch (v.getId()){
                    case R.id.Button_Distance_Add_Number0:
                        text.setText(text.getText().toString()+"0");
                        break;
                    case R.id.Button_Distance_Add_Number1:
                        text.setText(text.getText().toString()+"1");
                        break;
                    case R.id.Button_Distance_Add_Number2:
                        text.setText(text.getText().toString()+"2");
                        break;
                    case R.id.Button_Distance_Add_Number3:
                        text.setText(text.getText().toString()+"3");
                        break;
                    case R.id.Button_Distance_Add_Number4:
                        text.setText(text.getText().toString()+"4");
                        break;
                    case R.id.Button_Distance_Add_Number5:
                        text.setText(text.getText().toString()+"5");
                        break;
                    case R.id.Button_Distance_Add_Number6:
                        text.setText(text.getText().toString()+"6");
                        break;
                    case R.id.Button_Distance_Add_Number7:
                        text.setText(text.getText().toString()+"7");
                        break;
                    case R.id.Button_Distance_Add_Number8:
                        text.setText(text.getText().toString()+"8");
                        break;
                    case R.id.Button_Distance_Add_Number9:
                        text.setText(text.getText().toString()+"9");
                        break;
                    case R.id.Button_Distance_Add_NumberDel:
                        String val=text.getText().toString();
                        if(val.equals("")){
                            val="";
                        }else{
                            val = val.substring(0, val.length() - 1);
                        }
                        text.setText(val);
                        break;
                }

            }
        };

        //버튼 입력 구문
        btn0=(Button)findViewById(R.id.Button_Distance_Add_Number0);
        btn1=(Button)findViewById(R.id.Button_Distance_Add_Number1);
        btn2=(Button)findViewById(R.id.Button_Distance_Add_Number2);
        btn3=(Button)findViewById(R.id.Button_Distance_Add_Number3);
        btn4=(Button)findViewById(R.id.Button_Distance_Add_Number4);
        btn5=(Button)findViewById(R.id.Button_Distance_Add_Number5);
        btn6=(Button)findViewById(R.id.Button_Distance_Add_Number6);
        btn7=(Button)findViewById(R.id.Button_Distance_Add_Number7);
        btn8=(Button)findViewById(R.id.Button_Distance_Add_Number8);
        btn9=(Button)findViewById(R.id.Button_Distance_Add_Number9);
        backspace=(Button)findViewById(R.id.Button_Distance_Add_NumberDel);

        //리스너 연결
        btn0.setOnClickListener(clickListener);
        btn1.setOnClickListener(clickListener);
        btn2.setOnClickListener(clickListener);
        btn3.setOnClickListener(clickListener);
        btn4.setOnClickListener(clickListener);
        btn5.setOnClickListener(clickListener);
        btn6.setOnClickListener(clickListener);
        btn7.setOnClickListener(clickListener);
        btn8.setOnClickListener(clickListener);
        btn9.setOnClickListener(clickListener);
        backspace.setOnClickListener(clickListener);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(3);
        finish();
        super.onBackPressed();

    }

}