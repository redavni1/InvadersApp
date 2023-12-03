package com.invaders.invadersapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ItemAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;

    /* Array for item sets */
    int[] arrItemImage, arrItemPrice, arrItemQuantity;
    /* Array for item name */
    String[] arrItemName;

    /* item adapter for item store page*/
    public ItemAdapter(Context context, int[] arrItemImage, String[] arrItemName, int[] arrItemPrice, int[] arrItemQuantity) {
        this.context = context;
        this.arrItemName = arrItemName;
        this.arrItemImage = arrItemImage;
        this.arrItemPrice = arrItemPrice;
        this.arrItemQuantity = arrItemQuantity;
    }

    /* getCount() : count number of item */
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
        /* find views in item_layout using each id */
        ImageView itemImage = view.findViewById(R.id.itemimage);
        TextView itemName = view.findViewById(R.id.itemname);
        TextView itemPrice = view.findViewById(R.id.itemprice);
        TextView itemQuantity = view.findViewById(R.id.itemquantity);

        /* set each text and image */
        itemImage.setImageResource(arrItemImage[position]);
        itemName.setText(arrItemName[position]);
        itemPrice.setText(String.valueOf(arrItemPrice[position]));
        itemQuantity.setText("quantity : " + String.valueOf(arrItemQuantity[position]));

        /* fill the grid view by returning view */
        return view;
    }
}
