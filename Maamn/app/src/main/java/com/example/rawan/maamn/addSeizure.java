package com.example.rawan.maamn;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;


public class addSeizure extends AppCompatActivity {
    TextView textView,textView3,textView4, textView5,textView2;
    View view;
    Button button3, button4;
    ImageView imageView;
    EditText editText2, editText3,editText4, editText5;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    String amPm;
    ConnectionClass connectionClass;
    String ti,da,duration,des;
    boolean dayy=false,monthh=false;
    int day,year1, month1;
    private String LOG_TAG = "addSeizure";
    private boolean doneOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seizure);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textView = (TextView) findViewById(R.id.textView);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView2 = (TextView) findViewById(R.id.textView2);
        view = (View) findViewById(R.id.view);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        connectionClass = new ConnectionClass();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        editText4.setFocusable(false);
        editText3.setFocusable(false);

        editText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year1 = calendar.get(Calendar.YEAR);
                month1 = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(addSeizure.this, R.style.DatePickerTheme ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if(dayOfMonth<9){
                            dayy=true;
                        }
                        if (month<9){
                            monthh=true;
                        }
                        if(dayy==true||monthh==true){
                            if(dayy==true&&monthh==true){
                                editText4.setText(year+"-0"+(month+1 )+"-0"+dayOfMonth);
                            }else if(dayy==true&&monthh==false){
                                editText4.setText(year+"-"+(month+1 )+"-0"+dayOfMonth);
                            }else if(dayy==false&&monthh==true){
                                editText4.setText(year+"-0"+(month+1 )+"-"+dayOfMonth);
                            }
                        }else{
                            editText4.setText(year+"-"+(month+1 )+"-"+dayOfMonth);
                        }

                    }



                },year1,month1,day);

                datePickerDialog.show();


            }
        });
        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minut = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog( addSeizure.this,R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        if (hourOfDay >= 12) {
//                            amPm = "PM";
//                        }else {
//                            amPm = "AM";
//                        }
                        editText3.setText(String.format("%02d:%02d",hourOfDay,minute));

                        //editText3.setText(hourOfDay+":"+minute+amPm);
                    }
                },hour,minut, true);
                timePickerDialog.show();
            }
        });
        editText5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFilter timeFilter;
                timeFilter  = new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                               int dstart, int dend) {

                        if(source.length() > 1 && doneOnce == false){
                            source = source.subSequence(source.length()-1, source.length());
                            if(source.charAt(0)  >= '0' && source.charAt(0) <= '2'){
                                doneOnce = true;
                                return source;
                            }else{
                                return "";
                            }
                        }


                        if (source.length() == 0) {
                            return null;// deleting, keep original editing
                        }
                        String result = "";
                        result += dest.toString().substring(0, dstart);
                        result += source.toString().substring(start, end);
                        result += dest.toString().substring(dend, dest.length());

                        if (result.length() > 5) {
                            Toast done = Toast.makeText(addSeizure.this, " الفترة يجن أن تكون(00:00) ", Toast.LENGTH_LONG);
                            done.show();// do not allow this edit
                        }
                        boolean allowEdit = true;
                        char c;
                        if (result.length() > 0) {
                            c = result.charAt(0);
                            allowEdit &= (c >= '0' && c <= '2');
                        }
                        if (result.length() > 1) {
                            c = result.charAt(1);
                            if(result.charAt(0) == '0' || result.charAt(0) == '1')
                                allowEdit &= (c >= '0' && c <= '9');
                            else
                                allowEdit &= (c >= '0' && c <= '3');
                        }
                        if (result.length() > 2) {
                            c = result.charAt(2);
                            allowEdit &= (c == ':');
                        }
                        if (result.length() > 3) {
                            c = result.charAt(3);
                            allowEdit &= (c >= '0' && c <= '5');
                        }
                        if (result.length() > 4) {
                            c = result.charAt(4);
                            allowEdit &= (c >= '0' && c <= '9');
                        }
                        return allowEdit ? null : "";
                    }

                };


                editText5 = (EditText) findViewById(R.id.editText5);
                editText5.setFilters(new InputFilter[] { timeFilter });


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

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ti=editText3.getText().toString();
                da = editText4.getText().toString();
                des = editText2.getText().toString();
                duration = editText5.getText().toString();
                if (isValid(da, ti, duration, des)) {
//true
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Connection con;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(ConnectionClass.url, ConnectionClass.un, ConnectionClass.password);
                        stmt = con.createStatement();
                        String userID = login.GetUserId();
                        int result = 0, resultt = 0;
                        if ( login.GetUserType().equals("2")) {
//                        Toast test = Toast.makeText(addSeizure.this, "\n" + da, Toast.LENGTH_SHORT);
//                        test.show();
                            String sql = "INSERT INTO `seizure` (`sei_ID`, `date`, `time`, `duration`, `description`, `patient_ID`, `caregiver_ID`) VALUES (NULL, '" + da + "', '" + ti + ":00" + "', '" + "00:" + duration + "', '" + des + "', '" + userID + "',NULL)";
                            resultt = stmt.executeUpdate(sql);
                        }else if (login.GetUserType().equals("1")) {
//                        Toast test = Toast.makeText(addSeizure.this, "\n" + da, Toast.LENGTH_SHORT);
//                        test.show();
                            String sql1 = "INSERT INTO `seizure` (`sei_ID`, `date`, `time`, `duration`, `description`, `patient_ID`,`caregiver_ID`) VALUES (NULL, '" + da + "', '" + ti + ":00" + "', '" + "00:" + duration + "', '" + des + "', NULL,'" + userID + "')";
                            result = stmt.executeUpdate(sql1);

                        }

                        if (result == 1 || resultt == 1) {
                            Toast done = Toast.makeText(addSeizure.this, " تمت الإضافة ", Toast.LENGTH_LONG);
                          //  Toast.makeText(addSeizure.this, "تمت الإضافة بنجاح ", Toast.LENGTH_LONG).show();
                            done.show();
                           // finish();
                            Intent k = new Intent(addSeizure.this, view_seizure.class);
                            startActivity(k);
                        } else {
                            Toast done = Toast.makeText(addSeizure.this, " عذرًاً" + "\n" + "حدث مشكلة حاول مجددًاً ", Toast.LENGTH_LONG);
                            done.show();
                        }
                        stmt.close();
                        con.close();
                    }
                    catch (SQLException se){
                        Toast errorToast = Toast.makeText(addSeizure.this, "يجب أن تكون متصلا ًبالانترنت! " ,Toast.LENGTH_LONG);
                        errorToast.show();
                        se.printStackTrace();
                    }
                    catch (Exception e){
                        Toast errorToast = Toast.makeText(addSeizure.this, " "+e.getMessage() ,Toast.LENGTH_LONG);
                        errorToast.show();

                    }
                }

//




            }
        });

    }
    public boolean isValid(String da , String ti , String duration , String des) {
        //validate all inputs
        if (da.equals("")) {
            editText4.setError("يجب ملئ الخانة");
            return false; }
        if (ti.equals("")) {
            editText3.setError("يجب ملئ الخانة");

            return false; }
        if (duration.equals("")) {
            editText5.setError("يجب ملئ الخانة");
            return false; }
        if (des.equals("")) {
            editText2.setError("يجب ملئ الخانة");
            return false; }
        return true;
    }

}


