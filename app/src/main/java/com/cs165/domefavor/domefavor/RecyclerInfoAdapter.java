package com.cs165.domefavor.domefavor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/*
* RecyclerView Adapter that allows to add a header view.
* */
public class RecyclerInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    private List<PriceItem> mItemList;

    public RecyclerInfoAdapter(List<PriceItem> itemList) {
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_info, parent, false);
            return RecyclerInfoItemViewHolder.newInstance(view);
        } else if (viewType == TYPE_HEADER) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_header, parent, false);
            return new RecyclerHeaderViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!isPositionHeader(position)) {
            RecyclerInfoItemViewHolder holder = (RecyclerInfoItemViewHolder) viewHolder;
            String itemID = mItemList.get(position - 1).getPersonID(); // header
            String itemAge = mItemList.get(position - 1).getAge();
            String itemGender = mItemList.get(position - 1).getGender();
            String itemPrice = String.valueOf(mItemList.get(position - 1).getPrice());
            holder.setItemText("Name ID: " + itemID, "Age: " + itemAge, "Gender: " + itemGender, "The price he/she gave:" + itemPrice);
        }
    }

    public int getBasicItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + 1; // header
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
