package com.cs165.domefavor.domefavor;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerInfoItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mItemID, mItemAge, mItemPrice, mItemGender;
    private final ImageView image;

    public RecyclerInfoItemViewHolder(final View parent, TextView itemID, TextView mItemAge, TextView mItemPrice, TextView mItemGender, ImageView image) {
        super(parent);
        this.mItemID = itemID;
        this.mItemAge = mItemAge;
        this.mItemPrice = mItemPrice;
        this.mItemGender = mItemGender;
        this.image = image;
    }

    public static RecyclerInfoItemViewHolder newInstance(View parent) {
        TextView itemID = (TextView) parent.findViewById(R.id.itemID);
        TextView itemAge = (TextView) parent.findViewById(R.id.itemAge);
        TextView itemPrice = (TextView) parent.findViewById(R.id.itemPrice);
        TextView itemGender = (TextView) parent.findViewById(R.id.itemGender);
        ImageView image = (ImageView) parent.findViewById(R.id.itempic);
        return new RecyclerInfoItemViewHolder(parent, itemID, itemAge, itemPrice, itemGender, image);
    }

    public void setItemText(CharSequence text1, CharSequence text2, CharSequence text3, CharSequence text4, Uri uri) {
        mItemID.setText(text1);
        mItemAge.setText(text2);
        mItemGender.setText(text3);
        mItemPrice.setText(text4);
        if (uri != null)
            image.setImageURI(uri);
        else
            image.setImageResource(R.drawable.default_profile);
    }

}
