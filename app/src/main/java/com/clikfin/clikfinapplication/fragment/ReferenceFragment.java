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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;
import com.clikfin.clikfinapplication.externalRequests.Request.ReferenceDetails;
import com.clikfin.clikfinapplication.externalRequests.Response.ApplyLoanResponse;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

public class ReferenceFragment extends Fragment {
    EditText edRef1Name, edRef1ContactNo, edRef1RelationShip, edRef1CityName, edRef2Name, edRef2ContactNo, edRef2RelationShip, edRef2CityName;
    TextView ref1Name_error, ref1ContactNo_error, ref1Relationship_error, ref1CityName_error, ref2Name_error, ref2ContactNo_error, ref2Relationship_error, ref2CityName_error;
    Button btnReferenceInfoNext;
    FragmentActivity activity;
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
        View view = inflater.inflate(R.layout.fragment_references, container, false);
        edRef1Name = view.findViewById(R.id.edRef1Name);
        edRef1ContactNo = view.findViewById(R.id.edRef1ContactNo);
        edRef1RelationShip = view.findViewById(R.id.edRef1RelationShip);
        edRef1CityName = view.findViewById(R.id.edRef1CityName);
        edRef2Name = view.findViewById(R.id.edRef2Name);
        edRef2ContactNo = view.findViewById(R.id.edRef2ContactNo);
        edRef2RelationShip = view.findViewById(R.id.edRef2RelationShip);
        edRef2CityName = view.findViewById(R.id.edRef2CityName);

        ref1CityName_error = view.findViewById(R.id.ref1CityName_error);
        ref2CityName_error = view.findViewById(R.id.ref2CityName_error);
        ref1ContactNo_error = view.findViewById(R.id.ref1ContactNo_error);
        ref2ContactNo_error = view.findViewById(R.id.ref2ContactNo_error);
        ref1Relationship_error = view.findViewById(R.id.ref1Relationship_error);
        ref2Relationship_error = view.findViewById(R.id.ref2Relationship_error);
        ref1Name_error = view.findViewById(R.id.ref1Name_error);
        ref2Name_error = view.findViewById(R.id.ref2Name_error);

        edRef1Name.setFilters(new InputFilter[]{Common.letterFilter});
        edRef2Name.setFilters(new InputFilter[]{Common.letterFilter});
        btnReferenceInfoNext = view.findViewById(R.id.btnReferenceInfoNext);

        btnReferenceInfoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkInputValidation()) {
                    if (Common.isNetworkConnected(context)) {
                        sendReferenceData(getReferenceData());
                    } else {
                        Common.networkDisconnectionDialog(context);
                    }
                }

            }
        });
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_references));
        return view;
    }

    private void sendReferenceData(ReferenceDetails referenceDetails) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");
        String url = APIClient.BASE_URL + "/application/" + loanApplicationId + "/references";
        Call<ApplyLoanResponse> call = APIClient.getClient(APIClient.type.JSON).postReferencesDetails(url, authToken, referenceDetails);
        call.enqueue(new APICallbackInterface<ApplyLoanResponse>(context) {
            @Override
            public void onResponse(Call<ApplyLoanResponse> call, Response<ApplyLoanResponse> response) {
                super.onResponse(call, response);
                switch (response.code()) {
                    case 200:
                        if (response.body() != null) {
                            SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
                            editor.putString(getString(R.string.loan_application_status), response.body().getStatus());
                            editor.apply();
                            ApplyLoanResponse referencesResponse = response.body();
                            if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.bank_details_pending))) {
                                replaceFragment(new BankDetailsFragment());
                            } else if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.documents_pending))) {
                                replaceFragment(new DocumentUploadFragment());
                            } else if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.under_review))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.disbursement_pending))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.disbursed))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.rejected))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            }
                        }
                        break;
                    case 400:
                        Converter<ResponseBody, ReferenceDetails> referenceDetailsConverter = APIClient.getRetrofit().responseBodyConverter(ReferenceDetails.class, new Annotation[0]);
                        try {
                            setServerErrorMsg(referenceDetailsConverter.convert(response.errorBody()));
                        } catch (Exception e) {
                            Common.logExceptionToCrashlaytics(e);
                        }
                        break;
                    case 403:
                    case 401:
                        Toast.makeText(context, getString(R.string.logged_out), Toast.LENGTH_LONG).show();
                        replaceFragmentWithPopBackStack(new LoginFragment());
                        break;
                    case 409:
                        try {
                            Converter<ResponseBody, ApplyLoanResponse> PersonalDetailsResponseConverter = APIClient.getRetrofit().responseBodyConverter(ApplyLoanResponse.class, new Annotation[0]);
                            ApplyLoanResponse referencesResponse = PersonalDetailsResponseConverter.convert(response.errorBody());
                            SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
                            editor.putString(getString(R.string.loan_application_id), referencesResponse.getApplicationId());
                            editor.putString(getString(R.string.loan_application_status), referencesResponse.getStatus());
                            editor.commit();
                            if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.bank_details_pending))) {
                                replaceFragment(new BankDetailsFragment());
                            } else if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.documents_pending))) {
                                replaceFragment(new DocumentUploadFragment());
                            } else if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.under_review))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.disbursement_pending))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.disbursed))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (referencesResponse.getStatus().equalsIgnoreCase(getString(R.string.rejected))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            }
                        } catch (IOException e) {
                            Common.logExceptionToCrashlaytics(e);
                        }
                        break;
                    case 500:
                        Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ApplyLoanResponse> call, Throwable t) {
                super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setServerErrorMsg(ReferenceDetails referenceDetails) {
        if (referenceDetails.getRef2CityName() != null) {
            Common.setError(ref2CityName_error, "Reference2 City Name " + referenceDetails.getRef2CityName(), context);
            edRef2CityName.requestFocus();
        } else {
            Common.setError(ref2CityName_error, "", context);
        }
        if (referenceDetails.getReferences() != null) {
            Common.setError(ref2CityName_error, referenceDetails.getReferences(), context);
            edRef2CityName.requestFocus();
        } else {
            Common.setError(ref2CityName_error, "", context);
        }
        if (referenceDetails.getRef2RelationShip() != null) {
            Common.setError(ref2Relationship_error, "Reference2 Relationship " + referenceDetails.getRef2RelationShip(), context);
            edRef2RelationShip.requestFocus();
        } else {
            Common.setError(ref2Relationship_error, "", context);
        }
        if (referenceDetails.getRef2ContactNo() != null) {
            Common.setError(ref2ContactNo_error, "Reference2 Contact Number " + referenceDetails.getRef2ContactNo(), context);
            edRef2ContactNo.requestFocus();
        } else {
            Common.setError(ref2ContactNo_error, "", context);
        }
        if (referenceDetails.getRef2Name() != null) {
            Common.setError(ref2Name_error, "Reference2 Name " + referenceDetails.getRef2Name(), context);
            edRef2Name.requestFocus();
        } else {
            Common.setError(ref2Name_error, "", context);
        }
        if (referenceDetails.getRef1CityName() != null) {
            Common.setError(ref1CityName_error, "Reference1 City Name " + referenceDetails.getRef1CityName(), context);
            edRef1CityName.requestFocus();
        } else {
            Common.setError(ref1CityName_error, "", context);
        }
        if (referenceDetails.getRef1RelationShip() != null) {
            Common.setError(ref1Relationship_error, "Reference1 Relationship " + referenceDetails.getRef1RelationShip(), context);
            edRef1RelationShip.requestFocus();
        } else {
            Common.setError(ref1Relationship_error, "", context);
        }
        if (referenceDetails.getRef1ContactNo() != null) {
            Common.setError(ref1ContactNo_error, "Reference1 Contact Number " + referenceDetails.getRef1ContactNo(), context);
            edRef1ContactNo.requestFocus();
        } else {
            Common.setError(ref1ContactNo_error, "", context);
        }
        if (referenceDetails.getRef1Name() != null) {
            Common.setError(ref1Name_error, "Reference1 Name " + referenceDetails.getRef1Name(), context);
            edRef1Name.requestFocus();
        } else {
            Common.setError(ref1Name_error, "", context);
        }


    }

    private boolean checkInputValidation() {
        if (edRef1Name.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(ref1Name_error, "Reference1 Name " + getString(R.string.input_field_empty), context);
            edRef1Name.requestFocus();
            return false;
        } else {
            Common.setError(ref1Name_error, "", context);

        }
        if (edRef1ContactNo.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(ref1ContactNo_error, "Reference1 Contact Number " + getString(R.string.input_field_empty), context);
            edRef1ContactNo.requestFocus();
            return false;
        } else if (!Common.isValidPhoneNumber(edRef1ContactNo.getText().toString().trim())) {
            Common.setError(ref1ContactNo_error, getString(R.string.contact_number_invalid_error), context);
            edRef1ContactNo.requestFocus();
            return false;
        } else {
            Common.setError(ref1ContactNo_error, "", context);

        }
        if (edRef1RelationShip.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(ref1Relationship_error, "Reference1 Relationship " + getString(R.string.input_field_empty), context);
            edRef1RelationShip.requestFocus();
            return false;
        } else {
            Common.setError(ref1Relationship_error, "", context);
        }
        if (edRef1CityName.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(ref1CityName_error, "Reference1 City Name " + getString(R.string.input_field_empty), context);
            edRef1CityName.requestFocus();
            return false;
        } else {
            Common.setError(ref1CityName_error, "", context);
        }

        if (edRef2Name.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(ref2Name_error, "Reference2 Name " + getString(R.string.input_field_empty), context);
            edRef2Name.requestFocus();
            return false;
        } else {
            Common.setError(ref2Name_error, "", context);

        }

        if (edRef2ContactNo.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(ref2ContactNo_error, "Reference2 Contact Number " + getString(R.string.input_field_empty), context);
            edRef2ContactNo.requestFocus();
            return false;
        } else if (!Common.isValidPhoneNumber(edRef2ContactNo.getText().toString().trim())) {
            Common.setError(ref2ContactNo_error, getString(R.string.contact_number_invalid_error), context);
            edRef2ContactNo.requestFocus();
            return false;
        } else {
            Common.setError(ref2ContactNo_error, "", context);

        }
        if (edRef2RelationShip.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(ref2Relationship_error, "Reference2 Relationship " + getString(R.string.input_field_empty), context);
            edRef2RelationShip.requestFocus();
            return false;
        } else {
            Common.setError(ref2Relationship_error, "", context);

        }
        if (edRef2CityName.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(ref2CityName_error, "Reference2 City Name " + getString(R.string.input_field_empty), context);
            edRef2CityName.requestFocus();
            return false;
        } else {
            Common.setError(ref2CityName_error, "", context);

        }
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void replaceFragmentWithPopBackStack(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private ReferenceDetails getReferenceData() {
        ReferenceDetails referenceDetails = new ReferenceDetails();
        referenceDetails.setRef1Name(edRef1Name.getText().toString());
        referenceDetails.setRef2Name(edRef2Name.getText().toString());
        referenceDetails.setRef1ContactNo(edRef1ContactNo.getText().toString());
        referenceDetails.setRef2ContactNo(edRef2ContactNo.getText().toString());
        referenceDetails.setRef1RelationShip(edRef1RelationShip.getText().toString());
        referenceDetails.setRef2RelationShip(edRef2RelationShip.getText().toString());
        referenceDetails.setRef1CityName(edRef1CityName.getText().toString());
        referenceDetails.setRef2CityName(edRef2CityName.getText().toString());
        return referenceDetails;
    }

}
