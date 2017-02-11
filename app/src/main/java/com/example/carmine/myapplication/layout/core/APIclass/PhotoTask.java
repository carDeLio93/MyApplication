package com.example.carmine.myapplication.layout.core.APIclass;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;

public abstract class PhotoTask extends AsyncTask<String, Void, AttributedPhoto> {

    private int mHeight;

    private int mWidth;
    GoogleApiClient client1;

    public PhotoTask(int width, int height, GoogleApiClient client) {
        mHeight = height;
        mWidth = width;
        client1 = client;
    }

    @Override
    protected AttributedPhoto doInBackground(String... params) {

        final String placeId = params[0];
        Log.d("fgdssdfasdfasdfasdfasdfad", params[0]);
        AttributedPhoto attributedPhoto = null;
        PlacePhotoMetadataResult result = Places.GeoDataApi.getPlacePhotos(client1, placeId).await();
        if (result.getStatus().isSuccess()) {

            PlacePhotoMetadataBuffer photoMetadataBuffer = result.getPhotoMetadata();
            if (photoMetadataBuffer.getCount() > 0 && !isCancelled()) {
                PlacePhotoMetadata photo = photoMetadataBuffer.get(0);
                CharSequence attribution = photo.getAttributions();
                Bitmap image = photo.getScaledPhoto(client1, mWidth, mHeight).await().getBitmap();
                attributedPhoto = new AttributedPhoto(attribution, image);
            }
            photoMetadataBuffer.release();
        }
        return attributedPhoto;
    }
}