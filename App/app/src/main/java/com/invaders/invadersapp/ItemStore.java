package com.invaders.invadersapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ItemStore extends AppCompatActivity {

    GridView gridView;
    int[] itemImage = {R.drawable.item_shield, R.drawable.item_bomb, R.drawable.item_bstone, R.drawable.item_pstone};
    String[] itemName = {"shield", "bomb", "bstone", "pstone"};
    int[] itemPrice = {50, 100, 150, 150};
    int[] itemQuantity = {0,1,2,3};
    int coinQuantity = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemstore);

        gridView = findViewById(R.id.itemGridView);

        ItemAdapter itemAdapter = new ItemAdapter(ItemStore.this, itemImage, itemName, itemPrice, itemQuantity);

        gridView.setAdapter(itemAdapter);
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), itemName[position] + "번 선택", Toast.LENGTH_SHORT);
//            }
//        });
    }
}
