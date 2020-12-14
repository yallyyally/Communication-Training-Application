package com.example.communication_training_application;

import android.util.Log;

import com.example.communication_training_application.model.LipReadingData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String SC_TAG = "RetrofitCommunication";
    private static Gson gson;
    private static Retrofit retrofit;
    private static RetrofitService service;

    // ** 레트로핏 요청 (콜백함수, 요청구분, 파라미터) ** //
    public static void request(Callback callback, String gubun, final HashMap<String, String> hashMap) {
        Log.d(SC_TAG, "=========> 요청 : " + gubun);

        if (gson == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
        }

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://3.92.237.164:3000/") // 서버 호스트
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        if( service == null){
            service = retrofit.create(RetrofitService.class);
        }

        if ("call_lip_reading".equals(gubun)) {
            Call<List<LipReadingData>> call = service.getLipReading();
            call.enqueue(callback);
        }

    }

}
