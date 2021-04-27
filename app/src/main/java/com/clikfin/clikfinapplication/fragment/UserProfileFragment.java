package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserProfileFragment extends Fragment {
    static FragmentActivity activity;
    Context context;
    ImageView imgIsBasicInfoUploaded, imgIsProfessionalInfoUploaded, imgIsReferenceInfoUploaded, imgIsBankDetailsUploaded, imgIsDocumentInfoUploaded;
    RelativeLayout layBasicInfo, layProfessionalInfo, layReferences, layBankDetails, layDocuments;
    TextView loanStatus;

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
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_profile));

        imgIsBasicInfoUploaded = view.findViewById(R.id.imgIsBasicInfoUploaded);
        imgIsProfessionalInfoUploaded = view.findViewById(R.id.imgIsProfessionalInfoUploaded);
        imgIsReferenceInfoUploaded = view.findViewById(R.id.imgIsReferenceInfoUploaded);
        imgIsBankDetailsUploaded = view.findViewById(R.id.imgIsBankDetailsUploaded);
        imgIsDocumentInfoUploaded = view.findViewById(R.id.imgIsDocumentInfoUploaded);

        layBasicInfo = view.findViewById(R.id.layBasicInfo);
        layProfessionalInfo = view.findViewById(R.id.layProfessionalInfo);
        layReferences = view.findViewById(R.id.layReferences);
        layBankDetails = view.findViewById(R.id.layBankDetails);
        layDocuments = view.findViewById(R.id.layDocuments);

        loanStatus = view.findViewById(R.id.loanStatus);
        imgIsBasicInfoUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLoanApplicationStatusClick(v);
            }
        });
        imgIsProfessionalInfoUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLoanApplicationStatusClick(v);
            }
        });
        imgIsReferenceInfoUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLoanApplicationStatusClick(v);
            }
        });
        imgIsBankDetailsUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLoanApplicationStatusClick(v);
            }
        });
        imgIsDocumentInfoUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLoanApplicationStatusClick(v);
            }
        });
        getLoanApplicationStatus();
        return view;
    }

    public void OnLoanApplicationStatusClick(View view) {
        ImageView imgView = (ImageView) view;
        if (imgView.getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.ic_ok_arrow).getConstantState()) {
            Toast.makeText(context, "Details are already uploaded to the server", Toast.LENGTH_LONG).show();
        } else {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) activity.findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().findItem(R.id.bottom_navigation_dashboard).setChecked(true);
            replaceFragment(new DashboardFragment());
        }

    }

    private void getLoanApplicationStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String loanApplicationStatus = sharedPreferences.getString(getString(R.string.loan_application_status), "");
        if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.employee_details_pending))) {
            loanStatus.setVisibility(View.GONE);
            imgIsBasicInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.references_pending))) {
            loanStatus.setVisibility(View.GONE);
            imgIsBasicInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
            imgIsProfessionalInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.bank_details_pending))) {
            loanStatus.setVisibility(View.GONE);
            imgIsBasicInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
            imgIsProfessionalInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
            imgIsReferenceInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.documents_pending))) {
            loanStatus.setVisibility(View.GONE);
            imgIsBasicInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
            imgIsProfessionalInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
            imgIsReferenceInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
            imgIsBankDetailsUploaded.setImageResource(R.drawable.ic_ok_arrow);
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.under_review)) || loanApplicationStatus.equalsIgnoreCase(getString(R.string.disbursement_pending)) || loanApplicationStatus.equalsIgnoreCase(getString(R.string.disbursed))) {
            imgIsBasicInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
            imgIsProfessionalInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
            imgIsReferenceInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
            imgIsBankDetailsUploaded.setImageResource(R.drawable.ic_ok_arrow);
            imgIsDocumentInfoUploaded.setImageResource(R.drawable.ic_ok_arrow);
            loanStatus.setVisibility(View.VISIBLE);
            if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.under_review))) {
                loanStatus.setText(getString(R.string.under_review_msg));
                loanStatus.setTextColor(getResources().getColor(R.color.splash_color));
            } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.disbursement_pending))) {
                loanStatus.setText(getString(R.string.disbursed_pending_msg));
                loanStatus.setTextColor(getResources().getColor(R.color.colorAccent));
            } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.disbursed))) {
                loanStatus.setTextColor(getResources().getColor(R.color.colorAccent));
                loanStatus.setText(getString(R.string.disbursed_msg));
            }
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.rejected))) {
            loanStatus.setVisibility(View.VISIBLE);
            loanStatus.setTextColor(getResources().getColor(R.color.error));
            loanStatus.setText(getString(R.string.rejected_msg));
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content_frame, fragment);
        //  fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
