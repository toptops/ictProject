package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class Signup extends AppCompatActivity {

    Button button;
    CheckBox c1,c2,c3,c4,c5,c6,c7;
    Boolean b1,b2,b3,b4,b5,b6,b7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        button = (Button)findViewById(R.id.button4);
        button.setEnabled(false);



        findViewById(R.id.checkBox).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                buttonSetEnable2(view);
            }
        });
        findViewById(R.id.checkBox2).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                buttonSetEnable2(view);
            }
        });
        findViewById(R.id.checkBox3).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                buttonSetEnable2(view);
            }
        });
        findViewById(R.id.checkBox4).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                buttonSetEnable(view);
            }
        });
        findViewById(R.id.checkBox5).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                buttonSetEnable2(view);
            }
        });
        findViewById(R.id.checkBox6).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                buttonSetEnable2(view);
            }
        });
        findViewById(R.id.checkBox7).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                buttonSetEnable2(view);
            }
        });
    }

    public void buttonSetEnable(View v){
        c1 = (CheckBox)findViewById(R.id.checkBox);
        c2 = (CheckBox)findViewById(R.id.checkBox2);
        c3 = (CheckBox)findViewById(R.id.checkBox3);
        c4 = (CheckBox)findViewById(R.id.checkBox4);
        c5 = (CheckBox)findViewById(R.id.checkBox5);
        c6 = (CheckBox)findViewById(R.id.checkBox6);
        c7 = (CheckBox)findViewById(R.id.checkBox7);

        if(c4.isChecked()==true){
            c2.setChecked(true);
            c3.setChecked(true);
            c1.setChecked(true);
            c5.setChecked(true);
            c6.setChecked(true);
            c7.setChecked(true);
        }else if(c4.isChecked()==false){
            c2.setChecked(false);
            c3.setChecked(false);
            c1.setChecked(false);
            c5.setChecked(false);
            c6.setChecked(false);
            c7.setChecked(false);
        }

        if( c1.isChecked()==true && c2.isChecked()==true && c3.isChecked()==true && c5.isChecked()==true ){
            button.setEnabled(true);
        }else if(c1.isChecked()==false || c2.isChecked()==false || c3.isChecked()==false || c5.isChecked()==false ){
            button.setEnabled(false);
        }
    }

    public void buttonSetEnable2(View v){
        c1 = (CheckBox)findViewById(R.id.checkBox);
        c2 = (CheckBox)findViewById(R.id.checkBox2);
        c3 = (CheckBox)findViewById(R.id.checkBox3);
        c4 = (CheckBox)findViewById(R.id.checkBox4);
        c5 = (CheckBox)findViewById(R.id.checkBox5);
        c6 = (CheckBox)findViewById(R.id.checkBox6);
        c7 = (CheckBox)findViewById(R.id.checkBox7);

        if( c1.isChecked()==true && c2.isChecked()==true && c3.isChecked()==true && c5.isChecked()==true ){
            button.setEnabled(true);
        }else if(c1.isChecked()==false || c2.isChecked()==false || c3.isChecked()==false || c5.isChecked()==false ){
            button.setEnabled(false);
        }
    }


    public void mainsignClick(View V) {
        Intent intent = new Intent(getApplicationContext(), MainSignFrom.class);
        startActivity(intent);
    }
}
