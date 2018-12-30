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

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextpassword;
    private TextView textViewsignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), profileActivity.class));
        }

        buttonSignin = (Button) findViewById(R.id.Login_loginBtn);
        editTextEmail = (EditText) findViewById(R.id.Login_email);
        editTextpassword = (EditText) findViewById(R.id.Login_password);
        textViewsignup = (TextView) findViewById(R.id.Login_text_view);

        buttonSignin.setOnClickListener(this);
        textViewsignup.setOnClickListener(this);



    }

    private void userLogin(){
        String Email = editTextEmail.getText().toString();
        String password = editTextpassword.getText().toString();
        if(TextUtils.isEmpty(Email)){
            Toast.makeText(this,"please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Login, please wait..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(Email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), profileActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignin){
            userLogin();
        }
        if (view == textViewsignup){
            finish();
            startActivity(new Intent(this, Signup.class));
        }
    }
}
