package com.example.rawan.maamn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

public class view_seizure extends AppCompatActivity {

    private static final String TAG = "view_seizure";
    ConnectionClass connectionClass;

    private ImageButton imageButton;
    private ImageView imageView5;
    private ImageButton imageButton2;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;
    private ImageButton imageButton7;
    private ImageButton imageButton17;

    ListView listView;
    Seizure seizure;
    ArrayList<Seizure> arrayList = new ArrayList<>();
    Adapter_Seizure adapter_seizure;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_seizure);
        if(login.GetIslogged()==false){
            Intent intent = new Intent(view_seizure.this, login.class);
            startActivity(intent);
        }
        imageButton = (ImageButton) findViewById (R.id.imageButtonh1); //home
        imageView5 = (ImageButton) findViewById(R.id.ImageButtons1); //s
        imageButton2 = (ImageButton) findViewById(R.id.ImageButtor1);//r
        imageButton4=(ImageButton) findViewById(R.id.ImageButtonm1);//m
        imageButton5=(ImageButton)findViewById(R.id.ImageButtonl1);//l
        imageButton6=(ImageButton) findViewById(R.id.imageButton66); //add
        imageButton7=(ImageButton)findViewById(R.id.imageButton7); //report
        imageButton17 =(ImageButton)findViewById(R.id.imageButtonup1); //profile


        //////////

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(view_seizure.this, caregiverHome.class);
//                startActivity(intent);

                if (login.GetUserType().equals("1")) {
                    Intent intent = new Intent(view_seizure.this, caregiverHome.class);
                    startActivity(intent);
                }else if (login.GetUserType().equals("2")) {
                    Intent intent = new Intent(view_seizure.this, Activity_patient_home.class);
                    startActivity(intent);
                }
            }
        });

        imageButton17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(view_seizure.this, caregiverHome.class);
//                startActivity(intent);

                if (login.GetUserType().equals("1")) {
                    Intent intent = new Intent(view_seizure.this, caregiverHome.class);
                    startActivity(intent);
                }else if (login.GetUserType().equals("2")) {
                    Intent intent = new Intent(view_seizure.this, Activity_patient_home.class);
                    startActivity(intent);
                }
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_seizure.this, view_seizure.class);
                startActivity(intent);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_seizure.this, view_result.class);
                startActivity(intent);
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_seizure.this, view_reminder.class);
                startActivity(intent);
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.SetIslogged(false);
                Intent intent = new Intent(view_seizure.this, login.class);
                startActivity(intent);
            }
        });

        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(view_seizure.this, addSeizure.class);
                startActivity(intent);
            }
        });

        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(view_seizure.this, activity_monthly__report.class);
                startActivity(intent);
            }
        });

        //String id_c = "6";
        //String id_p = "0";
        arrayList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.seizure_list);

        connectionClass = new ConnectionClass();
        seizure = new Seizure();

        try {
            String sql = null;
            //String columName = null;
            String userID = login.GetUserId();

            if(login.GetUserType().equals("1")){
                //columName =  "caregiver_ID";
                sql = "select * from seizure where caregiver_ID = '" + userID + "' ORDER BY date";

            } else if(login.GetUserType().equals("2")){
                //columName = "patient_ID";
                sql = "select * from seizure where patient_ID = '" + userID + "' ORDER BY date";

            }
            //    String sql = "select * from seizure where patient_ID = '" + id + "' ORDER BY date";
            ResultSet rs = connectionClass.st.executeQuery(sql);
            arrayList.clear();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    //     String sei_ID, date, time, duration, description, patient_ID;
                    seizure = new Seizure();
                    seizure.setSei_ID(rs.getString(1));
                    seizure.setDate(rs.getString(2));
                    seizure.setTime(rs.getString(3));
                    seizure.setDuration(rs.getString(4));
                    seizure.setDescription(rs.getString(5));
                    seizure.setPatient_ID(rs.getString(6));
                    seizure.setCaregiver_ID(rs.getString(7));

                    arrayList.add(seizure);
                }
                adapter_seizure = new Adapter_Seizure(view_seizure.this, arrayList);
                listView.setAdapter(adapter_seizure);
            }

            else {
                Log.d(TAG, "لا يوجد نوبات مسجلة");
                Toast.makeText(this, "لا يوجد نوبات مسجلة", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }
    }
}
