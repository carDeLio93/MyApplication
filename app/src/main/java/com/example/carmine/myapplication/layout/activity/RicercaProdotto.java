package com.example.carmine.myapplication.layout.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.data.Vendita;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewRicercaProdotto;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;

import java.util.ArrayList;
import java.util.HashMap;

public class RicercaProdotto extends AppCompatActivity {
    public EditText ricerca;
    private Button cerca;
    private ListView listaProdotti;
    private String idA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricrca_per_prodottp);
        ricerca = (EditText) findViewById(R.id.ricercaP);
        cerca = (Button) findViewById(R.id.cercaP);
        listaProdotti = (ListView) findViewById(R.id.prodottiRicercati);
        cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nomeP = ricerca.getText().toString();
                class ricercaP extends AsyncTask<Void, Void, String> {
                    ProgressDialog loading;
                    public String status;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(RicercaProdotto.this, "Adding...", "Wait...", false, false);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        String[] split = s.split("#");
                        idA = split[0];
                        String[] idAtt = new String[split.length / 5];
                        String[] nomeAtt = new String[split.length / 5];
                        String[] nomeProd = new String[split.length / 5];
                        String[] PrezzoProd = new String[split.length / 5];
                        String[] tipologia = new String[split.length / 5];

                        int j = 0;
                        final ArrayList<Vendita> lista = new ArrayList<Vendita>();

                        for (int i = 0; i < split.length / 5; i++) {
                            idAtt[i] = split[j];
                            j++;
                            nomeAtt[i] = split[j];
                            j++;
                            nomeProd[i] = split[j];
                            j++;
                            PrezzoProd[i] = split[j];
                            j++;
                            tipologia[i] = split[j];
                            j++;
                            lista.add(new Vendita(idAtt[i], nomeAtt[i], nomeProd[i], PrezzoProd[i], tipologia[i]));
                        }
                        adapterListViewRicercaProdotto a = new adapterListViewRicercaProdotto(RicercaProdotto.this, R.layout.listview, lista);
                        listaProdotti.setAdapter(a);
                        listaProdotti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent i = new Intent(RicercaProdotto.this, PlaceActGoogle.class);
                                attivita a = attivita.getInstance();
                                a.setNome(lista.get(position).getNomeAttivita());
                                a.setId(lista.get(position).getIdAttivita());
                                startActivity(i);
                                finish();

                            }
                        });
                    }

                    @Override
                    protected String doInBackground(Void... v) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put(Config.NAME_PRODUCT, '%' + nomeP + '%');
                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(Config.RICERCA_PER_NOME_PRODOTTO, params);
                        return res;
                    }
                }
                ricercaP p = new ricercaP();
                p.execute();

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RicercaProdotto.this, MainActivityAftherLogin.class));
        finish();

    }
}
