package com.codekiller.retrofitwithfcm;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("posts")
    Call<ArrayList<JSONFile>> getJson();
}
