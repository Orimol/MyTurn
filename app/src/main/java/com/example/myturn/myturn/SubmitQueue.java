package com.example.myturn.myturn;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Iterator;

public class SubmitQueue extends AppCompatActivity {

    DatabaseReference MyDB;
    DatabaseReference MyDBforUsername;
    private String User_id;
    private String business_id;
    private String reception_hours;
    private String dayname;
    private String date;
    private String time;
    private String my_first_name;
    private String my_last_name;
    private boolean date_flag;
    private int selected_minute;
    private int selected_hour;
    private String [] timeforqueue = new String [8];
    private boolean [] flag_for_queue = new boolean [8];
    private TextView select_a_date;
    private TextView select_a_time;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private FirebaseAuth firebaseAuth;

    private TextView textViewqueue_1;
    private TextView textViewqueue_2;
    private TextView textViewqueue_3;
    private TextView textViewqueue_4;
    private TextView textViewqueue_5;
    private TextView textViewqueue_6;
    private TextView textViewqueue_7;
    private TextView textViewqueue_8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_queue);
        //all of this if statements are for getting the children of the DB root.
        if(getIntent().hasExtra("SendToSubmit")){
            reception_hours=getIntent().getExtras().getString("SendToSubmit");
        }
        if(getIntent().hasExtra("UserId")){
            User_id=getIntent().getExtras().getString("UserId");
        }
        if(getIntent().hasExtra("BusinessId")){
            business_id=getIntent().getExtras().getString("BusinessId");
        }
        //may delete if not working
        for(int i=0; i<8; i++){
            flag_for_queue[i]=false;
        }
        //till here delete
        firebaseAuth = FirebaseAuth.getInstance();
        String my_user_id = firebaseAuth.getCurrentUser().getUid();
        MyDBforUsername = FirebaseDatabase.getInstance().getReference().child("Users").child(my_user_id);
        MyDBforUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                    my_first_name = iterator.next().getValue().toString();
                    my_last_name = iterator.next().getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        select_a_date = (TextView) findViewById(R.id.ASQ_selectadate);
        select_a_time = (TextView) findViewById(R.id.ASQ_selectatime);
        textViewqueue_1 = (TextView) findViewById(R.id.ASQ_queue_1);
        textViewqueue_2 = (TextView) findViewById(R.id.ASQ_queue_2);
        textViewqueue_3 = (TextView) findViewById(R.id.ASQ_queue_3);
        textViewqueue_4 = (TextView) findViewById(R.id.ASQ_queue_4);
        textViewqueue_5 = (TextView) findViewById(R.id.ASQ_queue_5);
        textViewqueue_6 = (TextView) findViewById(R.id.ASQ_queue_6);
        textViewqueue_7 = (TextView) findViewById(R.id.ASQ_queue_7);
        textViewqueue_8 = (TextView) findViewById(R.id.ASQ_queue_8);
        select_a_time.setEnabled(false);
        textViewqueue_1.setEnabled(false);
        textViewqueue_2.setEnabled(false);
        textViewqueue_3.setEnabled(false);
        textViewqueue_4.setEnabled(false);
        textViewqueue_5.setEnabled(false);
        textViewqueue_6.setEnabled(false);
        textViewqueue_7.setEnabled(false);
        textViewqueue_8.setEnabled(false);
        date_flag=false;
        select_a_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SubmitQueue.this,
                        android.R.style.Theme_DeviceDefault_DialogWhenLarge,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow();//.setBackgroundDrawable(new ColorDrawable(Color.blue(0000)));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year,month,dayOfMonth);
                int dayofweek =c.get(Calendar.DAY_OF_WEEK);
                dayname = getDayOfWeek(dayofweek);
                if(dayofweek>5){
                    Toast.makeText(SubmitQueue.this,"please select another day",Toast.LENGTH_SHORT).show();
                    finish();
                }
                month+=1;
                date = dayOfMonth + "\\" + month + "\\" + year;
                Calendar cal = Calendar.getInstance();
                int corrent_year = cal.get(Calendar.YEAR);
                int corrent_month = cal.get(Calendar.MONTH);
                int corrent_day = cal.get(Calendar.DAY_OF_MONTH);
                if ((corrent_year > year) ||
                        ((corrent_month > month-1) && (corrent_year == year)) ||
                        ((corrent_day > dayOfMonth) && (corrent_year == year) && (corrent_month == month-1))){
                    Toast.makeText(SubmitQueue.this, "invalid date has been selected! please try again", Toast.LENGTH_LONG).show();
                    finish();
                }

                select_a_date.setText(date);
                select_a_time.setEnabled(true);
            }
        };

        select_a_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialogtime=new TimePickerDialog(SubmitQueue.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        8,0,true);
                dialogtime.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogtime.show();
            }
        });
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                if(hourOfDay>16 ||hourOfDay<8){
                    Toast.makeText(SubmitQueue.this,"please select another hour",Toast.LENGTH_SHORT).show();
                    finish();
                }
                time = hourOfDay + ":" + minute;
                if (minute<10)
                    time = hourOfDay + ":0" + minute;
                select_a_time.setText(time);
                selected_hour = hourOfDay;
                selected_minute = minute;

                //
                selected_minute = CheckMinute(selected_minute);
                String [] textforqueue = new String[8];
                for (int i=0; i<8; i++){
                    time = selected_hour + ":" + selected_minute;
                    if(selected_minute==0)
                        time = selected_hour + ":0" + selected_minute;
                    timeforqueue[i] = time;
                    textforqueue[i] = date + " "  + dayname + " " + time;
                    selected_minute+=15;
                    if (selected_minute==60){
                        selected_minute=0;
                        selected_hour++;
                    }
                    if(selected_hour==16) break;
                }
                textViewqueue_1.setText(textforqueue[0]);
                textViewqueue_2.setText(textforqueue[1]);
                textViewqueue_3.setText(textforqueue[2]);
                textViewqueue_4.setText(textforqueue[3]);
                textViewqueue_5.setText(textforqueue[4]);
                textViewqueue_6.setText(textforqueue[5]);
                textViewqueue_7.setText(textforqueue[6]);
                textViewqueue_8.setText(textforqueue[7]);
                textViewqueue_1.setEnabled(true);
                textViewqueue_2.setEnabled(true);
                textViewqueue_3.setEnabled(true);
                textViewqueue_4.setEnabled(true);
                textViewqueue_5.setEnabled(true);
                textViewqueue_6.setEnabled(true);
                textViewqueue_7.setEnabled(true);
                textViewqueue_8.setEnabled(true);

                //if there is a problem delete from here.
                MyDB = FirebaseDatabase.getInstance().getReference().child("Business").child(business_id).child("ZZZZQueue");
                MyDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> iterator;
                        DataSnapshot nextchild;
                        if(dataSnapshot.hasChildren()){
                            for(DataSnapshot item:dataSnapshot.getChildren()){
                                if (item.getKey().equalsIgnoreCase(date)){
                                    iterator = item.getChildren().iterator();
                                    while (iterator.hasNext()){
                                        nextchild = item.child(iterator.next().getKey());
                                        for(int i=0; i<8; i++){
                                            if (nextchild.getKey().equals(timeforqueue[i])){
                                                flag_for_queue[i]=true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(flag_for_queue[0])  textViewqueue_1.setTextColor(getResources().getColor(R.color.red));
                        else  textViewqueue_1.setTextColor(getResources().getColor(R.color.green));
                        if(flag_for_queue[1])  textViewqueue_2.setTextColor(getResources().getColor(R.color.red));
                        else  textViewqueue_2.setTextColor(getResources().getColor(R.color.green));
                        if(flag_for_queue[2])  textViewqueue_3.setTextColor(getResources().getColor(R.color.red));
                        else  textViewqueue_3.setTextColor(getResources().getColor(R.color.green));
                        if(flag_for_queue[3])  textViewqueue_4.setTextColor(getResources().getColor(R.color.red));
                        else  textViewqueue_4.setTextColor(getResources().getColor(R.color.green));
                        if(flag_for_queue[4])  textViewqueue_5.setTextColor(getResources().getColor(R.color.red));
                        else  textViewqueue_5.setTextColor(getResources().getColor(R.color.green));
                        if(flag_for_queue[5])  textViewqueue_6.setTextColor(getResources().getColor(R.color.red));
                        else  textViewqueue_6.setTextColor(getResources().getColor(R.color.green));
                        if(flag_for_queue[6])  textViewqueue_7.setTextColor(getResources().getColor(R.color.red));
                        else  textViewqueue_7.setTextColor(getResources().getColor(R.color.green));
                        if(flag_for_queue[7])  textViewqueue_8.setTextColor(getResources().getColor(R.color.red));
                        else  textViewqueue_8.setTextColor(getResources().getColor(R.color.green));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //delet till here
            }
        };

       // MyDB = FirebaseDatabase.getInstance().getReference().child("Business").child(business_id).child(User_id).child(reception_hours);
          MyDB = FirebaseDatabase.getInstance().getReference().child("Business").child(business_id).child("Queue");
          textViewqueue_1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(!flag_for_queue[0]){
                      MyDB.child(date).child(timeforqueue[0]).setValue(my_first_name+" "+my_last_name);
                              Intent success_submiteion = new Intent(getApplicationContext(),profileActivity.class);
                              startActivity(success_submiteion);
                              Toast.makeText(getApplicationContext(),"queue ordered, thanks you!",Toast.LENGTH_LONG).show();
                              finish();
                  }
                  else Toast.makeText(getApplicationContext(),"this queue is already taken",Toast.LENGTH_LONG).show();
//                  MyDB.addListenerForSingleValueEvent(new ValueEventListener() {
//                      @Override
//                      public void onDataChange(DataSnapshot dataSnapshot) {
//                          Iterator<DataSnapshot> iterator;
//                          DataSnapshot nextchild;
//                          if(dataSnapshot.hasChildren()){
//                              for(DataSnapshot item:dataSnapshot.getChildren()){
//                                  if (item.getKey().equalsIgnoreCase(date)){
//                                       iterator = item.getChildren().iterator();
//                                       while (iterator.hasNext()){
//                                           nextchild = item.child(iterator.next().getKey());
//                                           if (nextchild.getKey().equals(timeforqueue[0])){
//                                                date_flag=true;
//                                           }
//                                       }
//                                       if(date_flag){
//                                           Toast.makeText(getApplicationContext(),"this queue is already taken",Toast.LENGTH_LONG).show();
//                                       }
//                                       else{
//                                           MyDB.child(date).child(timeforqueue[0]).setValue(my_first_name+" "+my_last_name);
//                                           Intent success_submiteion = new Intent(getApplicationContext(),profileActivity.class);
//                                           startActivity(success_submiteion);
//                                           Toast.makeText(getApplicationContext(),"queue ordered, thanks you!",Toast.LENGTH_LONG).show();
//                                           finish();
//                                       }
//                                  }
//                              }
//                          }
//                          else{
//                              MyDB.child(date).child(timeforqueue[0]).setValue(my_first_name+" "+my_last_name);
//                              Intent success_submiteion = new Intent(getApplicationContext(),profileActivity.class);
//                              startActivity(success_submiteion);
//                              Toast.makeText(getApplicationContext(),"queue ordered, thanks you!",Toast.LENGTH_LONG).show();
//                              finish();
//                          }
//
//                      }
//
//                      @Override
//                      public void onCancelled(DatabaseError databaseError) {
//
//                      }
//                  });
              }
          });

          textViewqueue_2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(!flag_for_queue[1]){
                      MyDB.child(date).child(timeforqueue[1]).setValue(my_first_name+" "+my_last_name);
                      Intent success_submiteion = new Intent(getApplicationContext(),profileActivity.class);
                      startActivity(success_submiteion);
                      Toast.makeText(getApplicationContext(),"queue ordered, thanks you!",Toast.LENGTH_LONG).show();
                      finish();
                  }
                  else Toast.makeText(getApplicationContext(),"this queue is already taken",Toast.LENGTH_LONG).show();
              }
          });

          textViewqueue_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag_for_queue[2]){
                    MyDB.child(date).child(timeforqueue[2]).setValue(my_first_name+" "+my_last_name);
                    Intent success_submiteion = new Intent(getApplicationContext(),profileActivity.class);
                    startActivity(success_submiteion);
                    Toast.makeText(getApplicationContext(),"queue ordered, thanks you!",Toast.LENGTH_LONG).show();
                    finish();
                }
                else Toast.makeText(getApplicationContext(),"this queue is already taken",Toast.LENGTH_LONG).show();
            }
        });

        textViewqueue_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag_for_queue[3]){
                    MyDB.child(date).child(timeforqueue[3]).setValue(my_first_name+" "+my_last_name);
                    Intent success_submiteion = new Intent(getApplicationContext(),profileActivity.class);
                    startActivity(success_submiteion);
                    Toast.makeText(getApplicationContext(),"queue ordered, thanks you!",Toast.LENGTH_LONG).show();
                    finish();
                }
                else Toast.makeText(getApplicationContext(),"this queue is already taken",Toast.LENGTH_LONG).show();
            }
        });

        textViewqueue_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag_for_queue[4]){
                    MyDB.child(date).child(timeforqueue[4]).setValue(my_first_name+" "+my_last_name);
                    Intent success_submiteion = new Intent(getApplicationContext(),profileActivity.class);
                    startActivity(success_submiteion);
                    Toast.makeText(getApplicationContext(),"queue ordered, thanks you!",Toast.LENGTH_LONG).show();
                    finish();
                }
                else Toast.makeText(getApplicationContext(),"this queue is already taken",Toast.LENGTH_LONG).show();
            }
        });

        textViewqueue_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag_for_queue[5]){
                    MyDB.child(date).child(timeforqueue[5]).setValue(my_first_name+" "+my_last_name);
                    Intent success_submiteion = new Intent(getApplicationContext(),profileActivity.class);
                    startActivity(success_submiteion);
                    Toast.makeText(getApplicationContext(),"queue ordered, thanks you!",Toast.LENGTH_LONG).show();
                    finish();
                }
                else Toast.makeText(getApplicationContext(),"this queue is already taken",Toast.LENGTH_LONG).show();
            }
        });

        textViewqueue_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag_for_queue[6]){
                    MyDB.child(date).child(timeforqueue[6]).setValue(my_first_name+" "+my_last_name);
                    Intent success_submiteion = new Intent(getApplicationContext(),profileActivity.class);
                    startActivity(success_submiteion);
                    Toast.makeText(getApplicationContext(),"queue ordered, thanks you!",Toast.LENGTH_LONG).show();
                    finish();
                }
                else Toast.makeText(getApplicationContext(),"this queue is already taken",Toast.LENGTH_LONG).show();
            }
        });

        textViewqueue_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag_for_queue[7]){
                    MyDB.child(date).child(timeforqueue[7]).setValue(my_first_name+" "+my_last_name);
                    Intent success_submiteion = new Intent(getApplicationContext(),profileActivity.class);
                    startActivity(success_submiteion);
                    Toast.makeText(getApplicationContext(),"queue ordered, thanks you!",Toast.LENGTH_LONG).show();
                    finish();
                }
                else Toast.makeText(getApplicationContext(),"this queue is already taken",Toast.LENGTH_LONG).show();
            }
        });



    }

    private String getDayOfWeek(int value){
        String day = "";
        switch(value){
            case 1:
                day="Sun";
                break;
            case 2:
                day="Mon";
                break;
            case 3:
                day="Tue";
                break;
            case 4:
                day="Wed";
                break;
            case 5:
                day="Thu";
                break;
            case 6:
                day="Fri";
                break;
            case 7:
                day="Sat";
                break;
        }
        return day;
    }

    private int CheckMinute (int minute){
        time = selected_hour + ":" + selected_minute;
        if (selected_minute<10)
            time = selected_hour + ":0" + selected_minute;
        return 0;
    }
}





