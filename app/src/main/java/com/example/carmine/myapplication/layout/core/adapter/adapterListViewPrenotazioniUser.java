package com.example.carmine.myapplication.layout.core.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.Prenotazione;

import java.util.List;

public class adapterListViewPrenotazioniUser extends ArrayAdapter<Prenotazione> {

    public adapterListViewPrenotazioniUser(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview, null);
        ImageView m = (ImageView) convertView.findViewById(R.id.imageView3);
        TextView data = (TextView) convertView.findViewById(R.id.textViewName);
        TextView User = (TextView) convertView.findViewById(R.id.textViewNumber);
        TextView posti = (TextView) convertView.findViewById(R.id.textViewNomeProdotto);
        TextView locale = (TextView) convertView.findViewById(R.id.textViewPrezzoProdotto);
        Prenotazione c = getItem(position);
        if (c.getStato().equals("INVIATA") || c.getStato().equals("VISUALIZZATA")) {
            m.setImageResource(R.drawable.prenotazioni);
        } else if (c.getStato().equals("ACCETTATA"))
            m.setImageResource(R.drawable.prenotazioni_accett);
        else if (c.getStato().equals("RIFIUTATA"))
            m.setImageResource(R.drawable.prenotazioni_rifiut);

        data.setText("data: " + c.getDay() + "/" + c.getMonth() + "/" + c.getYear() + " ora: " + c.getOre() + ":" + c.getMinuti());
        User.setText("sig: " + c.getNome());
        posti.setText("posti: " + c.getNumeroPosti());
        locale.setText("locale: " + c.getCf());

        return convertView;
    }

}