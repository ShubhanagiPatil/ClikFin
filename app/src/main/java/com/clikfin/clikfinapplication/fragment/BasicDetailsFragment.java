package com.clikfin.clikfinapplication.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;
import com.clikfin.clikfinapplication.adapter.ItemAdapter;
import com.clikfin.clikfinapplication.adapter.ItemAdapter.*;
import com.clikfin.clikfinapplication.externalRequests.Request.PersonalDetails;
import com.clikfin.clikfinapplication.externalRequests.Response.ApplyLoanResponse;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;
import com.google.android.material.bottomsheet.BottomSheetBehavior;


import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

@SuppressWarnings("All")
public class BasicDetailsFragment extends Fragment  {
    private LinearLayout requestLinear, uploadLiner;

    private Fragment fragment = null;
    private EditText edDOB, edmiddleName, edFirstname, edMothername, edSpousename, edLastName, fatherEd, motherEd, edContactNo, edEmailId, edAadharCard, edPanCard, edLine1, edLine2, edLandMark, edPinCode, edPermanentAddressLine1, edPermanentAddressLine2, edPermanentAddressLandMark, edPermanentPinCode;
    private Spinner spinnerAddressProof;
    final String[] namePrefix = {"MR", "MS", "MRS"};
    private Button btnNext;
    RadioGroup rgGender, rgMaritalStatus, rgCurrentResidency;
    private static final int REQUEST_CAMERA_CADE = 202;
    int SELECT_FILE = 1;
    int REQUEST_CAMERA = 2;
    private Uri uri;
    String imageFilePath = "";
    String imageFilePath2 = "";
    Bitmap bmp;
    byte[] imageBytes;
    int flag;
    ScrollView scrollView;
    private Calendar newCalendar;
    private DatePickerDialog dobdatepicker;
    private SimpleDateFormat simpleDateFormat;
    TextView fatherName_error, currentResidencyError, noOfYearsStayingError, genderError, maritalError, addressProofError, spouseName_error, motherName_error, middleName_error, firstName_error, lastName_error, contactNo_error, emailIdError, aadharCardError, panCardError, DOBError, currentAddressLine1Error, currentAddressLine2Error, currentAddressLandMarkError, currentAddressCityError, currentAddressStateError, currentAddressPinCodeError, permanentAddressLine1Error, permanentAddressLine2Error, permanentAddressLandMarkError, permanentAddressCityError, permanentAddressStateError, permanentAddressPinCodeError, educationalQualificationError;
    String[] addressprooftype, stateNameList, educationQualificationList;
    CheckBox chkSameAddress;
    private EditText edCity, edFatherName, edPermanentAddressCity, noOfYearsStayingYears;
    LinearLayout laySpouse;
    Spinner spinnerStateList, spinnerPermanentAddressState, spinnerEducationalQualification;
    FragmentActivity activity;
    Context context;
    View bottomSheet, blur_background;
    BottomSheetBehavior bottomSheetBehavior;
    RecyclerView recyclerView;
    LinearLayout basic_view;
    ItemAdapter itemAdapter;

    //private Button btnLogin;
    public BasicDetailsFragment() {
        // Required empty public constructor
    }


    public static BasicDetailsFragment newInstance(String param1, String param2) {
        BasicDetailsFragment fragment = new BasicDetailsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic_details, container, false);
       // bottomSheet = activity.findViewById(R.id.bottom_sheet);
        basic_view = view.findViewById(R.id.basic_view);
        blur_background = view.findViewById(R.id.scrollView);
       /// recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        edFirstname = view.findViewById(R.id.edFirstName);
        edmiddleName = view.findViewById(R.id.edMiddleName);
        edLastName = view.findViewById(R.id.edLastName);
        edFatherName = view.findViewById(R.id.edFatherName);
        edMothername = view.findViewById(R.id.edMotherName);
        edSpousename = view.findViewById(R.id.edSpouseName);
        edContactNo = view.findViewById(R.id.edContactNo);
        edEmailId = view.findViewById(R.id.edEmailId);
        edAadharCard = view.findViewById(R.id.edAadharCard);
        edPanCard = view.findViewById(R.id.edPanCard);
        rgGender = view.findViewById(R.id.rgGender);

