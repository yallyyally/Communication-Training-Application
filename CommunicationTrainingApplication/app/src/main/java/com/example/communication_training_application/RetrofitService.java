package com.example.communication_training_application;

import com.example.communication_training_application.model.LipReadingData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {

    @GET("api/lip_reading")
    Call<List<LipReadingData>> getLipReading();
}
