package com.example.carmine.myapplication.layout.core.APIclass;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.activity.MainActivityAftherLogin;
import com.example.carmine.myapplication.layout.activity.PlaceActGoogle;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;
import com.google.android.gms.appindexing.AppIndex;
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

/**
 * Created by carmine on 12/01/2017.
 */

public class map implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private static final String LOG_TAG = "PlacesAPIActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private TextView mPlaceAttribution;
    private static final int PERMISSION_REQUEST_CODE = 100;

    private LocationListener lL;
    private LocationManager lm;
    private MarkerOptions myMarker;
    private MarkerOptions myPosition;
    private GoogleApiClient mGoogleApiClient;
    private boolean autoPosizionamentoCamere = true;
    private GoogleMap mMap;
    private ArrayList<MarkerOptions> interestPoint;
    private ArrayList<String> info;
    private LatLng posizionePlaceSelezionato;
    private boolean risultatoRicerca = true;
    private Place place;
    private MarkerOptions markerRisultatoRicerca;
    private MainActivityAftherLogin mainActivityAftherLogin;


    public map(MainActivityAftherLogin MainActivityAftherLogin) {
        this.mainActivityAftherLogin = MainActivityAftherLogin;
        lm = (LocationManager) MainActivityAftherLogin.getSystemService(MainActivityAftherLogin.getApplicationContext().LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(mainActivityAftherLogin, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mainActivityAftherLogin, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(mainActivityAftherLogin, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1337);
        }


        lL = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

                if (myPosition == null && mMap != null) {
                    myPosition = new MarkerOptions().position(position);
                    myMarker = new MarkerOptions().position(position).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    mMap.addMarker(myMarker);

                    if (mGoogleApiClient.isConnected()) {
                        if (ContextCompat.checkSelfPermission(mainActivityAftherLogin,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(mainActivityAftherLogin,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    PERMISSION_REQUEST_CODE);
                        } else {
                            addMarkerPlace();
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
        lm.requestLocationUpdates("gps", 5, 0, lL);
        mGoogleApiClient = new GoogleApiClient.Builder(MainActivityAftherLogin).addApi(Places.PLACE_DETECTION_API).addApi(AppIndex.API).addApi(Places.GEO_DATA_API).enableAutoManage(MainActivityAftherLogin, GOOGLE_API_CLIENT_ID, this).build();
        SupportMapFragment mapFragment = (SupportMapFragment) MainActivityAftherLogin.getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mPlaceAttribution = (TextView) MainActivityAftherLogin.findViewById(R.id.place_attribution);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (risultatoRicerca) {

                    //entra qui quando selezioni un lovale intorno a te
                    String[] q = new String[2];
                    q = marker.getId().split("m");
                    String dati = info.get(Integer.parseInt(q[1]) - 1);
                    String[] q1 = new String[2];
                    q1 = dati.split(" ");
                    OpenPlacePageActivity(q1[0]);
                } else {
                    //entra qui ogni volta che cerchi un locale per nome e lo selezioni
                    OpenPlacePageActivity(place.getId());
                    risultatoRicerca = true;
                }
                return false;
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());
    }

    public void addMarkerPlace() throws SecurityException {
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

    public void openAutocompleteActivity() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(mainActivityAftherLogin);
            mainActivityAftherLogin.startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(mainActivityAftherLogin, e.getConnectionStatusCode(),
                    0).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            Log.d("place selected", message);
        }
    }

    private void OpenPlacePageActivity(String dati) {
        Intent i = new Intent(mainActivityAftherLogin, PlaceActGoogle.class);
        attivita a = attivita.getInstance();
        a.setId(dati);
        mainActivityAftherLogin.startActivity(i);
    }

    public void onStart() {
        mGoogleApiClient.connect();
        AppIndex.AppIndexApi.start(mGoogleApiClient, mainActivityAftherLogin.getIndexApiAction());

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, int RESULT_OK) {
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                place = PlaceAutocomplete.getPlace(mainActivityAftherLogin, data);
                posizionePlaceSelezionato = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posizionePlaceSelezionato, 16));
                markerRisultatoRicerca = new MarkerOptions().position(posizionePlaceSelezionato);
                mMap.addMarker(markerRisultatoRicerca);
                if (info == null)
                    info = new ArrayList<String>();
                info.add(place.getId() + " " + place.getName());
                risultatoRicerca = false;
                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(mainActivityAftherLogin, data);
                Log.d("Place Selected: ", "Error: Status = " + status.toString());
            }
        }
    }

    public void onStop() {
        AppIndex.AppIndexApi.end(mGoogleApiClient, mainActivityAftherLogin.getIndexApiAction());
        mGoogleApiClient.disconnect();

    }

    public void update() {
        if (ActivityCompat.checkSelfPermission(mainActivityAftherLogin, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainActivityAftherLogin, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates("gps", 5, 0, lL);

    }

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }
}
