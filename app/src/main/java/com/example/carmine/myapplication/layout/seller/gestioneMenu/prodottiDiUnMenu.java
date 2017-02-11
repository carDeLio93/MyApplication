package com.example.carmine.myapplication.layout.seller.gestioneMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.ProdottiLocale;

public class prodottiDiUnMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodotti_di_un_menu);
        Bundle dati = getIntent().getExtras();
        String idMenu = (String) dati.get("idMenu");
        ListView list = (ListView) findViewById(R.id.listView);
        TextView textView = (TextView) findViewById(R.id.textViewListVew);
        textView.setText("prodotti del menu");
        ProdottiLocale p = new ProdottiLocale();
        p.getProdottiDelMenu(prodottiDiUnMenu.this, list, idMenu);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(prodottiDiUnMenu.this, GestioneMenu.class));
        finish();
    }
}
