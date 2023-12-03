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
    String[] arrItemPrice;
    int[] arrItemImage;

    public ItemAdapter(Context context, String[] arrItemPrice, int[] arrItemImage) {
        this.context = context;
        this.arrItemPrice = arrItemPrice;
        this.arrItemImage = arrItemImage;
    }

    @Override
    public int getCount() {
        return arrItemPrice.length;
    }

    @Override
    public Object getItem(int position) {
        return arrItemPrice[position];
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
        TextView itemPrice = view.findViewById(R.id.itemprice);

        itemImage.setImageResource(arrItemImage[position]);
        itemPrice.setText(arrItemPrice[position]);

        return view;
    }
}
