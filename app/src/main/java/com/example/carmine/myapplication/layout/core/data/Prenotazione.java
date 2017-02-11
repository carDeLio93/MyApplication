package com.example.carmine.myapplication.layout.core.data;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.activity.ReservationActivity;
import com.example.carmine.myapplication.layout.activity.le_miePrenotazioni;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.DialogCustom;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewPrenotazioniDiUnLocale;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewPrenotazioniUser;
import com.example.carmine.myapplication.layout.core.data.singleton.Utente;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;
import com.example.carmine.myapplication.layout.seller.Prenotazioni.le_prenotazioni_del_mio_locale;
import com.example.carmine.myapplication.layout.seller.gestioneProdotti.GestioneProdotti;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carmine on 22/01/2017.
 */

public class Prenotazione {
    private String nome, cognome, cf, idAttivita;
    private String numeroPosti;
    private int year;
    private int month;

    public String getIdPrenotazione() {
        return idPrenotazione;
    }

    public void setIdPrenotazione(String idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
    }

    private String idPrenotazione;

    private int day;
    private int ore;
    private String stato;
    private ListView listView;
    private Boolean selected = false;

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getStato() {
        return stato;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public void setIdAttivita(String idAttivita) {
        this.idAttivita = idAttivita;
    }

    public ListView getListView() {
        return listView;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setNumeroPosti(String numeroPosti) {
        this.numeroPosti = numeroPosti;
    }

    private int minuti;

    public Prenotazione() {
    }

    ;

    public Prenotazione(int Day, int minuti, int month, int ore, int year, String nome, String cognome, String numeroPosti, String cf, String idAttivita) {
        this.idAttivita = idAttivita;
        this.setDay(day);
        this.setMinuti(minuti);
        this.setMonth(month);
        this.setOre(ore);
        this.setYear(year);
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.numeroPosti = numeroPosti;
        this.day = Day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    public void setMinuti(int minuti) {
        this.minuti = minuti;
    }

    public void Reserve(final Context context) {
        class Reservation extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Loading...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();

                params.put(Config.KEY_EMP_NAME, nome);
                params.put(Config.KEY_LASTNAME, cognome);
                params.put(Config.KEY_CF, cf);
                params.put(Config.KEY_IDPLACE, idAttivita);
                params.put(Config.KEY_NUMERO, numeroPosti);
                params.put(Config.KEY_ANNO, Integer.toString(year));
                params.put(Config.KEY_MESE, Integer.toString(month));
                params.put(Config.KEY_GIORNO, Integer.toString(day));
                params.put(Config.KEY_ORA, Integer.toString(ore));
                params.put(Config.KEY_MINUTI, Integer.toString(minuti));

                RequestHandler rh = new RequestHandler();

                String res = rh.sendPostRequest(Config.RESERVE, params);
                return res;
            }


        }
        Reservation reservation = new Reservation();
        reservation.execute();
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCf() {
        return cf;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getIdAttivita() {
        return idAttivita;
    }

    public String getNumeroPosti() {
        return numeroPosti;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getOre() {
        return ore;
    }

    public int getMinuti() {
        return minuti;
    }

    public void getReservatioOfLocal(final Context context, final ListView l) {
        this.listView = l;
        class ricercaPrenotazioniDelLocale extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Adding...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                JSONObject jsonObject = null;
                final ArrayList<Prenotazione> list = new ArrayList<Prenotazione>();
                try {
                    jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String cfUser = jo.getString("cfUser");
                        int ora = Integer.parseInt(jo.getString("ora"));
                        int giorno = Integer.parseInt(jo.getString("giorno"));
                        int anno = Integer.parseInt(jo.getString("anno"));
                        int minuti = Integer.parseInt(jo.getString("minuti"));
                        int mese = Integer.parseInt(jo.getString("mese"));
                        String nomeUtente = jo.getString("nomeUtente");
                        String cognomeUtente = jo.getString("cognomeUtente");
                        String numeroPosti = jo.getString("numeroPosti");
                        String statoPrenotazione = jo.getString("statoPrenotazione");
                        String idPrenotazione = jo.getString("idPrenotazione");

                        Prenotazione pren = new Prenotazione();
                        pren.setCf(cfUser);
                        pren.setOre(ora);
                        pren.setDay(giorno);
                        pren.setYear(anno);
                        pren.setMinuti(minuti);
                        pren.setMonth(mese);
                        pren.setNome(nomeUtente);
                        pren.setCognome(cognomeUtente);
                        pren.setNumeroPosti(numeroPosti);
                        pren.setStato(statoPrenotazione);
                        pren.setIdPrenotazione(idPrenotazione);
                        list.add(pren);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterListViewPrenotazioniDiUnLocale a = new adapterListViewPrenotazioniDiUnLocale(context, R.layout.listview, list);
                l.setAdapter(a);
                l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    }
                });
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_ID_PLACE, attivita.getInstance().getId());
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.PRENOTAZIONI_DI_UN_LOCALE, params);
                return res;

            }
        }
        ricercaPrenotazioniDelLocale r = new ricercaPrenotazioniDelLocale();
        r.execute();
    }

    public void getMyReservation(final Context context, final ListView l) {
        class ricercaPrenotazioniEffettuateDallUtente extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Adding...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                JSONObject jsonObject = null;
                final ArrayList<Prenotazione> list = new ArrayList<Prenotazione>();
                try {
                    jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String cfUser = jo.getString("cfUser");
                        int ora = Integer.parseInt(jo.getString("ora"));
                        int giorno = Integer.parseInt(jo.getString("giorno"));
                        int anno = Integer.parseInt(jo.getString("anno"));
                        int minuti = Integer.parseInt(jo.getString("minuti"));
                        int mese = Integer.parseInt(jo.getString("mese"));
                        String nomeUtente = jo.getString("nomeUtente");
                        String cognomeUtente = jo.getString("cognomeUtente");
                        String numeroPosti = jo.getString("numeroPosti");
                        String statoPrenotazione = jo.getString("statoPrenotazione");
                        String idPrenotazione = jo.getString("idPrenotazione");

                        Prenotazione pren = new Prenotazione();
                        pren.setCf(cfUser);
                        pren.setOre(ora);
                        pren.setDay(giorno);
                        pren.setYear(anno);
                        pren.setMinuti(minuti);
                        pren.setMonth(mese);
                        pren.setNome(nomeUtente);
                        pren.setCognome(cognomeUtente);
                        pren.setNumeroPosti(numeroPosti);
                        pren.setStato(statoPrenotazione);
                        pren.setIdPrenotazione(idPrenotazione);
                        list.add(pren);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterListViewPrenotazioniUser a = new adapterListViewPrenotazioniUser(context, R.layout.listview, list);
                l.setAdapter(a);
                l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        if (list.get(position).getStato().equals("RIFIUTATA")) {
                            DialogCustom d = new DialogCustom(context, "la prenotazione è stata rifiutata come si desidera procedere?", "riprovare", "annullare");
                            d.getButtonUno().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    context.startActivity(new Intent(context, ReservationActivity.class).putExtra("idPlace", list.get(position).getIdAttivita()));

                                }
                            });
                            d.getButtonDue().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    context.startActivity(new Intent(context, le_miePrenotazioni.class));
                                }
                            });
                        } else {
                        }
                    }
                });
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_CF, Utente.getInstance().getCf());
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.PRENOTAZIONI_FATTE_USER, params);
                return res;

            }
        }
        ricercaPrenotazioniEffettuateDallUtente r = new ricercaPrenotazioniEffettuateDallUtente();
        r.execute();
    }

    public void accetta(final Context context) {
        if (listView.getCount() > 0) {
            Toast.makeText(context, "seleziona una prenotazione", Toast.LENGTH_LONG).show();
        } else
            for (int i = 0; i < listView.getCount(); i++) {

                final Prenotazione p = (Prenotazione) listView.getItemAtPosition(i);
                if (p.getSelected()) {
                    Log.d("selezionati ", p.getIdPrenotazione() + " " + p.getSelected() + " " + p.getCf() + " " + attivita.getInstance().getId());

                    class AccettaPrenotazione extends AsyncTask<Void, Void, String> {
                        ProgressDialog loading;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            loading = ProgressDialog.show(context, "Adding...", "Wait...", false, false);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            loading.dismiss();
                            context.startActivity(new Intent(context, le_prenotazioni_del_mio_locale.class));
                        }

                        @Override
                        protected String doInBackground(Void... v) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("cf", p.getCf());
                            params.put("idAttivita", attivita.getInstance().getId());
                            params.put("stato", "ACCETTATA");
                            params.put("idPren", p.getIdPrenotazione());

                            RequestHandler rh = new RequestHandler();
                            String res = rh.sendPostRequest(Config.MODIFICA_PRENOTAZIONE, params);
                            return res;
                        }
                    }
                    AccettaPrenotazione r = new AccettaPrenotazione();
                    r.execute();
                }
            }
    }

    public void rifiuta(final Context context) {
        if (listView.getCount() > 0) {
            Toast.makeText(context, "seleziona una prenotazione", Toast.LENGTH_LONG).show();
        } else
            for (int i = 0; i < listView.getCount(); i++) {

                final Prenotazione p = (Prenotazione) listView.getItemAtPosition(i);
                if (p.getSelected()) {
                    class RifiutaPrenotazione extends AsyncTask<Void, Void, String> {
                        ProgressDialog loading;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            loading = ProgressDialog.show(context, "Adding...", "Wait...", false, false);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            loading.dismiss();
                            context.startActivity(new Intent(context, le_prenotazioni_del_mio_locale.class));
                        }

                        @Override
                        protected String doInBackground(Void... v) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("cf", p.getCf());
                            params.put("idAttivita", attivita.getInstance().getId());
                            params.put("stato", "RIFIUTATA");
                            params.put("idPren", p.getIdPrenotazione());

                            RequestHandler rh = new RequestHandler();
                            String res = rh.sendPostRequest(Config.MODIFICA_PRENOTAZIONE, params);
                            return res;
                        }
                    }
                    RifiutaPrenotazione r = new RifiutaPrenotazione();
                    r.execute();
                }
            }
    }
}




