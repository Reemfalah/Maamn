package com.example.rawan.maamn;

public class test {

    /*
    package com.example.abodi.wshalghada;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import static com.android.volley.toolbox.Volley.newRequestQueue;
//import com.kristijandraca.backgroundmaillibrary.BackgroundMail;


public class Password extends AppCompatActivity {
    public Button Submit;
    public EditText Email;
    public TextView Note2;
    private Button back;
    private ProgressBar progressbar;
    String email = null;
    RequestQueue requestQueue;
    String URL = DBConnection.serverSideURL + "Reset";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Submit = findViewById(R.id.Submit);
        Email = findViewById(R.id.Email);
        back = findViewById(R.id.button7);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        requestQueue = newRequestQueue(this.getApplicationContext());
        Note2 = findViewById(R.id.Note2);
        progressbar = findViewById(R.id.progressBar8);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note2.setTextColor(Color.RED);
                progressbar.setVisibility(View.VISIBLE);
                email = Email.getText().toString();
                if (!email.equals("")) {
                    URL = DBConnection.serverSideURL + "Reset";
                    URL = URL.concat("?email=" + email);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressbar.setVisibility(View.GONE);
                            if (response.equals("unsuccessful")) {
                                Toast.makeText(Password.this, "البريد الالكتروني غير موجود",
                                        Toast.LENGTH_LONG).show();
                                Note2.setText("البريد الالكتروني غير موجود الرجاء ادخال البريد الالكتروني المسجل");
                            } else if (response.equals("successful")) {
                                Intent intent = new Intent(Password.this, token.class);
                                startActivity(intent);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressbar.setVisibility(View.GONE);
                            Toast errorToast = Toast.makeText(Password.this, "توجد مشكله! الرجاء التحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }
                    });

                    stringRequest.setRetryPolicy(DBConnection.policy);
                    requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(Password.this, "الرجاء ادخال البريد الالكتروني",
                            Toast.LENGTH_LONG).show();
                    Note2.setText("*الرجاء ادخال البريد الالكتروني ");

                }


            }

        });
    }
}
//    public static ClientResponse SendSimpleMessage() {
//        Client client = Client.create();
//        client.addFilter(new HTTPBasicAuthFilter("api", "c3ab7a193b1fa0fbee0d7d12c1cb52c7-52cbfb43-23f111ad"));
//        WebResource webResource = client.resource("https://api.mailgun.net/v3/sandboxc02c1739abe5477982c50e4d4b16c8ef.mailgun.org/messages");
//        MultivaluedMapImpl formData = new MultivaluedMapImpl();
//        formData.add("from", "Mailgun Sandbox <postmaster@sandboxc02c1739abe5477982c50e4d4b16c8ef.mailgun.org>");
//        formData.add("to", "Rawan <rawan.f.h@gmail.com>");
//        formData.add("subject", "Hello Rawan");
//        formData.add("text", "Congratulations Rawan, you just sent an email with Mailgun!  You are truly awesome!");
//        return webResource
//                .type(MediaType.APPLICATION_FORM_URLENCODED)
//                .post(ClientResponse.class, formData);
//    }
// You can see a record of this email in your logs: https://app.mailgun.com/app/logs

// You can send up to 300 emails/day from this sandbox server.
// Next, you should add your own domain so you can send 10,000 emails/month for free.






     */


}
