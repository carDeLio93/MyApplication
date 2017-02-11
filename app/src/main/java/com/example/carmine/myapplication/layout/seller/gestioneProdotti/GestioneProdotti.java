package com.example.carmine.myapplication.layout.seller.gestioneProdotti;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.ProdottiLocale;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;
import com.example.carmine.myapplication.layout.seller.mainActivityAfterLoginAsSeller;

public class GestioneProdotti extends AppCompatActivity {
    private TextView textView;
    private Button aggiungi;
    private Button elimina;

    private Button modifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestione_prodotti);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView = (TextView) findViewById(R.id.textViewListVew);
        textView.setText("prodotti del locale");
        aggiungi = (Button) findViewById(R.id.aggiungiProdotto);
        elimina = (Button) findViewById(R.id.eliminaProdotti);

        modifica = (Button) findViewById(R.id.modificaProdotti);

        setSupportActionBar(toolbar);
        ListView listView = (ListView) findViewById(R.id.listView);
        final ProdottiLocale p = new ProdottiLocale();
        p.getProdottiDelLocale(GestioneProdotti.this, listView, attivita.getInstance().getId());

        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.elimina(GestioneProdotti.this);
            }
        });
        modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.modifica(GestioneProdotti.this);
            }
        });
        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(GestioneProdotti.this, createProduct.class));
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GestioneProdotti.this, mainActivityAfterLoginAsSeller.class));
        finish();
    }

    public void finishAct() {
        finish();
    }
}
