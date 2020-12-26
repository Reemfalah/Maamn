package com.example.rawan.maamn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

public class view_result extends AppCompatActivity {

    private static final String TAG = "view_result";
    TextView Tv1;
    TextView Tv2;
    TextView Tv3;

    result res ;

    Intent intent;
    String id;

    ConnectionClass connectionClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);
        if(login.GetIslogged()==false){
            Intent intent = new Intent(view_result.this, login.class);
            startActivity(intent);
        }
        intent = getIntent();
        id = intent.getStringExtra("id");

        Tv1 = (TextView) findViewById(R.id.Tv1);
        Tv2 = (TextView) findViewById(R.id.Tv2);
        Tv3 = (TextView) findViewById(R.id.Tv3);

        id = login.GetUserId();

        try {
            getDataFromDatabase(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getDataFromDatabase(String id) throws SQLException {



        connectionClass = new ConnectionClass();


        try {
            String sql = null;
            //String columName = null;
            String userID = login.GetUserId();


            if(login.GetUserType().equals("1")){
              //  columName =  "caregiver_ID";
                sql = "select * from classification_result where caregiver_ID = '" + userID + "'";

            } else if(login.GetUserType().equals("2")){
               // columName = "patient_ID";
                sql = "select * from classification_result where patient_ID = '" + userID + "'";

            }
            //    String sql = "select * from seizure where patient_ID = '" + id + "' ORDER BY date";
            //sql = "select * from classification_result where "+columName+" = '" + id + "'";
            ResultSet rs = connectionClass.st.executeQuery(sql);


            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    //     String sei_ID, date, time, duration, description, patient_ID;
                    res = new result();
                    res.setRes(rs.getString(3));
                    res.setRdate(rs.getString(4));
                    res.setDesc(rs.getString(2));
                    displayData();
                }
            }

            else {
                Log.d(TAG, "لا يوجد نتائج مسجلة");
                Toast.makeText(this, "لا يوجد نتائج مسجلة", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }

    }

    private void displayData() {

        Tv1.setText(res.getRes());
        Tv2.setText(res.getRdate());
        Tv3.setText(res.getDesc());

    }
}
