package com.example.communication_training_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.communication_training_application.model.UiseongUitaeData;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DictionaryActivity extends AppCompatActivity {
    //ArrayList<Uiseong> ulist = new ArrayList<>();

    ArrayList<UiseongUitaeData> uiseongList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        ImageView ivHome = (ImageView) findViewById(R.id.iv_home);
        ivHome.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        HashMap<String, String> resultMap = new HashMap();

        trustAllHosts();
        RetrofitClient.request(cbUiseongUiTae,"call_uiseongs", resultMap);


        //사전 데이터 받아와서 리스트에 추가

        /*for(int i = 0; i < 10; i++){

        }*/
/*
        ArrayList<String> url = new ArrayList<>();

        url.add("https://media.giphy.com/media/l3V0BY1HsT9DJskVi/giphy.gif");
        url.add("https://media.giphy.com/media/l3V0lsGtTMSB5YNgc/giphy.gif");
        url.add("https://media.giphy.com/media/3o6ozFNGTtax2pSGmA/giphy.gif");
        url.add("https://media.giphy.com/media/3o6ozxRrKIQgKyKdoc/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");
        url.add("https://media.giphy.com/media/TObbUke0z8Mo/giphy.gif");
*/
        //사전 데이터 받아와서 리스트에 추가
        /*for(int i = 0; i < 10; i++){
            ulist.add(new Uiseong("word "+i, "meaning "+i, "example "+i, url.get(i)));
        }*/
        /*
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }
         */

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.rv) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 RecyclerviewAdapter 객체 지정.
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(uiseongList) ;       //에러나서 주석처리해둠******
        recyclerView.setAdapter(adapter) ;
    }

    Callback cbUiseongUiTae = new Callback<ArrayList<UiseongUitaeData>>() {
        private final static String TAG = "RetrofitCommunication";
        String cbTAG = "레트로핏 - cbRoomCheck()";

        @Override
        public void onResponse(Call<ArrayList<UiseongUitaeData>> call, Response<ArrayList<UiseongUitaeData>> response) {
            if (response.isSuccessful()) {
                uiseongList = response.body();
                Log.d(TAG, "의성이"+ uiseongList.get(0).getAnswer());



            } else {
                //CommonAlert.toastMsg(cbTAG + "레트로핏 콜백 요청 실패(1) ", mContext);
                Log.e(TAG, cbTAG + "레트로핏 콜백 요청 실패(1) ");
            }
        }

        @Override
        public void onFailure(Call<ArrayList<UiseongUitaeData>> call, Throwable t) {
            //CommonAlert.toastMsg(cbTAG + "레트로핏 콜백 요청 실패(2) " + t, mContext);
            Log.e(TAG, cbTAG + "레트로핏 콜백 요청 실패(2) " + t);
        }
    };

    private void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.3");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
