package com.invaders.invadersapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;

    int[] arrItemImage, arrItemPrice, arrItemQuantity;
    String[] arrItemName;

    public ItemAdapter(Context context, int[] arrItemImage, String[] arrItemName, int[] arrItemPrice, int[] arrItemQuantity) {
        this.context = context;
        this.arrItemName = arrItemName;
        this.arrItemImage = arrItemImage;
        this.arrItemPrice = arrItemPrice;
        this.arrItemQuantity = arrItemQuantity;
    }

    @Override
    public int getCount() {
        return arrItemName.length;
    }

    @Override
    public Object getItem(int position) {
        return arrItemName[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null){
            view = inflater.inflate(R.layout.item_layout, null);
        }
        ImageView itemImage = view.findViewById(R.id.itemimage);
        TextView itemName = view.findViewById(R.id.itemname);
        TextView itemPrice = view.findViewById(R.id.itemprice);
        TextView itemQuantity = view.findViewById(R.id.itemquantity);

        itemImage.setImageResource(R.drawable.item_shield);
        itemName.setText(arrItemName[position]);
        itemPrice.setText(String.valueOf(arrItemPrice[position]));
        itemQuantity.setText(String.valueOf(arrItemQuantity[position]));

        return view;
    }
}
