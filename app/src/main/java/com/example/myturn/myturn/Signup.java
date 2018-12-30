package com.example.myturn.myturn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity implements View.OnClickListener{

    private Button button_register;
    private EditText editTextemail;
    private EditText editTextpassword;
    private TextView textViewsignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), profileActivity.class));
        }

        button_register = (Button) findViewById(R.id.Signup_loginBtn);
        editTextemail = (EditText) findViewById(R.id.Signup_email);
        editTextpassword = (EditText) findViewById(R.id.Signup_password);
        textViewsignin = (TextView) findViewById(R.id.Signup_text_view);

        button_register.setOnClickListener(this);
        textViewsignin.setOnClickListener(this);
    }

    public void registerUser(){
        String email = editTextemail.getText().toString().trim();
        String pass =  editTextpassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("registering please wait..");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            //user is successfully registered and logged in
                            finish();
                            startActivity(new Intent(getApplicationContext(), editProfile.class));
                            Toast.makeText(getApplicationContext(),"Register successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Register failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onClick(View view){
        if(view==button_register){
            registerUser();
        }
        if(view==textViewsignin){
            startActivity(new Intent(this, Login.class));
        }
    }

}
