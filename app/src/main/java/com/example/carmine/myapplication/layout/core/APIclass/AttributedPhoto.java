package com.example.carmine.myapplication.layout.core.APIclass;

import android.graphics.Bitmap;

public class AttributedPhoto {

    public final CharSequence attribution;

    public final Bitmap bitmap;

    public AttributedPhoto(CharSequence attribution, Bitmap bitmap) {
        this.attribution = attribution;
        this.bitmap = bitmap;
    }
}