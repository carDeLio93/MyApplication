package com.example.carmine.myapplication.layout.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.LocaliSeller;

import java.util.List;

public class adapterListViewLocaliVenditore extends ArrayAdapter<LocaliSeller> {

    public adapterListViewLocaliVenditore(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview, null);
        ImageView m = (ImageView) convertView.findViewById(R.id.imageView3);
        TextView nome = (TextView) convertView.findViewById(R.id.textViewName);
        TextView marca = (TextView) convertView.findViewById(R.id.textViewNumber);
        TextView tipologia = (TextView) convertView.findViewById(R.id.textViewNomeProdotto);
        LocaliSeller c = getItem(position);
        m.setImageResource(R.drawable.bar);
        nome.setText("nome: " + c.getNome());
        marca.setText("marca: " + c.getCitta());
        tipologia.setText("via: " + c.getVia());
        return convertView;
    }

}