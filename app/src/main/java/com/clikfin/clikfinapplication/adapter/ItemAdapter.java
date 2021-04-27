package com.clikfin.clikfinapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clikfin.clikfinapplication.R;

import java.util.List;

import retrofit2.Callback;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<String> mItems;
    private ItemListener mListener;

    public ItemAdapter(List<String> items, ItemListener listener) {
        mItems = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bottom_sheet_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mItems.get(position));
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        String item;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.item_id);
        }

        void setData(String item) {
            this.item = item;
            textView.setText(item);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

   public  interface ItemListener {
        void onItemClick(String item);
    }
}