package com.example.carmine.myapplication.layout.seller.gestioneMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.ProdottiLocale;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;
import com.example.carmine.myapplication.layout.seller.mainActivityAfterLoginAsSeller;

public class aggiungiProdottiAlMenu extends AppCompatActivity {
    TextView textView;
    ListView listView;
    Button aggiungi;
    Button skip;
    String idMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_prodotti_al_menu);
        listView = (ListView) findViewById(R.id.listView);
        Bundle datipassati = getIntent().getExtras();
        idMenu = (String) datipassati.get("idMenuInserito");
        textView = (TextView) findViewById(R.id.textViewListVew);
        textView.setText("seleziona i prodotti da aggiungere");
        skip = (Button) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(aggiungiProdottiAlMenu.this, mainActivityAfterLoginAsSeller.class));
                finish();
            }
        });

        ProdottiLocale p = new ProdottiLocale();
        p.getProdottiNonNelMenu(aggiungiProdottiAlMenu.this, listView, attivita.getInstance().getId(), idMenu);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(aggiungiProdottiAlMenu.this, GestioneMenu.class));
    }

    public void finishAct() {
        finish();
    }
}
