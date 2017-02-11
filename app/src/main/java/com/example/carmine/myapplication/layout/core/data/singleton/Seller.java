package com.example.carmine.myapplication.layout.core.data.singleton;

/**
 * Created by carmine on 22/01/2017.
 */

public class Seller {
    private static Seller seller = null;
    private String cf, nome, cognome, username, email;

    private Seller() {

    }

    public static Seller getInstance() {
        if (seller == null) {
            seller = new Seller();
        }
        return seller;
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
