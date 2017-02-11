package com.example.carmine.myapplication.layout.core;

public class Config {
    //Address of our scripts of the CRUD
    //funzionanti
    public static final String ip = "192.168.42.205";
    public static final String URL_LOGIN_USER = "http://" + ip + "/sito/testUser.php";
    public static final String URL_LOCALI_DI_UN_SELLER = "http://" + ip + "/sito/allLocalForOneSeller.php";
    public static final String URL_GET_LOCALI_PREFERITI = "http://" + ip + "/sito/getLocaliPreferiti.php";
    public static final String PRENOTAZIONI_DI_UN_LOCALE = "http://" + ip + "/sito/allReservationForOneLocal.php";
    public static final String PRENOTAZIONI_FATTE_USER = "http://" + ip + "/sito/allReservationOfUser.php";
    public static final String MENU_DI_UN_LOCALE = "http://" + ip + "/sito/menuDiUnLocale.php";
    public static final String PRODOTTI_DI_UN_MENU = "http://" + ip + "/sito/searchProductOfMenu.php?";
    public static final String ELIMINA_PRODOTTO = "http://" + ip + "/sito/DELETEpRODUCT.php?";
    public static final String ELIMINA_MENU = "http://" + ip + "/sito/deletemenu.php?";
    public static final String INSERISCI_PRODOTTO_NEL_MENU = "http://" + ip + "/sito/insertProductIntoMenu.php?";
    public static final String LOCALE_REGISTRATO = "http://" + ip + "/sito/localeRegistrato.php?";

    public static final String URL_LOGIN_SELLER = "http://" + ip + "/sito/test.php";
    public static final String RESERVE = "http://" + ip + "/sito/reserve.php";
    public static final String URL_AGGIUNGI_AI_PREFERITI = "http://" + ip + "/sito/AggiuntaPreferiti.php";
    public static final String MODIFICA_PRENOTAZIONE = "http://" + ip + "/sito/modificaPrenotazione.php";

    public static final String URL_ADD_USER = "http://" + ip + "/sito/testati/insertDate.php";
    public static final String URL_ADD_PREFER_PRODUCT = "http://" + ip + "/sito/menu.php";

    public static final String RICERCA_PER_NOME_PRODOTTO = "http://" + ip + "/sito/search.php?search=";
    public static final String RICERCA_OFFERTE_LOCALE = "http://" + ip + "/sito/ricercaOfferte.php";
    public static final String URL_INSERT_PLACE = "http://" + ip + "/sito/testati/insertPlace.php";
    public static final String RICERCA_PRODOTTI_LOCALE = "http://" + ip + "/sito/searchProduct.php?";
    public static final String RICERCA_PRODOTTI_NON_NEL_MENU = "http://" + ip + "/sito/searchProductNonNelMenu.php?";

    //
    public static final String ID_LOCALE = "idloc";
    public static final String NOME_LOCALE = "nomeloc";
    public static final String CITTA_LOCALE = "cittaloc";
    public static final String VIA_LOCALE = "vialoc";
    public static final String TIPOLOGIA_LOCALE = "tipoloc";
    public static final String CF_SELLER_LOCALE = "cfsellerloc";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_DESCRIZIONE = "descrizione";
    public static final String NAME_PRODUCT = "search";
    public static final String KEY_EMP_NAME = "name";
    public static final String KEY_EMP_NOME = "nome";
    public static final String KEY_EMP_DESG = "desg";
    public static final String KEY_EMP_SAL = "salary";
    public static final String KEY_EMP_USER = "user";
    public static final String KEY_EMP_PASSWORD = "pass";
    public static final String KEY_CF = "cf";
    public static final String KEY_ORA = "ora";
    public static final String KEY_GIORNO = "giorno";
    public static final String KEY_ANNO = "anno";
    public static final String KEY_MINUTI = "minuti";
    public static final String KEY_MESE = "mese";
    public static final String KEY_USER = "user";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_NUMERO = "numero";

    public static final String KEY_PREZZO = "prezzo";
    public static final String KEY_MAIL = "email";

    public static final String KEY_IDPLACE = "idPlace";
    public static final String KEY_ID_PLACE = "idPlace";
    public static final String INSERT_PRODUCT = "http://" + ip + "/sito/insertProduct.php";
    public static final String UPDATE_PRODUCT = "http://" + ip + "/sito/updateProduct.php";

    public static final String KEY_EMP_LASTNAME = "lastname";
    public static final String KEY_SELLER = "venditore";
    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_DESG = "desg";
    public static final String TAG_SAL = "salary";

    //employee id to pass with intent
    public static final String EMP_ID = "emp_id";
}
