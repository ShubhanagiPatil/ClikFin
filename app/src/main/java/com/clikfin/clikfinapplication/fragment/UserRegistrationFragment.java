package com.clikfin.clikfinapplication.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;
import com.clikfin.clikfinapplication.constants.Regex;
import com.clikfin.clikfinapplication.externalRequests.Request.RegisterUser;
import com.clikfin.clikfinapplication.externalRequests.Response.AuthenticatedUser;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;
import com.google.android.material.snackbar.Snackbar;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

public class UserRegistrationFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private EditText userFullName, userContact, userEmail, userPassword, userRePassword;
    private TextView nameError, contactNumberError, emailError, passwordError, rePasswordError;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_registration, container, false);
        Button submitButton;
        userFullName = view.findViewById(R.id.name);
        nameError = view.findViewById(R.id.name_error);
        userContact = view.findViewById(R.id.contact_num);
        contactNumberError = view.findViewById(R.id.contact_error);
        userEmail = view.findViewById(R.id.email);
        emailError = view.findViewById(R.id.email_error);
        userPassword = view.findViewById(R.id.password);
        passwordError = view.findViewById(R.id.password_error);
        userRePassword = view.findViewById(R.id.re_password);
        rePasswordError = view.findViewById(R.id.re_password_error);
        submitButton = view.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(this);
        return view;
    }

    private boolean checkValidInput() {
        boolean returnValue = true;
        String nameInput = userFullName.getText().toString();
        String contactInput = userContact.getText().toString();
        String emailInput = userEmail.getText().toString();
        String passwordInput = userPassword.getText().toString();
        String rePasswordInput = userRePassword.getText().toString();

        if (nameInput.isEmpty()) {
            Common.setError(nameError, this.getString(R.string.name_empty_error), getActivity());
            returnValue = false;
        } else {
            nameError.setText("");
        }

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

        if (emailInput.isEmpty()) {
            Common.setError(emailError, this.getString(R.string.email_empty_error), getActivity());
            returnValue = false;
        } else if (!emailInput.toLowerCase().matches(Regex.EMAIL_PATTERN)) {
            Common.setError(emailError, this.getString(R.string.email_invalid_error), getActivity());
            returnValue = false;
        } else {
            emailError.setText("");
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

        if (rePasswordInput.isEmpty()) {
            Common.setError(rePasswordError, this.getString(R.string.re_password_empty_error), getActivity());
            returnValue = false;
        } else if (!rePasswordInput.equals(passwordInput)) {
            Common.setError(rePasswordError, this.getString(R.string.re_password_mismatch_error), getActivity());
            returnValue = false;
        } else {
            rePasswordError.setText("");
        }

        return returnValue;
    }

    private void registerUser() {
        String nameInput = userFullName.getText().toString();
        String contactInput = userContact.getText().toString();
        String emailInput = userEmail.getText().toString();
        String passwordInput = userPassword.getText().toString();
        RegisterUser registerUser = new RegisterUser(nameInput, emailInput, passwordInput, contactInput);
        Call<AuthenticatedUser> call = APIClient.getClient(APIClient.type.JSON).registerUser(registerUser);
        call.enqueue(new APICallbackInterface<AuthenticatedUser>(getActivity()) {
            @Override
            public void onResponse(Call<AuthenticatedUser> call, Response<AuthenticatedUser> response) {
               super.onResponse(call, response);
                // Common.enableTouchOnScreen(getActivity());
                switch (response.code()) {
                    case 201:
                        if (response.body() != null) {
                            String authenticatedToken = response.body().getToken();
                            SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(getActivity());
                            editor.putString(getString(R.string.user_auth_token), authenticatedToken);
                            editor.putString(getString(R.string.user_name), nameInput);
                            editor.putString(getString(R.string.user_email), emailInput);
                            editor.apply();
                            Intent dashboardScreen = new Intent(getActivity(), DashboardActivity.class);
                            startActivity(dashboardScreen);
                            getActivity().finish();
                        } else {
                            Snackbar.make(view, getString(R.string.server_error), Snackbar.LENGTH_LONG).show();
                        }
                        break;
                    case 400:
                        Converter<ResponseBody, RegisterUser> registerUserConverter = APIClient.getRetrofit().responseBodyConverter(RegisterUser.class, new Annotation[0]);
                        try {
                            setServerErrorMsg(registerUserConverter.convert(response.errorBody()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 409:
                        Snackbar.make(view, getString(R.string.user_already_registered), Snackbar.LENGTH_LONG).show();
                        break;
                    case 500:
                        Snackbar.make(view, getString(R.string.server_error), Snackbar.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<AuthenticatedUser> call, Throwable t) {
                super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Snackbar.make(view, getString(R.string.internet_connectivity_issue), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setServerErrorMsg(RegisterUser registerUser) {
        if (registerUser.getName() != null) {
            Common.setError(nameError, registerUser.getName(), getActivity());
        }
        if (registerUser.getEmail() != null) {
            Common.setError(emailError, registerUser.getEmail(), getActivity());
        }
        if (registerUser.getPhoneNumber() != null) {
            Common.setError(contactNumberError, registerUser.getPhoneNumber(), getActivity());
        }
        if (registerUser.getPassword() != null) {
            Common.setError(passwordError, registerUser.getPassword(), getActivity());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submitButton) {
            if (checkValidInput()) {
                // Common.disableTouchOnScreen(getActivity());
                registerUser();
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Common.hideKeyboard(getActivity());
        view.performClick();
        return false;
    }
}
