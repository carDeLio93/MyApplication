package com.example.carmine.myapplication.layout.core.data;

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
import com.example.carmine.myapplication.layout.activity.Product_of_place;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.DialogCustom;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewChekProduct;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewProdottiDelLocale;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;
import com.example.carmine.myapplication.layout.seller.SelectPlaceOfSeller;
import com.example.carmine.myapplication.layout.seller.gestioneMenu.UpdateMenu;
import com.example.carmine.myapplication.layout.seller.gestioneMenu.aggiungiProdottiAlMenu;
import com.example.carmine.myapplication.layout.seller.gestioneProdotti.GestioneProdotti;
import com.example.carmine.myapplication.layout.seller.gestioneProdotti.UpdateProduct;
import com.example.carmine.myapplication.layout.seller.mainActivityAfterLoginAsSeller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carmine on 24/01/2017.
 */

public class ProdottiLocale {
    private String nome, marca, tipologia, prezzo;
    private String idProd;
    private Boolean selected = false;
    private ListView listView;

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {

        this.prezzo = prezzo;
    }

    public ProdottiLocale() {
    }

    public ProdottiLocale(String id, String nome, String marca, String tipologia) {
        setIdProd(id);
        setNome(nome);
        setMarca(marca);
        setTipologia(tipologia);
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public void setIdProd(String idProd) {
        this.idProd = idProd;
    }

    public Boolean getSelected() {
        return selected;
    }

    public String getIdProd() {
        return idProd;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void getProdottiDelLocale(final Context context, final ListView lista, final String idPlace) {
        this.listView = lista;

        class ricercaProdottiDelLocale extends AsyncTask<Void, Void, String> {
            final ArrayList<ProdottiLocale> list = new ArrayList<ProdottiLocale>();

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
                try {
                    jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String nome = jo.getString("nome");
                        String marca = jo.getString("marca");
                        String tipologia = jo.getString("tipologia");
                        String id = jo.getString("idProdotto");

                        ProdottiLocale p = new ProdottiLocale(id, nome, marca, tipologia);
                        list.add(p);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterListViewChekProduct a = new adapterListViewChekProduct(context, R.layout.listview, list);
                lista.setAdapter(a);
                lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                lista.setClickable(true);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_ID_PLACE, idPlace);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.RICERCA_PRODOTTI_LOCALE, params);
                return res;

            }
        }
        ricercaProdottiDelLocale r = new ricercaProdottiDelLocale();
        r.execute();
    }

    public void getProdottiNonNelMenu(final aggiungiProdottiAlMenu context, final ListView lista, final String idPlace, final String idMenu) {
        this.listView = lista;

        class ricercaProdottiNonNelMenu extends AsyncTask<Void, Void, String> {
            final ArrayList<ProdottiLocale> list = new ArrayList<ProdottiLocale>();

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
                try {
                    jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String nome = jo.getString("nome");
                        String marca = jo.getString("marca");
                        String tipologia = jo.getString("tipologia");
                        String id = jo.getString("idProdotto");

                        ProdottiLocale p = new ProdottiLocale(id, nome, marca, tipologia);
                        list.add(p);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterListViewProdottiDelLocale a = new adapterListViewProdottiDelLocale(context, R.layout.listview, list);
                lista.setAdapter(a);
                lista.setClickable(true);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        DialogCustom d = new DialogCustom(context, "aggiungere il prodotto?", "si", "no");
                        d.getButtonUno().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Menu m = new Menu();
                                m.inserisciProdotto(context, idMenu, list.get(position).getIdProd());
                                context.startActivity(new Intent(context, aggiungiProdottiAlMenu.class).putExtra("idMenuInserito", idMenu));
                                context.finishAct();
                            }
                        });
                        d.getButtonDue().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                context.startActivity(new Intent(context, aggiungiProdottiAlMenu.class).putExtra("idMenuInserito", idMenu));
                                context.finishAct();
                            }
                        });
                    }
                });
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_ID_PLACE, idPlace);
                params.put("idMenu", idMenu);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.RICERCA_PRODOTTI_NON_NEL_MENU, params);
                return res;

            }
        }
        ricercaProdottiNonNelMenu r = new ricercaProdottiNonNelMenu();
        r.execute();
    }

    public void getProdottiDelMenu(final Context context, final ListView lista, final String idMenu) {
        final ArrayList<ProdottiLocale> list = new ArrayList<ProdottiLocale>();

        class ricercaProdottiDelMenu extends AsyncTask<Void, Void, String> {
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
                try {
                    jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String nome = jo.getString("nome");
                        String marca = jo.getString("marca");
                        String tipologia = jo.getString("tipologia");
                        String prezzo = jo.getString("prezzo");
                        String id = jo.getString("idProdotto");
                        ProdottiLocale p = new ProdottiLocale(id, nome, marca, tipologia);
                        p.setPrezzo(prezzo);
                        list.add(p);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapterListViewChekProduct a = new adapterListViewChekProduct(context, R.layout.listview, list);
                lista.setAdapter(a);

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("idMenu", idMenu);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.PRODOTTI_DI_UN_MENU, params);
                return res;

            }
        }
        ricercaProdottiDelMenu r = new ricercaProdottiDelMenu();
        r.execute();
    }

    public void elimina(final GestioneProdotti context) {
        for (int i = 0; i < listView.getCount(); i++) {

            final ProdottiLocale p = (ProdottiLocale) listView.getItemAtPosition(i);
            if (p.getSelected()) {
                Log.d("selezionati ", p.nome + " " + p.getSelected() + " " + p.getIdProd());

                class EliminaProdottoLocale extends AsyncTask<Void, Void, String> {
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
                        context.startActivity(new Intent(context, GestioneProdotti.class));
                        context.finishAct();
                    }

                    @Override
                    protected String doInBackground(Void... v) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id", p.getIdProd());
                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(Config.ELIMINA_PRODOTTO, params);
                        return res;
                    }
                }
                EliminaProdottoLocale r = new EliminaProdottoLocale();
                r.execute();
            }
        }
    }


    public void modifica(final GestioneProdotti context) {
        int cont = 0;
        ProdottiLocale p = null;
        ProdottiLocale p1 = null;

        for (int i = 0; i < listView.getCount(); i++) {
            p = (ProdottiLocale) listView.getItemAtPosition(i);
            if (p.getSelected()) {
                p1 = (ProdottiLocale) listView.getItemAtPosition(i);

                cont++;
            }
        }
        if (cont == 1) {
            Intent i = new Intent(context, UpdateProduct.class);
            i.putExtra("idProd", p1.getIdProd());
            i.putExtra("nomeProd", p1.getNome());
            i.putExtra("marca", p1.getMarca());
            i.putExtra("tipologia", p1.getTipologia());
            context.startActivity(i);
            context.finishAct();
        } else if (cont > 1) {
            Toast.makeText(context, "non puoi modificare piu prodotti contemporaneamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "selezionare un prodotto da modificare", Toast.LENGTH_LONG).show();

        }
    }


}




