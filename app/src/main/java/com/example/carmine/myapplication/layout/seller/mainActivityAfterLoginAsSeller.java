package com.example.carmine.myapplication.layout.seller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.activity.LoginActivity;
import com.example.carmine.myapplication.layout.activity.MainActivityAftherLogin;
import com.example.carmine.myapplication.layout.core.APIclass.AttributedPhoto;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.data.OffertaLocale;
import com.example.carmine.myapplication.layout.core.APIclass.PhotoTask;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewOfferte;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;
import com.example.carmine.myapplication.layout.seller.Prenotazioni.le_prenotazioni_del_mio_locale;
import com.example.carmine.myapplication.layout.seller.gestioneMenu.GestioneMenu;
import com.example.carmine.myapplication.layout.seller.gestioneProdotti.GestioneProdotti;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class mainActivityAfterLoginAsSeller extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private ImageView image;
    private ListView listaOfferte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_after_login_as_seller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listaOfferte = (ListView) findViewById(R.id.offerteLocale);

        class ricercaOfferteLocale extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(mainActivityAfterLoginAsSeller.this, "Adding...", "Wait...", false, false);
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
                adapterListViewOfferte a = new adapterListViewOfferte(mainActivityAfterLoginAsSeller.this, R.layout.listview, lista);
                listaOfferte.setAdapter(a);
                listaOfferte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
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


        GoogleApiClient client = new GoogleApiClient.Builder(mainActivityAfterLoginAsSeller.this)
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
                    // dati=(TextView)  findViewById(R.id.dati);
                    //dati.setText(idAttivita);
                }
            }

        }.execute(attivita.getInstance().getId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.gestioneProdotti) {
            Intent i = new Intent(mainActivityAfterLoginAsSeller.this, GestioneProdotti.class);
            startActivity(i);
            finish();
        } else if (id == R.id.gestioneMenu) {
            Intent i = new Intent(mainActivityAfterLoginAsSeller.this, GestioneMenu.class);
            startActivity(i);
            finish();
        } else if (id == R.id.prenotazioniPerIlMioLocale) {

            startActivity(new Intent(mainActivityAfterLoginAsSeller.this, le_prenotazioni_del_mio_locale.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startActivity(new Intent(mainActivityAfterLoginAsSeller.this, SelectPlaceOfSeller.class));
            finish();
        }
    }
}
