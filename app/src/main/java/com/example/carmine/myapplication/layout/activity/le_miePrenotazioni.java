package com.example.carmine.myapplication.layout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.Prenotazione;

public class le_miePrenotazioni extends AppCompatActivity {
    ListView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_le_mie_prenotazioni);
        l = (ListView) findViewById(R.id.ListprenotazioniLocale);
        Prenotazione p = new Prenotazione();
        p.getMyReservation(le_miePrenotazioni.this, l);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(le_miePrenotazioni.this, MainActivityAftherLogin.class));
        finish();

    }
}
