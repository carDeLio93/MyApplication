package com.example.carmine.myapplication.layout.seller.gestioneMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.seller.gestioneProdotti.GestioneProdotti;
import com.example.carmine.myapplication.layout.seller.gestioneProdotti.UpdateProduct;

public class UpdateMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdateMenu.this, GestioneMenu.class));
        finish();
    }
}
