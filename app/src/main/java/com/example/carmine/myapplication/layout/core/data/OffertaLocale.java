package com.example.carmine.myapplication.layout.core.data;

/**
 * Created by carmine on 24/01/2017.
 */

public class OffertaLocale {
    private String titoloOfferta, DescrizioneOfferta;

    public OffertaLocale(String tit, String desc) {
        setTitoloOfferta(tit);
        setDescrizioneOfferta(desc);
    }


    public String getTitoloOfferta() {
        return titoloOfferta;
    }

    public String getDescrizioneOfferta() {
        return DescrizioneOfferta;
    }

    public void setTitoloOfferta(String titoloOfferta) {
        this.titoloOfferta = titoloOfferta;
    }

    public void setDescrizioneOfferta(String descrizioneOfferta) {
        DescrizioneOfferta = descrizioneOfferta;
    }
}
