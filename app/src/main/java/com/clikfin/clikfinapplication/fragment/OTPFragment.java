package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.externalRequests.Request.Login;
import com.clikfin.clikfinapplication.externalRequests.Request.OTPSendRequest;
import com.clikfin.clikfinapplication.externalRequests.Request.OTPVerifyRequest;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

public class OTPFragment extends Fragment {
    static FragmentActivity activity;
    Context context;
    EditText edOTP;
    Button btnVerifyOtp;
    TextView tvOTPTime, regMobNo, tvEditMobNo, resendOTP;
    LinearLayout layOTPTimer, layResendOTP;
    public static int RE_SENT_USER_MOBILE_NUMBER_TRAILS;
    String userMobile;
    public static boolean isResendMobileNumber;
    TextView tvVerifyOTPError;

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
        View view = inflater.inflate(R.layout.activity_o_t_p, container, false);
        edOTP = view.findViewById(R.id.edOTP);
        btnVerifyOtp = view.findViewById(R.id.btnVerifyOtp);
        tvOTPTime = view.findViewById(R.id.tvOTPTime);
        layOTPTimer = view.findViewById(R.id.layOTPTimer);
        layResendOTP = view.findViewById(R.id.layResendOTP);
        regMobNo = view.findViewById(R.id.tvRegMobNo);
        tvEditMobNo = view.findViewById(R.id.tvEditMobNo);
        resendOTP = view.findViewById(R.id.resendOTP);
        tvVerifyOTPError = view.findViewById(R.id.tvVerifyOTPError);
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        userMobile = sharedPreferences.getString(getString(R.string.user_mobile), "");

        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString regMobSpannable = new SpannableString(regMobNo.getText().toString() + " ");
        builder.append(regMobSpannable);

        SpannableString redSpannable = new SpannableString(userMobile);
        redSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.splash_color)), 0, userMobile.length(), 0);
        builder.append(redSpannable);

        regMobNo.setText(builder, TextView.BufferType.SPANNABLE);

        tvEditMobNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isResendMobileNumber", true);
                RE_SENT_USER_MOBILE_NUMBER_TRAILS++;
                bundle.putInt("resendTrailsNumber", RE_SENT_USER_MOBILE_NUMBER_TRAILS);
                Fragment loginFragment = new LoginWithMobileNumber();
                loginFragment.setArguments(bundle);
                replaceFragment(loginFragment);
            }
        });

        edOTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 6) {
                    btnVerifyOtp.setTextColor(Color.parseColor("#ffffff"));
                    btnVerifyOtp.setBackground(context.getDrawable(R.drawable.round_button));

                } else {
                    btnVerifyOtp.setTextColor(getResources().getColor(R.color.splash_color));
                    btnVerifyOtp.setBackground(context.getDrawable(R.drawable.border_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                layResendOTP.setVisibility(View.GONE);
                layOTPTimer.setVisibility(View.VISIBLE);
                tvOTPTime.setText("" + millisUntilFinished / 1000);
                Log.i("Timing", "" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                layOTPTimer.setVisibility(View.GONE);
                layResendOTP.setVisibility(View.VISIBLE);
            }
        }.start();

        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edOTP.getText().toString().length() == 6) {
                    submitOTP(userMobile, edOTP.getText().toString().trim());
                } else {
                    edOTP.setError("Enter 6 digit OTP");
                }
            }
        });
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP(userMobile, true);
            }
        });
       /* btnSubmitOtp = view.findViewById(R.id.btnSubmitOtp);
        btnResendOtp = view.findViewById(R.id.btnResendOtp);
        otptime = view.findViewById(R.id.otptime);
        btnSubmitOtp.setVisibility(View.VISIBLE);

        new CountDownTimer(15000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                otptime.setText("" + millisUntilFinished / 1000);
                Log.i("Timing", "" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                btnSubmitOtp.setVisibility(View.GONE);
                btnResendOtp.setVisibility(View.VISIBLE);
            }
        }.start();


        btnResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<String> call = APIClient.getClient(APIClient.type.JSON).postResendOtp("");
                call.enqueue(new APICallbackInterface<String>(context) {
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
        });*/
        return view;
    }

    private void submitOTP(String mobileNumber, String otp) {
        tvVerifyOTPError.setVisibility(View.GONE);
        OTPVerifyRequest otpVerifyRequest = new OTPVerifyRequest();
        otpVerifyRequest.setPhoneNumber(mobileNumber);
        otpVerifyRequest.setOtp(otp);
        Call<String> call = APIClient.getClient(APIClient.type.JSON).postVerifyOtp(otpVerifyRequest);
        call.enqueue(new APICallbackInterface<String>(context) {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                super.onResponse(call, response);
                switch (response.code()) {
                    case 204:
                        Toast.makeText(context, "OTP verify successfully", Toast.LENGTH_LONG).show();
                        break;
                    case 409:
                        Toast.makeText(context, "OTP already verified", Toast.LENGTH_LONG).show();
                        break;
                    case 412:
                        Toast.makeText(context, "OTP expired", Toast.LENGTH_LONG).show();
                        break;
                    case 406:
                        Toast.makeText(context, "Incorrect OTP", Toast.LENGTH_LONG).show();
                        break;
                    case 429:
                        Toast.makeText(context, "Too many requests", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(context, "User with mobile umber does not registered", Toast.LENGTH_LONG).show();
                        break;
                    case 400:
                        Converter<ResponseBody, OTPVerifyRequest> loginConverter = APIClient.getRetrofit().responseBodyConverter(OTPVerifyRequest.class, new Annotation[0]);
                        try {
                            setServerErrorMsg(loginConverter.convert(response.errorBody()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                super.onFailure(call, t);
            }
        });


    }

    private void resendOTP(String mobileNumber, boolean isRetry) {
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
                            setResendOTPServerError(PersonalDetailsConverter.convert(response.errorBody()));
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

    private void setServerErrorMsg(OTPVerifyRequest otpVerifyRequest) {
        if (otpVerifyRequest.getOtp() != null) {
            edOTP.setError("OTP " + otpVerifyRequest.getOtp());
        }
        if (otpVerifyRequest.getPhoneNumber() != null) {
            tvVerifyOTPError.setVisibility(View.VISIBLE);
            tvVerifyOTPError.setText("Mobile number " + otpVerifyRequest.getPhoneNumber());
        }

    }

    private void setResendOTPServerError(OTPSendRequest otpSendRequest) {
        if (otpSendRequest.getPhoneNumber() != null) {
            edOTP.setError("Mobile number " + otpSendRequest.getPhoneNumber());
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
        //   fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
