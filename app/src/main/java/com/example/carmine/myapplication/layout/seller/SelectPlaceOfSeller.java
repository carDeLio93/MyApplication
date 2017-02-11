package com.example.carmine.myapplication.layout.seller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.activity.LoginActivity;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.data.LocaliSeller;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.data.singleton.Seller;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewLocaliVenditore;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectPlaceOfSeller extends AppCompatActivity {
    private ListView lista;
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place_of_seller);
        t = (TextView) findViewById(R.id.textViewListVew);
        t.setText("Scegli il tuo locale...");
        lista = (ListView) findViewById(R.id.listView);

        class ricercaIMieiLocali extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SelectPlaceOfSeller.this, "Adding...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

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

                adapterListViewLocaliVenditore a = new adapterListViewLocaliVenditore(SelectPlaceOfSeller.this, R.layout.listview, list);
                lista.setAdapter(a);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        list.get(position).getId();
                        attivita a = attivita.getInstance();
                        a.setId(list.get(position).getId());
                        a.setNome(list.get(position).getNome());
                        a.setCitta(list.get(position).getCitta());
                        a.setVia(list.get(position).getVia());
                        startActivity(new Intent(SelectPlaceOfSeller.this, mainActivityAfterLoginAsSeller.class));
                        finish();
                    }
                });


            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                Seller s = Seller.getInstance();
                params.put(Config.KEY_CF, s.getCf());
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_LOCALI_DI_UN_SELLER, params);
                return res;

            }
        }
        ricercaIMieiLocali r = new ricercaIMieiLocali();
        r.execute();
    }

    private boolean exit = false;

    @Override
    public void onBackPressed() {

        if (exit) {
            {
                startActivity(new Intent(SelectPlaceOfSeller.this, LoginActivity.class));
                finish();
            }

        } else {
            Toast.makeText(this, "ripremere il tasto back per effettuare il logout",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);


        }
    }
}

