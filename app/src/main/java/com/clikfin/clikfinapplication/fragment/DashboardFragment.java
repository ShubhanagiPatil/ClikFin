package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.activity.DashboardActivity;
import com.clikfin.clikfinapplication.adapter.dashboard_viewPager;
import com.clikfin.clikfinapplication.externalRequests.Response.ApplyLoanResponse;
import com.clikfin.clikfinapplication.model.OnBoardItem;
import com.clikfin.clikfinapplication.network.APICallbackInterface;
import com.clikfin.clikfinapplication.network.APIClient;
import com.clikfin.clikfinapplication.util.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class DashboardFragment extends Fragment {
    Button btnApplyLoan, btnCibilCheck, btnReferAndEarn;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment = null;
    private int dotsCount;
    private ImageView[] dots;
    ViewPager viewPager;
    static Timer timer;
    static TimerTask timerTask;
    FragmentActivity activity;
    Context context;
    BottomNavigationView bottomNavigationView;
    PagerAdapter  mAdapter;
    final ArrayList<OnBoardItem> onBoardItems = new ArrayList<>();
LinearLayout SliderDots;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard1, container, false);
        btnApplyLoan = view.findViewById(R.id.btn_apply_now);
        btnCibilCheck = view.findViewById(R.id.btn_check_now);
        btnReferAndEarn = view.findViewById(R.id.btn_refer_and_earn);
        viewPager =  view.findViewById(R.id.scrollingViewpager);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);


        loadData();

        mAdapter = new dashboard_viewPager(context, onBoardItems);
        viewPager.setAdapter(mAdapter);

       // setUiPageViewController();



        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");


        btnApplyLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isNetworkConnected(context)) {
                    if (!loanApplicationId.equalsIgnoreCase("")) {
                        getLoanApplicationStatus();
                    } else {
                        fragmentNavigation();
                    }
                } else {
                    Common.networkDisconnectionDialog(context);
                }
            }


        });
        btnCibilCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CibilCheckFragment());
            }
        });
        btnReferAndEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ReferAndEarnFragment());
            }
        });

        ((DashboardActivity) context).setNavigationTitle(getString(R.string.title_dashboard));
        return view;
    }

    private void fragmentNavigation() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String loanApplicationStatus = sharedPreferences.getString(getString(R.string.loan_application_status), "");
        if (loanApplicationStatus.equalsIgnoreCase("")) {
            replaceFragment(new BasicDetailsFragment());
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.employee_details_pending))) {
            replaceFragment(new EmploymentFragment());
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.references_pending))) {
            replaceFragment(new ReferenceFragment());
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.bank_details_pending))) {
            replaceFragment(new BankDetailsFragment());
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.documents_pending))) {
            replaceFragment(new DocumentUploadFragment());
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.under_review))) {
            replaceFragment(new LoanApplicationStatusFragment());
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.disbursement_pending))) {
            replaceFragment(new LoanApplicationStatusFragment());
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.disbursed))) {
            replaceFragment(new LoanApplicationStatusFragment());
        } else if (loanApplicationStatus.equalsIgnoreCase(getString(R.string.rejected))) {
            replaceFragment(new LoanApplicationStatusFragment());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable(){

                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem()+1)%onBoardItems.size());
                    }
                });
            }
        };
        timer  = new Timer();
        timer.schedule(timerTask, 4000, 4000);
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(context);
            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(0, 0, 0, 0);

            SliderDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

    }
    public void loadData() {

        int[] header = {R.string.scrolling_header_1, R.string.scrolling_header_2};
        int[] desc = {R.string.scrolling_disc_1, R.string.scrolling_disc_2};
        int[] imageId = {R.drawable.scrolling_img1, R.drawable.scrolling_img2};


        for (int i = 0; i < imageId.length; i++) {
            OnBoardItem item = new OnBoardItem();
            item.setImageID(imageId[i]);
            item.setTitle(getResources().getString(header[i]));
            item.setDescription(getResources().getString(desc[i]));

            onBoardItems.add(item);
        }
    }
    private void getLoanApplicationStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(getString(R.string.user_auth_token), "");
        String loanApplicationId = sharedPreferences.getString(getString(R.string.loan_application_id), "");
        String url = APIClient.BASE_URL + "/application/" + loanApplicationId + "/status";
        Call<ApplyLoanResponse> call = APIClient.getClient(APIClient.type.JSON).getLoanApplicationStatus(authToken, url);
        call.enqueue(new APICallbackInterface<ApplyLoanResponse>(context) {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ApplyLoanResponse> call, Response<ApplyLoanResponse> response) {
                super.onResponse(call, response);
                switch (response.code()) {
                    case 200:
                        SharedPreferences.Editor editor = Common.getSharedPreferencesEditor(activity);
                        editor.putString(getString(R.string.loan_application_status), response.body().getStatus());
                        editor.putString(getString(R.string.loan_application_id), response.body().getApplicationId());
                        editor.apply();
                        fragmentNavigation();
                        break;
                    case 401:
                    case 403:
                        Toast.makeText(context, getString(R.string.logged_out), Toast.LENGTH_LONG).show();
                        fragmentManager = activity.getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentManager.popBackStack();
                        fragmentTransaction.replace(R.id.content_frame, new LoginFragment());
                        fragmentTransaction.commit();
                        break;
                    case 500:
                        Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
                        break;
                }

            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ApplyLoanResponse> call, Throwable t) {
                super.onFailure(call, t);
                Common.logAPIFailureToCrashlyatics(t);
                if (isAdded())
                    Toast.makeText(context, getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        fragmentManager = activity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        // fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
    public void replaceFragmentMainFrame(Fragment fragment) {
        fragmentManager =activity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
       // fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

}
