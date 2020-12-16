package com.example.communication_training_application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.LinearLayout;

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

public class DictionaryActivity extends AppCompatActivity implements TextWatcher {
    //ArrayList<Uiseong> ulist = new ArrayList<>();

    ArrayList<UiseongUitaeData> uiseongList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerviewAdapter adapter;
    //SearchRecyclerViewAdapter sAdapter;

    //ArrayList <UiseongUitaeData> items = new ArrayList<>();

    //private RecyclerviewAdapter recyclerviewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        LinearLayout ll = (LinearLayout)findViewById(R.id.ll_dictionary);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        ll.setPadding(0, statusBarHeight(this), 0, 0);

        ImageView ivHome = (ImageView) findViewById(R.id.iv_home);
        ivHome.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        EditText editText = (EditText) findViewById(R.id.search_bar);
        editText.addTextChangedListener(this);


        HashMap<String, String> resultMap = new HashMap();

        trustAllHosts();
        RetrofitClient.request(cbUiseongUiTae,"call_uiseongs", resultMap);


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = findViewById(R.id.rv) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;


        // 리사이클러뷰에 RecyclerviewAdapter 객체 지정.

    }

    Callback cbUiseongUiTae = new Callback<ArrayList<UiseongUitaeData>>() {
        private final static String TAG = "RetrofitCommunication";
        String cbTAG = "레트로핏 - cbRoomCheck()";

        @Override
        public void onResponse(Call<ArrayList<UiseongUitaeData>> call, Response<ArrayList<UiseongUitaeData>> response) {
            if (response.isSuccessful()) {
                uiseongList = response.body();
                Log.d(TAG, "의성이"+ uiseongList.get(0).getAnswer());

                adapter = new RecyclerviewAdapter(uiseongList) ;       //에러나서 주석처리해둠******

                recyclerView.setAdapter(adapter) ;

            } else {
                //CommonAlert.toastMsg(cbTAG + "레트로핏 콜백 요청 실패(1) ", mContext);
                Log.e(TAG, cbTAG + "레트로핏 콜백 요청 실패(1) ");
            }
        }

        @Override
        public void onFailure(Call<ArrayList<UiseongUitaeData>> call, Throwable t) {
            //CommonAlert.toastMsg(cbTAG + "레트로핏 콜백 요청 실패(2) " + t, mContext);
            Toast.makeText(getApplicationContext(),"서버 문제로 데이터 다운 불가",Toast.LENGTH_LONG);

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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        adapter.getFilter().filter(charSequence);

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
    public int statusBarHeight(Context context){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height","dimen","android");
        if (resourceId > 0) { result = getResources().getDimensionPixelSize(resourceId); } return result;
    }
}
