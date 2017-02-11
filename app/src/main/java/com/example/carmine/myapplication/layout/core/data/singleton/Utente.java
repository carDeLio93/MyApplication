package com.example.carmine.myapplication.layout.core.data.singleton;

/**
 * Created by carmine on 22/01/2017.
 */

public class Utente {
    private static Utente user = null;
    private String cf, nome, cognome, username, email;

    private Utente() {

    }

    public static Utente getInstance() {
        if (user == null) {
            user = new Utente();
        }
        return user;
    }

    public String getCf() {
        return cf;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


    public void setCf(String cf) {
        this.cf = cf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
