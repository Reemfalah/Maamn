
package com.example.rawan.maamn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class ResetPassword extends AppCompatActivity {

    EditText editText2;
    Button button;
    String email;
    //ConnectionClass connectionClass;
    Statement stmt;
    Connection con;
    String URL = ConnectionClass.serverSideURL + "Reset";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editText2 = (EditText) findViewById(R.id.editText101);
        button = (Button) findViewById(R.id.button);
        requestQueue= newRequestQueue(this.getApplicationContext());

        //check wifi connection
        // isConnected();

        //  connectionClass = new ConnectionClass();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                email = editText2.getText().toString();
                if (!email.equals("")) {
                    URL = URL.concat("?email=" + email);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("unsuccessful")) {
                                Toast.makeText(ResetPassword.this, "البريد الالكتروني غير موجود",
                                        Toast.LENGTH_LONG).show();
                            } else if (response.equals("successful")) {
                                Intent intent = new Intent(ResetPassword.this, token.class);
                                startActivity(intent);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof TimeoutError) {
                                Toast errorToast = Toast.makeText(ResetPassword.this, "TimeoutError", Toast.LENGTH_SHORT);
                                errorToast.show();
                            } else if (error instanceof NoConnectionError) {
                                Toast errorToast = Toast.makeText(ResetPassword.this, "NoConnectionError", Toast.LENGTH_SHORT);
                                errorToast.show();
                            } else if (error instanceof AuthFailureError) {
                                Toast errorToast = Toast.makeText(ResetPassword.this, "AuthFailureError", Toast.LENGTH_SHORT);
                                errorToast.show();
                            } else if (error instanceof ServerError) {
                                Toast errorToast = Toast.makeText(ResetPassword.this, "ServerError", Toast.LENGTH_SHORT);
                                errorToast.show();
                            } else if (error instanceof NetworkError) {
                                Toast errorToast = Toast.makeText(ResetPassword.this, "NetworkError", Toast.LENGTH_SHORT);
                                errorToast.show();
                            } else if (error instanceof ParseError) {
                                Toast errorToast = Toast.makeText(ResetPassword.this, "ParseError", Toast.LENGTH_SHORT);
                                errorToast.show();
                            }
                            else{Toast errorToast = Toast.makeText(ResetPassword.this, "توجد مشكله! الرجاء التحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT);
                                errorToast.show();
                            }
                        }
                    });

                    stringRequest.setRetryPolicy(ConnectionClass.policy);
                    requestQueue.add(stringRequest);
                    //                     stringRequest.setRetryPolicy(DBConnection.policy);
//                     requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(ResetPassword.this, "الرجاء ادخال البريد الالكتروني",
                            Toast.LENGTH_LONG).show();

                }



//                if (email.equals("")) {
//                    Toast.makeText(ResetPassword.this, "فضلا ادخل البريد الالكتروني ", Toast.LENGTH_SHORT).show();
//                } else if (!email.equals("")) {
//                    Intent intent = new Intent(ResetPassword.this, newpassword.class);
//                    startActivity(intent);
//                }
//
//                try {
//
//                    String q = "SELECT * FROM caregiver WHERE email='" + email + "' UNION SELECT * FROM patient WHERE email='" + email + "'";
//                   ResultSet resultSet = stmt.executeQuery(q);
//                    int isthere = 0;
//                   while (resultSet.next()) {
//                        isthere++;
//                    }
//                    if (isthere > 0) {
//                        stmt.close();
//                        con.close();
//                        Intent intent = new Intent(ResetPassword.this, newpassword.class);
//                        startActivity(intent);
//                    } else {
//
//                        Toast errorToast = Toast.makeText(ResetPassword.this, "البريد الالكتروني غير موجود فضلا ادخل البريد الالكتروني المسجل", Toast.LENGTH_SHORT);
//                        errorToast.show();
//                    }
//                } catch (SQLException se) {
//                    Toast errorToast = Toast.makeText(ResetPassword.this, "!!!!!!!!!!", Toast.LENGTH_SHORT);
//                    errorToast.show();
//                    se.printStackTrace();
//                } catch (Exception e) {
//                    Toast errorToast = Toast.makeText(ResetPassword.this, " " + e.getMessage(), Toast.LENGTH_SHORT);
//                    errorToast.show();
//                }
            }


        });
        //button

    }//oncreat
}//class
