package com.example.carmine.myapplication.layout.seller.gestioneMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.Menu;
import com.example.carmine.myapplication.layout.seller.gestioneProdotti.GestioneProdotti;
import com.example.carmine.myapplication.layout.seller.mainActivityAfterLoginAsSeller;

public class GestioneMenu extends AppCompatActivity {
    private ListView listView;
    private Button aggiungi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestione_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = (TextView) findViewById(R.id.textViewListVew);
        textView.setText("menu del locale");
        listView = (ListView) findViewById(R.id.listView);
        aggiungi = (Button) findViewById(R.id.aggiungiMenu);
        final Menu menu = new Menu();
        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestioneMenu.this, createMenu.class));
                finish();
            }
        });
        menu.getListMenuOfLocal(GestioneMenu.this, listView);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GestioneMenu.this, mainActivityAfterLoginAsSeller.class));
        finish();
    }

    public void finishAct() {
        finish();
    }
}
