package com.example.rawan.maamn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

import static com.example.rawan.maamn.ConnectionClass.con;

public class p_register extends AppCompatActivity {

    String username, userdisplay, useremail, userpassword, userpassword2, pname,page ,number,number2;
    Button button3, button;
    EditText editText4, editText3, editText9, editText11, editText, editText7, editText6, editText5, editText8,editText10;
    TextView textView7;
    ConnectionClass connectionClass;
    ResultSet resultSet2;

    // check password
    //private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})"; //ok

//    static SharedPreferences getSharedPreferences(Context context){
//        return PreferenceManager.getDefaultSharedPreferences(context); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //check wifi connection
        isConnected();

        editText4 = ((EditText) findViewById(R.id.editText4));
        editText3 = ((EditText) findViewById(R.id.editText3));
        editText9 = ((EditText) findViewById(R.id.editText9));
        editText11 = ((EditText) findViewById(R.id.editText11));
        editText7 = ((EditText) findViewById(R.id.editText7));
        editText6 = ((EditText) findViewById(R.id.editText6));
        editText5 = ((EditText) findViewById(R.id.editText5));
        editText8 = ((EditText) findViewById(R.id.editText8));
        editText10 = ((EditText) findViewById(R.id.editText10));

        button3 = (Button) findViewById(R.id.button3);
        textView7 = (TextView) findViewById(R.id.textView7);
        connectionClass = new ConnectionClass();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(p_register.this, login.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Validate inputs
                username = editText4.getText().toString();
                userdisplay = editText3.getText().toString();
                useremail = editText9.getText().toString();
                userpassword = editText11.getText().toString();
                userpassword2 = editText7.getText().toString();
                number = editText8.getText().toString();
                number2 = editText10.getText().toString();

                String pat ="";

                if (isValid(userdisplay, username, useremail, userpassword, userpassword2, number)) {
//            //database connection
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Connection con;
                    Statement stmt;
                    Statement stmt2;

//                    String hashpass = md5(userpassword);
//            //user = new User();
//           // String userName = user.Register(username, userdisplay, useremail, hashpass, pname, page, number);
//                    if (username != null ) {
//                        //SaveLogin.setUsername(getApplicationContext(),Username);
//                        //redirect to home activity (spicalty)
//                        //startActivity(new Intent(HomeFragment.this, Specialties.class));
//                        Intent intent = new Intent(p_register.this, p_register.class);
//                        startActivity(intent);
//                    } else {
//                        //error message
//                        Toast errorToast = Toast.makeText(p_register.this, "اسم المستخدم مسجل مسبقاً", Toast.LENGTH_SHORT);
//                        errorToast.show();
//                    }
                    //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    //StrictMode.setThreadPolicy(policy);

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection(ConnectionClass.url, ConnectionClass.un, ConnectionClass.password);

                        stmt = con.createStatement();

                        String q= "SELECT * FROM patient WHERE user_name='"+username+"'";
                        String q2= "SELECT * FROM caregiver WHERE user_name='"+username+"'";

                        ResultSet resultSet=stmt.executeQuery(q);
                        int isthere=0;
                        int ishere2=0;
                        while (resultSet.next()){
                            isthere++;
                        }
                        ResultSet resultSet3=stmt.executeQuery(q2);
                        while (resultSet3.next()){
                            ishere2++;
                        }
                        if(isthere>0||ishere2>0){
                            Toast errorToast = Toast.makeText(p_register.this, "اسم المستخدم مسجل مسبقاً", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }

                        else {
                            userpassword=md5(userpassword);
                            stmt2 = con.createStatement();
                            String sql = "INSERT INTO `patient` (`patient_ID`,`name`, `user_name`, `email`, `password`) VALUES (NULL,'" + userdisplay + "', '" + username + "', '" + useremail + "', '" + userpassword + "')";
                            int result = stmt2.executeUpdate(sql);

                            String q1= "SELECT `patient_ID` FROM patient WHERE user_name='"+username+"'";
                            resultSet2=stmt2.executeQuery(q1);
                            while (resultSet2.next()) {
                                pat = resultSet2.getString("patient_ID");

                            }
                            String sql2 = "INSERT INTO `patientphone` (`patient_ID`,`importantNumber`) VALUES ('"+pat+"','"+number+"')";
                            int result2 = stmt2.executeUpdate(sql2);

                            if (!(number2.equals(null)||number2.equals(""))){
                                String sql3 = "INSERT INTO `patientphone` (`patient_ID`,`importantNumber`) VALUES ('"+pat+"','"+number2+"')";
                                int result3 = stmt2.executeUpdate(sql3);
                            }

                            // Toast test = Toast.makeText(p_register.this, "\n" + number, Toast.LENGTH_SHORT);
                            // test.show();
                            if (result == 1 && result2 == 1) {
                                Toast done = Toast.makeText(p_register.this, " تمت عملية التسجيل بنجاح ", Toast.LENGTH_SHORT);
                                done.show();
                                finish();
                                Intent intent = new Intent(p_register.this, Activity_patient_home.class);
                                startActivity(intent);
                            } else {
                                Toast done = Toast.makeText(p_register.this, " عذرًاً" + "\n" + "حدث مشكلة حاول مجددًاً ", Toast.LENGTH_SHORT);
                                done.show();
                            }
                            stmt.close();
                            stmt2.close();
                            con.close();
                        }
                    } catch (SQLException se) {
                        Toast errorToast = Toast.makeText(p_register.this, "الرجاء التأكد من اتصال الانترنت!", Toast.LENGTH_SHORT);
                        errorToast.show();
                        se.printStackTrace();
                    } catch (Exception e) {
                        Toast errorToast = Toast.makeText(p_register.this, " " + e.getMessage(), Toast.LENGTH_SHORT);
                        errorToast.show();
                    }

                }

            }
        });
    }

    //Validate Password
           /* public boolean validate( String Password){
                return PASSWORD_PATTERN.matches(Password);
            }*/


    public boolean isValid(String userdisplay , String username , String useremail , String userpassword , String userpassword2, String number ) {
        //validate all inputs
        if (username.equals("")) {
            editText3.setError("يجب ملئ الخانة");
            return false; }

        if (userdisplay.equals("")) {
            editText4.setError("يجب ملئ الخانة");
            return false; }

        if (useremail.equals("")) {
            editText9.setError("يجب ملئ الخانة");
            return false; }

        if (userpassword.equals("")) {
            editText11.setError("يجب ملئ الخانة");
            return false; }

        if (userpassword2.equals("")) {
            editText7.setError("يجب ملئ الخانة");
            return false; }
        if (number.equals("")){
            editText8.setError("يجب ملئ الخانة");
            return false;
        }
        if(username.length()==11){
            editText4.setError("يجب ألا يزيد اسم المستخدم عن ١٠ أحرف");
            return false; }

        if(userdisplay.length()==30){
            editText3.setError("يجب ألا يزيد الاسم الظاهر عن ٣٠ حرف");
            return false; }

        if(useremail.length()==30){
            editText9.setError("يجب ألا يزيد البريد الإلكتروني عن ٣٠ حرف");
            return false; }

        if(userpassword.length()==20){
            editText11.setError("يجب ألا تزيد كلمة المرور عن ٢٠ حرف");
            return false; }

        if(userpassword2.length()==20){
            editText7.setError("يجب ألا تزيد كلمة المرور عن ٢٠ حرف");
            return false; }

        if(!userpassword .equals(userpassword2)){
            editText7.setError("كلمة المرور غير مطابقة");
            return false; }
        if(userpassword.length()<8){
            editText11.setError("يجب ان يتكون رمز الدخول من ثمانيه حروف او اكثر");
            return false;
        }
        if(number.length()==11){
            editText8.setError("يجب ألا يزيد رقم الهاتف عن ١٠ أرقام");
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
                    default: editText9.setError("البريد الإلكتروني غير صحيح");
                        return false;
                }
            }
            else {
                editText9.setError("البريد الإلكتروني غير صحيح");
                return false;
            }

        }catch (Exception e ){
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return true;
    }


    public void isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
        }
        else {
            showDialog();
        }
    }


    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage ("أنت غير متصل بالانترنت .. هل تريد الاتصال أم إغلاق التطبيق؟")
                .setCancelable(false)
                .setPositiveButton("اﻻﺗﺼﺎل", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }


                })
                .setNegativeButton("إﻏﻼق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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

}
