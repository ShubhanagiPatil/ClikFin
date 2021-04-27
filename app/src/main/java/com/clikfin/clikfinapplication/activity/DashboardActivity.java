package com.clikfin.clikfinapplication.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.fragment.AboutUsFragment;
import com.clikfin.clikfinapplication.fragment.CibilCheckFragment;
import com.clikfin.clikfinapplication.fragment.DashboardFragment;
import com.clikfin.clikfinapplication.fragment.EligibleCheckFragment;
import com.clikfin.clikfinapplication.fragment.FAQFragment;
import com.clikfin.clikfinapplication.fragment.OurPartnersFragment;
import com.clikfin.clikfinapplication.fragment.PrivacyPolicyFragment;
import com.clikfin.clikfinapplication.fragment.TermConditionFragment;
import com.clikfin.clikfinapplication.fragment.UserProfileFragment;
import com.clikfin.clikfinapplication.util.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

@SuppressWarnings("ALL")
public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener {
    public static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView nv;
    private Toolbar toolbar;
    private ImageView image_home;
    private TextView toolbartext;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment = null;
    private RelativeLayout clikfinRelative;
    private String setFragment;
    BottomNavigationView bottomNavigationView;
    NavigationView sideNavigationView;
    TextView tvUserName, tvUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for notification bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        toolbar = findViewById(R.id.toolbar);
        // setNavigationTitle("Dashboard");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        sideNavigationView = findViewById(R.id.side_navigation_view);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        View header = sideNavigationView.getHeaderView(0);
        tvUserName = (TextView) header.findViewById(R.id.tvUserName);
        tvUserEmail = (TextView) header.findViewById(R.id.tvUserEmailId);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mDrawerToggle = setupDrawerToggle();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        sideNavigationView.setNavigationItemSelectedListener(DashboardActivity.this);
        bottomNavigationView.setOnNavigationItemSelectedListener(DashboardActivity.this);
        bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_dashboard);
        SharedPreferences sharedPreferences = DashboardActivity.this.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        tvUserName.setText(sharedPreferences.getString(getString(R.string.user_name), ""));
        tvUserEmail.setText(sharedPreferences.getString(getString(R.string.user_email), ""));

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    // show back button
                    mDrawerToggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().popBackStack();
                        }
                    });
                } else {
                    //show hamburger
                    mDrawerToggle.setDrawerIndicatorEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    mDrawerToggle.syncState();
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDrawerLayout.openDrawer(GravityCompat.START);
                        }
                    });
                }
            }
        });

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.Open, R.string.Close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        return mDrawerToggle;
    }

    //side navigation item selected
    //bottom navigation item selected
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_t_a_c:
                replaceFragment(new TermConditionFragment());
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_privacy_policy:
                replaceFragment(new PrivacyPolicyFragment());
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_about_us:
                replaceFragment(new AboutUsFragment());
                mDrawerLayout.closeDrawers();
                break;
           /* case R.id.nav_settings:
                replaceFragment(new SettingFragment());
                mDrawerLayout.closeDrawers();
                break;*/
            case R.id.nav_help:
                open();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_support:
                open();
                mDrawerLayout.closeDrawers();
                break;
          /*  case R.id.nav_share:
                replaceFragment(new ShareFragment());
                mDrawerLayout.closeDrawers();
                break;*/
            case R.id.nav_f_a_q:
                replaceFragment(new FAQFragment());
                mDrawerLayout.closeDrawers();
                break;
           /* case R.id.nav_log_out:
                replaceFragment(new LogoutFragment());
                mDrawerLayout.closeDrawers();
                break;*/
            case R.id.nav_our_partners:
                replaceFragment(new OurPartnersFragment());
                mDrawerLayout.closeDrawers();
                break;
            case R.id.bottom_navigation_dashboard:
                replaceFragment(new DashboardFragment());
                mDrawerLayout.closeDrawers();
                break;
            case R.id.bottom_navigation_cibil_check:
                replaceFragment(new CibilCheckFragment());
                mDrawerLayout.closeDrawers();
                break;
            case R.id.bottom_navigation_user_profile:
                replaceFragment(new UserProfileFragment());
                mDrawerLayout.closeDrawers();
                break;
            case R.id.bottom_navigation_emi_cal:
                replaceFragment(new EligibleCheckFragment());
                mDrawerLayout.closeDrawers();
                break;

        }
        return true;
    }

    /*public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    public void setNavigationTitle(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(title);
    }

    public void open() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
        alertDialogBuilder.setTitle("Contact Us");

        alertDialogBuilder.setMessage("support@clikfin.com ");
        // alertDialogBuilder.setMessage("(daily 9 AM -8 PM, except public holidays) for any queries");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //  getActivity().finish();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#2d55a5"));
    }

    public void replaceFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Common.hideKeyboard(DashboardActivity.this);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
            alertDialogBuilder.setTitle("Alert!");

            alertDialogBuilder.setMessage("Are you sure want to exit?");
            // alertDialogBuilder.setMessage("(daily 9 AM -8 PM, except public holidays) for any queries");
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });

            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } else {
            super.onBackPressed();
        }
    }
}
