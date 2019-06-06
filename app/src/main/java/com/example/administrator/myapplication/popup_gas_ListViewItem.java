package com.example.administrator.myapplication;

/**
 * Created by choi on 2016-12-07.
 */

public class popup_gas_ListViewItem {
    private String date; //날짜가져옴
    private String gastotal; //주유 금액
    private String gasprice; //리터당 금액
    private String mileage;  //연비
    private String gasliter; //주유량
    private String distance; //주행 거리
    private String memo; // 메모

    public String getDate(){
        return date;
    }
    public String getGastotal() {return gastotal;}
    public String getGasprice(){return gasprice;}
    public String getDistance(){
        return distance;
    }
    public String getMileage(){return mileage;}
    public String getGasliter(){return gasliter;}
    public String getMemo(){return memo;}

    public void setDate(String date){
        this.date=date;
    }
    public void setGastotal(String gastotal){
        this.gastotal=gastotal;
    }
    public void setGasprice(String gasprice){
        this.gasprice=gasprice;
    }
    public void setDistance(String distance){
        this.distance=distance;
    }
    public void setMileage(String mileage){this.mileage=mileage;}
    public void setGasliter(String gasliter){this.gasliter=gasliter;}
    public void setMemo(String memo){
        this.memo=memo;
    }

}