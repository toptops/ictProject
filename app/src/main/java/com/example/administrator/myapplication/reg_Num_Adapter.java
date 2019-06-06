package com.example.administrator.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by top on 2016-12-07.
 */

public class reg_Num_Adapter extends BaseAdapter {
    private ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos =i;
        final Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.popup_get_text_p,  viewGroup,false);
        }

        TextView textView = (TextView)view.findViewById(R.id.TextView_GetText_P);
        textView.setText(arrayList.get(pos));

        return view;
    }

    public void additem(String num){
        arrayList.add(num);
    }
}
