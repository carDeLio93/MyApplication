package com.example.carmine.myapplication.layout.core.data;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.DialogCustom;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewMenu;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;
import com.example.carmine.myapplication.layout.seller.gestioneMenu.UpdateMenu;
import com.example.carmine.myapplication.layout.seller.gestioneMenu.prodottiDiUnMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carmine on 24/01/2017.
 */

public class LocaliSeller {
    private String nome, citta, via, id;

    public LocaliSeller(String id, String nome, String citta, String via) {
        this.id = id;
        this.nome = nome;
        this.citta = citta;
        this.via = via;
    }

    public String getNome() {
        return nome;
    }

    public String getCitta() {
        return citta;
    }

    public String getVia() {
        return via;
    }

    public String getId() {
        return id;
    }
}
