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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editProfile extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference MyDB;
    private EditText editTextfirstname;
    private EditText editTextlastname;
    private EditText editTextPhone;
    private Button buttonsave;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String path=firebaseUser.getUid();
        MyDB = FirebaseDatabase.getInstance().getReference().child("Users").child(path);
        progressDialog = new ProgressDialog(this);

        editTextfirstname = (EditText) findViewById(R.id.EP_firsname);
        editTextlastname = (EditText) findViewById(R.id.EP_lastname);
        editTextPhone = (EditText) findViewById(R.id.EP_phonenumber);
        buttonsave = (Button) findViewById(R.id.EP_save);

        buttonsave.setOnClickListener(this);
    }

    public void saveinfo(){
        String firstname = editTextfirstname.getText().toString().trim();
        String lastname = editTextlastname.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if(TextUtils.isEmpty(firstname)){
            Toast.makeText(getApplicationContext(),"please enter first name", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(lastname)){
            Toast.makeText(getApplicationContext(),"please enter last name", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(getApplicationContext(),"please enter phone number", Toast.LENGTH_SHORT).show();
        }
        PersonalInfo PI = new PersonalInfo(firstname,lastname,phone);
        progressDialog.setMessage("saving info..");
        progressDialog.show();
        MyDB.setValue(PI).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), profileActivity.class));
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view == buttonsave){
            saveinfo();
        }
    }
}
