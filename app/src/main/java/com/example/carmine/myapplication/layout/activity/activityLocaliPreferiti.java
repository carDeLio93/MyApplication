package com.example.carmine.myapplication.layout.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.data.LocaliSeller;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.data.singleton.Utente;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewLocaliVenditore;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class activityLocaliPreferiti extends AppCompatActivity {
    private ListView lista;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place_of_seller);
        lista = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textViewListVew);
        textView.setText("i miei locali preferiti...");
        class ricercaIMieiLocali extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {

                super.onPostExecute(s);

                JSONObject jsonObject = null;
                final ArrayList<LocaliSeller> list = new ArrayList<LocaliSeller>();
                try {
                    jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String id = jo.getString("id");
                        String nome = jo.getString("nome");
                        String citta = jo.getString("citta");
                        String via = jo.getString("via");
                        LocaliSeller locale = new LocaliSeller(id, nome, citta, via);
                        list.add(locale);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapterListViewLocaliVenditore a = new adapterListViewLocaliVenditore(activityLocaliPreferiti.this, R.layout.listview, list);
                lista.setAdapter(a);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(activityLocaliPreferiti.this, PlaceActGoogle.class).putExtra("dati", list.get(position).getId()));
                        attivita a = attivita.getInstance();
                        a.setNome(list.get(position).getNome());
                        a.setId(list.get(position).getId());
                        a.setCitta(list.get(position).getCitta());
                        a.setVia(list.get(position).getVia());
                        finish();

                    }
                });


            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("cf", Utente.getInstance().getCf());
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_GET_LOCALI_PREFERITI, params);
                return res;

            }
        }
        ricercaIMieiLocali r = new ricercaIMieiLocali();
        r.execute();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(activityLocaliPreferiti.this, MainActivityAftherLogin.class));
        finish();

    }
}
