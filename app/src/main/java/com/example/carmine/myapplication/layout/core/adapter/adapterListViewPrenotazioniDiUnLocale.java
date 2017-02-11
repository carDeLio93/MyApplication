package com.example.carmine.myapplication.layout.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.Prenotazione;
import com.example.carmine.myapplication.layout.core.data.ProdottiLocale;

import java.util.List;

public class adapterListViewPrenotazioniDiUnLocale extends ArrayAdapter<Prenotazione> {
    CheckBox checkBox;

    public adapterListViewPrenotazioniDiUnLocale(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listviewcheck, null);
        ImageView m = (ImageView) convertView.findViewById(R.id.imageView3);
        TextView data = (TextView) convertView.findViewById(R.id.textViewName);
        TextView User = (TextView) convertView.findViewById(R.id.textViewNumber);
        TextView posti = (TextView) convertView.findViewById(R.id.textViewNomeProdotto);
        TextView stato = (TextView) convertView.findViewById(R.id.textViewPrezzoProdotto);
        checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxListView);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prenotazione p = getItem(position);
                p.setSelected(!p.getSelected());
            }
        });
        Prenotazione c = getItem(position);
        m.setImageResource(R.drawable.prenotazioni);
        data.setText("data: " + c.getDay() + "/" + c.getMonth() + "/" + c.getYear() + " ora: " + c.getOre() + ":" + c.getMinuti());
        User.setText("sig: " + c.getNome());
        posti.setText("posti: " + c.getNumeroPosti());
        stato.setText("stato: " + c.getStato());

        return convertView;
    }

}