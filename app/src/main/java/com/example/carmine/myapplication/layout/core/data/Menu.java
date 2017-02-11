package com.example.carmine.myapplication.layout.core.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.activity.activityMenu;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.DialogCustom;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.adapter.adapterListViewMenu;
import com.example.carmine.myapplication.layout.core.data.singleton.attivita;
import com.example.carmine.myapplication.layout.seller.gestioneMenu.GestioneMenu;
import com.example.carmine.myapplication.layout.seller.gestioneMenu.UpdateMenu;
import com.example.carmine.myapplication.layout.seller.gestioneMenu.prodottiDiUnMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carmine on 22/01/2017.
 */
public class Menu {
    private String id;
    private String nome;
    private String descrizione;

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void getListMenuOfLocal(final GestioneMenu context, final ListView lista) {
        class ricercaMenuDelLocale extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONObject jsonObject = null;
                final ArrayList<Menu> list = new ArrayList<Menu>();
                try {
                    jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String idMenu = jo.getString("id");
                        String title = jo.getString("title");
                        String desc = jo.getString("descrizione");
                        Menu menu = new Menu();
                        menu.setId(idMenu);
                        menu.setNome(title);
                        menu.setDescrizione(desc);
                        list.add(menu);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapterListViewMenu adapter = new adapterListViewMenu(context, R.layout.listview, list);
                lista.setAdapter(adapter);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        context.startActivity(new Intent(context, prodottiDiUnMenu.class).putExtra("idMenu", list.get(position).getId()));
                        context.finishAct();
                    }
                });

                lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                        DialogCustom d = new DialogCustom(context, "selezionare un opzione... ", "elimina menu", "modifica menu");
                        d.getButtonUno().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                eliminaMenu(context, list.get(position).getId());
                            }
                        });
                        d.getButtonDue().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                context.startActivity(new Intent(context, UpdateMenu.class));
                                context.finishAct();

                            }
                        });


                        return true;
                    }

                });


            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("idPlace", attivita.getInstance().getId());
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.MENU_DI_UN_LOCALE, params);
                return res;

            }
        }
        ricercaMenuDelLocale r = new ricercaMenuDelLocale();
        r.execute();
    }

    public void getListMenuOfLocalForActivityMenu(final activityMenu context, final ListView lista) {
        class ricercaMenuDelLocale extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONObject jsonObject = null;
                final ArrayList<Menu> list = new ArrayList<Menu>();
                try {
                    jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String idMenu = jo.getString("id");
                        String title = jo.getString("title");
                        String desc = jo.getString("descrizione");
                        Menu menu = new Menu();
                        menu.setId(idMenu);
                        menu.setNome(title);
                        menu.setDescrizione(desc);
                        list.add(menu);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapterListViewMenu adapter = new adapterListViewMenu(context, R.layout.listview, list);
                lista.setAdapter(adapter);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        context.startActivity(new Intent(context, prodottiDiUnMenu.class).putExtra("idMenu", list.get(position).getId()));
                        context.finishAct();

                    }
                });

                lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                        DialogCustom d = new DialogCustom(context, "selezionare un opzione... ", "elimina menu", "modifica menu");
                        d.getButtonUno().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                eliminaMenu(context, list.get(position).getId());
                            }
                        });
                        d.getButtonDue().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                context.startActivity(new Intent(context, UpdateMenu.class));
                                context.finishAct();


                            }
                        });


                        return true;
                    }

                });


            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("idPlace", attivita.getInstance().getId());
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.MENU_DI_UN_LOCALE, params);
                return res;

            }
        }
        ricercaMenuDelLocale r = new ricercaMenuDelLocale();
        r.execute();
    }

    public void eliminaMenu(final Context context, final String id) {
        class EliminaMenu extends AsyncTask<Void, Void, String> {
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

                context.startActivity(new Intent(context, GestioneMenu.class));
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.ELIMINA_MENU, params);
                return res;
            }
        }
        EliminaMenu r = new EliminaMenu();
        r.execute();
    }

    public void inserisciProdotto(final Context context, final String idMenu, final String idProdotto) {
        class insertProduct extends AsyncTask<Void, Void, String> {
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
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("idMenu", idMenu);
                params.put("idProdotto", idProdotto);
                params.put("prezzo", "50");
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.INSERISCI_PRODOTTO_NEL_MENU, params);
                return res;
            }
        }
        insertProduct r = new insertProduct();
        r.execute();
    }
}


