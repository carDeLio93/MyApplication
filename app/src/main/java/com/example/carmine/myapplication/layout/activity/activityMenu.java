package com.example.carmine.myapplication.layout.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.Menu;

public class activityMenu extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        listView = (ListView) findViewById(R.id.listView);
        Menu menu = new Menu();
        menu.getListMenuOfLocalForActivityMenu(activityMenu.this, listView);
        TextView t = (TextView) findViewById(R.id.textViewListVew);
        t.setText("menu del locale");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(activityMenu.this, PlaceActGoogle.class));
        finish();
    }

    public void finishAct() {
        finish();
    }
}
