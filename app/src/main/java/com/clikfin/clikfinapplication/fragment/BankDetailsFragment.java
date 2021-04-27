package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;
import com.clikfin.clikfinapplication.externalRequests.Request.BankDetails;
import com.clikfin.clikfinapplication.externalRequests.Response.ApplyLoanResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.BankDetailsResponse;
import com.clikfin.clikfinapplication.externalRequests.Response.UploadDocumentResponse;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

@SuppressWarnings("All")
public class BankDetailsFragment extends Fragment {
    private Spinner spinnerBankName;
    Button btnBankInfoSubmit;
    String[] bankName = null;
    static FragmentActivity activity;
    Context context;

    private EditText edAccountHolderName, edAccountNo, edReEnterAccountNo, edIFSCCode;
    private TextView accountHolderName_error, accountNumber_error, reEnterAccountNumber_error, IFSCCode_error, bankName_error;

    public BankDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof FragmentActivity) {
            activity = (FragmentActivity) context;
        }
    }

    public static BankDetailsFragment newInstance(String param1, String param2) {
        BankDetailsFragment fragment = new BankDetailsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_details, container, false);
        edAccountHolderName = view.findViewById(R.id.edAccountHolderName);
        edAccountNo = view.findViewById(R.id.edAccountNo);
        edReEnterAccountNo = view.findViewById(R.id.edReEnterAccountNo);
        edIFSCCode = view.findViewById(R.id.edIFSCCOde);
        spinnerBankName = view.findViewById(R.id.spinnerBankName);
        btnBankInfoSubmit = view.findViewById(R.id.btnBankInfoSubmit);

        accountHolderName_error = view.findViewById(R.id.accountHolderName_error);
        accountNumber_error = view.findViewById(R.id.accountNumber_error);
        reEnterAccountNumber_error = view.findViewById(R.id.reEnterAccountNumber_error);
        IFSCCode_error = view.findViewById(R.id.IFSCCode_error);
        bankName_error = view.findViewById(R.id.bankName_error);

        edAccountHolderName.setFilters(new InputFilter[]{Common.letterFilter});

        if (Common.isNetworkConnected(context)) {
            getBankNames();
        } else {
            Common.networkDisconnectionDialog(context);
        }

        btnBankInfoSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidInput()) {
                    if (Common.isNetworkConnected(context)) {
                        postBankDetails(bankDetailsData());
                    } else {
                        Common.networkDisconnectionDialog(context);
                    }
                }
            }
        });
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_bank_details));
        return view;
    }

    private void getBankNames() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        Call<String[]> call = APIClient.getClient(APIClient.type.JSON).getBank(authToken);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                //super.onResponse(call, response);
                if (response.body() != null) {
                    bankName = new String[response.body().length + 1];
                    bankName[0] = "Select Bank Name";
                    for (int i = 0; i < response.body().length; i++) {
                        bankName[i + 1] = response.body()[i];
                    }
                    ArrayAdapter<String> namePrefixAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, bankName);
                    namePrefixAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    spinnerBankName.setAdapter(namePrefixAdapter);
                    btnBankInfoSubmit.setBackground(context.getDrawable(R.drawable.custom_rect));
                    btnBankInfoSubmit.setTextColor(Color.parseColor("#ffffff"));
                    btnBankInfoSubmit.setEnabled(true);
                }

            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                //super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postBankDetails(BankDetails bankDetails) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");
        String url = APIClient.BASE_URL + "/application/" + loanApplicationId + "/bankDetails";
        Call<BankDetailsResponse> call = APIClient.getClient(APIClient.type.JSON).postBankDetails(url, authToken, bankDetails);
        call.enqueue(new APICallbackInterface<BankDetailsResponse>(context) {
            @Override
            public void onResponse(Call<BankDetailsResponse> call, Response<BankDetailsResponse> response) {
                super.onResponse(call, response);
                switch (response.code()) {
                    case 200:
                        if (response.body() != null) {
                            BankDetailsResponse bankDeatilRespons = response.body();
                            SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);


                            UploadDocumentResponse uploadDocumentResponse = response.body().getDocumentStatus();

                            editor.putBoolean(getString(R.string.isUpload_upload_pan_card), uploadDocumentResponse.getPAN_CARD().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_current_residency_proof), uploadDocumentResponse.getCURRENT_ADDRESS_PROOF().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_1), uploadDocumentResponse.getPAY_SLIP_1().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_2), uploadDocumentResponse.getPAY_SLIP_2().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_pay_slip_3), uploadDocumentResponse.getPAY_SLIP_3().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_1), uploadDocumentResponse.getBANK_STATEMENT_1().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_2), uploadDocumentResponse.getBANK_STATEMENT_2().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_bank_statement_3), uploadDocumentResponse.getBANK_STATEMENT_3().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_aadhar_front), uploadDocumentResponse.getAADHAAR_CARD_FRONT().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_aadhar_back), uploadDocumentResponse.getAADHAAR_CARD_BACK().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_photo), uploadDocumentResponse.getPHOTO().isUploaded());
                            editor.putBoolean(getString(R.string.isUpload_company_id), uploadDocumentResponse.getCOMPANY_ID().isUploaded());


                            editor.putBoolean(getString(R.string.isMandatory_upload_pan_card), uploadDocumentResponse.getPAN_CARD().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_current_residency_proof), uploadDocumentResponse.getCURRENT_ADDRESS_PROOF().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_pay_slip_1), uploadDocumentResponse.getPAY_SLIP_1().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_pay_slip_2), uploadDocumentResponse.getPAY_SLIP_2().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_pay_slip_3), uploadDocumentResponse.getPAY_SLIP_3().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_bank_statement_1), uploadDocumentResponse.getBANK_STATEMENT_1().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_bank_statement_2), uploadDocumentResponse.getBANK_STATEMENT_2().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_bank_statement_3), uploadDocumentResponse.getBANK_STATEMENT_3().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_aadhar_front), uploadDocumentResponse.getAADHAAR_CARD_FRONT().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_aadhar_back), uploadDocumentResponse.getAADHAAR_CARD_BACK().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_photo), uploadDocumentResponse.getPHOTO().isMandatory());
                            editor.putBoolean(getString(R.string.isMandatory_company_id), uploadDocumentResponse.getCOMPANY_ID().isMandatory());

                            editor.putString(getString(R.string.loan_application_id), bankDeatilRespons.getApplicationId());
                            editor.putString(getString(R.string.loan_application_status), bankDeatilRespons.getStatus());
                            editor.apply();
                            if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.documents_pending))) {
                                replaceFragment(new DocumentUploadFragment());
                            } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.under_review))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursement_pending))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursed))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.rejected))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            }
                        }
                        break;
                    case 400:
                        Converter<ResponseBody, BankDetails> bankDetailsConverter = APIClient.getRetrofit().responseBodyConverter(BankDetails.class, new Annotation[0]);
                        try {
                            setServerErrorMsg(bankDetailsConverter.convert(response.errorBody()));
                        } catch (Exception e) {
                            Common.logExceptionToCrashlaytics(e);
                        }
                        break;
                    case 403:
                    case 401:
                        Toast.makeText(context, getString(R.string.logged_out), Toast.LENGTH_LONG).show();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentManager.popBackStack();
                        fragmentTransaction.replace(R.id.apply_loan_frame, new LoginFragment());
                        fragmentTransaction.commit();
                        break;
                    case 409:
                        try {
                            Converter<ResponseBody, ApplyLoanResponse> PersonalDetailsResponsConverter = APIClient.getRetrofit().responseBodyConverter(ApplyLoanResponse.class, new Annotation[0]);
                            ApplyLoanResponse bankDeatilRespons = PersonalDetailsResponsConverter.convert(response.errorBody());
                            SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
                            editor.putString(getString(R.string.loan_application_id), bankDeatilRespons.getApplicationId());
                            editor.putString(getString(R.string.loan_application_status), bankDeatilRespons.getStatus());
                            editor.commit();
                            if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.documents_pending))) {
                                replaceFragment(new DocumentUploadFragment());
                            } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.under_review))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursement_pending))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursed))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (bankDeatilRespons.getStatus().equalsIgnoreCase(getString(R.string.rejected))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            }

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
            public void onFailure(Call<BankDetailsResponse> call, Throwable t) {
                super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.internet_connectivity_issue), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private BankDetails bankDetailsData() {
        BankDetails bankDetails = new BankDetails();
        bankDetails.setAccountHolderName(edAccountHolderName.getText().toString());
        bankDetails.setAccountNumber(edAccountNo.getText().toString());
        bankDetails.setReEnterAccountNumber(edReEnterAccountNo.getText().toString());
        bankDetails.setIFSCCode(edIFSCCode.getText().toString());
        bankDetails.setBankName(spinnerBankName.getSelectedItem().toString());
        return bankDetails;
    }

    private void setServerErrorMsg(BankDetails bankDetails) {
        if (bankDetails.getIFSCCode() != null) {
            Common.setError(IFSCCode_error, "IFSC Code " + bankDetails.getIFSCCode(), context);
            edIFSCCode.requestFocus();
        } else {
            Common.setError(IFSCCode_error, "", context);
        }
        if (bankDetails.getAccountNumber() != null) {
            Common.setError(accountNumber_error, "Account Number " + bankDetails.getAccountNumber(), context);
            edAccountNo.requestFocus();
        } else {
            Common.setError(accountNumber_error, "", context);
        }
        if (bankDetails.getBankName() != null) {
            Common.setError(bankName_error, "Bank Name " + bankDetails.getBankName(), context);
            spinnerBankName.setFocusable(true);
            spinnerBankName.requestFocus();
        } else {
            Common.setError(bankName_error, "", context);
        }
        if (bankDetails.getAccountHolderName() != null) {
            Common.setError(accountHolderName_error, "Account Holder Name " + bankDetails.getAccountHolderName(), context);
            edAccountHolderName.requestFocus();
        } else {
            Common.setError(accountHolderName_error, "", context);
        }


    }

    private boolean checkValidInput() {
        boolean returnValue = true;
        if (edAccountHolderName.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(accountHolderName_error, "Account Holder Name " + getString(R.string.input_field_empty), context);
            edAccountHolderName.requestFocus();
            return false;
        } else {
            Common.setError(accountHolderName_error, "", context);
            returnValue = true;
        }
        if (spinnerBankName.getSelectedItem().toString().trim().equalsIgnoreCase("Select Bank Name")) {
            Common.setError(bankName_error, getString(R.string.select_bank_name), context);
            return false;
        } else {
            Common.setError(bankName_error, "", context);
            returnValue = true;
        }
        if (edAccountNo.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(accountNumber_error, "Account Number " + getString(R.string.input_field_empty), context);
            edAccountNo.requestFocus();
            return false;
        } else {
            Common.setError(accountNumber_error, "", context);
            returnValue = true;
        }
        if (edReEnterAccountNo.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(reEnterAccountNumber_error, "Re-Enter Account Number " + getString(R.string.input_field_empty), context);
            edReEnterAccountNo.requestFocus();
            return false;
        } else {
            Common.setError(reEnterAccountNumber_error, "", context);
            returnValue = true;
        }
        if (!edAccountNo.getText().toString().trim().equals(edReEnterAccountNo.getText().toString().trim())) {
            Common.setError(reEnterAccountNumber_error, getString(R.string.ac_not_match), context);
            edReEnterAccountNo.requestFocus();
            return false;
        } else {
            Common.setError(reEnterAccountNumber_error, "", context);
            returnValue = true;
        }
        if (edIFSCCode.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(IFSCCode_error, "IFSC Code " + getString(R.string.input_field_empty), context);
            edIFSCCode.requestFocus();
            return false;
        } else {
            Common.setError(IFSCCode_error, "", context);
            returnValue = true;
        }

        return returnValue;
    }
}