package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;
import com.clikfin.clikfinapplication.activity.MainActivity;
import com.clikfin.clikfinapplication.constants.Regex;
import com.clikfin.clikfinapplication.externalRequests.Request.FCMTokenRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.Login;
import com.clikfin.clikfinapplication.externalRequests.Response.AuthenticatedUser;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {

    EditText password, contactNumber;
    TextView buttonLogin, buttonPassword, forgotPassword;
    TextView contactNumberError, passwordError;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        contactNumber = view.findViewById(R.id.contact_number);
        contactNumberError = view.findViewById(R.id.contact_error);
        password = view.findViewById(R.id.password);
        passwordError = view.findViewById(R.id.password_error);
        buttonLogin = view.findViewById(R.id.login);
        buttonPassword = view.findViewById(R.id.register);
        forgotPassword = view.findViewById(R.id.forgot_password);

        buttonLogin.setOnClickListener(this);
        buttonPassword.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager;
        Fragment fragment;

        switch (v.getId()) {
            case R.id.login:
                if (checkValidInput()) {
                    // Common.disableTouchOnScreen(getActivity());
                    performLogin();
                }
                break;
            case R.id.register:
                try {
                    fragmentManager = getActivity().getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragment = new UserRegistrationFragment();
                    fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } catch (NullPointerException e) {
                    Common.logExceptionToCrashlaytics(e);
                }
                break;
            case R.id.forgot_password:
                break;
        }
    }

    private boolean checkValidInput() {
        boolean returnValue = true;
        String contactInput = contactNumber.getText().toString();
        String passwordInput = password.getText().toString();

        if (contactInput.isEmpty()) {
            Common.setError(contactNumberError, this.getString(R.string.contact_number_empty_error), getActivity());
            returnValue = false;
        } else if (contactInput.length() < 10) {
            Common.setError(contactNumberError, this.getString(R.string.contact_number_length_error), getActivity());
            returnValue = false;
        } else if (!contactInput.matches(Regex.MOBILE_NUMBER_PATTERN)) {
            Common.setError(contactNumberError, this.getString(R.string.contact_number_invalid_error), getActivity());
            returnValue = false;
        } else {
            contactNumberError.setText("");
        }

        if (passwordInput.isEmpty()) {
            Common.setError(passwordError, this.getString(R.string.password_empty_error), getActivity());
            returnValue = false;
        } else if (passwordInput.length() < 8) {
            Common.setError(passwordError, this.getString(R.string.password_length_error), getActivity());
            returnValue = false;
        } else {
            passwordError.setText("");
        }

        return returnValue;
    }

    private void setServerErrorMsg(Login login) {
        if (login.getPhoneNumber() != null) {
            Common.setError(contactNumberError, login.getPhoneNumber(), getActivity());
        }
        if (login.getPassword() != null) {
            Common.setError(passwordError, login.getPassword(), getActivity());
        }
        if(login.getError()!=null){
            Common.setError(passwordError, login.getError(), getActivity());
        }
    }

    private void performLogin() {
        String phoneNumber = contactNumber.getText().toString();
        String Password = password.getText().toString();
        Login login = new Login();
        login.setPhoneNumber(phoneNumber);
        login.setPassword(Password);
        Call<AuthenticatedUser> call = APIClient.getClient(APIClient.type.JSON).login(login);
        call.enqueue(new APICallbackInterface<AuthenticatedUser>(getActivity()) {
            @Override
            public void onResponse(@NonNull Call<AuthenticatedUser> call, @NonNull Response<AuthenticatedUser> response) {
                super.onResponse(call, response);
                switch (response.code()) {
                    case 200:
                        if (response.body() != null) {
                            SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(getActivity());

                            String authenticatedToken = response.body().getToken();
                            String userId=response.body().getUserId();
                            editor.putString(getString(R.string.user_auth_token), authenticatedToken);
                            editor.putString(getString(R.string.user_id),userId);
                            editor.apply();

                            FirebaseMessaging.getInstance().getToken()
                                    .addOnCompleteListener(new OnCompleteListener<String>() {
                                        @Override
                                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(getActivity(),"FCM Token Generation Fail",Toast.LENGTH_LONG).show();
                                                return;
                                            }

                                            // Get new FCM registration token
                                            String token = task.getResult();
                                            Log.i("FCM Token",token);
                                            if(token!=null){

                                                FCMTokenRequest fcmTokenRequest=new FCMTokenRequest();
                                                fcmTokenRequest.setFcmToken(token);
                                                fcmTokenRequest.setUserId(userId);

                                                Call<String> call = APIClient.getClient(APIClient.type.JSON).postFCMToken(fcmTokenRequest);
                                                call.enqueue(new APICallbackInterface<String>(getActivity()) {
                                                    @Override
                                                    public void onResponse(Call<String> call, Response<String> response) {
                                                        super.onResponse(call, response);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<String> call, Throwable t) {
                                                        super.onFailure(call, t);
                                                    }
                                                });
                                            }

                                        }
                                    });



                            Intent dashboardScreen = new Intent(getActivity(), DashboardActivity.class);
                            startActivity(dashboardScreen);
                            getActivity().finish();
                        } else {
                            Snackbar.make(view, getString(R.string.server_error), Snackbar.LENGTH_LONG).show();
                        }
                        break;
                    case 400:
                        Converter<ResponseBody, Login> loginConverter = APIClient.getRetrofit().responseBodyConverter(Login.class, new Annotation[0]);
                        try {
                            setServerErrorMsg(loginConverter.convert(response.errorBody()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 500:
                        Snackbar.make(view, getString(R.string.server_error), Snackbar.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthenticatedUser> call, @NonNull Throwable t) {
                super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Snackbar.make(view, getString(R.string.internet_connectivity_issue), Snackbar.LENGTH_LONG).show();
            }
        });
    }


}
