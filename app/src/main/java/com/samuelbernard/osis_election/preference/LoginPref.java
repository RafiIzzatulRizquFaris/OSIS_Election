package com.samuelbernard.osis_election.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginPref {
    private static final String PREF_NAME = "loginpref";
    private static final String ID_MESIN = "id_mesin";
    private static final String ID_KANDIDAT = "id_kandidat";
    private static final String NAMA_KETUA = "nama_ketua";
    private static final String NAMA_WAKIL = "nama_wakil";
    private static final String VISI = "visi";
    private static final String MISI = "misi";
    private static final String FOTO = "foto";
    private static final String VOTE = "vote";

    private final SharedPreferences preferences;

    public LoginPref(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setIdMesin(int id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ID_MESIN, id);
        editor.apply();
    }

    public void setIdKandidat(int id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ID_KANDIDAT, id);
        editor.apply();
    }

    public int getIdMesin() {
        return preferences.getInt(ID_MESIN, 100);
    }

    public int getIdKandidat() {
        return preferences.getInt(ID_KANDIDAT, 100);
    }

    public void setNamaKetua(String nama){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAMA_KETUA, nama);
        editor.apply();
    }

    public String getNamaKetua(){
        return preferences.getString(NAMA_KETUA,"");
    }

    public void setNamaWakil(String nama){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAMA_WAKIL, nama);
        editor.apply();
    }

    public String getNamaWakil(){
        return preferences.getString(NAMA_WAKIL,"");
    }

    public void setVisi(String visi){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(VISI, visi);
        editor.apply();
    }

    public String getVisi(){
        return preferences.getString(VISI,"");
    }

    public void setMisi(String misi){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MISI, misi);
        editor.apply();
    }

    public String getMisi(){
        return preferences.getString(MISI,"");
    }

    public void setFoto(String path){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FOTO, path);
        editor.apply();
    }

    public String getFoto(){
        return preferences.getString(FOTO,"");
    }

    public void setVote(String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(VOTE, value);
        editor.apply();
    }

    public String getVote(){
        return preferences.getString(VOTE,"");
    }
}