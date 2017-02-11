package com.example.carmine.myapplication.layout.seller.gestioneProdotti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.DialogCustom;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;
import com.example.carmine.myapplication.layout.seller.gestioneMenu.GestioneMenu;

import java.util.HashMap;

import static com.example.carmine.myapplication.R.id.nome;

public class UpdateProduct extends AppCompatActivity {
    private EditText name;
    private EditText marc;
    private EditText prezzoProdotto;
    private Button modifica;
    ArrayAdapter<String> adapter;

    private Spinner spinner;

    private String idProd, nomeProd, marca;
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        Bundle datipassati = getIntent().getExtras();
        idProd = (String) datipassati.get("idProd");
        nomeProd = (String) datipassati.get("nomeProd");
        marca = (String) datipassati.get("marca");

        Log.d("dati passati", idProd + " " + nomeProd + " " + marca + " " + attivita.getInstance().getId());
        Spinner spinner = (Spinner) findViewById(R.id.spinnerUP);
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"TIPOLOGIA", "BIBITA", "BIRRA", "PANINO", "PIZZA"}
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {
                selected = (String) adapter.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        //Initializing views
        name = (EditText) findViewById(R.id.nomeUP);
        name.setText(nomeProd);
        marc = (EditText) findViewById(R.id.marcaUP);
        marc.setText(marca);
        prezzoProdotto = (EditText) findViewById(R.id.prezzoProdottoUP);
        modifica = (Button) findViewById(R.id.modifica);

        //Setting listeners to button
        modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().equals("") && !marc.getText().toString().equals("") && !prezzoProdotto.getText().toString().equals("")) {
                    confirmDialog();
                } else {
                    Toast.makeText(getApplicationContext(), "inserire tutti i dati richiesti", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void confirmDialog() {
        DialogCustom d = new DialogCustom(UpdateProduct.this, "coonfermi la modifica", "si", "no");
        d.getButtonUno().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
                startActivity(new Intent(UpdateProduct.this, GestioneProdotti.class));
                finish();
            }
        });

    }

    private void updateProduct() {
        final String na = name.getText().toString().trim();
        final String desg = marc.getText().toString().trim();
        final String prezzoProd = prezzoProdotto.getText().toString().trim();
        final String tipologia = selected;

        class UpdateProd extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateProduct.this, "Adding...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_EMP_NAME, na);
                params.put(Config.KEY_EMP_DESG, desg);
                params.put(Config.KEY_EMP_SAL, tipologia);
                params.put(Config.KEY_IDPLACE, attivita.getInstance().getId());
                params.put(Config.KEY_PREZZO, prezzoProd);
                params.put("idProd", idProd);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.UPDATE_PRODUCT, params);
                return res;
            }
        }

        UpdateProd ae = new UpdateProd();
        ae.execute();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdateProduct.this, GestioneProdotti.class));
        finish();
    }
}
