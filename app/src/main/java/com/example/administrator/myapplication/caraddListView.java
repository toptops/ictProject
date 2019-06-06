package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by top on 2016-12-07.
 */

public class caraddListView extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItems = new ArrayList<ListViewItem>();

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItems.get(i);
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
            view = inflater.inflate(R.layout.popup_setting_cars_p,  viewGroup,false);
        }

        TextView carName = (TextView)view.findViewById(R.id.TextView_Setting_Cars_Name);
        TextView carNum = (TextView)view.findViewById(R.id.TextView_Setting_Cars_Number);
        Button button = (Button)view.findViewById(R.id.Button_Setting_Cars_Modify);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,car_edit.class);
                ListViewItem listViewItem = listViewItems.get(pos);
                intent.putExtra("name",listViewItem.getName());
                intent.putExtra("num",listViewItem.getCarNum());
                context.startActivity(intent);
            }
        });

        Button button1 = (Button)view.findViewById(R.id.Button_Setting_Cars_View);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,main.class);
                ListViewItem listViewItem = listViewItems.get(pos);
                intent.putExtra("name",listViewItem.getName());
                intent.putExtra("num",listViewItem.getCarNum());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
        ListViewItem listViewItem = listViewItems.get(pos);

        carName.setText(listViewItem.getName());
        carNum.setText(listViewItem.getCarNum());
        return view;
    }

    public void addItem(String carName,String carNum){
        ListViewItem item = new ListViewItem();

        item.setName(carName);
        item.setCarNum(carNum);

        listViewItems.add(item);
    }
}
