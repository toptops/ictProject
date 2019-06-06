package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

/**
 * Created by top on 2016-12-07.
 */

public class reg_Name extends AppCompatActivity {
    EditText editText;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(3);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_get_text_name);

        editText = (EditText)findViewById(R.id.EditText_Get_Text);

        final GridView gridView;
        final reg_Name_Adapter adapter;

        adapter=new reg_Name_Adapter();

        gridView = (GridView)findViewById(R.id.GridView_GetText);
        gridView.setAdapter(adapter);

        Button regNameButton = (Button)findViewById(R.id.Button_Get_Text_OK);
        regNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.additem(editText.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("Name",editText.getText().toString());
                setResult(0,intent);
                finish();
            }
        });
    }
}
