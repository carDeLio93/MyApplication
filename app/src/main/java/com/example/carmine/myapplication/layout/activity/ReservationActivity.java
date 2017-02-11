package com.example.carmine.myapplication.layout.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.data.Prenotazione;
import com.example.carmine.myapplication.layout.core.data.singleton.Utente;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;

import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ReservationActivity extends AppCompatActivity {
    private Button reserve;
    private DatePicker date;
    private TimePicker time;
    private TextView posti;
    String regex = "[0-9]+";
    Calendar c = new GregorianCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        reserve = (Button) findViewById(R.id.reserve);

        date = (DatePicker) findViewById(R.id.datePicker);
        time = (TimePicker) findViewById(R.id.timePicker);
        posti = (TextView) findViewById(R.id.postiPrenotation);
        if (reserve != null)
            reserve.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.N)
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    int year = date.getYear();
                    int month = date.getMonth();
                    int day = date.getDayOfMonth();
                    int ore = time.getHour();
                    int minuti = time.getMinute();
                    if (posti.getText().toString() != "") {
                        if (posti.getText().toString().matches(regex)) {
                            if (controllaData()) {
                                Utente u = Utente.getInstance();
                                String numPosti = posti.getText().toString();
                                Prenotazione p = new Prenotazione(day, minuti, month, ore, year, u.getNome(), u.getCognome(), numPosti, u.getCf(), attivita.getInstance().getId());
                                p.Reserve(ReservationActivity.this);
                            } else {
                                Toast.makeText(ReservationActivity.this, "data non valida", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ReservationActivity.this, "posti inseriti non validi", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ReservationActivity.this, "inserisci tutti i dati", Toast.LENGTH_LONG).show();
                    }

                }
            });
    }

    public boolean controllaData() {

        return true;

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReservationActivity.this, PlaceActGoogle.class));
        finish();

    }
}
