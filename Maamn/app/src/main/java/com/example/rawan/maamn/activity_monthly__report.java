package com.example.rawan.maamn;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;

public class activity_monthly__report extends AppCompatActivity {

    private static final String TAG = "Monthly_Report";
    TextView tv_month;
    TextView tv_average;
    TextView tv_longest;
    TextView tv_shortest;

    String month, average, longest, shortest;

    int houre, minute, second;
    int[] arrayHoure;
    int[] arrayMinute;
    int[] arraySecond;

    Intent intent;
    String id;
    ConnectionClass connectionClass;
    ArrayList<Status_info> arrayList = new ArrayList();

    String duration1 = null, duration2 = null, duration3 = null;
    Spinner spinner_month;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly__report);


        intent = getIntent();
        id = intent.getStringExtra("id");

        tv_month = (TextView) findViewById(R.id.report_month);
        tv_average = (TextView) findViewById(R.id.report_average);
        tv_longest = (TextView) findViewById(R.id.report_longest);
        tv_shortest = (TextView) findViewById(R.id.report_shortest);
        spinner_month = (Spinner) findViewById(R.id.spinner_month);
        String[] monthes = new String[]{"اختيار", "01" , "02" , "03" , "04" , "05" , "06" , "07" , "08", "09", "10", "11", "12"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, monthes);
        spinner_month.setAdapter(adapter);

    /*    intent = getIntent();
        id = intent.getStringExtra("id");
        date = intent.getStringExtra("date");

        if(id != null & date != null){
            getReport();
        }   */


        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id1) {
                // id = "2";
                month = spinner_month.getSelectedItem().toString();
                if(!month.equals("اختيار"))

                    try {
                        getReport();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getReport() throws SQLException {

        tv_month.setText(month);

        connectionClass = new ConnectionClass();

        String userID = login.GetUserId();
        String sql = null;

        if(login.GetUserType().equals("1")){
            //  columName =  "caregiver_ID";
            sql = "select * from seizure where caregiver_ID = '" + userID + "'  ";

        } else if(login.GetUserType().equals("2")){
            // columName = "patient_ID";
            sql = "select * from seizure where patient_ID = '" + userID + "'  ";

        }
        // String sql = "select * from seizure where patient_ID = '" + userID + "'  ";

        Status_info statusinfo = new Status_info();
        arrayList.clear();

        ResultSet rs = connectionClass.st.executeQuery(sql);
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                String mdate = rs.getString("date");
                if (mdate.substring(mdate.indexOf("-"), mdate.indexOf("-")+3).contains(month)) {
                    Log.d(TAG, mdate);

                    statusinfo = new Status_info();
                    statusinfo.setPatient_id(id);
                    statusinfo.setDate(rs.getString("date"));   // date  - 0000-00-00
                    statusinfo.setTime(rs.getString("time"));  //    time  -  22:22:22
                    statusinfo.setDuration(rs.getString("duration"));  //   duration  -  00:00:00
                    statusinfo.setSei_id(rs.getString("sei_ID"));

                    arrayList.add(statusinfo);
                }
            }
            rs.close();
            Calculate();
            //    displayData();

        } else {
            Log.d(TAG, "لا يوجد نوبات مسجلة لهذا المستخدم");
            Toast.makeText(this, "لا يوجد نوبات مسجلة لهذا المستخدم", Toast.LENGTH_LONG).show();
            tv_average.setText("0");
            tv_longest.setText("0");
            tv_shortest.setText("0");
            tv_month.setText("0");

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Calculate() {

        if (arrayList.size() > 0) {


            String duration;

            arrayHoure = new int[arrayList.size()];
            arrayMinute = new int[arrayList.size()];
            arraySecond = new int[arrayList.size()];

            for (int i = 0; i < arrayList.size(); i++) {

                duration = arrayList.get(i).getDuration();
                Log.d("duraaaation", duration);

                houre = Integer.parseInt(duration.substring(0, (duration.indexOf(":"))));
                arrayHoure[i] = houre;
                duration1 = duration.substring(0, (duration.indexOf(":")));
                Log.d("duraaaation1", duration1);   //00

                minute = Integer.parseInt(duration.substring(3, 5));
                arrayMinute[i] = minute;
                duration2 = duration.substring(3, 5);
                Log.d("duraaaation2", duration2);

                second = Integer.parseInt(duration.substring(6, duration.length()));
                arraySecond[i] = second;
                duration3 = duration.substring(6, duration.length());
                Log.d("duraaaation3", duration3);

            }
            int totalHoure = 0;
            for (int i = 0; i < arrayHoure.length; i++) {
                totalHoure += arrayHoure[i];
            }

            int totalMinute = 0;
            for (int i = 0; i < arrayMinute.length; i++) {
                totalMinute += arrayMinute[i];
            }

            int totalSecond = 0;
            for (int i = 0; i < arraySecond.length; i++) {
                totalSecond += arraySecond[i];
            }

            Log.d("max", "" + totalHoure);
            Log.d("max", "" + totalMinute);
            Log.d("max", "" + totalSecond);

            double av1 = (totalHoure / arrayList.size());
            double av2 = (totalMinute / arrayList.size());
            double av3 = (totalSecond / arrayList.size());
            average = " " + av1 + " : " + av2 + " : " + av3;
            tv_average.setText(average);

            ////////////// max duration //////////////

            tv_month.setText("" + arrayList.size());

            ArrayList<String> arrayList1 = new ArrayList();
            arrayList1
                    .clear();

            for (int i = 0; i < arrayList.size(); i++) {

                arrayList1.add(arrayList.get(i).getDuration().toString());
            }

            String mdur = null;
            int h1 = 0;
            int maxHoure = 0;
            String smaxHoure = null;

            for (int i = 0; i < arrayList.size(); i++) {
                mdur = arrayList1.get(i);
                Log.d("durations", "" + mdur);

                String mhour = mdur.substring(0, 2);
                Log.d("durations2", "" + mhour);

                int h = Integer.parseInt(mhour);
                if (h1 < h) {
                    h1 = h;
                }
            }

            maxHoure = h1;
            if (maxHoure >= 0 & maxHoure <= 9) {
                smaxHoure = "0" + maxHoure;
            } else {
                smaxHoure = "" + maxHoure;
            }
            Log.d("maxHoure", smaxHoure);


            int m1 = 0;
            int maxMinute = 0;
            String smaxMinute = null;

            for (int i = 0; i < arrayList.size(); i++) {
                mdur = arrayList1.get(i);

                if (mdur.startsWith(smaxHoure + ":")) {
                    String mminute = mdur.substring(3, 5);
                    int m = Integer.parseInt(mminute);
                    if (m1 < m) {
                        m1 = m;

                    }
                }
            }
            maxMinute = m1;
            if (maxMinute >= 0 & maxMinute <= 9) {
                smaxMinute = "0" + maxMinute;
            } else {
                smaxMinute = "" + maxMinute;
            }

            Log.d("maxMinute", smaxMinute);

            int s1 = 0;
            int maxSecond = 0;
            String smaxSecond = null;

            for (int i = 0; i < arrayList.size(); i++) {
                mdur = arrayList1.get(i);

                if (mdur.startsWith(smaxHoure + ":" + smaxMinute + ":")) {
                    String msecond = mdur.substring(mdur.length() - 2);
                    int s = Integer.parseInt(msecond);
                    if (s1 < s) {
                        s1 = s;
                    }
                }
            }

            maxSecond = s1;
            if (maxSecond >= 0 & maxSecond <= 9) {
                smaxSecond = "0" + maxSecond;
            } else {
                smaxSecond = "" + maxSecond;
            }

            Log.d("maxSecond", smaxSecond);
            Log.d("longest", smaxHoure + ":" + smaxMinute + ":" + smaxSecond);
            String longest = smaxHoure + ":" + smaxMinute + ":" + smaxSecond;
            tv_longest.setText(longest);

            ////////////// min duration //////////////

            String mdur1 = null;
            int h2 = Integer.parseInt(arrayList1.get(0).substring(0, 2));
            int minHoure;
            String sminHoure = null;

            for (int i = 0; i < arrayList1.size(); i++) {
                mdur1 = arrayList1.get(i);
                String mhour = mdur1.substring(0, 2);
                int h = Integer.parseInt(mhour);
                if (h2 > h) {
                    h2 = h;
                }
            }
            minHoure = h2;
            if (minHoure >= 0 & minHoure <= 9) {
                sminHoure = "0" + minHoure;
            } else {
                sminHoure = "" + minHoure;
            }
            Log.d("minHoure", sminHoure);


            int m2 = 0;

            int minMinute = 0;
            String sminMinute = null;
            ArrayList<String> arrSpec = new ArrayList<>();
            arrSpec.clear();

            for (int i = 0; i < arrayList1.size(); i++) {
                mdur1 = arrayList1.get(i);

                if (mdur1.startsWith(sminHoure + ":")) {
                    arrSpec.add(mdur1);
                }
            }

            for (int i = 0; i < arrSpec.size(); i++) {
                m2 = Integer.parseInt(arrSpec.get(0).substring(3, 5));
                if (m2 >= (Integer.parseInt(arrSpec.get(i).substring(3, 5)))) {
                    m2 = Integer.parseInt(arrSpec.get(i).substring(3, 5));
                }
            }
            minMinute = m2;
            if (minMinute >= 0 & minMinute <= 9) {
                sminMinute = "0" + minMinute;
            } else {
                sminMinute = "" + minMinute;
            }
            Log.d("minMinute", sminMinute);


            ArrayList<String> arrSpec2 = new ArrayList<>();
            arrSpec2.clear();

            int s2 = 0;
            int minSecond = 0;
            String sminSecond = null;

            for (int i = 0; i < arrayList1.size(); i++) {
                mdur1 = arrayList1.get(i);
                if (mdur1.startsWith(sminHoure + ":" + sminMinute + ":")) {
                    arrSpec2.add(mdur1);
                }
            }

            for (int i = 0; i < arrSpec2.size(); i++) {

                s2 = Integer.parseInt((arrSpec2.get(0)).substring(arrSpec2.get(0).length() - 2));

                mdur1 = arrSpec2.get(i);
                String msecond = mdur1.substring(mdur1.length() - 2);
                int s = Integer.parseInt(msecond);
                if (s2 >= s) {
                    s2 = s;
                }
            }
            minSecond = s2;
            if (minSecond >= 0 & minSecond <= 9) {
                sminSecond = "0" + minSecond;
            } else {
                sminSecond = "" + minSecond;
            }

            Log.d("minSecond", sminSecond);
            Log.d("shortest", sminHoure + ":" + sminMinute + ":" + sminSecond);
            String shortest = sminHoure + ":" + sminMinute + ":" + sminSecond;
            tv_shortest.setText(shortest);

        } else {
            Toast.makeText(this, "لا يوجد نوبات مسجلة خلال هذا الشهر", Toast.LENGTH_LONG).show();
            tv_average.setText("0");
            tv_longest.setText("0");
            tv_shortest.setText("0");
            tv_month.setText("0");
        }
    }

    private void displayData() {

        tv_month.setText(month);
        tv_average.setText(average);
        tv_longest.setText(longest);
        tv_shortest.setText(shortest);
    }
}
