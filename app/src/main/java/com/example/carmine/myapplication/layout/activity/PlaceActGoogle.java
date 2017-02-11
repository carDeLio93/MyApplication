package com.example.carmine.myapplication.layout.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.APIclass.AttributedPhoto;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.data.LocaliSeller;
import com.example.carmine.myapplication.layout.core.data.OffertaLocale;
import com.example.carmine.myapplication.layout.core.APIclass.PhotoTask;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.data.singleton.Utente;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewOfferte;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PlaceActGoogle extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private ImageView image;

    private ListView listaOfferte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        listaOfferte = (ListView) findViewById(R.id.offerteLocale);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        class ricercaOfferteLocale extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PlaceActGoogle.this, "Adding...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                String[] split = s.split("#");
                String[] titoloOff = new String[split.length / 2];
                String[] DescrizioneOff = new String[split.length / 2];
                int j = 0;
                List lista = new LinkedList();

                for (int i = 0; i < split.length / 2; i++) {
                    titoloOff[i] = split[j];
                    j++;
                    DescrizioneOff[i] = split[j];
                    j++;
                    lista.add(new OffertaLocale(titoloOff[i], DescrizioneOff[i]));

                }
                adapterListViewOfferte a = new adapterListViewOfferte(PlaceActGoogle.this, R.layout.listview, lista);
                listaOfferte.setAdapter(a);

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_ID_PLACE, attivita.getInstance().getId());
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.RICERCA_OFFERTE_LOCALE, params);
                return res;
            }
        }
        ricercaOfferteLocale r = new ricercaOfferteLocale();
        r.execute();

        //iniz immagine negozio
        GoogleApiClient client = new GoogleApiClient.Builder(PlaceActGoogle.this)
                .addApi(Places.PLACE_DETECTION_API).addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .build();
        PhotoTask p = (PhotoTask) new PhotoTask(700, 700, client) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(AttributedPhoto attributedPhoto) {
                if (attributedPhoto != null) {
                    image = (ImageView) findViewById(R.id.imageView2);
                    image.setImageBitmap(attributedPhoto.bitmap);

                }
            }
        }.execute(attivita.getInstance().getId());

        //bottone preferiti
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utente.getInstance().getCf().equals("NON_AUTENTICATO")) {
                    Toast.makeText(PlaceActGoogle.this, "effettua il login per accedere a questo contenuto", Toast.LENGTH_LONG).show();
                } else {
                    class aggiungiAiPreferiti extends AsyncTask<Void, Void, String> {
                        ProgressDialog loading;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();

                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            Toast.makeText(PlaceActGoogle.this, "il locale è stato aggiunto ai preferiti", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        protected String doInBackground(Void... v) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("id", attivita.getInstance().getId());
                            params.put("cf", Utente.getInstance().getCf());
                            RequestHandler rh = new RequestHandler();
                            String res = rh.sendPostRequest(Config.URL_AGGIUNGI_AI_PREFERITI, params);
                            return res;
                        }
                    }
                    aggiungiAiPreferiti a = new aggiungiAiPreferiti();
                    a.execute();
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_menu) {
            startActivity(new Intent(PlaceActGoogle.this, activityMenu.class).putExtra("idPlace", attivita.getInstance().getId()));
            finish();
        } else if (id == R.id.nav_prodotti) {

            openProductActivity();

        } else if (id == R.id.nav_prenotazioni) {
            if (Utente.getInstance().getCf().equals("NON_AUTENTICATO")) {
                Toast.makeText(PlaceActGoogle.this, "effettua il login per accedere a questo contenuto", Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(PlaceActGoogle.this, ReservationActivity.class);
                startActivity(i);
                finish();

            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }

    public void openProductActivity() {
        Intent i = new Intent(PlaceActGoogle.this, Product_of_place.class);
        startActivity(i);
        finish();

    }

    public void openActivityLocaliPreferiti() {
        startActivity(new Intent(PlaceActGoogle.this, activityLocaliPreferiti.class));
        finish();

    }


}
