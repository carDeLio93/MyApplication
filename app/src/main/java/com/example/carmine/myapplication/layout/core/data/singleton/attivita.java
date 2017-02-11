package com.example.carmine.myapplication.layout.core.data.singleton;

/**
 * Created by carmine on 24/01/2017.
 */

public class attivita {
    private String id, nome, citta, via, tipologia, cf_proprietario;
    private static attivita attivita = null;


    private attivita() {

    }

    public static attivita getInstance() {
        if (attivita == null) {
            attivita = new attivita();
        }
        return attivita;
    }


    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCitta() {
        return citta;
    }

    public String getVia() {
        return via;
    }

    public String getTipologia() {
        return tipologia;
    }

    public String getCf_proprietario() {
        return cf_proprietario;
    }

    public com.example.carmine.myapplication.layout.core.data.singleton.attivita setId(String id) {
        this.id = id;
        return null;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public void setCf_proprietario(String cf_proprietario) {
        this.cf_proprietario = cf_proprietario;
    }
}
