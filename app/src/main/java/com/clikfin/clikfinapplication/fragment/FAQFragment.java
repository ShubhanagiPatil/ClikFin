package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;


public class FAQFragment extends Fragment {
    static FragmentActivity activity;
    Context context;
    public FAQFragment() {
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
    public static FAQFragment newInstance(String param1, String param2) {
        return new FAQFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_f_a_q));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f_a_q, container, false);
    }
}
