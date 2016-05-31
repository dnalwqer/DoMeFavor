package com.cs165.domefavor.domefavor;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/*
* RecyclerView Adapter that allows to add a header view.
* */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    private List<TaskItem> mItemList;
    private List<Uri> mList;
    private static ClickListener clickListener;

    public RecyclerAdapter(List<TaskItem> itemList, List<Uri> mList) {
        this.mItemList = itemList;
        this.mList = mList;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerAdapter.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
            TextView itemTextView = (TextView) view.findViewById(R.id.itemTextView);
            ImageView itemImage = (ImageView) view.findViewById(R.id.itemImage);
            TextView biders = (TextView) view.findViewById(R.id.bider);
            return new RecyclerItemViewHolder(view, itemTextView, itemImage, biders);
        } else if (viewType == TYPE_HEADER) {
            final View view = LayoutInflater.from(context).inflate(R.layout.recycler_header, parent, false);
            return new RecyclerHeaderViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!isPositionHeader(position)) {
            RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
            String itemText = mItemList.get(position - 1).getTaskName(); // header
            Uri uri = mList.get(position - 1);
            if(mItemList.get(position - 1).getStatus().equals("ToBeFinish")) {
                holder.setItemText(itemText, uri, "Awaits Completion");
            }else{
                String bider = mItemList.get(position - 1).getBiders();
                holder.setItemText(itemText, uri, bider);
            }
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

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    class RecyclerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private final TextView mItemTextView;
        private final ImageView mImageView;
        private final TextView bider;


        public RecyclerItemViewHolder(final View parent, TextView itemTextView, ImageView itemImage, TextView bider) {
            super(parent);
            parent.setOnClickListener(this);
            parent.setOnLongClickListener(this);
            mItemTextView = itemTextView;
            mImageView = itemImage;
            this.bider = bider;
        }

        public RecyclerItemViewHolder newInstance(View parent) {
            TextView itemTextView = (TextView) parent.findViewById(R.id.itemTextView);
            ImageView itemImage = (ImageView) parent.findViewById(R.id.itemImage);
            TextView biders = (TextView) parent.findViewById(R.id.bider);
            return new RecyclerItemViewHolder(parent, itemTextView, itemImage, biders);
        }

        public void setItemText(CharSequence text, Uri uri, CharSequence biderss) {
            mItemTextView.setText(text);
            if (uri != null)
                mImageView.setImageURI(uri);
            else
                mImageView.setImageResource(R.drawable.default_profile);
            bider.setText(biderss);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

}
