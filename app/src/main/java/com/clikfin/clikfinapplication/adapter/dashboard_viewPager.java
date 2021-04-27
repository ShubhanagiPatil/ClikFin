package com.clikfin.clikfinapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.model.OnBoardItem;
import com.clikfin.clikfinapplication.util.ViewPagerAdapter;

import java.util.ArrayList;

public class dashboard_viewPager extends ViewPagerAdapter {

    private Context mContext;
    ArrayList<OnBoardItem> onBoardItems = new ArrayList<>();

    public dashboard_viewPager(Context context, ArrayList<OnBoardItem> items) {
        super(context);
        this.mContext = context;
        this.onBoardItems = items;
    }

    @Override
    public int getCount() {
        return onBoardItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.dashboard_view_pager_item, container, false);
        TextView scrolling_header = itemView.findViewById(R.id.scrolling_header);
        TextView scrolling_content = itemView.findViewById(R.id.scrolling_content);
        ImageView scrolling_img = itemView.findViewById(R.id.scrolling_img);
        scrolling_header.setText(onBoardItems.get(position).getTitle());
        scrolling_content.setText(onBoardItems.get(position).getDescription());
        scrolling_img.setImageResource(onBoardItems.get(position).getImageID());

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
