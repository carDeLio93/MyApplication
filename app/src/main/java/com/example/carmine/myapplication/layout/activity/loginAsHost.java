package com.example.carmine.myapplication.layout.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class loginAsHost extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private static final String LOG_TAG = "PlacesAPIActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;

    private Button ricercaLocale;
    private TextView mPlaceDetailsText;
    private TextView mPlaceAttribution;

    private LocationListener lL;
    private LocationManager lm;
    private MarkerOptions myMarker;
    private MarkerOptions markerRisultatoRicerca;
    private MarkerOptions myPosition;
    private ArrayList<MarkerOptions> interestPoint;
    private ArrayList<String> info;
    private GoogleMap mMap;
    private GoogleApiClient client;
    private GoogleApiClient mGoogleApiClient;
    private boolean autoPosizionamentoCamere = true;
    private LatLng posizionePlaceSelezionato;
    private boolean risultatoRicerca = true;
    private Place place;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain_activity_after_login_as_host);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lm = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mGoogleApiClient = new GoogleApiClient.Builder(loginAsHost.this)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .build();

        lL = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
                if (myPosition == null) {
                    myPosition = new MarkerOptions().position(position);
                    myMarker = new MarkerOptions().position(position).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    mMap.addMarker(myMarker);

                    if (mGoogleApiClient.isConnected()) {
                        if (ContextCompat.checkSelfPermission(loginAsHost.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(loginAsHost.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    PERMISSION_REQUEST_CODE);
                        } else {
                            callPlaceDetectionApi();
                        }
                    }

                } else {
                    myPosition.position(position);
                }
                if (autoPosizionamentoCamere) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
                    autoPosizionamentoCamere = false;
                }

            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
            return;
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm.requestLocationUpdates("gps", 5, 0, lL);
        }
        lm.requestLocationUpdates("gps", 5, 0, lL);

        ricercaLocale = (Button) findViewById(R.id.ricerca_locale);
        ricercaLocale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAutocompleteActivity();
            }
        });
        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
        mPlaceAttribution = (TextView) findViewById(R.id.place_attribution);


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
                lm.requestLocationUpdates("gps", 5, 0, lL);

        }

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPlaceDetectionApi();
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (risultatoRicerca) {
                    String id = marker.getId();
                    String[] q = new String[2];
                    q = id.split("m");
                    String[] dati = new String[2];
                    dati[0] = marker.getId();
                    dati[1] = info.get(Integer.parseInt(q[1]) - 1);
                    OpenPlacePageActivity(dati);
                } else {
                    String[] dati = new String[2];
                    dati[0] = place.getId();
                    dati[1] = (String) place.getName();
                    OpenPlacePageActivity(dati);
                    risultatoRicerca = true;
                }
                return false;
            }
        });
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

    private void callPlaceDetectionApi() {
        addMarkerPlace();
    }

    private void addMarkerPlace() throws SecurityException {
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                interestPoint = new ArrayList<MarkerOptions>();
                info = new ArrayList<String>();
                int i = 0;

                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    interestPoint.add(new MarkerOptions().position(placeLikelihood.getPlace().getLatLng()));
                    info.add(placeLikelihood.getPlace().getId() + " " + placeLikelihood.getPlace().getName());
                    mMap.addMarker(interestPoint.get(i));
                    i++;
                }
                likelyPlaces.release();
            }
        });
    }

    private void openAutocompleteActivity() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
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

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                place = PlaceAutocomplete.getPlace(this, data);
                posizionePlaceSelezionato = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posizionePlaceSelezionato, 16));
                markerRisultatoRicerca = new MarkerOptions().position(posizionePlaceSelezionato);
                mMap.addMarker(markerRisultatoRicerca);
                risultatoRicerca = false;


                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
                } else {
                    mPlaceAttribution.setText("");
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.d("Place Selected: ", "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }


    public void OpenPlacePageActivity(String[] dati) {
        Intent i = new Intent(this, PlaceActGoogle.class);
        i.putExtra("dati", dati);
        startActivity(i);
    }

}
