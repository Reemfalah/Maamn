package com.example.rawan.maamn;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.sql.ResultSet;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class newpassword extends AppCompatActivity {

    public Button Submit;
    public EditText Password1,Password2;
    String token = null;
    RequestQueue requestQueue;
    String URL ;
    String pass1,pass2;
    public TextView Note2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            token = extras.getString("token");
        }
        Submit = findViewById(R.id.Submit);
        requestQueue = newRequestQueue(this.getApplicationContext());
        Password1 = findViewById(R.id.Password1);
        Password2 = findViewById(R.id.Password2);
        Note2 = findViewById(R.id.Note2);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note2.setTextColor(Color.RED);

                pass1=Password1.getText().toString();
                pass2=Password2.getText().toString();
                if(!(pass1.equals("")||pass2.equals(""))){
                    if((pass1.length()>=20)||(pass2.length()>=20)){
                        Note2.setText("يجب ألا تزيد كلمة المرور عن ٢٠ خانه");
                        Toast.makeText(newpassword.this, "يجب ألا تزيد كلمة المرور عن ٢٠ خانه",
                                Toast.LENGTH_LONG).show();
                    }else if((pass1.length()<8)||(pass2.length()<8)){
                        Note2.setText("يجب ان يتكون رمز الدخول من ثمانيه خانات على الأقل");
                    }else if(!(pass2.equals(pass1))){

                        Note2.setText("يجب ان تطابق كلمة المرور المدخلة");

                    }else{
                        URL = ConnectionClass.serverSideURL + "NewPassword";
                        URL = URL.concat("?password="+ pass1+"&token=" + token);

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("successful")){
                                    Toast.makeText(newpassword.this, "تم التغيير بنجاح! الرجاء تسجيل الدخول بكلمة المرور الجديدة",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(newpassword.this, login.class);
                                    startActivity(intent);

                                }
                                else  if (response.equals("unsuccessful")) {
                                    Toast.makeText(newpassword.this, "يوجد خطأ الرجاء المحاولة مرة أخرى",
                                            Toast.LENGTH_LONG).show();

                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast errorToast = Toast.makeText(newpassword.this, "توجد مشكله! الرجاء التحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT);
                                errorToast.show();
                            }
                        });
                        stringRequest.setRetryPolicy(ConnectionClass.policy);
                        requestQueue.add(stringRequest);


                    }}else{
                    Toast.makeText(newpassword.this, "الرجاء تعبئة جميع الحقول",
                            Toast.LENGTH_LONG).show();
                    Note2.setText("*الرجاء تعبئة جميع الحقول ");



                }

            }
        });


    }//creat
}//class
