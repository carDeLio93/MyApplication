package com.example.carmine.myapplication.layout.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.Vendita;

import java.util.List;

public class adapterListViewRicercaProdotto extends ArrayAdapter<Vendita> {

    public adapterListViewRicercaProdotto(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview, null);
        TextView nomeAttivita = (TextView) convertView.findViewById(R.id.textViewName);
        TextView nomeProdotto = (TextView) convertView.findViewById(R.id.textViewNumber);
        TextView PrezzoProdotto = (TextView) convertView.findViewById(R.id.textViewNomeProdotto);
        ImageView m = (ImageView) convertView.findViewById(R.id.imageView3);


        Vendita c = getItem(position);
        switch (c.getTipoProdotto()) {
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
        nomeAttivita.setText("nome attivita: " + c.getNomeAttivita());
        nomeProdotto.setText("nome prodotto: " + c.getNomeProdotto());
        PrezzoProdotto.setText("prezzo: " + c.getPrezzoProdotto() + "€");
        return convertView;
    }

}