package com.example.rawan.maamn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class caregiverHome extends AppCompatActivity {
    private TextView tv;
    private ImageButton imageButton;
    private ImageView imageView5;
    private ImageButton imageButton2;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_home);
        ///////
        if(login.GetIslogged()==false){
            Intent intent = new Intent(caregiverHome.this, login.class);
            startActivity(intent);
        }//////////
        ///name of user
        tv=(TextView)findViewById(R.id.textView7);
        String Name=login.GetUserName();
        tv.setText(Name);////

        imageButton = (ImageButton) findViewById (R.id.imageButton);
        imageView5 = (ImageView) findViewById(R.id.imageView50);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton200);
        imageButton4=(ImageButton) findViewById(R.id.imageButton40);
        imageButton5=(ImageButton)findViewById(R.id.imageButton56);
        imageButton50=(ImageButton)findViewById(R.id.imageButtonu90);
        /////
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(caregiverHome.this, caregiverHome.class);
                startActivity(intent);

                }
        });

        imageButton50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(caregiverHome.this, caregiver_viewinfo.class);
                startActivity(intent);

            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(caregiverHome.this, view_seizure.class);
                startActivity(intent);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(caregiverHome.this, view_result.class);
                startActivity(intent);
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(caregiverHome.this, view_reminder.class);
                startActivity(intent);
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.SetIslogged(false);
                Intent intent = new Intent(caregiverHome.this, login.class);
                startActivity(intent);
            }
        });




    }


}
