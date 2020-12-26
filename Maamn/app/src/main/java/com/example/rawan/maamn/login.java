package com.example.rawan.maamn;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class login extends AppCompatActivity {

    //    SharedPreferences sp;
//    ResultSet resultSet = null;
    public static String Name,UId,Utype ;
    public static boolean logged;
    String userName;
    String Password;
    EditText username ;
    EditText password;
    Button login;
    TextView type;
    TextView textView2;
    boolean find=false;
    String q,q2;
    int  isthere,isthere2;
    ResultSet resultSet;
    ConnectionClass connectionClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logged=false;
        username = (EditText) findViewById(R.id.editText2);
        password = (EditText) findViewById(R.id.editText);
        login = (Button) findViewById(R.id.button);
        type = (TextView) findViewById(R.id.textView3);
        textView2 = (TextView) findViewById(R.id.textView2);
        connectionClass = new ConnectionClass();
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, userType.class);
                startActivity(intent);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, ResetPassword.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = username.getText().toString();
                Password=password.getText().toString();
                if(IsValled(userName,Password)){
                    Password=md5(Password);
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Connection con;
                    Statement stmt;
                    try {

                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(ConnectionClass.url, ConnectionClass.un, ConnectionClass.password);
                        stmt = con.createStatement();


                        q="select * from patient where user_name='" + userName + "'AND password='" + Password + "'";
                        resultSet = stmt.executeQuery(q); //Here table name is users and userName,password are columns. fetching all the records and storing in a resultSet.
                        isthere=0;
                        while (resultSet.next()){
                            Name=resultSet.getString("name");
                            UId = resultSet.getString("patient_ID");
                            isthere++;
                        }
                        if(isthere>0){
                            con.close();
                            stmt.close();
                            find=true;
                            logged=true;
                            Utype = "2";

                            Intent intent = new Intent(login.this, Activity_patient_home.class);
                            startActivity(intent);
                        }else {
                            q2="select * from caregiver where user_name='" + userName + "'AND password='" + Password + "'";
                            resultSet = stmt.executeQuery(q2); //Here table name is users and userName,password are columns. fetching all the records and storing in a resultSet.
                            isthere2=0;
                            while (resultSet.next()){
                                Name=resultSet.getString("name");
                                UId = resultSet.getString("caregiver_ID");

                                isthere2++;
                            }
                            if(isthere2>0){
                                con.close();
                                stmt.close();
                                find=true;
                                logged=true;
                                Utype = "1";

                                Intent intent = new Intent(login.this, caregiverHome.class);
                                startActivity(intent);
                            }else{
                                Toast errorToast = Toast.makeText(login.this, "اسم المستخدم أو كلمة المرور خطأ", Toast.LENGTH_LONG);
                                errorToast.show();
                                con.close();
                                stmt.close();

                            }
                        }





                    } catch (SQLException se) {
                        Toast errorToast = Toast.makeText(login.this, "الرجاء الإتصال بالانترنت", Toast.LENGTH_SHORT);
                        errorToast.show();
                        se.printStackTrace();
                    } catch (Exception e) {
                        Toast errorToast = Toast.makeText(login.this, " " + e.getMessage(), Toast.LENGTH_SHORT);
                        errorToast.show();
                    }
                }
            }
        });

    }

    public boolean IsValled(String U, String P){

        if(U.equals(null)||U.equals("")){

            username.setError("الرجاء تعبئة الحقل");
            return false;
        } else if(P.equals(null)||P.equals("")){
            password.setError("الرجاء تعبئة الحقل");
            return false;

        } else return true;

    }
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    public static String GetUserName(){
        return Name;
    }

    public static String GetUserId(){
        return UId;
    }
    public static String GetUserType(){
        return Utype;
    }

    public static boolean GetIslogged(){
        return logged;
    }
    public static void SetIslogged(boolean L){
        logged=L;
    }
}
