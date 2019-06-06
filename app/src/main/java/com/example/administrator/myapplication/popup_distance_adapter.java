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

public class popup_distance_adapter extends BaseAdapter {

    private ArrayList<ListViewItem2> listViewItems = new ArrayList<ListViewItem2>();

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
            view = inflater.inflate(R.layout.popup_distance_p,  viewGroup,false);
        }

        TextView date = (TextView)view.findViewById(R.id.TextView_Distance_P_1);
        TextView distance = (TextView)view.findViewById(R.id.TextView_Distance_P_2);

        ListViewItem2 listViewItem2 = listViewItems.get(pos);

        date.setText(listViewItem2.getDate());
        distance.setText(listViewItem2.getDistance());
        return view;
    }

    public void addItem(String date,String distance){
        ListViewItem2 item = new ListViewItem2();

        item.setDate(date);
        item.setDistance(distance);

        listViewItems.add(item);
    }
}