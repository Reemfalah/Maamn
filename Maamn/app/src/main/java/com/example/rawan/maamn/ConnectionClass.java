package com.example.rawan.maamn;

//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.RetryPolicy;

import android.os.StrictMode;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionClass {

    static Connection con;
    static Statement st;
    final static String TAG= "ConnectionClass";

    //public String classs = "com.mysql.jdbc.Driver";
    public static final String url = "jdbc:mysql://mysql7003.site4now.net/db_a46231_reemf6";
    public static final String un = "a46231_reemf6";
    public static final String password = "REEMIT10";
    public static final String serverSideURL = "http://192.168.100.8:8080/server/";
    public static final int socketTimeout = 300000;//30 seconds - change to what you want
    public static RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);


    //public static RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public ConnectionClass() {
        createConnection();
    }

    public static void createConnection() {

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.d(TAG,"class loaded successfully");
            con = DriverManager.getConnection(ConnectionClass.url, ConnectionClass.un, ConnectionClass.password);

            Log.d(TAG,"Connected successfully");

            st = con.createStatement();
            Log.d(TAG,"Statement created successfully");

        }

        catch (SQLException ex) {
            // log an exception. fro example:
            Log.d(TAG,"Failed to create the database connection. "+ex);

        }
        catch (ClassNotFoundException ex) {
            // log an exception. for example:
            Log.d(TAG,"Driver not found. "+ex);
        }
    }
}