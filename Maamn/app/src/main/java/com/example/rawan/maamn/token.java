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

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class token extends AppCompatActivity {
    public Button Submit;
    public EditText Token;
    RequestQueue requestQueue;
    String tokenn = null;
    String URL = ConnectionClass.serverSideURL + "token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        requestQueue = newRequestQueue(this.getApplicationContext());
        Submit = findViewById(R.id.Submit);
        Token = findViewById(R.id.token);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokenn = Token.getText().toString();
                if (!tokenn.equals("")) {
                    URL = URL.concat("?tokencode=" + tokenn);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("unsuccessful")) {
                                Toast.makeText(token.this, "الرمز المدخل غير صحيح",
                                        Toast.LENGTH_LONG).show();
                                URL = ConnectionClass.serverSideURL + "token";
                            }else if (response.equals("successful")){
                                Intent intent = new Intent(token.this, newpassword.class);
                                intent.putExtra("token",tokenn);
                                startActivity(intent);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast errorToast = Toast.makeText(token.this, "توجد مشكله! الرجاء التحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }
                    });
                    stringRequest.setRetryPolicy(ConnectionClass.policy);
                    requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(token.this, "الرجاء ادخال الرمز المرسل",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
