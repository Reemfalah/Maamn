package com.example.rawan.maamn;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Activity_patient_home extends AppCompatActivity {

   // Intent intent;
    String userid;
    private TextView tv;
    private ImageButton imageButton101;
    private ImageView imageView5;
    private ImageButton imageButton2;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButtonu99;
    private Button send;

    ConnectionClass connectionClass;
    ArrayList argentPhoneList;
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        ///////
        if(login.GetIslogged()==false){
            Intent intent = new Intent(Activity_patient_home.this, login.class);
            startActivity(intent);
        }
        tv=(TextView)findViewById(R.id.textView7);
        String Name=login.GetUserName();
        tv.setText(Name);////
        //bar button
        imageButton101 = (ImageButton) findViewById (R.id.imageButton101);
        imageView5 = (ImageView) findViewById(R.id.imageView501);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton201);
        imageButton4=(ImageButton) findViewById(R.id.imageButton401);
        imageButton5=(ImageButton)findViewById(R.id.imageButton501);
        imageButtonu99=(ImageButton)findViewById(R.id.imageButtonu99);
        send=(Button)findViewById(R.id.send);


        imageButton101.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_patient_home.this, Activity_patient_home.class);
                startActivity(intent);
            }
        });

        imageButtonu99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_patient_home.this, patient_viewinfo.class);
                startActivity(intent);
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_patient_home.this, view_seizure.class);
                startActivity(intent);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_patient_home.this, view_result.class);
                startActivity(intent);
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_patient_home.this, view_reminder.class);
                startActivity(intent);
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.SetIslogged(false);
                Intent intent = new Intent(Activity_patient_home.this, login.class);
                startActivity(intent);
            }
        });


        connectionClass = new ConnectionClass();
        argentPhoneList = new ArrayList();


        userid =login.GetUserId();

        try {
            getPhoneFromDataBase(userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        send.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)){
            send.setEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
    }

    private void getPhoneFromDataBase(String id) throws SQLException {
        argentPhoneList.clear();
        String sql = "select * from patientPhone where patient_ID = '" + id + "'";
        ResultSet rs = connectionClass.st.executeQuery(sql);

        if(rs.isBeforeFirst()) {
            while (rs.next()) {
                argentPhoneList.add(rs.getString(2));
            }
            rs.close();
        }
        else {
            Toast.makeText(this, "لا يوجد ارقام طوارئ مسجلة", Toast.LENGTH_LONG).show();
        }
    }

    public void sendmsg(View view) {
        String phoneNumber;
        String smsMessage = "لدي نوبة صرع احتاج إلى مساعدة!";

        if (argentPhoneList.size() >0) {
            for (int i = 0; i < argentPhoneList.size(); i++) {
                phoneNumber=argentPhoneList.get(i).toString();
                if(phoneNumber == null || phoneNumber.length() == 0 ||
                        smsMessage == null || smsMessage.length() == 0){
                    return;
                }
                if(checkPermission(Manifest.permission.SEND_SMS)){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, smsMessage, null, null);
                    Toast.makeText(this, "تم الإرسال!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "خطأ في الإرسال!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void openPatientInfo(View view) {
        startActivity(new Intent(this, patient_viewinfo.class));
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}
