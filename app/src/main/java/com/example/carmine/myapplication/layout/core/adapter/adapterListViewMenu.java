package com.example.carmine.myapplication.layout.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.Menu;
import com.example.carmine.myapplication.layout.core.data.OffertaLocale;

import java.util.List;

public class adapterListViewMenu extends ArrayAdapter<Menu> {

    public adapterListViewMenu(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview, null);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageView3);

        TextView nome = (TextView) convertView.findViewById(R.id.textViewName);
        TextView descrizione = (TextView) convertView.findViewById(R.id.textViewNumber);
        Menu c = getItem(position);
        nome.setText(c.getNome());
        descrizione.setText(c.getDescrizione());

        return convertView;
    }

}