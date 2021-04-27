package com.clikfin.clikfinapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.adapter.OnBoard_Adapter;
import com.clikfin.clikfinapplication.model.OnBoardItem;

import java.util.ArrayList;

public class OnBoardFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    int previous_pos = 0;
    final ArrayList<OnBoardItem> onBoardItems = new ArrayList<>();
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private OnBoard_Adapter mAdapter;
    private Button btn_get_started;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_board, container, false);


        btn_get_started = view.findViewById(R.id.btn_get_started);
        ViewPager on_boarding_pager = view.findViewById(R.id.pager_introduction);
        pager_indicator = view.findViewById(R.id.viewPagerCountDots);
        loadData();
        mAdapter = new OnBoard_Adapter(getActivity(), onBoardItems);
        on_boarding_pager.setAdapter(mAdapter);
        on_boarding_pager.setCurrentItem(0);
        on_boarding_pager.addOnPageChangeListener(this);
        btn_get_started.setOnClickListener(this);

        setUiPageViewController();

        return view;
    }


    public void loadData() {

        int[] header = {R.string.ob_header1, R.string.ob_header2, R.string.ob_header3};
        int[] desc = {R.string.ob_desc1, R.string.ob_desc2, R.string.ob_desc3};
        int[] imageId = {R.drawable.intro_info_1, R.drawable.intro_info_2, R.drawable.intro_info_3};


        for (int i = 0; i < imageId.length; i++) {
            OnBoardItem item = new OnBoardItem();
            item.setImageID(imageId[i]);
            item.setTitle(getResources().getString(header[i]));
            item.setDescription(getResources().getString(desc[i]));

            onBoardItems.add(item);
        }
    }

    // Button bottomUp animation

    public void show_animation() {
        Animation show = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up_dialog);

        btn_get_started.startAnimation(show);

        show.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                btn_get_started.setVisibility(View.VISIBLE);
                pager_indicator.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btn_get_started.clearAnimation();

            }

        });


    }

    // Button top down animation

    public void hide_animation() {
        Animation hide = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_down);

        btn_get_started.startAnimation(hide);

        hide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                btn_get_started.setVisibility(View.INVISIBLE);
                pager_indicator.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btn_get_started.clearAnimation();
                btn_get_started.setVisibility(View.INVISIBLE);

            }

        });


    }

    // setup the
    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(0, 0, 0, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
        }

        dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));


        int pos = position + 1;

        if (pos == dotsCount && previous_pos == (dotsCount - 1)) {
            show_animation();
        } else if (pos == (dotsCount - 1) && previous_pos == dotsCount) {
            hide_animation();
        }

        previous_pos = pos;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.btn_get_started) {
            FragmentManager fragmentManager;
            Fragment fragment;
            SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.user_on_board), true);
            editor.apply();

            fragmentManager = getActivity().getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (getResources().getBoolean(R.bool.is_login_with_mobile_number)) {
                fragment = new LoginWithMobileNumber();
            } else {
                fragment = new LoginFragment();
            }
            fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            fragmentTransaction.commit();
        }
    }
}
