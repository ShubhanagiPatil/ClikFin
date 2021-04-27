package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;

public class EligibleCheckFragment extends Fragment {
    private EditText edLoanAmount,edLoanmonth,tvroi;
    private TextView emiTv;
    private Button btnEMI;
    private SeekBar seekBar,monthBar;
    static FragmentActivity activity;
    Context context;
    public EligibleCheckFragment() {
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
    public static EligibleCheckFragment newInstance(String param1, String param2) {
        return new EligibleCheckFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_eligible_check, container, false);
        edLoanAmount=view.findViewById(R.id.edLoanAmount);
        edLoanmonth=view.findViewById(R.id.edLoanmonth);
        btnEMI=view.findViewById(R.id.btnEMI);
        emiTv=view.findViewById(R.id.emiTv);
        monthBar=view.findViewById(R.id.monthBar);
        seekBar=view.findViewById(R.id.seekBar);
        tvroi=view.findViewById(R.id.tvroi);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                edLoanAmount.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        monthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                edLoanmonth.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnEMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edLoanAmount.getText().length()==0){
                    getToast("Please Enter Loan Amount");
                }else if(edLoanmonth.getText().length()==0){
                    getToast("Please Enter Month");
                }else if(tvroi.getText().length()==0){
                    getToast("Please Enter Rate Of Interest");
                }else{
                    findEMI();
                }

            }
        });
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_emi_calculator));
        return view;
    }

    private void findEMI(){
   /*     double loanAmount = Integer.parseInt(tvLoanAmount.getText().toString());
        double interestRate = (Integer.parseInt(tvroi.getText().toString()));
        double loanPeriod = Integer.parseInt(edLoanmonth.getText().toString());
        double r = interestRate/1200;
        double r1 = Math.pow(r+1,loanPeriod);

        double monthlyPayment = (double) ((r+(r/(r1-1))) * loanAmount);
        double totalPayment = monthlyPayment * loanPeriod;


        emiTv.setText(new DecimalFormat("##.##").format(totalPayment)); */

      /*  float loanAmount = Float.parseFloat(tvLoanAmount.getText().toString());
        float interestRate = Float.parseFloat(tvroi.getText().toString());
        float loanPeriod = Float.parseFloat(edLoanmonth.getText().toString());

        float Dvdnt = calDvdnt(interestRate, loanPeriod);
        float FD = calFinalDvdnt(loanAmount, interestRate, Dvdnt);
        float D = calDivider(Dvdnt);
        float emi = calEmi(FD, D);

        emiTv.setText(String.valueOf(emi)); */

        double loanAmount = Double.parseDouble(edLoanAmount.getText().toString());
        double interestRate = Double.parseDouble(tvroi.getText().toString());
        double loanPeriod = Double.parseDouble(edLoanmonth.getText().toString());

        double monthlyinterestratio=(interestRate/100)/12;
        double monthlyinterest=monthlyinterestratio*loanAmount;
        double top=Math.pow((1+monthlyinterestratio),loanPeriod);
        double bottom=top -1;
        double sp=top/bottom;
        double emi=((loanAmount * monthlyinterestratio) *sp);
        //double result=emi

        emiTv.setText(String.format("%.2f", emi));

    }

    public float calDvdnt(float Rate, float Months) {
        return (float)(Math.pow(1 + Rate, Months));
    }
    public float calFinalDvdnt(float Principal, float Rate, float Dvdnt) {
        return (float)(Principal * Rate * Dvdnt);
    }
    public float calDivider(float Dvdnt) {
        return (float)(Dvdnt - 1);
    }
    public float calEmi(float FD, Float D) {
        return (float)(FD / D);
    }

    private void getToast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }

}
