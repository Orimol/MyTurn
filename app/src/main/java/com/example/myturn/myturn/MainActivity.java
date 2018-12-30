package com.example.myturn.myturn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button MangerLogin = (Button) findViewById(R.id.MA_Manger_Login_Btn);
        MangerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ML_Activity = new Intent(getApplicationContext(), Login.class);
                startActivity(ML_Activity);
            }
        });

        final Button MangerSignup = (Button) findViewById(R.id.MA_Manger_Signup_Btn);
        MangerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MS_Activity = new Intent(getApplicationContext(), Signup.class);
                startActivity(MS_Activity);
            }
        });
    }
}
