package com.example.rawan.maamn;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.rawan.maamn.ConnectionClass.con;

public class caregiver_viewinfo extends AppCompatActivity {

    EditText et_name;
    EditText et_username;
    EditText et_email;
    EditText et_password;
    EditText et_phone;
    EditText et_pname;
    EditText et_page;
    //private String LOG_TAG = "MainActivity";

    String name, user_name, email, password, phone, pname, page,oldPass,oldUN;

    Intent intent;
    String id;

    ConnectionClass connectionClass;
    Caregiver caregiver;

    final static String TAG = "caregiver_viewinfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_viewinfo);

        id = login.GetUserId();


        et_name = (EditText) findViewById(R.id.giver_name);
        et_username = (EditText) findViewById(R.id.giver_username);
        et_email = (EditText) findViewById(R.id.giver_email);
        et_password = (EditText) findViewById(R.id.giver_password);
        et_phone = (EditText) findViewById(R.id.giver_phone);
        et_pname = (EditText) findViewById(R.id.giver_pname);
        et_page = (EditText) findViewById(R.id.giver_page);

        try {
            getDataFromDatabase(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getDataFromDatabase(String id) throws SQLException {

        caregiver = new Caregiver();
        connectionClass = new ConnectionClass();

        String sql = "select * from caregiver where caregiver_ID = '" + id + "'";
        ResultSet rs = connectionClass.st.executeQuery(sql);
        Log.d(TAG, "222");

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                caregiver.setId(rs.getString(1));
                Log.d(TAG, caregiver.getId());

                caregiver.setName(rs.getString(2));
                caregiver.setUsername(rs.getString(3));
                oldUN=rs.getString(3);
                caregiver.setEmail(rs.getString(4));
                caregiver.setPassword(rs.getString(5));
                oldPass=rs.getString(5);
                caregiver.setPhone(rs.getString(6));
                caregiver.setPatient_anme(rs.getString(7));
                caregiver.setPatient_age(rs.getString(8));
                Log.d(TAG, "33");

                displayData();

            }

        } else {
            Log.d(TAG, "لا يوجد معلومات مسجلة لهذا المستخدم");
            Toast.makeText(this, "لا يوجد معلومات مسجلة لهذا المستخدم", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayData() {

        et_name.setText(caregiver.getName());
        et_username.setText(caregiver.getUsername());
        et_email.setText(caregiver.getEmail());
        et_password.setText(caregiver.getPassword());
        et_phone.setText(caregiver.getPhone());
        et_pname.setText(caregiver.getPatient_anme());
        et_page.setText(caregiver.getPatient_age());
    }

    public void updateData(View view) throws SQLException {

        name = et_name.getText().toString();
        user_name = et_username.getText().toString();
        email = et_email.getText().toString();
        password = et_password.getText().toString();
        phone = et_phone.getText().toString();
        pname = et_pname.getText().toString();
        page = et_page.getText().toString();

//        if(!name.isEmpty() & !user_name.isEmpty() & !email.isEmpty() & !password.isEmpty()& !phone.isEmpty() &
//                !pname.isEmpty() & !page.isEmpty()){
//


        if (isValid(name, user_name, email, password, phone, pname, page)) {
//            //database connection
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection con;
            Statement stmt;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(ConnectionClass.url, ConnectionClass.un, ConnectionClass.password);

                stmt = con.createStatement();
                if(!(oldUN.equals(user_name))){
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
                    Toast errorToast = Toast.makeText(caregiver_viewinfo.this, "اسم المستخدم مسجل مسبقاً", Toast.LENGTH_SHORT);
                    errorToast.show();
                }} else {

                    connectionClass = new ConnectionClass();
//                    if(!(password.equals(oldPass))){
//                        password = md5(password);
//                    }



//                    String sql2 = "update caregiver set name = '" + name + "', user_name = '" + user_name + "' , email = '" + email
//                            + "' , password = '" + password + "' , phoneNumber = '" + phone + "' , patient_name = '" + pname
//                            + "' , patient_age = '" + page + "' where caregiver_ID = '" + id + "' ";
                    String sql2 = "update caregiver set name = '"+name+"', user_name = '"+user_name+"' , email = '"+email+"' , password = '"+password+"' , phoneNumber = '"+phone+"' , patient_name = '"+pname+"' , patient_age = '"+page+"' where caregiver_ID = '"+id+"'  ";


//                    int result = connectionClass.st.executeUpdate(sql2);
                    int result = stmt.executeUpdate(sql2);

                    if (result>0){
                        Toast.makeText(this, "تم تحديث المعلومات بنجاح", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(caregiver_viewinfo.this, caregiverHome.class);
                    startActivity(intent);}
                    else Toast.makeText(this, "حدثت مشكلة حاول مجدداً", Toast.LENGTH_SHORT).show();

                }

            } catch (SQLException se) {
                Toast errorToast = Toast.makeText(caregiver_viewinfo.this, "الرجاء التأكد من اتصال الانترنت!", Toast.LENGTH_SHORT);
                errorToast.show();
                se.printStackTrace();
            } catch (Exception e) {
                Toast errorToast = Toast.makeText(caregiver_viewinfo.this, " " + e.getMessage(), Toast.LENGTH_SHORT);
                errorToast.show();
            }
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
    public boolean isValid(String userdisplay , String username , String useremail , String userpassword , String number, String pname, String page ) {
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

        if (pname.equals("")){

            et_pname.setError("يجب ملئ الخانة");
            return false;
        }
        if (page.equals("")){
            et_page.setError("يجب ملئ الخانة");
            return false;
        }
        if (number.equals("")){
            et_phone.setError("يجب ملئ الخانة");
            return false;
        }
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
        if(number.length()==11){
            et_phone.setError("يجب ألا يزيد رقم الهاتف عن ١٠ أرقام");
            return false; }




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



