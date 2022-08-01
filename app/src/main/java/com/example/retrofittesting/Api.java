package com.example.retrofittesting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "http://192.168.1.12:8000";

    @GET("/modelNumbers")
    Call<String> getModelNumbers();

    @GET("/problemsByModelNo")
    Call<List<Results>> getProblemsByModelNo(@Query("modelNo") String modelNo);

    @PATCH("/addSolution")
    Call<Results> addSolution(@Query("modelNo") String modelNo, @Query("problem") String problem, @Body SolutionListModel solutionListModel);

}