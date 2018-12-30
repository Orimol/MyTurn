package com.example.myturn.myturn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class profileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference MyDB;

    private TextView textViewUserEmail;
    private Button buttonlogout;
    private Button buttonEditProfile;
    private Button buttonBusinessRegister;
    private Button buttonFindBusiness;
    private EditText editTextEnterBusinessname;
    private boolean BusinessExist;
    String userid;
    String business_name_to_send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //user not connected- move to homepage
            finish();
            startActivity(new Intent(this, Login.class));
        }
        MyDB = FirebaseDatabase.getInstance().getReference().child("Business");
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        BusinessExist=false;

        textViewUserEmail = (TextView) findViewById(R.id.PA_user);
        buttonlogout = (Button) findViewById(R.id.PA_Logout);
        textViewUserEmail.setText("welcome "+firebaseUser.getEmail());
        buttonEditProfile = (Button) findViewById(R.id.PA_editprofile);
        buttonBusinessRegister = (Button) findViewById(R.id.PA_businessregister);
        buttonFindBusiness = (Button) findViewById(R.id.PA_findbusiness);
        editTextEnterBusinessname = (EditText) findViewById(R.id.PA_enterbusinessname);

        buttonlogout.setOnClickListener(this);
        buttonBusinessRegister.setOnClickListener(this);
        buttonEditProfile.setOnClickListener(this);
        buttonFindBusiness.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == buttonlogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if(v == buttonBusinessRegister){
            startActivity(new Intent(this, Business.class));
        }
        if(v == buttonEditProfile){
            startActivity(new Intent(this, editProfile.class));
        }
        if(v == buttonFindBusiness){
            final Intent business_display = new Intent(getApplicationContext(), BusinessDisplay.class);
            final String business_name_entered_by_user = editTextEnterBusinessname.getText().toString();

            MyDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChildren()){
                        for (DataSnapshot item:dataSnapshot.getChildren()){
                            String dbitem = item.getKey();
                            if(business_name_entered_by_user.equalsIgnoreCase(dbitem)) {
                                BusinessExist = true;
                                Iterator<DataSnapshot> dataSnapshotIterator = item.getChildren().iterator();
                                DataSnapshot nextchild = item.child(dataSnapshotIterator.next().getKey());
                                userid=nextchild.getKey();
                                business_name_to_send=dbitem;
                                business_display.putExtra("business", business_name_to_send);
                                business_display.putExtra("item",userid);
                            }
                        }
                    }
                    if(BusinessExist)
                        startActivity(business_display);
                    else
                        Toast.makeText(getApplicationContext(),"business dose not exist", Toast.LENGTH_SHORT).show();
                    BusinessExist=false;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

        }
    }
}
