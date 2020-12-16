package com.example.communication_training_application;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.communication_training_application.model.LipReadingData;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static List<LipReadingData> lipReadingData = new ArrayList<LipReadingData>();
    ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Security.insertProviderAt(Conscrypt.newProvider(), 1);
        setContentView(R.layout.activity_main);

        HashMap<String, String> resultMap = new HashMap();

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        trustAllHosts();
        RetrofitClient.request(cbLipReading,"call_lip_reading", resultMap);

        Button btnLip = (Button) findViewById(R.id.btn_lip);
        btnLip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LipReadingActivity.class);
                startActivity(intent);
            }
        });

        Button btnDic = (Button) findViewById(R.id.btn_dic);
        btnDic.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DictionaryActivity.class);
                startActivity(intent);
            }
        });

    }


    Callback cbLipReading = new Callback<List<LipReadingData>>() {
        private final static String TAG = "RetrofitCommunication";
        String cbTAG = "레트로핏 - cbRoomCheck()";

        @Override
        public void onResponse(Call<List<LipReadingData>> call, Response<List<LipReadingData>> response) {
            if (response.isSuccessful()) {
                lipReadingData = response.body();
                Log.d(TAG, cbTAG + " 콜백 : " + lipReadingData.get(0).toString());


            } else {
                //CommonAlert.toastMsg(cbTAG + "레트로핏 콜백 요청 실패(1) ", mContext);
                Log.e(TAG, cbTAG + "레트로핏 콜백 요청 실패(1) ");
            }
        }

        @Override
        public void onFailure(Call<List<LipReadingData>> call, Throwable t) {
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
