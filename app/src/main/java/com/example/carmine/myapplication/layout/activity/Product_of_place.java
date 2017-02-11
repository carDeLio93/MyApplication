package com.example.carmine.myapplication.layout.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewChekProduct;
import com.example.carmine.myapplication.layout.core.data.ProdottiLocale;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewProdottiDelLocale;
import com.example.carmine.myapplication.layout.core.data.singleton.Utente;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Product_of_place extends AppCompatActivity {
    private ListView l;
    private TextView textView;
    Button order, reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_of_place);

        textView = (TextView) findViewById(R.id.textView6);
        textView.setText("prodotti del locale");
        l = (ListView) findViewById(R.id.productOfPlace);

        reservation = (Button) findViewById(R.id.prenota);
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utente.getInstance().getCf().equals("NON_AUTENTICATO")) {
                    Toast.makeText(Product_of_place.this, "effettua il login per accedere a questo contenuto", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(Product_of_place.this, ReservationActivity.class);
                    i.putExtra("idLocale", attivita.getInstance().getId());
                    startActivity(i);
                }
                finish();

            }
        });


        ProdottiLocale p = new ProdottiLocale();
        p.getProdottiDelLocale(Product_of_place.this, l, attivita.getInstance().getId());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Product_of_place.this, PlaceActGoogle.class));
        finish();
    }
}
