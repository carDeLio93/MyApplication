package com.example.carmine.myapplication.layout.core.data;

/**
 * Created by carmine on 24/01/2017.
 */

public class Vendita {
    private String idAttivita, nomeAttivita, nomeProdotto, prezzoProdotto, tipoProdotto;

    public Vendita(String idAttivita, String nomeAttivita, String nomeProdotto, String prezzoProdotto, String tipo) {
        this.setIdAttivita(idAttivita);
        this.setNomeAttivita(nomeAttivita);
        this.setNomeProdotto(nomeProdotto);
        this.setPrezzoProdotto(prezzoProdotto);
        this.setTipoProdotto(tipo);
    }

    public String getTipoProdotto() {
        return tipoProdotto;
    }

    public void setTipoProdotto(String tipoProdotto) {
        this.tipoProdotto = tipoProdotto;
    }

    public String getIdAttivita() {
        return idAttivita;
    }

    public String getNomeAttivita() {
        return nomeAttivita;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public String getPrezzoProdotto() {
        return prezzoProdotto;
    }

    public void setIdAttivita(String idAttivita) {
        this.idAttivita = idAttivita;
    }

    public void setNomeAttivita(String nomeAttivita) {
        this.nomeAttivita = nomeAttivita;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public void setPrezzoProdotto(String prezzoProdotto) {
        this.prezzoProdotto = prezzoProdotto;
    }
}
