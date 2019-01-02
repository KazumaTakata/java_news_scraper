package com.mycompany.app;

public class NewsData {

    public  String Title;
    public  String PhotoUrl;
    public  String Url;
    public  String Time;
    public  String Day;


    public NewsData( String title, String photourl, String url, String day, String time ) {
        Title = title;
        PhotoUrl = photourl;
        Url = url;
        Day = day;
        Time = time;
    }

    public String  getClassName(){
        String classname = this.getClass().getSimpleName();
        return classname;
    }



}
