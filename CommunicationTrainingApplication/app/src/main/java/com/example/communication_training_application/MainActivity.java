package com.example.communication_training_application;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog.Builder serverDialogBuilder;
    AlertDialog alertDialog;
    AlertDialog serverDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final HashMap<String, String> resultMap = new HashMap();


        alertDialogBuilder = new AlertDialog.Builder(MainActivity.this); //네트워크 연결 실패시
        alertDialogBuilder.setTitle("네트워크 연결 실패");
        alertDialogBuilder.setMessage("네트워크 연결 후 다시 실행해주세요");
        alertDialogBuilder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        alertDialog = alertDialogBuilder.create();

        serverDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        serverDialogBuilder.setTitle("데이터 다운 실패");
        serverDialogBuilder.setMessage("아래 버튼을 눌러 다시 다운 받으세요");
        serverDialogBuilder.setPositiveButton("다운", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                trustAllHosts();
                RetrofitClient.request(cbLipReading, "call_lip_reading", resultMap);
            }
        });
        serverDialog = serverDialogBuilder.create();


        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        Log.d("retrofit", "인터넷 ");

        if(isNetworkAvailable(MainActivity.this)==false) {
            Log.d("retrofit", "인터넷 연결xx");
            alertDialog.show();
        }
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        connectivityManager.registerNetworkCallback(builder.build(), new ConnectivityManager.NetworkCallback() { //인터넷 연결확인
            @Override
            public void onAvailable(@NonNull Network network) {
                // 네트워크를 사용할 준비가 되었을 때

                Log.d("retrofit", "인터넷 연결됨");
                trustAllHosts();
                RetrofitClient.request(cbLipReading, "call_lip_reading", resultMap);
            }

            @Override
            public void onLost(@NonNull Network network) {
                // 네트워크가 끊겼을 때
                Log.d("retrofit", "인터넷 연결ㄴㄴ");

                alertDialog.show();

            }
        });

        LinearLayout ll = (LinearLayout)findViewById(R.id.ll_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        ll.setPadding(0, statusBarHeight(this), 0, 0);


        Button btnLip = (Button) findViewById(R.id.btn_lip);
        btnLip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });

        Button btnDic = (Button) findViewById(R.id.btn_dic);
        btnDic.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DictionaryActivity.class);
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
                Toast.makeText(getApplicationContext(), "데이터 다운 성공", Toast.LENGTH_SHORT).show();

            } else {
                //CommonAlert.toastMsg(cbTAG + "레트로핏 콜백 요청 실패(1) ", mContext);
                Log.e(TAG, cbTAG + "레트로핏 콜백 요청 실패(1) ");
            }
        }

        @Override
        public void onFailure(Call<List<LipReadingData>> call, Throwable t) {
            //CommonAlert.toastMsg(cbTAG + "레트로핏 콜백 요청 실패(2) " + t, mContext);
            Log.e(TAG, cbTAG + "레트로핏 콜백 요청 실패(2) " + t);
            serverDialog.show();
            //Toast.makeText(getApplicationContext(),"서버 문제로 데이터 다운 불가",Toast.LENGTH_LONG).show();
        }
    };

    private void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int statusBarHeight(Context context){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height","dimen","android");
        if (resourceId > 0) { result = getResources().getDimensionPixelSize(resourceId); } return result;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    public boolean isNetworkAvailable(Context context) {
        if(context == null)  return false;


        if (connectivityManager != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                        return true;
                    }
                }
            }

            else {

                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        Log.i("update_statut", "Network is available : true");
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("update_statut", "" + e.getMessage());
                }
            }
        }
        Log.i("update_statut","Network is available : FALSE ");
        return false;
    }
}
