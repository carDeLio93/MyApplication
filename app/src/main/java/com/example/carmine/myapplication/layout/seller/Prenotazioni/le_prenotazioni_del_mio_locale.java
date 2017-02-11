package com.example.carmine.myapplication.layout.seller.Prenotazioni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.Prenotazione;
import com.example.carmine.myapplication.layout.seller.mainActivityAfterLoginAsSeller;

public class le_prenotazioni_del_mio_locale extends AppCompatActivity {
    ListView l;
    Button accetta;
    Button rifiuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_le_prenotazioni_del_mio_locale);
        l = (ListView) findViewById(R.id.ListprenotazioniLocale);
        final Prenotazione p = new Prenotazione();
        p.getReservatioOfLocal(le_prenotazioni_del_mio_locale.this, l);
        accetta = (Button) findViewById(R.id.accettaPrenotazione);
        accetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.accetta(le_prenotazioni_del_mio_locale.this);
            }
        });
        rifiuta = (Button) findViewById(R.id.rifiutaPrenotazione);
        rifiuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.rifiuta(le_prenotazioni_del_mio_locale.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(le_prenotazioni_del_mio_locale.this, mainActivityAfterLoginAsSeller.class));
        finish();
    }
}
