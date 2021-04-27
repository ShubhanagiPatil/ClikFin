package com.clikfin.clikfinapplication.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

public class ContactActivity extends Dialog {
    public Activity login;
    private Button btngo,btnNext;
    private TextView otptime;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    Handler handler;

    int Seconds, Minutes, MilliSeconds ;

    public ContactActivity(Activity context) {
        super(context);
        this.login=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.contactdetails);




    }











}
