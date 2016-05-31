package com.cs165.domefavor.domefavor;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mItemTextView;
    private final ImageView mImageView;

    public RecyclerItemViewHolder(final View parent, TextView itemTextView, ImageView itemImage) {
        super(parent);
        mItemTextView = itemTextView;
        mImageView = itemImage;
    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        TextView itemTextView = (TextView) parent.findViewById(R.id.itemTextView);
        ImageView itemImage = (ImageView) parent.findViewById(R.id.itemImage);
        return new RecyclerItemViewHolder(parent, itemTextView, itemImage);
    }

    public void setItemText(CharSequence text, Uri uri) {
        mItemTextView.setText(text);
        if (uri != null)
            mImageView.setImageURI(uri);
        else
            mImageView.setImageResource(R.drawable.default_profile);
    }

}
