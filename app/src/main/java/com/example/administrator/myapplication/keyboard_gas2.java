package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by top on 2016-12-07.
 */

public class keyboard_gas2 extends AppCompatActivity {
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
    public Button backspace;
    public Button save;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_get_number);

        text=(TextView)findViewById(R.id.TextView_Get_Number_Input);

        View.OnClickListener clickListener=new View.OnClickListener(){
            @Override
            public void onClick(View v){

                switch (v.getId()){
                    case R.id.Button_Get_Number_Number0:
                        text.setText(text.getText().toString()+"0");
                        break;
                    case R.id.Button_Get_Number_Number1:
                        text.setText(text.getText().toString()+"1");
                        break;
                    case R.id.Button_Get_Number_Number2:
                        text.setText(text.getText().toString()+"2");
                        break;
                    case R.id.Button_Get_Number_Number3:
                        text.setText(text.getText().toString()+"3");
                        break;
                    case R.id.Button_Get_Number_Number4:
                        text.setText(text.getText().toString()+"4");
                        break;
                    case R.id.Button_Get_Number_Number5:
                        text.setText(text.getText().toString()+"5");
                        break;
                    case R.id.Button_Get_Number_Number6:
                        text.setText(text.getText().toString()+"6");
                        break;
                    case R.id.Button_Get_Number_Number7:
                        text.setText(text.getText().toString()+"7");
                        break;
                    case R.id.Button_Get_Number_Number8:
                        text.setText(text.getText().toString()+"8");
                        break;
                    case R.id.Button_Get_Number_Number9:
                        text.setText(text.getText().toString()+"9");
                        break;
                    case R.id.Button_Get_Number_NumberDel:
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
        save = (Button)findViewById(R.id.Button_Get_Number_OK) ;
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent();
                intent.putExtra("gastotal2",text.getText()); //인텐트에 자료 전송
                setResult(5,intent);
                finish();
            }
        });


        //버튼 입력 구문
        btn0=(Button)findViewById(R.id.Button_Get_Number_Number0);
        btn1=(Button)findViewById(R.id.Button_Get_Number_Number1);
        btn2=(Button)findViewById(R.id.Button_Get_Number_Number2);
        btn3=(Button)findViewById(R.id.Button_Get_Number_Number3);
        btn4=(Button)findViewById(R.id.Button_Get_Number_Number4);
        btn5=(Button)findViewById(R.id.Button_Get_Number_Number5);
        btn6=(Button)findViewById(R.id.Button_Get_Number_Number6);
        btn7=(Button)findViewById(R.id.Button_Get_Number_Number7);
        btn8=(Button)findViewById(R.id.Button_Get_Number_Number8);
        btn9=(Button)findViewById(R.id.Button_Get_Number_Number9);
        backspace=(Button)findViewById(R.id.Button_Get_Number_NumberDel);

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