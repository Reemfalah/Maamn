package com.example.rawan.maamn;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.DriverManager;
import java.sql.Connection;
import java.util.Date;
import java.util.Locale;

import static com.example.rawan.maamn.ConnectionClass.con;

public class addReminder extends AppCompatActivity {

    Button btn_add;
    //   Spinner spinner1;
    EditText et_medname, et_med_drug, et_med_time;
    EditText et_date;

    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;

    private String[] listofdays;
    private boolean[] checkeddays;
    private ArrayList<Integer> userDays = new ArrayList<>();
    Calendar dateCal;

    ConnectionClass connectionClass;

    final static String TAG= "addReminder";

    String medName,drug,remTime,medday;
    Calendar timeCal;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_add =(Button) findViewById(R.id.btn_add);

        et_medname =(EditText) findViewById(R.id.add_med_name);
        et_med_drug =(EditText) findViewById(R.id.add_med_drug);
        et_med_time =(EditText) findViewById(R.id.add_med_time);
        et_med_time.setFocusable(false);
        et_date =(EditText) findViewById(R.id.add_med_date);

        spinner =(Spinner) findViewById(R.id.add_med_spin);

        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(addReminder.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.days));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //    spinner1.setAdapter(myadapter);
        //spinner1.setOnItemSelectedListener(this);

        connectionClass = new ConnectionClass();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        et_med_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minut = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog( addReminder.this,R.style.TimePickerTheme,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeCal = Calendar.getInstance();
                        timeCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        timeCal.set(Calendar.MINUTE, minut);

//                        if (hourOfDay >= 12) {
//                            amPm = "PM";
//                        }else {
//                            amPm = "AM";
//                        }

                        et_med_time.setText(String.format("%02d:%02d",hourOfDay,minute));
                        remTime = String.format("%02d:%02d",hourOfDay,minute);

                        //editText3.setText(hourOfDay+":"+minute+amPm);
                    }
                },hour,minut, true);
                timePickerDialog.show();
            }
        });

        dateCal = Calendar.getInstance();
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcalendar = Calendar.getInstance();
                new DatePickerDialog(addReminder.this,R.style.DatePickerTheme,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateCal = Calendar.getInstance();
                        dateCal.set(Calendar.YEAR, year);
                        dateCal.set(Calendar.MONTH, month);
                        dateCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        medday = ""+ dateCal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                        et_date.setText(medday);
                    }
                },
                        mcalendar.get(Calendar.YEAR), mcalendar.get(Calendar.MONTH),
                        mcalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /***     spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String selectedItem = parent.getItemAtPosition(i).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
        });   */

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                medName = et_medname.getText().toString();
                drug = et_med_drug.getText().toString();
                remTime = et_med_time.getText().toString();

                if(isValid(medName,drug,remTime)){
                    Statement stmt;
                    Connection con;

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(ConnectionClass.url, ConnectionClass.un, ConnectionClass.password);
                        stmt = con.createStatement();
                        String userID = login.GetUserId();
                        int result = 0, resultt = 0;

                        //  String pID="2";
                        // String cID="3";
                        String med_date = et_date.getText().toString();

//                        Toast test = Toast.makeText(addSeizure.this, "\n" + da, Toast.LENGTH_SHORT);
//                        test.show();
                        //    String rem_ID, med_name, dose, medtime, day, patient_ID, caregiver_ID;
                        String rem_id = ""+((new Date().getTime())/1000);


                        if(login.GetUserType().equals("1")){
                            String sql="INSERT INTO `medication_reminder`VALUES ( '"
                                    +rem_id+"', '"+medName+"' , '"+drug +"', '"+remTime+":00"+"' , '"+med_date+"' , NULL , '"+userID+"' )";
                            result = stmt.executeUpdate(sql);

                        }
                        else if(login.GetUserType().equals("2")){
                            String sql2="INSERT INTO `medication_reminder`VALUES ( '"
                                    +rem_id+"', '"+medName+"' , '"+drug +"', '"+remTime+":00"+"' , '"+med_date+"' , '"+userID+"' , NULL )";
                            resultt = stmt.executeUpdate(sql2);

                        }

                        if (result == 1 || resultt == 1) {
                            Toast done = Toast.makeText(addReminder.this, " تمت الإضافة ", Toast.LENGTH_LONG);
                            addAlarm(spinner.getSelectedItem().toString());
                            Toast.makeText(addReminder.this, "تمت الإضافة بنجاح ", Toast.LENGTH_LONG).show();
                            done.show();
                            finish();
                            Intent k = new Intent(addReminder.this, view_reminder.class);
                            startActivity(k);
                        } else {
                            Toast done = Toast.makeText(addReminder.this, " عذرًاً" + "\n" + "حدث مشكلة حاول مجددًاً ", Toast.LENGTH_LONG);
                            done.show();
                        }

                    }
                    catch (SQLException se){
                        se.printStackTrace();
                    }
                    catch (Exception e){
                        Toast errorToast = Toast.makeText(addReminder.this, " "+e.getMessage() ,Toast.LENGTH_LONG);
                        errorToast.show();
                    }
                }
            }
        });
    }

    private void addAlarm(String interval) {

        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.set(
                dateCal.get(Calendar.YEAR),
                dateCal.get(Calendar.MONTH),
                dateCal.get(Calendar.DAY_OF_MONTH),
                timeCal.get(Calendar.HOUR_OF_DAY),
                timeCal.get(Calendar.MINUTE),
                0 );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alertIntent = new Intent(this, AlarmBroadcastReciver.class);
        alertIntent.putExtra("name", medName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1 , alertIntent, 0);
        //   alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(),pendingIntent );

        // repeated every day
        if(interval.equals("تكرار يومي")) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(),  24*60*60*1000 , pendingIntent);
        }
        else if(interval.equals("تكرار اسبوعي")) {
            // repeated every day in a weak
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }

        Toast.makeText(this, "Alarm set.", Toast.LENGTH_LONG).show();
    }

    public boolean isValid(String medName , String drug , String remTime) {
        //validate all inputs
        if (medName.equals("")) {
            et_medname.setError("يجب ملئ الخانة");
            return false; }
        if (drug.equals("")) {
            et_med_drug.setError("يجب ملئ الخانة");
            return false; }
        if (remTime.equals("")) {
            et_med_time.setError("يجب ملئ الخانة");
            return false; }
        return true;
    }
}
