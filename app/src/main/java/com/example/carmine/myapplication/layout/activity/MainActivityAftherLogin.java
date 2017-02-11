package com.example.carmine.myapplication.layout.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.APIclass.map;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.data.LocaliSeller;
import com.example.carmine.myapplication.layout.core.data.singleton.Utente;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.HashMap;

public class MainActivityAftherLogin extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = "PlacesAPIActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private com.example.carmine.myapplication.layout.core.APIclass.map map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_afther_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityAftherLogin.this, MainActivityAftherLogin.class));
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivityAftherLogin.this);
        map = new map(MainActivityAftherLogin.this);
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (exit) {
                {
                    startActivity(new Intent(MainActivityAftherLogin.this, LoginActivity.class));
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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_Ricerca_Per_Prodotto) {
            OpenRicercaPerProdottoActivity();
        } else if (id == R.id.nav_Riicerca_Nome) {
            openAutocompleteActivity();
        } else if (id == R.id.nav_Preferiti) {
            if (Utente.getInstance().getCf().equals("NON_AUTENTICATO")) {
                Toast.makeText(MainActivityAftherLogin.this, "effettua il login per accedere a questo contenuto", Toast.LENGTH_LONG).show();
            } else {
                startActivity(new Intent(MainActivityAftherLogin.this, activityLocaliPreferiti.class));

            }
        } else if (id == R.id.nav_leMiePrenotazioni) {
            if (Utente.getInstance().getCf().equals("NON_AUTENTICATO")) {
                Toast.makeText(MainActivityAftherLogin.this, "effettua il login per accedere a questo contenuto", Toast.LENGTH_LONG).show();
            } else
                startActivity(new Intent(MainActivityAftherLogin.this, le_miePrenotazioni.class));
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                map.update();
        }

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addMarkerPlace();
                }
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        map.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        map.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        map.onActivityResult(requestCode, resultCode, data, RESULT_OK);
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("loginAsHost Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    private void addMarkerPlace() throws SecurityException {
        map.addMarkerPlace();
    }

    private void openAutocompleteActivity() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            Log.d("place selected", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void OpenRicercaPerProdottoActivity() {
        Intent i = new Intent(MainActivityAftherLogin.this, RicercaProdotto.class);
        startActivity(i);
    }
}
