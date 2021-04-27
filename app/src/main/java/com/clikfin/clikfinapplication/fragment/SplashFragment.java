package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;


public class SplashFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        Thread background = new Thread() {
            public void run() {

                try {

                    sleep(3 * 1000);

                    SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
                    String authToken = sharedPref.getString(getString(R.string.user_auth_token), null);

                    Intent i;
                    if (authToken != null && !authToken.isEmpty()) {
                        i = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    } else {
                        sharedPref = getActivity().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
                        boolean isUserOnBoard = sharedPref.getBoolean(getString(R.string.user_on_board), false);
                        if (!isUserOnBoard) {
                            replaceFragment(new OnBoardFragment());
                        } else {
                            if (getResources().getBoolean(R.bool.is_login_with_mobile_number)) {
                                replaceFragment(new LoginWithMobileNumber());
                            } else {
                                replaceFragment(new LoginFragment());
                            }

                        }


                    }


                } catch (Exception e) {

                    e.printStackTrace();

                }
            }
        };

        // start thread
        background.start();

        return view;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
        fragmentTransaction.commit();
    }
}
