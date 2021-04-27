package com.clikfin.clikfinapplication.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.clikfin.clikfinapplication.R;

public class LoanStatusFragment extends Fragment {
   private CardView loanstatus;
    public LoanStatusFragment() {
        // Required empty public constructor
    }


    public static LoanStatusFragment newInstance(String param1, String param2) {
        return  new LoanStatusFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_loan_status, container, false);
        loanstatus=v.findViewById(R.id.loanstatus);
        loanstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://upwrds.in/JrPw6ufcIab"));
                startActivity(browserIntent);
            }
        });
        return v;
    }
}
