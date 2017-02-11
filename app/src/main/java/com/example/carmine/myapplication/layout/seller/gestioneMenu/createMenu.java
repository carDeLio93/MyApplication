package com.example.carmine.myapplication.layout.seller.gestioneMenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;

import java.util.HashMap;

/**
 * Created by Gemod on 18/01/2017.
 */

public class createMenu extends AppCompatActivity {
    private EditText descrizione, titolo;
    private Button button1, button2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);
        descrizione = (EditText) findViewById(R.id.DescrizioneMenu);
        titolo = (EditText) findViewById(R.id.titoloMenu);
        button1 = (Button) findViewById(R.id.creaMenu);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titolo.getText().toString().equals("") || descrizione.getText().toString().equals("")) {
                    Toast.makeText(createMenu.this, "inserire tutti i dati richiesti", Toast.LENGTH_LONG).show();
                } else {

                    final String tit = titolo.getText().toString();
                    final String des = descrizione.getText().toString();
                    class add_menu extends AsyncTask<Void, Void, String> {
                        ProgressDialog loading;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            loading = ProgressDialog.show(createMenu.this, "Adding...", "Wait...", false, false);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            loading.dismiss();
                            startActivity(new Intent(createMenu.this, aggiungiProdottiAlMenu.class).putExtra("idMenuInserito", s));
                            finish();
                        }

                        @Override
                        protected String doInBackground(Void... v) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put(Config.KEY_EMP_NOME, tit);
                            params.put(Config.KEY_DESCRIZIONE, des);
                            params.put(Config.KEY_IDPLACE, attivita.getInstance().getId());
                            RequestHandler rh = new RequestHandler();
                            String res = rh.sendPostRequest(Config.URL_ADD_PREFER_PRODUCT, params);
                            return res;
                        }
                    }

                    add_menu a = new add_menu();
                    a.execute();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(createMenu.this, GestioneMenu.class));
        finish();
    }
}
