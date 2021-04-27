package com.clikfin.clikfinapplication.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.constants.Constants;
import com.clikfin.clikfinapplication.externalRequests.Response.AuthenticatedUser;
import com.clikfin.clikfinapplication.fragment.SplashFragment;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            Constants.IN_APP_UPDATE_REQUEST);
                } catch (IntentSender.SendIntentException e) {
                    Common.logExceptionToCrashlaytics(e);
                }
            } else {

                if (getIntent().hasExtra("pushnotification")) {
                    Intent intent = null;

                    if(getIntent().getStringExtra("Title").equalsIgnoreCase("Hello")){
                        intent = new Intent(this, HelloActivity.class);
                    }else {
                        intent = new Intent(this, NotificationActivity.class);
                    }

                    startActivity(intent);
                    finish();
                }else{
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new SplashFragment();
                    fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
                    fragmentTransaction.commitAllowingStateLoss();

                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            this,
                                            Constants.IN_APP_UPDATE_REQUEST);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Common.hideKeyboard(MainActivity.this);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.IN_APP_UPDATE_REQUEST) {
            if(resultCode!=RESULT_OK){
                Toast.makeText(MainActivity.this,"Update failed...",Toast.LENGTH_LONG).show();
            }
        }
    }


}
