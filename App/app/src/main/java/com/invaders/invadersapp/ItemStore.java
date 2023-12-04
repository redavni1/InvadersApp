package com.invaders.invadersapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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
    int coinQuantity = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemstore);

        /* item grid for item list */
        gridView = findViewById(R.id.itemGridView);

        /* fill the empty grid with items */
        ItemAdapter itemAdapter = new ItemAdapter(ItemStore.this, itemImage, itemName, itemPrice, itemQuantity);
        gridView.setAdapter(itemAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* event when user click the item in grid */
            }
        });
    }
}
