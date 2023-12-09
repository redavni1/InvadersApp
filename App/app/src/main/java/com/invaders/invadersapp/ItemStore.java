package com.invaders.invadersapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemStore extends AppCompatActivity {
    /* grid view for item grid*/
    GridView gridView;
    /* array for item images png in drawable  */
    int[] itemImage = {R.drawable.item_shield, R.drawable.item_bomb, R.drawable.item_bstone, R.drawable.item_pstone};
    /* array for item name */
    String[] itemName = {"Shield", "Bomb", "Blue Gem", "Pink Gem"};
    /* array for item price*/
    int[] itemPrice = {50, 100, 150, 150};
    /* array for item quantity (the number of items this user owns currently) */
    int[] itemQuantity = {0,1,2,3};
    /* the number of coins this user owns currently */
    int coinQuantity = 200 ;
    /** Adapter for the grid view */
    ItemAdapter itemAdapter;


    /**
     * Called when the activity is starting.
     * This is where most initialization should go: calling setContentView(int) to inflate
     * the activity's UI, using findViewById(int) to programmatically interact with widgets in the UI.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemstore);

        // Initialize the grid view for displaying items
        gridView = findViewById(R.id.itemGridView);

        // Set up the adapter for the grid view with item details
        itemAdapter = new ItemAdapter(ItemStore.this, itemImage, itemName, itemPrice, itemQuantity);
        gridView.setAdapter(itemAdapter);

        /** Set up item click listener */
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleItemClick(position, view);
            }
        });
        ArrayList<HashMap<String, Object>> complexDataStructure = new ArrayList<>();
        for (int i = 0; i < itemImage.length; i++) {
            HashMap<String, Object> itemMap = new HashMap<>();
            itemMap.put("image", itemImage[i]);
            itemMap.put("name", itemName[i]);
            ArrayList<Integer> priceAndQuantity = new ArrayList<>();
            priceAndQuantity.add(itemPrice[i]);
            priceAndQuantity.add(itemQuantity[i]);
            itemMap.put("priceQuantity", priceAndQuantity);
            complexDataStructure.add(itemMap);
        }
    }

    /**
     * Handles the click event on grid items.
     *
     * @param position The position of the item in the grid that was clicked.
     * @param view     The view within the AdapterView that was clicked.
     */
    public void handleItemClick(int position, View view) {
        // Determine the message to be shown in the popup based on the purchase result
        if (position >= 0 && position < itemImage.length) {
            boolean purchaseResult = purchaseItem(position);
            String popupMessage;
            if (purchaseResult) {
                popupMessage = "Purchase complete!";
            } else {
                popupMessage = "Not enough coins!";
            }
            showPopup(view, popupMessage);
        }
    }

    /**
     * Processes the item purchase.
     *
     * @param position The position of the item in the grid to be purchased.
     * @return true if the purchase is successful, false otherwise.
     */
    public boolean purchaseItem(int position) {
        if (position < 0 || position >= itemPrice.length) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + position);
        }
        if (position >= 0 && position < itemPrice.length) {
            int price = itemPrice[position];
            if (coinQuantity >= price) {
                coinQuantity -= price;
                itemQuantity[position]++;
                updateUI();
                return true;
            }
        }
        return false;
    }

    /**
     * Shows a popup message.
     *
     * @param anchorView The view to anchor the popup window to.
     * @param message    The message to be displayed in the popup window.
     */
    public void showPopup(View anchorView, String message) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.custom_popup, null);

        PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Set the message in the popup's text view
        TextView textView = popupView.findViewById(R.id.popup_text);
        textView.setText(message);

        // Show the popup and automatically dismiss it after a delay
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

        new Handler().postDelayed(() -> popupWindow.dismiss(), 3000);
    }

    /**
     * Updates the user interface, particularly the item grid view and the coin balance.
     */
    public void updateUI() {
        runOnUiThread(() -> {
            // Notify the adapter of data changes so the UI can be refreshed
            itemAdapter.notifyDataSetChanged();
            // Update the displayed coin quantity
            TextView coinTextView = findViewById(R.id.item_coin);
            if (coinTextView != null) {
                coinTextView.setText(String.valueOf(coinQuantity));
            }
        });
    }
}