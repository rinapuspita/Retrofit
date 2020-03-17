package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.AppVersion;
import id.putraprima.retrofit.api.models.LoginResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private static final String APP_NAME = "appName";
    private static final String APP_VERSION = "appVersion";
    TextView lblAppName, lblAppTittle, lblAppVersion;
//    private SharedPreferences preferences;
    private ApiInterface mListener;
    private static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupLayout();
        if (checkInternetConnection()) {
            checkAppVersion();
        } else{
            Toast.makeText(SplashActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        setAppInfo();
    }

    private void setupLayout() {
        lblAppName = findViewById(R.id.lblAppName);
        lblAppTittle = findViewById(R.id.lblAppTittle);
        lblAppVersion = findViewById(R.id.lblAppVersion);
        //Sembunyikan lblAppName dan lblAppVersion pada saat awal dibuka
        lblAppVersion.setVisibility(View.INVISIBLE);
        lblAppName.setVisibility(View.INVISIBLE);
    }

    private boolean checkInternetConnection() {
        //TODO : 1. Implementasikan proses pengecekan koneksi internet, berikan informasi ke user jika tidak terdapat koneksi internet

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//        return true;
    }


    private void setAppInfo() {
        //TODO : 5. Implementasikan proses setting app info, app info pada fungsi ini diambil dari shared preferences
        //lblAppVersion dan lblAppName dimunculkan kembali dengan data dari shared preferences
//        String ver = preferences.getString("appVersion", "value");
        String appNa = getAppName(SplashActivity.this);
        String appVer = getAppVersion(SplashActivity.this);
//         String appNa = response.body().getApp();

        lblAppName.setText(appNa);
        lblAppVersion.setText(appVer);
        lblAppVersion.setVisibility(View.VISIBLE);
        lblAppName.setVisibility(View.VISIBLE);
    }

    private void checkAppVersion() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<AppVersion> call = service.getAppVersion();
        call.enqueue(new Callback<AppVersion>() {
            @Override
            public void onResponse(Call<AppVersion> call, Response<AppVersion> response) {

                Toast.makeText(SplashActivity.this, response.body().getApp(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(SplashActivity.this, response.body().getVersion(), Toast.LENGTH_SHORT).show();
                //Todo : 2. Implementasikan Proses Simpan Data Yang didapat dari Server ke SharedPreferences
                if (response.isSuccessful()) {
                    setAppName(SplashActivity.this, response.body().getApp());
                    setAppVersion(SplashActivity.this, response.body().getVersion());
                }
                //Todo : 3. Implementasikan Proses Pindah Ke MainActivity Jika Proses getAppVersion() sukses
                String nameApp = lblAppName.getText().toString();
                String verApp = lblAppVersion.getText().toString();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra(APP_NAME, nameApp);
                intent.putExtra(APP_VERSION, verApp);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<AppVersion> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Gagal Koneksi Ke Server", Toast.LENGTH_SHORT).show();
                //Todo : 4. Implementasikan Cara Notifikasi Ke user jika terjadi kegagalan koneksi ke server silahkan googling cara yang lain selain menggunakan TOAST
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

    public static String getAppName(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(APP_NAME, "name");
    }

    public static void setAppName(Context context, String appName) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(APP_NAME, appName).apply();
    }
    public static String getAppVersion(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(APP_VERSION, "1.0.0");
    }

    public static void setAppVersion(Context context, String appVer) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(APP_VERSION, appVer).apply();
    }

//    private void login() {
//        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
//        Call<LoginResponse> call = service.doLogin(loginRequest);
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                Toast.makeText(SplashActivity.this, response.body().getFirstName(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Toast.makeText(SplashActivity.this, "Gagal Koneksi Ke Server", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}
