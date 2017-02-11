package com.example.carmine.myapplication.layout.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.ProdottiLocale;

import java.util.List;

public class adapterListViewProdottiDelLocale extends ArrayAdapter<ProdottiLocale> {

    public adapterListViewProdottiDelLocale(Context context, int textViewResourceId, List objects) {
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
            default:
                m.setImageResource(R.drawable.ristorante);

        }
        nome.setText("nome: " + c.getNome());
        marca.setText("marca: " + c.getMarca());
        tipologia.setText("tipologia: " + c.getTipologia());
        return convertView;
    }

}