        noOfYearsStayingYears = view.findViewById(R.id.noOfYearsStayingYears);
        rgMaritalStatus = view.findViewById(R.id.rgMaritalStatus);
        rgCurrentResidency = view.findViewById(R.id.rgCurrentResidency);
        edLine1 = view.findViewById(R.id.edCurrentAddressLine1);
        edLine2 = view.findViewById(R.id.edCurrentAddressLine2);
        edLandMark = view.findViewById(R.id.edCurrentAddressLandMark);
        edPinCode = view.findViewById(R.id.edCurrentAddressPinCode);
        edPermanentAddressLine1 = view.findViewById(R.id.edPermanentAddressLine1);
        edPermanentAddressLine2 = view.findViewById(R.id.edPermanentAddressLine2);
        edPermanentAddressLandMark = view.findViewById(R.id.edPermanentAddressLandMark);
        edPermanentPinCode = view.findViewById(R.id.edPermanentAddressPinCode);
        edCity = view.findViewById(R.id.edCurrentAddressCity);
        spinnerStateList = view.findViewById(R.id.spinnerCurrentAddressState);
        spinnerEducationalQualification = view.findViewById(R.id.spinnerEducationalQualification);
        edPermanentAddressCity = view.findViewById(R.id.edPermanentAddressCity);
        spinnerPermanentAddressState = view.findViewById(R.id.spinnerPermanentAddressState);
        spinnerAddressProof = view.findViewById(R.id.spinnerAddressProof);
        btnNext = view.findViewById(R.id.btnPersonalInfoNext);
        scrollView = view.findViewById(R.id.scrollView);
        edDOB = view.findViewById(R.id.edDOB);
        edDOB.setFocusable(false);
        laySpouse = view.findViewById(R.id.laySpouse);

       /* spinnerAddressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });*/

        firstName_error = view.findViewById(R.id.firstName_error);
        middleName_error = view.findViewById(R.id.middleName_error);
        motherName_error = view.findViewById(R.id.motherName_error);
        fatherName_error = view.findViewById(R.id.fatherName_error);
        spouseName_error = view.findViewById(R.id.spouseName_error);
        lastName_error = view.findViewById(R.id.lastName_error);
        contactNo_error = view.findViewById(R.id.contactNo_error);
        emailIdError = view.findViewById(R.id.emailIdError);
        aadharCardError = view.findViewById(R.id.aadharCardError);
        panCardError = view.findViewById(R.id.panCardError);
        currentAddressLine1Error = view.findViewById(R.id.currentAddressLine1Error);
        currentAddressLine2Error = view.findViewById(R.id.currentAddressLine2Error);
        currentAddressLandMarkError = view.findViewById(R.id.currentAddressLandMarkError);
        currentAddressCityError = view.findViewById(R.id.currentAddressCityError);
        currentAddressStateError = view.findViewById(R.id.currentAddressStateError);
        currentAddressPinCodeError = view.findViewById(R.id.currentAddressPinCodeError);
        permanentAddressLine1Error = view.findViewById(R.id.permanentAddressLine1Error);
        permanentAddressLine2Error = view.findViewById(R.id.permanentAddressLine2Error);
        permanentAddressLandMarkError = view.findViewById(R.id.permanentAddressLandMarkError);
        permanentAddressCityError = view.findViewById(R.id.permanentAddressCityError);
        permanentAddressStateError = view.findViewById(R.id.permanentAddressStateError);
        permanentAddressPinCodeError = view.findViewById(R.id.permanentAddressPinCodeError);
        addressProofError = view.findViewById(R.id.addressProofError);
        genderError = view.findViewById(R.id.genderError);
        maritalError = view.findViewById(R.id.maritalError);
        DOBError = view.findViewById(R.id.DOBError);
        noOfYearsStayingError = view.findViewById(R.id.noOfYearsStayingError);
        currentResidencyError = view.findViewById(R.id.currentResidencyError);
        educationalQualificationError = view.findViewById(R.id.educationalQualificationError);
        chkSameAddress = view.findViewById(R.id.chkSameAddress);

        edFirstname.setFilters(new InputFilter[]{Common.letterFilter});
        edLastName.setFilters(new InputFilter[]{Common.letterFilter});
        edMothername.setFilters(new InputFilter[]{Common.letterFilter});
        edLastName.setFilters(new InputFilter[]{Common.letterFilter});
        edSpousename.setFilters(new InputFilter[]{Common.letterFilter});
        edFatherName.setFilters(new InputFilter[]{Common.letterFilter});


        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        if (Common.isNetworkConnected(context)) {
            getAddressProof();
        } else {
            Common.networkDisconnectionDialog(context);
        }

