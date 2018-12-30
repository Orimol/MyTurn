package com.example.myturn.myturn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Business extends AppCompatActivity {

    private DatabaseReference MyDB;
    private FirebaseAuth firebaseAuth;
    private EditText editTextBusinessName;
    private EditText editTextBusinessAddress;
    private EditText editTextBusinessPhone;
    private EditText editTextReceptionHours;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button buttonApply;
    private FirebaseUser firebaseUser;
   // enum Days{Sun,Mon,Tue,Wed,Thu}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //user not connected- move to homepage
            finish();
            startActivity(new Intent(this, Login.class));
        }
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        radioGroup = (RadioGroup) findViewById(R.id.AB_radiogroup);
        editTextBusinessName = (EditText) findViewById(R.id.AB_businessName);
        editTextBusinessAddress = (EditText) findViewById(R.id.AB_businessAddress);
        editTextBusinessPhone = (EditText) findViewById(R.id.AB_businessPhone);
        editTextReceptionHours= (EditText) findViewById(R.id.AB_receptionHours);
        buttonApply = (Button) findViewById(R.id.AB_apply);

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);
                List<Workday> workdays = new ArrayList<>();
                if (radioButton.getText().toString().equals("all days")){
                    Workday sunday = new Workday(Days.Sun,8,16);
                    Workday monday = new Workday(Days.Mon,8,16);
                    Workday tuesday = new Workday(Days.Tue,8,16);
                    Workday wednesday = new Workday(Days.Wed,8,16);
                    Workday thursday = new Workday(Days.Thu,8,16);
                    workdays.add(sunday);
                    workdays.add(monday);
                    workdays.add(tuesday);
                    workdays.add(wednesday);
                    workdays.add(thursday);
                }
                else{//right now its the same outcome
                    Workday sunday = new Workday(Days.Sun,8,16);
                    Workday monday = new Workday(Days.Mon,8,16);
                    Workday tuesday = new Workday(Days.Tue,8,16);
                    Workday wednesday = new Workday(Days.Wed,8,16);
                    Workday thursday = new Workday(Days.Thu,8,16);
                    workdays.add(sunday);
                    workdays.add(monday);
                    workdays.add(tuesday);
                    workdays.add(wednesday);
                    workdays.add(thursday);
                }
//                String workdaysstring="";
//                for(Workday w:workdays){
//                    workdaysstring+=w.toStirng();
//                }
                String businessname = editTextBusinessName.getText().toString().trim();
                String businessaddress = editTextBusinessAddress.getText().toString().trim();
                String businessphone = editTextBusinessPhone.getText().toString().trim();
               // String receptionhours = editTextReceptionHours.getText().toString().trim();
                BusinessInfo Mybusiness = new BusinessInfo(businessname, businessaddress, businessphone);
                HashMap<String ,String> businessmap = new HashMap<String, String>();
                businessmap.put("business name", Mybusiness.getBusinessName());
                businessmap.put("business address", Mybusiness.getAddress());
                businessmap.put("business phone", Mybusiness.getPhone());
             //   businessmap.put("reception hours", receptionhours);

                MyDB = FirebaseDatabase.getInstance().getReference().child("Business").child(businessname).child(firebaseUser.getUid());
                MyDB.setValue(businessmap);
                MyDB = FirebaseDatabase.getInstance().getReference().child("Business").child(businessname).child(firebaseUser.getUid())
                        .child("reception hours");
                for(Workday w:workdays ){
                    MyDB.child(w.getDay()).setValue(w.toStirng());
                }
                startActivity(new Intent(getApplicationContext(),profileActivity.class));
            }
        });
    }


}
