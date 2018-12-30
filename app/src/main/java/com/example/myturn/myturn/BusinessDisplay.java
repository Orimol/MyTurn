package com.example.myturn.myturn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class BusinessDisplay extends AppCompatActivity {

    DatabaseReference MyDB;
    TextView textViewBusinessName;
    TextView textViewBusinessAddress;
    TextView textViewBusinessPhone;
    TextView textViewReceptionHours;
    TextView textViewSun;
    TextView textViewMon;
    TextView textViewTue;
    TextView textViewWed;
    TextView textViewThu;

    Button buttonSubmitQueue;
    private String User_id;
    private String business_id;
    String send_to_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_display);

        if(getIntent().hasExtra("item")){
             User_id=getIntent().getExtras().getString("item");
        }
        if(getIntent().hasExtra("business")){
            business_id=getIntent().getExtras().getString("business");
        }

        MyDB = FirebaseDatabase.getInstance().getReference().child("Business").child(business_id).child(User_id);
        textViewBusinessName = (TextView) findViewById(R.id.ABD_businessname);
        textViewBusinessAddress = (TextView) findViewById(R.id.ABD_businessaddress);;
        textViewBusinessPhone = (TextView) findViewById(R.id.ABD_businessphone);
        textViewReceptionHours = (TextView) findViewById(R.id.ABD_receptionhours);
        textViewSun = (TextView) findViewById(R.id.ABD_sunday);
        textViewMon = (TextView) findViewById(R.id.ABD_monday);
        textViewTue = (TextView) findViewById(R.id.ABD_tuesday);
        textViewWed = (TextView) findViewById(R.id.ABD_wednesday);
        textViewThu = (TextView) findViewById(R.id.ABD_thursday);
        buttonSubmitQueue = (Button) findViewById(R.id.ABD_submitqueue);

        MyDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                DataSnapshot nextchild = dataSnapshot.child(dataSnapshotIterator.next().getKey());
                textViewBusinessAddress.setText("Business Address: "+nextchild.getValue().toString());
                nextchild = dataSnapshot.child(dataSnapshotIterator.next().getKey());
                textViewBusinessName.setText("Business Name: "+nextchild.getValue().toString());
                nextchild = dataSnapshot.child(dataSnapshotIterator.next().getKey());
                textViewBusinessPhone.setText("Business Phone: "+nextchild.getValue().toString());
                nextchild = dataSnapshot.child(dataSnapshotIterator.next().getKey());
                send_to_submit=nextchild.getKey();
                Iterator<DataSnapshot> dataSnapshotIteratorofreceptionhours = nextchild.getChildren().iterator();
                DataSnapshot reception_child = nextchild.child(dataSnapshotIteratorofreceptionhours.next().getKey());
                textViewMon.setText(reception_child.getValue().toString());
                reception_child = nextchild.child(dataSnapshotIteratorofreceptionhours.next().getKey());
                textViewSun.setText(reception_child.getValue().toString());
                reception_child = nextchild.child(dataSnapshotIteratorofreceptionhours.next().getKey());
                textViewThu.setText(reception_child.getValue().toString());
                reception_child = nextchild.child(dataSnapshotIteratorofreceptionhours.next().getKey());
                textViewTue.setText(reception_child.getValue().toString());
                reception_child = nextchild.child(dataSnapshotIteratorofreceptionhours.next().getKey());
                textViewWed.setText(reception_child.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonSubmitQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submit_to_queue = new Intent(getApplicationContext(), SubmitQueue.class);
                submit_to_queue.putExtra("SendToSubmit",send_to_submit);
                submit_to_queue.putExtra("UserId",User_id);
                submit_to_queue.putExtra("BusinessId",business_id);
                startActivity(submit_to_queue);
            }
        });

    }
}
