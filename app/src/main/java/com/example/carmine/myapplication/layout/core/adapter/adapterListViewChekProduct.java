package com.example.carmine.myapplication.layout.core.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.ProdottiLocale;

import java.util.ArrayList;
import java.util.List;

public class adapterListViewChekProduct extends ArrayAdapter<ProdottiLocale> {
    CheckBox checkBox;

    public adapterListViewChekProduct(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listviewcheck, null);
        TextView nome = (TextView) convertView.findViewById(R.id.textViewName);
        TextView marca = (TextView) convertView.findViewById(R.id.textViewNumber);
        TextView tipologia = (TextView) convertView.findViewById(R.id.textViewNomeProdotto);
        TextView prezzo = (TextView) convertView.findViewById(R.id.textViewPrezzoProdotto);
        ImageView m = (ImageView) convertView.findViewById(R.id.imageView3);


        checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxListView);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProdottiLocale c = getItem(position);
                c.setSelected(!c.getSelected());
            }
        });
        ProdottiLocale c = getItem(position);
        switch (c.getTipologia()) {
            case "BIRRA":
                m.setImageResource(R.drawable.birra);
                break;
            case "BIBITA":
                m.setImageResource(R.drawable.bibita);

                break;
            case "PIZZA":
                m.setImageResource(R.drawable.pizzeria);

                break;
            case "PANINO":
                m.setImageResource(R.drawable.panino);
                break;
            default: {
                m.setImageResource(R.drawable.ristorante);
            }

        }
        nome.setText("nome: " + c.getNome());
        marca.setText("marca: " + c.getMarca());
        tipologia.setText("tipologia: " + c.getTipologia());
        if (c.getPrezzo() != null)
            prezzo.setText("prezzo: " + c.getPrezzo());
        return convertView;
    }

}