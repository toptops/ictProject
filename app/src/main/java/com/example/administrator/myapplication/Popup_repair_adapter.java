package com.example.administrator.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Popup_repair_adapter extends BaseAdapter {
    private ArrayList<Popup_repair_ListViewItem> listViewItems = new ArrayList<Popup_repair_ListViewItem>();

    public int getCount() {
        return listViewItems.size();
    }

    public Object getItem(int i) {
        return listViewItems.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos =i;
        final Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.popup_repair_p,  viewGroup,false);
        }

        TextView date = (TextView)view.findViewById(R.id.TextView_Repair_P_1);
        TextView name = (TextView)view.findViewById(R.id.TextView_Repair_P_2);
        TextView price =(TextView)view.findViewById(R.id.TextView_Repair_P_3);
        TextView distance =(TextView)view.findViewById(R.id.TextView_Repair_P_4);
        TextView memo =(TextView)view.findViewById(R.id.TextView_Repair_P_5);


        Popup_repair_ListViewItem listViewItem = listViewItems.get(pos);

        date.setText(listViewItem.getDate());
        name.setText(listViewItem.getRepairname());
        price.setText(listViewItem.getRepairprice());
        distance.setText(listViewItem.getRepairdistance());
        memo.setText(listViewItem.getRepairmemo());
        return view;
    }

    public void addItem(String date,String name, String price, String distance, String memo){
        Popup_repair_ListViewItem item = new Popup_repair_ListViewItem();

        item.setDate(date);
        item.setRepairname(name);
        item.setRepairprice(price);
        item.setRepairdistance(distance);
        item.setRepairmemo(memo);

        listViewItems.add(item);
    }
}