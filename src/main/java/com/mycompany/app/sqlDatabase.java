package com.mycompany.app;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class sqlDatabase {

    public Connection c;
    private Boolean first = true;


    public  sqlDatabase( String dbfile ){

        try {
            String url = String.format("jdbc:sqlite:%s", dbfile);
            c = DriverManager.getConnection(url);
            System.out.printf("sqlite database is created at %s", dbfile);
        } catch (Exception e) {
            System.out.println("failed");
        }
    }

    private void createTable( NewsData newsmodel ) {

        String tableName = newsmodel.getClass().getSimpleName();
        Field[] fields = newsmodel.getClass().getFields();

        String sqlStatement = String.format( "CREATE TABLE IF NOT EXISTS %s ", tableName );
        sqlStatement += "(";

        for ( int i=0; i< fields.length ; i++ ) {
            String fieldName =  fields[i].getName();
            sqlStatement += fieldName.toLowerCase() + " TEXT,";
        }

        sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 1);

        sqlStatement +=")";



        try {
            Statement stmt = c.createStatement();
            stmt.execute(sqlStatement);
          } catch ( Exception e ) {
            System.out.println("failed");
        }
    }

    public void Insert( NewsData newsmodel ) {

        if (first) {
            createTable(newsmodel);
            first = false;
        }

        String tableName = newsmodel.getClass().getSimpleName();
        Field[] fields = newsmodel.getClass().getFields();

        String sqlStatement = String.format( "INSERT INTO %s ", tableName );
        sqlStatement += "(";

        for ( int i=0; i< fields.length ; i++ ) {
            String fieldName =  fields[i].getName();
            sqlStatement += fieldName.toLowerCase() + " ,";
        }

        sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 1);

        sqlStatement +=")";

        sqlStatement += "VALUES";

        sqlStatement += "(";

        for ( int i=0; i< fields.length ; i++ ) {
            sqlStatement += "?,";
        }
        sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 1);
        sqlStatement +=")";

        PreparedStatement pstmt;

        try {
            pstmt = c.prepareStatement(sqlStatement);
            for ( int i=0; i< fields.length ; i++ ) {
                Object value = fields[i].get(newsmodel);
                pstmt.setString(i + 1, value.toString());
            }

           pstmt.execute();
        } catch ( Exception e ) {
            System.out.println("failed");
        }


    }
}
