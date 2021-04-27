package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;
import com.clikfin.clikfinapplication.externalRequests.Request.ReferAndEarn;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

public class ReferAndEarnFragment extends Fragment {
    EditText ed_r_a_e_Name, ed_r_a_e_ContactNo, ed_r_a_e_City, edReferredByName, edReferredByContactNo;
    TextView r_a_e_Name_error, r_a_e_ContactNo_error, r_a_e_City_error, referredByName_error, referredByContactNo_error;

    static FragmentActivity activity;
    Context context;

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

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.refer_and_earn_fragment, container, false);
      /*  ed_r_a_e_Name = view.findViewById(R.id.ed_r_a_e_Name);
        ed_r_a_e_ContactNo = view.findViewById(R.id.ed_r_a_e_ContactNo);
        ed_r_a_e_City = view.findViewById(R.id.ed_r_a_e_City);
        edReferredByName = view.findViewById(R.id.edReferredByName);
        edReferredByContactNo = view.findViewById(R.id.edReferredByContactNo);

        r_a_e_Name_error = view.findViewById(R.id.r_a_e_Name_error);
        r_a_e_ContactNo_error = view.findViewById(R.id.r_a_e_ContactNo_error);
        r_a_e_City_error = view.findViewById(R.id.r_a_e_City_error);
        referredByName_error = view.findViewById(R.id.referredByName_error);
        referredByContactNo_error = view.findViewById(R.id.referredByContactNo_error);

        ed_r_a_e_Name.setFilters(new InputFilter[]{Common.letterFilter});
        edReferredByName.setFilters(new InputFilter[]{Common.letterFilter});

        Button btnReferenceInfoNext = view.findViewById(R.id.btnReferenceInfoNext);
        btnReferenceInfoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidInput()) {
                    if (Common.isNet(context)) {
                        postReferAndEarnDetails(getReferAndEarnData());
                    } else {
                        Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
*/
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_refer_and_earn));
        return view;
    }

    private void setServerErrorMsg(ReferAndEarn referAndEarn) {
        if (referAndEarn.getReferredByContactNo() != null) {
            Common.setError(referredByContactNo_error, "Contact Number " + referAndEarn.getReferredByContactNo(), context);
            edReferredByContactNo.requestFocus();
        } else {
            Common.setError(referredByContactNo_error, "", context);
        }
        if (referAndEarn.getReferredByName() != null) {
            Common.setError(referredByName_error, "Name " + referAndEarn.getReferredByName(), context);
            edReferredByName.requestFocus();
        } else {
            Common.setError(referredByName_error, "", context);
        }
        if (referAndEarn.getCity() != null) {
            Common.setError(r_a_e_City_error, "City " + referAndEarn.getCity(), context);
            ed_r_a_e_City.requestFocus();
        } else {
            Common.setError(r_a_e_City_error, "", context);
        }
        if (referAndEarn.getContactNo() != null) {
            Common.setError(r_a_e_ContactNo_error, "Contact Number " + referAndEarn.getContactNo(), context);
            ed_r_a_e_ContactNo.requestFocus();
        } else {
            Common.setError(r_a_e_ContactNo_error, "", context);
        }
        if (referAndEarn.getName() != null) {
            Common.setError(r_a_e_Name_error, "Name " + referAndEarn.getName(), context);
            ed_r_a_e_Name.requestFocus();
        } else {
            Common.setError(r_a_e_Name_error, "", context);
        }


    }

    private boolean checkValidInput() {
        boolean returnValue = true;
        if (ed_r_a_e_Name.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(r_a_e_Name_error, "Name " + getString(R.string.input_field_empty), context);
            ed_r_a_e_Name.requestFocus();
            return false;
        } else {
            Common.setError(r_a_e_Name_error, "", context);
        }
        if (ed_r_a_e_ContactNo.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(r_a_e_ContactNo_error, "Contact Number " + getString(R.string.input_field_empty), context);
            ed_r_a_e_ContactNo.requestFocus();
            return false;
        } else if (!Common.isValidPhoneNumber(ed_r_a_e_ContactNo.getText().toString().trim())) {
            Common.setError(r_a_e_ContactNo_error, getString(R.string.contact_number_invalid_error), context);
            ed_r_a_e_ContactNo.requestFocus();
            return false;
        } else {
            Common.setError(r_a_e_ContactNo_error, "", context);
        }
        if (ed_r_a_e_City.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(r_a_e_City_error, "City " + getString(R.string.input_field_empty), context);
            ed_r_a_e_City.requestFocus();
            return false;
        } else {
            Common.setError(r_a_e_City_error, "", context);
        }
        if (edReferredByName.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(referredByName_error, "Name " + getString(R.string.input_field_empty), context);
            edReferredByName.requestFocus();
            return false;
        } else {
            Common.setError(referredByName_error, "", context);
        }
        if (edReferredByContactNo.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(referredByContactNo_error, "Contact Number " + getString(R.string.input_field_empty), context);
            return false;
        } else if (!Common.isValidPhoneNumber(edReferredByContactNo.getText().toString().trim())) {
            Common.setError(referredByContactNo_error, getString(R.string.contact_number_invalid_error), context);
            return false;
        } else {
            Common.setError(referredByContactNo_error, "", context);
        }

        return returnValue;
    }

    private ReferAndEarn getReferAndEarnData() {
        ReferAndEarn referAndEarn = new ReferAndEarn();
        referAndEarn.setName(ed_r_a_e_Name.getText().toString());
        referAndEarn.setContactNo(ed_r_a_e_ContactNo.getText().toString());
        referAndEarn.setCity(ed_r_a_e_City.getText().toString());
        referAndEarn.setReferredByName(edReferredByName.getText().toString());
        referAndEarn.setReferredByContactNo(edReferredByContactNo.getText().toString());
        return referAndEarn;
    }

    private void postReferAndEarnDetails(ReferAndEarn referAndEarn) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        Call<String> call = APIClient.getClient(APIClient.type.JSON).postReferAndEarnDetails(authToken, referAndEarn);
        call.enqueue(new APICallbackInterface<String>(context) {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                super.onResponse(call, response);
                switch (response.code()) {
                    case 200:
                        break;
                    case 400:
                        Converter<ResponseBody, ReferAndEarn> referAndEarnDetailsConverter = APIClient.getRetrofit().responseBodyConverter(ReferAndEarn.class, new Annotation[0]);
                        try {
                            setServerErrorMsg(referAndEarnDetailsConverter.convert(response.errorBody()));
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
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
            }
        });
    }
}
