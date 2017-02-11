package com.example.carmine.myapplication.layout.seller.gestioneProdotti;
/**
 * Created by Gemod on 25/01/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

import java.util.HashMap;

import static com.example.carmine.myapplication.R.id.nome;

public class createProduct extends AppCompatActivity implements View.OnClickListener {

    //Defining views
    private EditText name;
    private EditText marca;
    private EditText prezzoProdotto;
    private Button buttonAdd;
    String selected;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
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
        name = (EditText) findViewById(nome);
        marca = (EditText) findViewById(R.id.marca);
        prezzoProdotto = (EditText) findViewById(R.id.prezzoProdotto);
        buttonAdd = (Button) findViewById(R.id.add);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);

    }


    //Adding an employee
    private void addEmployee() {

        final String name = this.name.getText().toString().trim();
        final String desg = marca.getText().toString().trim();
        final String prezzoProd = prezzoProdotto.getText().toString().trim();

        final String tipologia = selected;

        class AddEmployee extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(createProduct.this, "Adding...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_EMP_NAME, name);
                params.put(Config.KEY_EMP_DESG, desg);
                params.put(Config.KEY_EMP_SAL, tipologia);
                params.put(Config.KEY_IDPLACE, attivita.getInstance().getId());
                params.put(Config.KEY_PREZZO, prezzoProd);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.INSERT_PRODUCT, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonAdd) {
            if (selected == "TIPOLOGIA")
                Toast.makeText(getApplicationContext(), "seleziona una tipologia", Toast.LENGTH_LONG).show();
            else if (!name.getText().toString().equals("") && !marca.getText().toString().equals("") && !prezzoProdotto.getText().toString().equals("")) {
                confirmDialog();
            } else {
                Toast.makeText(getApplicationContext(), "inserire tutti i dati richiesti", Toast.LENGTH_LONG).show();
            }
        }


    }

    public void confirmDialog() {
        DialogCustom d = new DialogCustom(createProduct.this, "confermi l'aggiunta del nuovo prodotto", "si", "no");
        d.getButtonUno().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
                startActivity(new Intent(createProduct.this, createProduct.class));
                finish();

            }
        });
        d.getButtonDue().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(createProduct.this, createProduct.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(createProduct.this, GestioneProdotti.class));
        finish();
    }
}
