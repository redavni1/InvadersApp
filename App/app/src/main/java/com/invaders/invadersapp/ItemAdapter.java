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

    /**
     * Array for item sets
     */
    int[] arrItemImage, arrItemPrice, arrItemQuantity;
    /**
     * Array for item name
     */
    String[] arrItemName;

    /**
     *
     * item adapter for item store page
     *
     * @param context
     * @param arrItemImage
     * @param arrItemName
     * @param arrItemPrice
     * @param arrItemQuantity
     */
    public ItemAdapter(Context context, int[] arrItemImage, String[] arrItemName, int[] arrItemPrice, int[] arrItemQuantity) {
        this.context = context;
        this.arrItemName = arrItemName;
        this.arrItemImage = arrItemImage;
        this.arrItemPrice = arrItemPrice;
        this.arrItemQuantity = arrItemQuantity;
    }

    /**
     * count number of item
     * @return
     */
    @Override
    public int getCount() {
        return arrItemName.length;
    }

    /**
     *
     * @param position Position of the item whose data we want within the adapter's
     * data set.
     * @return
     */
    @Override
    public Object getItem(int position) {
        return arrItemName[position];
    }

    /**
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     *
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param view The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param viewGroup The parent that this view will eventually be attached to
     * @return
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null){
            view = inflater.inflate(R.layout.item_layout, null);
        }
        /**
         * find views in item_layout using each id
         */
        ImageView itemImage = view.findViewById(R.id.itemimage);
        TextView itemName = view.findViewById(R.id.itemname);
        TextView itemPrice = view.findViewById(R.id.itemprice);
        TextView itemQuantity = view.findViewById(R.id.itemquantity);

        /**
         * set each text and image
         */
        itemImage.setImageResource(arrItemImage[position]);
        itemName.setText(arrItemName[position]);
        itemPrice.setText(String.valueOf(arrItemPrice[position]));
        itemQuantity.setText("quantity : " + String.valueOf(arrItemQuantity[position]));

        /**
         * fill the grid view by returning view
         */
        return view;
    }
}
