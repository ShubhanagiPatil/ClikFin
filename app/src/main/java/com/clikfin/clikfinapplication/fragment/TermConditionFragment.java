package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;

public class TermConditionFragment extends Fragment {
    static FragmentActivity activity;
    Context context;
private TextView termTv;
    public TermConditionFragment() {
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
    public static TermConditionFragment newInstance(String param1, String param2) {
        return  new TermConditionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_term_condition, container, false);
       /* termTv=v.findViewById(R.id.termTv);
        termTv.setPaintFlags(termTv.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        termTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                File file = new File("res/raw/terms.pdf");
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
            }
        });*/
        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_terms_and_conditions));
        return view;
    }
}
