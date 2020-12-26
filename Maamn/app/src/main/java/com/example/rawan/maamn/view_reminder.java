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

public class view_reminder extends AppCompatActivity {

    private static final String TAG = "view_reminder";
    ConnectionClass connectionClass;

    private ImageButton imageButton;
    private ImageButton imageView5;
    private ImageButton imageButton2;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton ImageButton11;
    private ImageButton imageButton67;

    ListView listView;
    Medication medication;
    ArrayList<Medication> arrayList = new ArrayList<>();
    Adapter_Medication adapter_medication;
    String p_id;
    String c_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);
        if(login.GetIslogged()==false){
            Intent intent = new Intent(view_reminder.this, login.class);
            startActivity(intent);
        }
        imageButton = (ImageButton) findViewById (R.id.imageButton_home);
        imageButton67 = (ImageButton) findViewById (R.id.imageButtonu1);
        imageView5 = (ImageButton) findViewById(R.id.ImageButton_sez);
        imageButton2 = (ImageButton) findViewById(R.id.ImageButton_result);
        imageButton4=(ImageButton) findViewById(R.id.ImageButton_reminder);
        imageButton5=(ImageButton)findViewById(R.id.ImageButton_login);
        ImageButton11 =(ImageButton)findViewById(R.id.ImageButton11);

        //////////

        ImageButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_reminder.this, addReminder.class);
                startActivity(intent);
            }
        });

        //profile
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.GetUserType().equals("1")) {
                    Intent intent = new Intent(view_reminder.this, caregiver_viewinfo.class);
                    startActivity(intent);
                }else if (login.GetUserType().equals("2")) {
                    Intent intent = new Intent(view_reminder.this, patient_viewinfo.class);
                    startActivity(intent);
                }
            }
        });
        //home
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.GetUserType().equals("1")) {
                    Intent intent = new Intent(view_reminder.this, caregiverHome.class);
                    startActivity(intent);
                }else if (login.GetUserType().equals("2")) {
                    Intent intent = new Intent(view_reminder.this, Activity_patient_home.class);
                    startActivity(intent);
                }
            }
        });
        //Seizure
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_reminder.this, view_seizure.class);
                startActivity(intent);
            }
        });
        //Result
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_reminder.this, view_result.class);
                startActivity(intent);
            }
        });
        //Reminder
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_reminder.this, view_reminder.class);
                startActivity(intent);
            }
        });
        //Logout
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.SetIslogged(false);
                Intent intent = new Intent(view_reminder.this, login.class);
                startActivity(intent);
            }
        });


        //p_id = "2";
       // c_id = "3";

        arrayList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_med);

        connectionClass = new ConnectionClass();
        medication = new Medication();

        try {
            String sql = null;
            String userID = login.GetUserId();

            if(login.GetUserType().equals("1")){
               sql  = "select * from medication_reminder where caregiver_ID = '"+userID+"' ";
          //      sql  = "select * from medication_reminder where patient_ID = '" + p_id + "' and caregiver_ID = '"+c_id+"'";

            }
            else if(login.GetUserType().equals("2")){
                sql = "select * from medication_reminder where patient_ID = '" + userID + "' ";

            }
            ResultSet rs = connectionClass.st.executeQuery(sql);
            arrayList.clear();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {

                    //         String rem_ID, med_name, dose, medtime, day, patient_ID, caregiver_ID;
                    medication = new Medication();
                    medication.setRem_ID(rs.getString(1));
                    medication.setMed_name(rs.getString(2));
                    medication.setDose(rs.getString(3));
                    medication.setMedtime(rs.getString(4));
                    medication.setDay(rs.getString(5));
                    medication.setPatient_ID(rs.getString(6));
                    medication.setCaregiver_ID(rs.getString(7));

                    arrayList.add(medication);
                }
                adapter_medication = new Adapter_Medication(view_reminder.this, arrayList);
                listView.setAdapter(adapter_medication);
            }

            else {
                Log.d(TAG, "لا يوجد تذكيرات ");
                Toast.makeText(this, "لا يوجد تذكيرات", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }
    }
}