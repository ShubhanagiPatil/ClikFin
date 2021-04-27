package com.clikfin.clikfinapplication.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.clikfin.clikfinapplication.R;

public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
