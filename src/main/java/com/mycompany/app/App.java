package com.mycompany.app;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import javax.xml.soap.Text;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App
{

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        sqlDatabase conn = new sqlDatabase("sample.db");



        String blogUrl = "https://news.yahoo.co.jp";
        ArrayList<NewsData> rankednews = new ArrayList<NewsData>();
        try {
            Document doc = Jsoup.connect(blogUrl).get();
            Elements ele = doc.getElementsByClass("yjnSub_list_cont");
            Element favnews = ele.first();

            for (int i=1; i< 10; i += 2){
                String href = favnews.childNodes().get(i).childNodes().get(1).attributes().get("href");
                Node newsNode =  favnews.childNodes().get(i).childNodes().get(1).childNodes().get(3).childNodes().get(3).childNodes().get(3);
                TextNode titleNode = (TextNode) favnews.childNodes().get(i).childNodes().get(1).childNodes().get(3).childNodes().get(3).childNodes().get(1).childNodes().get(0);
                String title = titleNode.text();
                String  photourl = favnews.childNodes().get(i).childNodes().get(1).childNodes().get(3).childNodes().get(1).childNodes().get(1).attributes().get("src");
                String  day = ((TextNode) newsNode.childNodes().get(3).childNodes().get(1).childNodes().get(0)).text();
                String  time = ((TextNode) newsNode.childNodes().get(3).childNodes().get(3).childNodes().get(0)).text();
                rankednews.add( new NewsData(title, photourl, href, day, time) );
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for(int i = 0; i < rankednews.size(); i++ ) {
            conn.Insert(rankednews.get(i));
        }


    }


}
