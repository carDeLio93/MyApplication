package com.example.carmine.myapplication.layout.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.OffertaLocale;

import java.util.List;

public class adapterListViewOfferte extends ArrayAdapter<OffertaLocale> {


    public adapterListViewOfferte(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview, null);
        TextView nome = (TextView) convertView.findViewById(R.id.textViewName);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView3);
        imageView.setImageResource(R.drawable.offerta);
        TextView numero = (TextView) convertView.findViewById(R.id.textViewNumber);
        OffertaLocale c = getItem(position);
        nome.setText(c.getTitoloOfferta());
        numero.setText(c.getDescrizioneOfferta());
        return convertView;
    }

}