package com.clikfin.clikfinapplication.adapter;

import android.content.Context;

import com.clikfin.clikfinapplication.R;
import com.clikfin.clikfinapplication.model.OnBoardItem;
import com.clikfin.clikfinapplication.util.ViewPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class OnBoard_Adapter extends ViewPagerAdapter{
    private Context mContext;
    ArrayList<OnBoardItem> onBoardItems=new ArrayList<>();

    public OnBoard_Adapter(Context mContext, ArrayList<OnBoardItem> items) {
        super(mContext);
        this.mContext = mContext;
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
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.onboard_item, container, false);
        OnBoardItem item=onBoardItems.get(position);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.info_image);
        imageView.setImageResource(item.getImageID());
        TextView tv_title=(TextView)itemView.findViewById(R.id.info_header);
        tv_title.setText(item.getTitle());
        TextView tv_content=(TextView)itemView.findViewById(R.id.info_description);
        tv_content.setText(item.getDescription());
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}
