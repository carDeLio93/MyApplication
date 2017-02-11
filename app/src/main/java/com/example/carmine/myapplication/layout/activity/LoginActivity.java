package com.example.carmine.myapplication.layout.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carmine.myapplication.R;
import com.example.carmine.myapplication.layout.core.Config;
import com.example.carmine.myapplication.layout.core.DialogCustom;
import com.example.carmine.myapplication.layout.core.RequestHandler;
import com.example.carmine.myapplication.layout.core.data.singleton.Seller;
import com.example.carmine.myapplication.layout.core.data.singleton.Utente;
import com.example.carmine.myapplication.layout.seller.SelectPlaceOfSeller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.carmine.myapplication.layout.core.Config.KEY_CF;


public class LoginActivity extends AppCompatActivity {
    private String JSON_STRING;
    private Button login;
    private Button loginAsHost;
    private Button register;
    private EditText username, password;
    private CheckBox seller;
    private HashMap<String, String> employees;

    public void login() {
        JSON_STRING = "";
        final String username = this.username.getText().toString();
        final String pass = password.getText().toString();
        final boolean check = seller.isChecked();
        if (!(this.username.getText().toString().length() > 0) || !(password.getText().toString().length() > 0)) {
            Toast.makeText(LoginActivity.this, "fill all field", Toast.LENGTH_LONG).show();
        } else {
            class Login extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(LoginActivity.this, "Loading...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    if (resultLogin(s)) {
                        if (check) {
                            showitSeller(s);
                            OpenMainActivityAftherLoginAsSeller();
                        } else {
                            showit(s);
                            OpenMainActivityAftherLoginAsUser();
                        }
                    } else {
                        getError("User o password non corrette");
                    }
                }

                @Override
                protected String doInBackground(Void... v) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put(Config.KEY_EMP_NAME, username);
                    params.put(Config.KEY_EMP_DESG, pass);
                    RequestHandler rh = new RequestHandler();
                    String res;
                    if (check)
                        res = rh.sendPostRequest(Config.URL_LOGIN_SELLER, params);
                    else
                        res = rh.sendPostRequest(Config.URL_LOGIN_USER, params);
                    return res;
                }

                public void getError(String message) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    alertDialogBuilder.setMessage(message);

                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    //dismiss dialog
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

                public boolean resultLogin(String s) {

                    String jsonStr = s;
                    if (jsonStr != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(jsonStr);
                            String query_result = jsonObj.getString("query_result");
                            if (query_result.equals("FAILURE")) {
                                return false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
            }
            Login a = new Login();
            a.execute();
        }

    }

    public void OpenMainActivityAftherLoginAsUser() {
        startActivity(new Intent(this, MainActivityAftherLogin.class));
        finish();

    }

    public void OpenMainActivityAftherLoginAsSeller() {
        startActivity(new Intent(LoginActivity.this, SelectPlaceOfSeller.class));
        finish();

    }

    public void OpenMainActivityAftherLoginAsHost() {
        Utente.getInstance().setCf("NON_AUTENTICATO");
        startActivity(new Intent(LoginActivity.this, MainActivityAftherLogin.class));
        finish();

    }

    public void OpenRegistrationActivity() {
        startActivity(new Intent(this, RegistrationActivity.class));
        finish();

    }

    public void inizializzazione() {
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        seller = (CheckBox) findViewById(R.id.RadioButtonSeller);
        login = (Button) findViewById(R.id.email_sign_in_button);
        loginAsHost = (Button) findViewById(R.id.Login_as_host);
        register = (Button) findViewById(R.id.Register);
    }

    public void setListener() {
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        loginAsHost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMainActivityAftherLoginAsHost();
            }
        });
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenRegistrationActivity();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inizializzazione();
        setListener();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void showitSeller(String s) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String cf = jo.getString("cf");
                String nome = jo.getString("nome");
                String cognome = jo.getString("cognome");
                String user = jo.getString("username");
                String mail = jo.getString("email");

                Seller sell = Seller.getInstance();
                sell.setCf(cf);
                sell.setNome(nome);
                sell.setCognome(cognome);
                sell.setUsername(user);
                sell.setEmail(mail);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "premere nuovamente il tasto back per chiudere l'applicazione",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    private void showit(String s) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(KEY_CF);
                String nome = jo.getString(Config.KEY_EMP_NOME);
                String cognome = jo.getString(Config.KEY_EMP_LASTNAME);
                String user = jo.getString(Config.KEY_EMP_USER);
                String mail = jo.getString(Config.KEY_MAIL);

                Utente u = Utente.getInstance();
                u.setCf(id);
                u.setNome(nome);
                u.setCognome(cognome);
                u.setUsername(user);
                u.setEmail(mail);
                Log.d("aaaaaaaaaaaaaa", u.getCf());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
