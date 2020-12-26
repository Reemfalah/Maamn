package com.example.rawan.maamn;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class patient_viewinfo extends AppCompatActivity {

    private static final String TAG = "patient_viewinfo";
    EditText et_name;
    EditText et_username;
    EditText et_email;
    EditText et_password;

    String name, user_name, email, password, argentPhone1, oldUN,oldPass;
    ListView lv_agrentphone;
    ArrayList<String> argentPhonesList;
    ArrayAdapter adapter;

    Intent intent;
    String id;

    ConnectionClass connectionClass;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_viewinfo);

        intent = getIntent();
        id = intent.getStringExtra("id");

  /*      if(id != null) {
             try {
            getDataFromDatabase(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        */

        et_name = (EditText) findViewById(R.id.patient_name);
        et_username = (EditText) findViewById(R.id.patient_username);
        et_email = (EditText) findViewById(R.id.patient_email);
        et_password = (EditText) findViewById(R.id.patient_password);
        lv_agrentphone = (ListView) findViewById(R.id.patient_argentphones);
        argentPhonesList = new ArrayList<>();

        id = login.GetUserId();
        try {
            getDataFromDatabase(id);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        lv_agrentphone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                argentPhone1 = argentPhonesList.get(position);
                openDialog();
            }
        });
    }

    Dialog dialog;
    private void openDialog() {

        final View view = ((LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog1
                , null);
        view.findViewById(R.id.dialog_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        view.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        view.findViewById(R.id.dialog_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialognewPhone();
                dialog.cancel();
            }
        });
        view.findViewById(R.id.dialog_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDoialoge();
                dialog.cancel();
            }
        });

        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setCancelable(true);
        dialog.setContentView(view);
        dialog.show();
    }

    private void showDeleteDoialoge() {

        new AlertDialog.Builder(this)
                .setTitle("حذف")
                .setMessage("هل تريد حذف ارقام الطوارئ")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        String sql_delete = "delete from patientphone where patient_ID = '" + id +
                                "' and importantNumber= '" + argentPhone1 + "'";

                        int deleted = 0;
                        try {
                            deleted = connectionClass.st.executeUpdate(sql_delete);

                            if (deleted > 0) {
                                String sql22 = "select importantNumber from patientphone where patient_ID = '" + id + "'";
                                ResultSet rs22 = connectionClass.st.executeQuery(sql22);
                                argentPhonesList.clear();
                                if (rs22.next()) {
                                    while (rs22.next()) {
                                        argentPhonesList.add(rs22.getString(1));
                                    }
                                    rs22.close();

                                    if (argentPhonesList.size() > 0) {
                                        adapter = new ArrayAdapter(patient_viewinfo.this, R.layout.support_simple_spinner_dropdown_item, argentPhonesList);
                                        lv_agrentphone.setAdapter(adapter);
                                        Toast.makeText(patient_viewinfo.this, "تم التحديث بنجاح", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(patient_viewinfo.this, "تم حذف الرقم", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    Dialog dialog2;
    private void showDialognewPhone() {

        final View view = ((LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog2
                , null);
        view.findViewById(R.id.dialog_root2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.cancel();
            }
        });
        view.findViewById(R.id.dialog_cancel2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.cancel();
            }
        });

        final EditText et_phone = (EditText) view.findViewById(R.id.newphone);
        view.findViewById(R.id.dialog_edit2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPhone = et_phone.getText().toString();
                if(newPhone.equals("")) {
                    Toast.makeText(patient_viewinfo.this, "لا يوجد رقم", Toast.LENGTH_SHORT).show();
                }
                else {

                    ///////////////// update argent phone

                    String sql3 = "update patientphone set importantNumber = '" + newPhone +
                            "' where importantNumber = '" + argentPhone1 + "' and patient_ID = '" + id + "' ";

                    int result2 = 0;
                    try {
                        result2 = connectionClass.st.executeUpdate(sql3);

                        if (result2 > 0) {

                            String sql2 = "select importantNumber from patientphone where patient_ID = '" + id + "'";
                            ResultSet rs2 = connectionClass.st.executeQuery(sql2);
                            argentPhonesList.clear();
                            if(rs2.isBeforeFirst()) {
                                while (rs2.next()) {
                                    argentPhonesList.add(rs2.getString(1));
                                }
                                rs2.close();

                                if(argentPhonesList.size() > 0) {
                                    adapter = new ArrayAdapter(patient_viewinfo.this, R.layout.support_simple_spinner_dropdown_item, argentPhonesList);
                                    lv_agrentphone.setAdapter(adapter);
                                    Toast.makeText(patient_viewinfo.this, "تم التحديث بنجاح", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(patient_viewinfo.this, "لا يوجد رقم", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(patient_viewinfo.this, "لا يوجد ارقام طوارئ مسجلة", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(patient_viewinfo.this, "حدثت مشكلة حاول مجدداً", Toast.LENGTH_SHORT).show();

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                dialog2.cancel();

            }
        });

        dialog2 = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog2.setCancelable(true);
        dialog2.setContentView(view);
        dialog2.show();



    }

    private void getDataFromDatabase(String id) throws SQLException {

        patient = new Patient();
        connectionClass = new ConnectionClass();

        String sql = "select * from patient where patient_ID = '" + id + "' ";
        ResultSet rs = connectionClass.st.executeQuery(sql);

        if(rs.isBeforeFirst()) {
            while (rs.next()) {
                patient.setId(rs.getString(1));
                patient.setName(rs.getString(2));
                patient.setUsername(rs.getString(3));
                oldUN=rs.getString(3);
                patient.setEmail(rs.getString(4));
                patient.setPassword(rs.getString(5));
                oldPass=rs.getString(5);

                Log.d(TAG, patient.email + patient.name);
            }
        }

        else{
            Toast.makeText(this, "لا يوجد معلومات مخزنة", Toast.LENGTH_SHORT).show();
        }
        ///////////// get argent phone /////////////////////


        String sql2 = "select importantNumber from patientphone where patient_ID = '" + id + "'";
        ResultSet rs2 = connectionClass.st.executeQuery(sql2);
        argentPhonesList.clear();
        if(rs2.isBeforeFirst()) {
            while (rs2.next()) {
                argentPhonesList.add(rs2.getString(1));
            }
        }

        else {
            Toast.makeText(this, "لا يوجد معلومات مسجلة لهذا المستخدم", Toast.LENGTH_SHORT).show();
        }

        if(patient.getEmail() != null & (argentPhonesList.size()>0)){
            displayData();
        }

    }

    public void updatepatientData(View view) throws SQLException {

        name = et_name.getText().toString();
        user_name = et_username.getText().toString();
        email = et_email.getText().toString();
        password = et_password.getText().toString();

        // if(!name.isEmpty() & !user_name.isEmpty() & !email.isEmpty() & !password.isEmpty()& !argentPhonesList.isEmpty()) {
        if (isValid(name, user_name, email, password)) {
//            //database connection
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection con;
            Statement stmt;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(ConnectionClass.url, ConnectionClass.un, ConnectionClass.password);

                stmt = con.createStatement();
                if (!(oldUN.equals(user_name))) {
                    String q = "SELECT * FROM patient WHERE user_name='" + user_name + "'";
                    String q2 = "SELECT * FROM caregiver WHERE user_name='" + user_name + "'";

                    ResultSet resultSet99 = stmt.executeQuery(q);
                    int isthere = 0;
                    int ishere2 = 0;
                    while (resultSet99.next()) {
                        isthere++;
                    }
                    ResultSet resultSet77 = stmt.executeQuery(q2);
                    while (resultSet77.next()) {
                        ishere2++;
                    }
                    if (isthere > 0 || ishere2 > 0) {
                        Toast errorToast = Toast.makeText(patient_viewinfo.this, "اسم المستخدم مسجل مسبقاً", Toast.LENGTH_SHORT);
                        errorToast.show();
                    } } else {


                    connectionClass = new ConnectionClass();
//                    if(!(password.equals(oldPass))){
//                        password = md5(password);
//                    }

                    String sql2 = "update patient set name = '" + name + "', user_name = '" + user_name + "' , email = '" + email
                            + "', password = '" + password + "' where patient_ID = '" + id + "'";

                    int result = stmt.executeUpdate(sql2);

                    if (result>0){
                        Toast.makeText(this, "تم تحديث المعلومات بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(patient_viewinfo.this, caregiverHome.class);
                        startActivity(intent);}
                    else
                        Toast.makeText(this, "حدثت مشكلة ! حاول مجدداً", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException se) {
                Toast errorToast = Toast.makeText(patient_viewinfo.this, "الرجاء التأكد من اتصال الانترنت!", Toast.LENGTH_SHORT);
                errorToast.show();
                se.printStackTrace();
            } catch (Exception e) {
                Toast errorToast = Toast.makeText(patient_viewinfo.this, " " + e.getMessage(), Toast.LENGTH_SHORT);
                errorToast.show();
            }
        }

    }
    private void displayData() {

        et_name.setText(patient.getName());
        et_username.setText(patient.getUsername());
        et_email.setText(patient.getEmail());
        et_password.setText(patient.getPassword());

        if(argentPhonesList.size() > 0) {
            adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, argentPhonesList);
            lv_agrentphone.setAdapter(adapter);
        }
        else {
            Toast.makeText(this, "لا يوجد رقم", Toast.LENGTH_SHORT).show();
        }
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
    public boolean isValid(String userdisplay , String username , String useremail , String userpassword) {
        //validate all inputs
        if (username.equals("")) {
            et_username.setError("يجب ملئ الخانة");
            return false; }

        if (userdisplay.equals("")) {
            et_name.setError("يجب ملئ الخانة");
            return false; }

        if (useremail.equals("")) {
            et_email.setError("يجب ملئ الخانة");
            return false; }

        if (userpassword.equals("")) {
            et_password.setError("يجب ملئ الخانة");
            return false; }



//        if (number.equals("")){
//            et_phone.setError("يجب ملئ الخانة");
//            return false;
//        }
        if(username.length()==11){
            et_username.setError("يجب ألا يزيد اسم المستخدم عن ١٠ أحرف");
            return false; }

        if(userdisplay.length()==30){
            et_name.setError("يجب ألا يزيد الاسم الظاهر عن ٣٠ حرف");
            return false; }

        if(useremail.length()==30){
            et_email.setError("يجب ألا يزيد البريد الإلكتروني عن ٣٠ حرف");
            return false; }

        if(userpassword.length()==40){
            et_password.setError("يجب ألا تزيد كلمة المرور عن 40 حرف");
            return false; }

        if(userpassword.length()<8){
            et_password.setError("يجب ان تتكون كلمة المرور من ثمانية احرف او اكثر");
            return false;
        }
//        if(number.length()==11){
//            et_phone.setError("يجب ألا يزيد رقم الهاتف عن ١٠ أرقام");
//            return false; }




        try {
            if(useremail.contains("@")) {
                String Emailvalidation = useremail.substring(useremail.indexOf('@'));
                String pattren = Emailvalidation.toLowerCase();
                switch (Emailvalidation) {
                    case "@hotmail.com":
                        return true;
                    case "@gmail.com":
                        return true;
                    case "@outlook.com":
                        return true;
                    case "@icloud.com":
                        return true;
                    case "@yahoo.com":
                        return true;
                    default: et_email.setError("البريد الإلكتروني غير صحيح");
                        return false;
                }
            }
            else {
                et_email.setError("البريد الإلكتروني غير صحيح");
                return false;
            }

        }catch (Exception e ){
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return true;
    }

}