        setDateTimeField();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidInput()) {
                    if (Common.isNetworkConnected(context)) {
                        sendPersonalInfo(getPersonalInfoJSON());
                    } else {
                        Common.networkDisconnectionDialog(context);
                    }

                }


            }
        });

        rgMaritalStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedMaritalStatus = activity.findViewById(rgMaritalStatus.getCheckedRadioButtonId());
                if (selectedMaritalStatus.getText().toString().equalsIgnoreCase(getString(R.string.rd_married))) {
                    laySpouse.setVisibility(View.VISIBLE);
                } else {
                    laySpouse.setVisibility(View.GONE);
                }
            }
        });
        // rgMaritalStatus.check(0);
        chkSameAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edPermanentAddressLine1.setText(edLine1.getText().toString().trim());
                    edPermanentAddressLine2.setText(edLine2.getText().toString().trim());
                    edPermanentAddressLandMark.setText(edLandMark.getText().toString().trim());
                    edPermanentAddressCity.setText(edCity.getText().toString().trim());
                    spinnerPermanentAddressState.setSelection(spinnerStateList.getSelectedItemPosition());
                    edPermanentPinCode.setText(edPinCode.getText().toString().trim());
                } else {
                    edPermanentAddressLine1.setText("");
                    edPermanentAddressLine2.setText("");
                    edPermanentAddressLandMark.setText("");
                    edPermanentAddressCity.setText("");
                    spinnerPermanentAddressState.setSelection(0);
                    edPermanentPinCode.setText("");
                }
            }
        });
        edDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dobdatepicker.show();
            }
        });
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_personal_info));

      /*  bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
                if(newState==bottomSheetBehavior.STATE_EXPANDED){

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
               // blur_background.setBackgroundColor();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);*/
        return view;
    }

    private boolean checkValidInput() {
        boolean returnValue = true;
        if (edFirstname.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(firstName_error, "First Name " + getString(R.string.input_field_empty), context);
            edFirstname.requestFocus();
            return false;
        } else {
            Common.setError(firstName_error, "", context);
            returnValue = true;
        }
        if (edLastName.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(lastName_error, "Last Name " + getString(R.string.input_field_empty), context);
            edLastName.requestFocus();
            return false;
        } else {
            Common.setError(lastName_error, "", context);
            returnValue = true;
        }
        if (edFatherName.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(fatherName_error, "Father Name " + getString(R.string.input_field_empty), context);
            edFatherName.requestFocus();
            return false;
        } else {
            Common.setError(fatherName_error, "", context);
            returnValue = true;
        }
        if (edContactNo.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(contactNo_error, "Contact Number " + getString(R.string.input_field_empty), context);
            edContactNo.requestFocus();
            return false;
        } else if (!Common.isValidPhoneNumber(edContactNo.getText().toString().trim())) {
            Common.setError(contactNo_error, getString(R.string.contact_number_invalid_error), context);
            edContactNo.requestFocus();
            return false;
        } else {
            Common.setError(contactNo_error, "", context);
            returnValue = true;
        }
        if (edEmailId.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(emailIdError, "Email Id " + getString(R.string.input_field_empty), context);
            edEmailId.requestFocus();
            return false;
        } else if (!Common.isValidEmail(edEmailId.getText().toString().trim())) {
            Common.setError(emailIdError, getString(R.string.email_invalid_error), context);
            edEmailId.requestFocus();
            return false;
        } else {
            Common.setError(emailIdError, "", context);
            returnValue = true;
        }
        if (edAadharCard.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(aadharCardError, "Aadhar Card " + getString(R.string.input_field_empty), context);
            edAadharCard.requestFocus();
            return false;
        } else {
            Common.setError(aadharCardError, "", context);
            returnValue = true;
        }
        if (edPanCard.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(panCardError, "Pan Card " + getString(R.string.input_field_empty), context);
            edPanCard.requestFocus();
            return false;
        } else {
            Common.setError(panCardError, "", context);
            returnValue = true;
        }
        if (edDOB.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(DOBError, "Date Of Birth " + getString(R.string.input_field_empty), context);
            edDOB.setFocusable(true);
            edDOB.requestFocus();
            return false;
        } else {
            edDOB.setFocusable(false);
            Common.setError(DOBError, "", context);
            returnValue = true;
        }
        if (spinnerEducationalQualification.getSelectedItem().toString().trim().equalsIgnoreCase("Select Educational Qualification")) {
            Common.setError(educationalQualificationError, getString(R.string.select_educational_qualification), context);
            spinnerEducationalQualification.setFocusable(true);
            spinnerEducationalQualification.setFocusableInTouchMode(true);
            return false;
        } else {
            Common.setError(educationalQualificationError, "", context);
            returnValue = true;
        }
        if (edMothername.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(motherName_error, "Mother Name " + getString(R.string.input_field_empty), context);
            edMothername.requestFocus();
            return false;
        } else {
            Common.setError(motherName_error, "", context);
            returnValue = true;
        }
        RadioButton selectedMaritalStatus = activity.findViewById(rgMaritalStatus.getCheckedRadioButtonId());
        if (selectedMaritalStatus.getText().toString().equalsIgnoreCase(getString(R.string.rd_married))) {
            if (edSpousename.getText().toString().trim().equalsIgnoreCase("")) {
                Common.setError(spouseName_error, "Spouse Name " + getString(R.string.input_field_empty), context);
                edSpousename.requestFocus();
                return false;
            } else {
                Common.setError(spouseName_error, "", context);
                returnValue = true;
            }
        }
        if (noOfYearsStayingYears.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(noOfYearsStayingError, "No. Of Years Staying " + getString(R.string.input_field_empty), context);
            noOfYearsStayingYears.requestFocus();
            return false;
        } else {
            Common.setError(noOfYearsStayingError, "", context);
            returnValue = true;
        }
        if (edLine1.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(currentAddressLine1Error, "Current Address Line1 " + getString(R.string.input_field_empty), context);
            edLine1.requestFocus();
            return false;
        } else {
            Common.setError(currentAddressLine1Error, "", context);
            returnValue = true;
        }
        if (edLine2.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(currentAddressLine2Error, "Current Address Line2 " + getString(R.string.input_field_empty), context);
            edLine2.requestFocus();
            return false;
        } else {
            Common.setError(currentAddressLine2Error, "", context);
            returnValue = true;
        }
        if (edLandMark.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(currentAddressLandMarkError, "Current Address Landmark " + getString(R.string.input_field_empty), context);
            edLandMark.requestFocus();
            return false;
        } else {
            Common.setError(currentAddressLandMarkError, "", context);
            returnValue = true;
        }
        if (edCity.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(currentAddressCityError, "Current Address City " + getString(R.string.input_field_empty), context);
            edCity.requestFocus();
            return false;
        } else {
            Common.setError(currentAddressCityError, "", context);
            returnValue = true;
        }
        if (spinnerStateList.getSelectedItem().toString().trim().equalsIgnoreCase("Select State")) {
            Common.setError(currentAddressStateError, getString(R.string.select_state), context);
            spinnerStateList.setFocusable(true);
            return false;
        } else {
            Common.setError(currentAddressStateError, "", context);
            returnValue = true;
        }
        if (edPinCode.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(currentAddressPinCodeError, "Current Address Pin Code " + getString(R.string.input_field_empty), context);
            edPinCode.requestFocus();
            return false;
        } else {
            Common.setError(currentAddressPinCodeError, "", context);
            returnValue = true;
        }
        if (spinnerAddressProof.getSelectedItem().toString().equalsIgnoreCase("Select Address Proof")) {
            Common.setError(addressProofError, getString(R.string.select_address_proof), context);
            spinnerAddressProof.setFocusable(true);
            return false;
        } else {
            Common.setError(addressProofError, "", context);
            returnValue = true;
        }
        if (edPermanentAddressLine1.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(permanentAddressLine1Error, "Permanent Address Line1 " + getString(R.string.input_field_empty), context);
            edPermanentAddressLine1.requestFocus();
            return false;
        } else {
            Common.setError(permanentAddressLine1Error, "", context);
            returnValue = true;
        }
        if (edPermanentAddressLine2.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(permanentAddressLine2Error, "Permanent Address Line2 " + getString(R.string.input_field_empty), context);
            edPermanentAddressLine2.requestFocus();
            return false;
        } else {
            Common.setError(permanentAddressLine2Error, "", context);
            returnValue = true;
        }
        if (edPermanentAddressLandMark.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(permanentAddressLandMarkError, "Permanent Address Landmark " + getString(R.string.input_field_empty), context);
            edPermanentAddressLandMark.requestFocus();
            return false;
        } else {
            Common.setError(permanentAddressLandMarkError, "", context);
            returnValue = true;
        }
        if (edPermanentAddressCity.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(permanentAddressCityError, "Permanent Address City " + getString(R.string.input_field_empty), context);
            edPermanentAddressCity.requestFocus();
            return false;
        } else {
            Common.setError(permanentAddressCityError, "", context);
            returnValue = true;
        }
        if (spinnerPermanentAddressState.getSelectedItem().toString().trim().equalsIgnoreCase("Select State")) {
            Common.setError(permanentAddressStateError, getString(R.string.select_state), context);
            spinnerPermanentAddressState.setFocusable(true);
            return false;
        } else {
            Common.setError(permanentAddressStateError, "", context);
            returnValue = true;
        }
        if (edPermanentPinCode.getText().toString().trim().equalsIgnoreCase("")) {
            Common.setError(permanentAddressPinCodeError, "Permanent Address Pin Code " + getString(R.string.input_field_empty), context);
            edPermanentPinCode.requestFocus();
            return false;
        } else {
            Common.setError(permanentAddressPinCodeError, "", context);
            returnValue = true;
        }

        return returnValue;
    }

    private void getStates() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        Call<String[]> call = APIClient.getClient(APIClient.type.JSON).getState(authToken);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                // super.onResponse(call, response);
                if (response.body() != null) {
                    switch (response.code()) {
                        case 200:

                            stateNameList = new String[response.body().length + 1];
                                          stateNameList[0] = "Select State";
                            for (int i = 0; i < response.body().length; i++) {
                                stateNameList[i + 1] = response.body()[i];
                            }
                            ArrayAdapter stateAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, stateNameList);
                            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            spinnerStateList.setSelection(0);
                            spinnerStateList.setAdapter(stateAdapter);

                            spinnerPermanentAddressState.setSelection(0);
                            spinnerPermanentAddressState.setAdapter(stateAdapter);
                            getEducationalQualification();
                            break;
                        case 500:
                            Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                // super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAddressProof() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        Call<String[]> call = APIClient.getClient(APIClient.type.JSON).getAddressProof(authToken);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                // super.onResponse(call, response);
                switch (response.code()) {
                    case 200:
                        if (response.body() != null) {
                            ArrayList<String> items = new ArrayList();
                            addressprooftype = new String[response.body().length + 1];
                            addressprooftype[0] = "Select Address Proof";
                            for (int i = 0; i < response.body().length; i++) {
                                 addressprooftype[i + 1] = response.body()[i];
                                items.add(response.body()[i]);
                            }
                            ArrayAdapter addressProofAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, addressprooftype);
                            addressProofAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            spinnerAddressProof.setSelection(0);
                            spinnerAddressProof.setAdapter(addressProofAdapter);
                           /* itemAdapter = new ItemAdapter(items, BasicDetailsFragment.this::onItemClick);
                            recyclerView.setAdapter(itemAdapter);*/

                            getStates();
                        }
                        break;
                    case 500:
                        Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        break;

                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                //  super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getEducationalQualification() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        Call<String[]> call = APIClient.getClient(APIClient.type.JSON).getEducationalQualification(authToken);
        call.enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> response) {
                // super.onResponse(call, response);
                switch (response.code()) {
                    case 200:
                        if (response.body() != null) {
                            educationQualificationList = new String[response.body().length + 1];
                            educationQualificationList[0] = "Select Educational Qualification";
                            for (int i = 0; i < response.body().length; i++) {
                                educationQualificationList[i + 1] = response.body()[i];
                            }
                            ArrayAdapter addressProofAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, educationQualificationList);
                            addressProofAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            spinnerEducationalQualification.setSelection(0);
                            spinnerEducationalQualification.setAdapter(addressProofAdapter);
                            btnNext.setBackground(context.getDrawable(R.drawable.custom_rect));
                            btnNext.setTextColor(Color.parseColor("#ffffff"));
                            btnNext.setEnabled(true);
                        }
                        break;
                    case 500:
                        Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        break;

                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                // super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void sendPersonalInfo(PersonalDetails personalInfo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        Call<ApplyLoanResponse> call = APIClient.getClient(APIClient.type.JSON).postPersonalDetails(authToken, personalInfo);

        call.enqueue(new APICallbackInterface<ApplyLoanResponse>(context) {
            @Override
            public void onResponse(Call<ApplyLoanResponse> call, Response<ApplyLoanResponse> response) {
                super.onResponse(call, response);
                switch (response.code()) {
                    case 201:
                        if (response.body() != null) {
                            ApplyLoanResponse personalDetailsRespons = response.body();
                            SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
                            editor.putString(getString(R.string.loan_application_id), personalDetailsRespons.getApplicationId());
                            editor.putString(getString(R.string.loan_application_status), personalDetailsRespons.getStatus());
                            editor.commit();

                            if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.employee_details_pending))) {
                                replaceFragment(new EmploymentFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.references_pending))) {
                                replaceFragment(new ReferenceFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.bank_details_pending))) {
                                replaceFragment(new BankDetailsFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.documents_pending))) {
                                replaceFragment(new DocumentUploadFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.under_review))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursement_pending))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursed))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.rejected))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            }
                        }
                        break;
                    case 400:
                        Converter<ResponseBody, PersonalDetails> PersonalDetailsConverter = APIClient.getRetrofit().responseBodyConverter(PersonalDetails.class, new Annotation[0]);
                        try {
                            setServerErrorMsg(PersonalDetailsConverter.convert(response.errorBody()));
                        } catch (Exception e) {
                            Common.logExceptionToCrashlaytics(e);
                        }
                        break;
                    case 403:
                    case 401:
                        Toast.makeText(activity, getString(R.string.logged_out), Toast.LENGTH_LONG).show();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentManager.popBackStack();
                        fragmentTransaction.replace(R.id.content_frame, new LoginFragment());
                        fragmentTransaction.commitAllowingStateLoss();
                        break;
                    case 409:
                        try {
                            Converter<ResponseBody, ApplyLoanResponse> PersonalDetailsResponsConverter = APIClient.getRetrofit().responseBodyConverter(ApplyLoanResponse.class, new Annotation[0]);
                            ApplyLoanResponse personalDetailsRespons = PersonalDetailsResponsConverter.convert(response.errorBody());
                            SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
                            editor.putString(getString(R.string.loan_application_id), personalDetailsRespons.getApplicationId());
                            editor.putString(getString(R.string.loan_application_status), personalDetailsRespons.getStatus());
                            editor.commit();
                            if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.employee_details_pending))) {
                                replaceFragment(new EmploymentFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.references_pending))) {
                                replaceFragment(new ReferenceFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.bank_details_pending))) {
                                replaceFragment(new BankDetailsFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.documents_pending))) {
                                replaceFragment(new DocumentUploadFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.under_review))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursement_pending))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.disbursed))) {
                                replaceFragment(new LoanApplicationStatusFragment());
                            } else if (personalDetailsRespons.getStatus().equalsIgnoreCase(getString(R.string.rejected))) {
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
            public void onFailure(Call<ApplyLoanResponse> call, Throwable t) {
                super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
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

    private void setServerErrorMsg(PersonalDetails personalDetails) {
        if (personalDetails.getPermanentAddressPinCode() != null) {
            Common.setError(permanentAddressPinCodeError, "Permanent Address Pin Code " + personalDetails.getPermanentAddressPinCode(), context);
            edPermanentPinCode.requestFocus();
        } else {
            Common.setError(permanentAddressPinCodeError, "", context);
        }
        if (personalDetails.getPermanentAddressState() != null) {
            Common.setError(permanentAddressStateError, "Permanent Address State" + personalDetails.getPermanentAddressState(), context);
            spinnerPermanentAddressState.setFocusable(true);
        } else {
            Common.setError(permanentAddressStateError, "", context);
        }
        if (personalDetails.getPermanentAddressCity() != null) {
            Common.setError(permanentAddressCityError, "Permanent Address City " + personalDetails.getPermanentAddressCity(), context);
            edPermanentAddressCity.requestFocus();
        } else {
            Common.setError(permanentAddressCityError, "", context);
        }
        if (personalDetails.getPermanentAddressLandmark() != null) {
            Common.setError(permanentAddressLandMarkError, "Permanent Address Landmark " + personalDetails.getPermanentAddressLandmark(), context);
            edPermanentAddressLandMark.requestFocus();
        } else {
            Common.setError(permanentAddressLandMarkError, "", context);
        }
        if (personalDetails.getPermanentAddressLine2() != null) {
            Common.setError(permanentAddressLine2Error, "Permanent Address Line2 " + personalDetails.getPermanentAddressLine2(), context);
            edPermanentAddressLine2.requestFocus();
        } else {
            Common.setError(permanentAddressLine2Error, "", context);
        }
        if (personalDetails.getPermanentAddressLine1() != null) {
            Common.setError(permanentAddressLine1Error, "Permanent Address Line1 " + personalDetails.getPermanentAddressLine1(), context);
            edPermanentAddressLine1.requestFocus();
        } else {
            Common.setError(permanentAddressLine1Error, "", context);
        }
        if (personalDetails.getAddressProof() != null) {
            Common.setError(addressProofError, "Address Proof " + personalDetails.getAddressProof(), context);
            spinnerAddressProof.setFocusable(true);
        } else {
            Common.setError(addressProofError, "", context);
        }
        if (personalDetails.getCurrentAddressPinCode() != null) {
            Common.setError(currentAddressPinCodeError, "Current Address Pin Code " + personalDetails.getCurrentAddressPinCode(), context);
            edPinCode.requestFocus();
        } else {
            Common.setError(currentAddressPinCodeError, "", context);
        }
        if (personalDetails.getCurrentAddressState() != null) {
            Common.setError(currentAddressStateError, "Current Address State " + personalDetails.getCurrentAddressState(), context);
            spinnerStateList.setFocusable(true);
        } else {
            Common.setError(currentAddressStateError, "", context);
        }
        if (personalDetails.getCurrentAddressCity() != null) {
            Common.setError(currentAddressCityError, "Current Address City " + personalDetails.getCurrentAddressCity(), context);
            edCity.requestFocus();
        } else {
            Common.setError(currentAddressCityError, "", context);
        }
        if (personalDetails.getCurrentAddressLandmark() != null) {
            Common.setError(currentAddressLandMarkError, "Current Address Landmark " + personalDetails.getCurrentAddressLandmark(), context);
            edLandMark.requestFocus();
        } else {
            Common.setError(currentAddressLandMarkError, "", context);
        }
        if (personalDetails.getCurrentAddressLine2() != null) {
            Common.setError(currentAddressLine2Error, "Current Address Line2 " + personalDetails.getCurrentAddressLine2(), context);
            edLine2.requestFocus();
        } else {
            Common.setError(currentAddressLine2Error, "", context);
        }
        if (personalDetails.getCurrentAddressLine1() != null) {
            Common.setError(currentAddressLine1Error, "Current Address Line1 " + personalDetails.getCurrentAddressLine1(), context);
            edLine1.requestFocus();
        } else {
            Common.setError(currentAddressLine1Error, "", context);
        }
        if (personalDetails.getPermanentAddressStayingFor() != null) {
            Common.setError(noOfYearsStayingError, "No Of Years Staying " + personalDetails.getPermanentAddressStayingFor(), context);
            noOfYearsStayingYears.requestFocus();
        } else {
            Common.setError(noOfYearsStayingError, "", context);
        }
        if (personalDetails.getPermanentAddressType() != null) {
            Common.setError(currentResidencyError, "Current Residency " + personalDetails.getPermanentAddressType(), context);
            rgCurrentResidency.setFocusable(true);
        } else {
            Common.setError(currentResidencyError, "", context);
        }
        if (personalDetails.getSpouseName() != null) {
            Common.setError(spouseName_error, "Spouse Name " + personalDetails.getSpouseName(), context);
            edSpousename.requestFocus();
        } else {
            Common.setError(spouseName_error, "", context);
        }
        if (personalDetails.getMotherName() != null) {
            Common.setError(motherName_error, "Mother Name " + personalDetails.getMotherName(), context);
            edMothername.requestFocus();
        } else {
            Common.setError(motherName_error, "", context);
        }
        if (personalDetails.getMaritalStatus() != null) {
            Common.setError(maritalError, "Marital Status " + personalDetails.getMaritalStatus(), context);
            rgMaritalStatus.setFocusable(true);
        } else {
            Common.setError(maritalError, "", context);
        }
        if (personalDetails.getEducationalQualification() != null) {
            Common.setError(educationalQualificationError, "Educational Qualification " + personalDetails.getEducationalQualification(), context);
            spinnerEducationalQualification.setFocusable(true);
            spinnerEducationalQualification.requestFocus();
        } else {
            Common.setError(educationalQualificationError, "", context);
        }
        if (personalDetails.getDateOfBirth() != null) {
            Common.setError(DOBError, "Date Of Birth " + personalDetails.getDateOfBirth(), context);
            edDOB.requestFocus();
        } else {
            Common.setError(DOBError, "", context);
        }
        if (personalDetails.getGender() != null) {
            Common.setError(genderError, "Gender " + personalDetails.getGender(), context);
            rgGender.setFocusable(true);
        } else {
            Common.setError(genderError, "", context);
        }
        if (personalDetails.getPanNumber() != null) {
            Common.setError(panCardError, "Pan Number " + personalDetails.getPanNumber(), context);
            edPanCard.requestFocus();
        } else {
            Common.setError(panCardError, "", context);
        }
        if (personalDetails.getAadhaarNumber() != null) {
            Common.setError(aadharCardError, "Aadhaar Number " + personalDetails.getAadhaarNumber(), context);
            edAadharCard.requestFocus();
        } else {
            Common.setError(aadharCardError, "", context);
        }
        if (personalDetails.getEmail() != null) {
            Common.setError(emailIdError, "Email " + personalDetails.getEmail(), context);
            edEmailId.requestFocus();
        } else {
            Common.setError(emailIdError, "", context);
        }
        if (personalDetails.getPhoneNumber() != null) {
            Common.setError(contactNo_error, "Contact Number " + personalDetails.getPhoneNumber(), context);
            edContactNo.requestFocus();
        } else {
            Common.setError(contactNo_error, "", context);
        }
        if (personalDetails.getFatherName() != null) {
            Common.setError(fatherName_error, "Father Name " + personalDetails.getFatherName(), context);
            edFatherName.requestFocus();
        } else {
            Common.setError(fatherName_error, "", context);
        }
        if (personalDetails.getLastName() != null) {
            Common.setError(lastName_error, "Last Name " + personalDetails.getLastName(), context);
            edLastName.requestFocus();
        } else {
            Common.setError(lastName_error, "", context);
        }
        if (personalDetails.getMiddleName() != null) {
            Common.setError(middleName_error, "Middle Name " + personalDetails.getMiddleName(), context);
            edmiddleName.requestFocus();
        } else {
            Common.setError(middleName_error, "", context);
        }
        if (personalDetails.getFirstName() != null) {
            Common.setError(firstName_error, "First Name " + personalDetails.getFirstName(), context);
            edFirstname.requestFocus();
        } else {
            Common.setError(firstName_error, "", context);
        }

    }

    private PersonalDetails getPersonalInfoJSON() {
        PersonalDetails personalDetails = new PersonalDetails();

        personalDetails.setFirstName(edFirstname.getText().toString());
        personalDetails.setMiddleName(edmiddleName.getText().toString());
        personalDetails.setMotherName(edMothername.getText().toString());
        personalDetails.setFatherName(edFatherName.getText().toString());
        personalDetails.setLastName(edLastName.getText().toString());
        personalDetails.setPhoneNumber(edContactNo.getText().toString().trim());
        personalDetails.setEmail(edEmailId.getText().toString());
        personalDetails.setAadhaarNumber(edAadharCard.getText().toString());
        personalDetails.setPanNumber(edPanCard.getText().toString());
        RadioButton selectedGender = activity.findViewById(rgGender.getCheckedRadioButtonId());
        personalDetails.setGender(selectedGender.getText().toString());
        personalDetails.setDateOfBirth(edDOB.getText().toString());
        RadioButton selectedMaritalStatus = activity.findViewById(rgMaritalStatus.getCheckedRadioButtonId());
        personalDetails.setMaritalStatus(selectedMaritalStatus.getText().toString());

        if (selectedMaritalStatus.getText().toString().equalsIgnoreCase(getString(R.string.rd_married))) {
            personalDetails.setSpouseName(edSpousename.getText().toString());
        } else {
            personalDetails.setSpouseName("");
        }
        personalDetails.setCurrentAddressLine1(edLine1.getText().toString());
        personalDetails.setCurrentAddressLine2(edLine2.getText().toString());
        personalDetails.setCurrentAddressLandmark(edLandMark.getText().toString());
        personalDetails.setCurrentAddressCity(edCity.getText().toString());
        personalDetails.setCurrentAddressState(spinnerStateList.getSelectedItem().toString());
        personalDetails.setCurrentAddressPinCode(edPinCode.getText().toString());
        personalDetails.setAddressProof(spinnerAddressProof.getSelectedItem().toString());
        personalDetails.setEducationalQualification(spinnerEducationalQualification.getSelectedItem().toString());
        personalDetails.setPermanentAddressLine1(edPermanentAddressLine1.getText().toString());
        personalDetails.setPermanentAddressLine2(edPermanentAddressLine2.getText().toString());
        personalDetails.setPermanentAddressLandmark(edPermanentAddressLandMark.getText().toString());
        personalDetails.setPermanentAddressCity(edPermanentAddressCity.getText().toString());
        personalDetails.setPermanentAddressState(spinnerPermanentAddressState.getSelectedItem().toString());
        personalDetails.setPermanentAddressPinCode(edPermanentPinCode.getText().toString());
        String noOfYearsStayingFor = noOfYearsStayingYears.getText().toString() + "year";
        personalDetails.setPermanentAddressStayingFor(noOfYearsStayingFor);
        RadioButton selectedCurrentResidency = activity.findViewById(rgCurrentResidency.getCheckedRadioButtonId());
        personalDetails.setPermanentAddressType(selectedCurrentResidency.getText().toString());

        return personalDetails;
    }

    public void setDateTimeField() {
        //get datepicker dialog for date of birth
        newCalendar = Calendar.getInstance();
        dobdatepicker = new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                Date today = Calendar.getInstance().getTime();
                Date date1 = newDate.getTime();

                String todayvalue = simpleDateFormat.format(today);
                String selectdatevalue = simpleDateFormat.format(date1);


                if (selectdatevalue.equals(todayvalue)) {
                    // if(date1.equals(today)){
                    Toast.makeText(getContext(), "Please Select The Correct Date", Toast.LENGTH_LONG).show();
                    edDOB.setText("");
                    //  edDOB.setFocusable(false);
                } else {
                    //  edDOB.setFocusable(false);
                    edDOB.setText(simpleDateFormat.format(newDate.getTime()).trim());
                }


            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dobdatepicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        //   dobdatepicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(R.color.splash_color);


    }


    /*@Override
    public void onItemClick(String item) {
        spinnerAddressProof.setText(item);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }*/
}
