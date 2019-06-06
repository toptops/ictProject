package com.example.administrator.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by choi on 2016-12-07.
 */

public class popup_gas_adapter extends BaseAdapter {

    private ArrayList<popup_gas_ListViewItem> listViewItems = new ArrayList<popup_gas_ListViewItem>();

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
            view = inflater.inflate(R.layout.popup_gas_p,  viewGroup,false);
        }

        TextView date = (TextView)view.findViewById(R.id.TextView_Gas_P_1); //날짜
        TextView gastotal = (TextView)view.findViewById(R.id.TextView_Gas_P_2); //주유금액
        TextView gasprice = (TextView)view.findViewById(R.id.TextView_Gas_P_3); //리터당 금액
        TextView mileage = (TextView)view.findViewById(R.id.TextView_Gas_P_4); //연비
        TextView gasliter = (TextView)view.findViewById(R.id.TextView_Gas_P_5); //주유량
        TextView distance = (TextView)view.findViewById(R.id.TextView_Gas_P_6); //주행거리


        popup_gas_ListViewItem listViewItem = listViewItems.get(pos);

        date.setText(listViewItem.getDate());  //날짜 설정
        gastotal.setText(listViewItem.getGastotal()); //주유 금액 설정
        gasprice.setText(listViewItem.getGasprice()); //리터당 금액 설정
        mileage.setText(listViewItem.getMileage()); //연비
        gasliter.setText(listViewItem.getGasliter()); //주유량
        distance.setText(listViewItem.getDistance()); //주행거리 설정

        return view;
    }

    public void addItem(String date,String gastotal, String gasprice, String mileage, String gasliter,String memo, String distance){
        popup_gas_ListViewItem item = new popup_gas_ListViewItem();
        /// 2. 주유금액 3. 리터당 금액 4. 연비 5. 주유량 메모 6주행거리
        item.setDate(date);
        item.setGastotal(gastotal);
        item.setGasprice(gasprice);
        item.setMileage(mileage);
        item.setGasliter(gasliter);
        item.setDistance(distance);
        item.setMemo(memo);

        listViewItems.add(item);
    }
}