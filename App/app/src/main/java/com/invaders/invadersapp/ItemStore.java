package com.invaders.invadersapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemstore);

        /* item grid for item list */
        gridView = findViewById(R.id.itemGridView);

        /* fill the empty grid with items */
        itemAdapter = new ItemAdapter(ItemStore.this, itemImage, itemName, itemPrice, itemQuantity);
        gridView.setAdapter(itemAdapter);

        /** Set up item click listener */
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** event when user click the item in grid */
                int price = itemPrice[position];

                if(coinQuantity >= price){
                    // Deduct coins
                    coinQuantity -= price;
                    // Increment item quantity
                    itemQuantity[position]++;
                    // Update UI (coin and item quantity)
                    update_UI();
                    // Display purchase completion message
                    Toast.makeText(ItemStore.this, "Purchase complete!",Toast.LENGTH_SHORT).show();
                }
                else{
                    // Display not enough coins message
                    Toast.makeText(ItemStore.this, "Not enough coins!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * Updates the UI to reflect changes in item quantities and coin balance.
     */
    private void update_UI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Reflect changes in the adapter
                itemAdapter.notifyDataSetChanged();
                TextView coinTextView = findViewById(R.id.item_coin);
                if (coinTextView != null) {
                    // Update the coin quantity TextView
                    coinTextView.setText(String.valueOf(coinQuantity));
                }
            }
        });

    }
}
