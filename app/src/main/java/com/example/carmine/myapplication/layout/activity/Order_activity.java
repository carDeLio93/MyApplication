package com.example.carmine.myapplication.layout.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.data.Menu;
import com.example.carmine.myapplication.layout.core.data.ProdottiLocale;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewChekProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Order_activity extends AppCompatActivity {
    private ListView l;
    private String idPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordination);

        l = (ListView) findViewById(R.id.prnotaProdotti);
        Bundle b = getIntent().getExtras();
        idPlace = (String) b.get("idPlace");
        ProdottiLocale p = new ProdottiLocale();
        p.getProdottiDelLocale(Order_activity.this, l, idPlace);
    }
}
