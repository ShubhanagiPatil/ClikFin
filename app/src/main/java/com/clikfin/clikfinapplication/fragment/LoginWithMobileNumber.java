package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.externalRequests.Request.OTPSendRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.PersonalDetails;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

public class LoginWithMobileNumber extends Fragment {
    static FragmentActivity activity;
    Context context;
    Button btnGetOtp;
    EditText edMobNo;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof FragmentActivity) {
            activity = (FragmentActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_with_mobile_number, container, false);
        btnGetOtp = view.findViewById(R.id.btnGetOtp);
        edMobNo = view.findViewById(R.id.edOTP);


        edMobNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 10 && Common.isValidPhoneNumber(s.toString().trim())) {
                    //    btnGetOtp.setClickable(true);
                    btnGetOtp.setTextColor(Color.parseColor("#ffffff"));
                    btnGetOtp.setBackground(context.getDrawable(R.drawable.round_button));

                } else {
                    //   btnGetOtp.setClickable(false);
                    btnGetOtp.setTextColor(getResources().getColor(R.color.splash_color));
                    btnGetOtp.setBackground(context.getDrawable(R.drawable.border_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMobNo.getText().toString().trim().length() == 10 && Common.isValidPhoneNumber(edMobNo.getText().toString().trim())) {
                    if (getArguments() != null) {
                        boolean isResendMobileNumber = getArguments().getBoolean("isResendMobileNumber");
                        int resendTrailsNumber = getArguments().getInt("resendTrailsNumber");
                        if (isResendMobileNumber && resendTrailsNumber == 3) {
                            Toast.makeText(context, "You have reached maximum limit to edit mobile number", Toast.LENGTH_LONG).show();
                        } else {
                            postUserMobileNumber(edMobNo.getText().toString().trim(), true);
                        }
                    } else {
                        postUserMobileNumber(edMobNo.getText().toString().trim(), false);
                    }

                } else {
                    edMobNo.setError(getString(R.string.valid_mobile_number_msg));
                }


            }
        });
        return view;
    }

    private void postUserMobileNumber(String mobileNumber, boolean isRetry) {
        OTPSendRequest otpSendRequest = new OTPSendRequest();
        otpSendRequest.setPhoneNumber(mobileNumber);
        otpSendRequest.setRetry(isRetry);
        Call<String> call = APIClient.getClient(APIClient.type.JSON).postLoginWithMobileNumber(otpSendRequest);
        call.enqueue(new APICallbackInterface<String>(context) {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                super.onResponse(call, response);

                switch (response.code()) {
                    case 204:
                        Toast.makeText(context, "OTP send successfully", Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(getActivity());
                        editor.putString(getString(R.string.user_mobile), edMobNo.getText().toString().trim());
                        editor.apply();
                        replaceFragment(new OTPFragment());
                        break;
                    case 409:
                    case 412:
                        Toast.makeText(context, "MSG91-Integration error", Toast.LENGTH_LONG).show();
                        break;
                    case 408:
                        Toast.makeText(context, "OTP expired", Toast.LENGTH_LONG).show();
                        break;
                    case 429:
                        Toast.makeText(context, "Too many request", Toast.LENGTH_LONG).show();
                        break;
                    case 400:
                        Converter<ResponseBody, OTPSendRequest> PersonalDetailsConverter = APIClient.getRetrofit().responseBodyConverter(OTPSendRequest.class, new Annotation[0]);
                        try {
                            setServerErrorMsg(PersonalDetailsConverter.convert(response.errorBody()));
                        } catch (Exception e) {
                            Common.logExceptionToCrashlaytics(e);
                        }
                        break;
                    case 500:
                        Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        break;

                }
            }


            @Override
            public void onFailure(Call<String> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
    private void setServerErrorMsg(OTPSendRequest otpSendRequest) {
        if(otpSendRequest.getPhoneNumber()!=null){
            edMobNo.setError("Mobile number "+otpSendRequest.getPhoneNumber());
        }
    }
    public void replaceFragment(Fragment fragment) {
        fragmentManager = activity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        // fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
        //   fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
