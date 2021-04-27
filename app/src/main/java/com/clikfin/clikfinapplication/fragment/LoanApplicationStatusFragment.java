package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;

public class LoanApplicationStatusFragment extends Fragment {
    TextView tvLoanApplicationStatusDescription,tvLoanStatusHeader;
    ImageView imgApplicationStatus;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    static FragmentActivity activity;
    Context context;
String userName;
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
        View view = inflater.inflate(R.layout.fragment_loan_application_status, container, false);
        tvLoanApplicationStatusDescription = view.findViewById(R.id.tvLoanApplicationStatusDescription);
        tvLoanStatusHeader=view.findViewById(R.id.tvLoanStatusHeader);
        imgApplicationStatus = view.findViewById(R.id.imgApplicationStatus);
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.loan_application_status));


        fragmentNavigation();
        return view;
    }

    private void fragmentNavigation() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String loanApplicationStatus = sharedPreferences.getString(getString(R.string.loan_application_status), "");
        userName=sharedPreferences.getString(getString(R.string.user_name),"");

        if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.under_review))) {
            tvLoanStatusHeader.setText(getString(R.string.congratulations));
            tvLoanApplicationStatusDescription.setText("Hi "+userName);
            tvLoanApplicationStatusDescription.setText(" "+tvLoanApplicationStatusDescription.getText().toString()+ " "+getString(R.string.under_review_msg));
            imgApplicationStatus.setImageResource(R.drawable.ic_ok);
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.rejected))) {
            tvLoanStatusHeader.setText(getString(R.string.thank_you));
            tvLoanApplicationStatusDescription.setText("Hi "+userName);
            tvLoanApplicationStatusDescription.setText(" "+tvLoanApplicationStatusDescription.getText().toString()+" "+getString(R.string.rejected_msg));
            imgApplicationStatus.setImageResource(R.drawable.ic_ok);
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.disbursement_pending))) {
            tvLoanStatusHeader.setText(getString(R.string.congratulations));
            tvLoanApplicationStatusDescription.setText("Hi "+userName);
            tvLoanApplicationStatusDescription.setText(" "+tvLoanApplicationStatusDescription.getText().toString()+" "+getString(R.string.disbursed_pending_msg));
            imgApplicationStatus.setImageResource(R.drawable.ic_ok);
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.disbursed))) {
            tvLoanStatusHeader.setText(getString(R.string.congratulations));
            tvLoanApplicationStatusDescription.setText("Hi "+userName);
            imgApplicationStatus.setImageResource(R.drawable.ic_ok);
            tvLoanApplicationStatusDescription.setText(" "+tvLoanApplicationStatusDescription.getText().toString()+" "+getString(R.string.disbursed_msg));
        }
    }
}
