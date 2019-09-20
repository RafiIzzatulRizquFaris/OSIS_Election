package com.samuelbernard.osis_election.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("mesin")
    Call<ResponseBody> loginMesin(
            @Query("username") String username,
            @Query("password") String password
    );

    @GET("tambah/riwayat")
    Call<ResponseBody> addVote(
            @Query("id_pemilih") String id_pemilih,
            @Query("id_mesin") String id_mesin,
            @Query("id_kandidat") String id_kandidat
    );

    @GET("pemilih/{no_identitas}")
    Call<ResponseBody> cekPemilih(
            @Path("no_identitas") String id_pemilih
    );

    @GET("status/pemilih/{no_identitas}")
    Call<ResponseBody> pemilihMemilih(
            @Path("no_identitas") String id_pemilih
    );
}