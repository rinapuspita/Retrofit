package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.AppVersion;
import id.putraprima.retrofit.api.models.LoginRequest;
import id.putraprima.retrofit.api.models.LoginResponse;
import id.putraprima.retrofit.api.models.MeRequest;
import id.putraprima.retrofit.api.models.MeResponse;
import id.putraprima.retrofit.api.models.RegisterRequest;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.putraprima.retrofit.ui.SplashActivity.getAppName;
import static id.putraprima.retrofit.ui.SplashActivity.getAppVersion;

public class MainActivity extends AppCompatActivity {
    private static final String APP_NAME = "appName";
    private static final String APP_VERSION = "appVersion";
    private static final String EMAIL_KEY = "emailKey";
    private static final String PASSWORD_KEY = "passwordKey";
    private static final String KEY_NAME = "nameKey";
    private static final int ID_KEY = 0;
    //    private static final int ID_KEY = 0;
    TextView appName, appVersion;
    private AppVersion app;
    private LoginRequest loginRequest;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin,btnRegister;
    private static SharedPreferences preferences;
    private ApiInterface mListener;
    RegisterRequest registerRequest;
    MeRequest meRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appName = findViewById(R.id.mainTxtAppName);
        appVersion = findViewById(R.id.mainTxtAppVersion);
        etEmail = findViewById(R.id.edtEmail);
        etPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.bntToRegister);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // TODO: display value here
            appName.setText(extras.getString(APP_NAME));
            appVersion.setText(extras.getString(APP_VERSION));
        }

    }
public void login() {
    ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
    Call<LoginResponse> call = service.doLogin(loginRequest);
    call.enqueue(new Callback<LoginResponse>() {
        @Override
        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            Toast.makeText(MainActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, response.body().getToken_type(), Toast.LENGTH_SHORT).show();
            String expires_in = Integer.toString(response.body().getExpires_in());
            Toast.makeText(MainActivity.this, expires_in, Toast.LENGTH_SHORT).show();

            if (response != null) {
                meRequest = new MeRequest(response.body().getToken());
                me();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("token", response.body().token);
                intent.putExtra("token_type", response.body().token_type);
                startActivity(intent);
            }
        }

        @Override
        public void onFailure(Call<LoginResponse> call, Throwable t) {
            Toast.makeText(MainActivity.this, "Gagal Koneksi Ke Server", Toast.LENGTH_SHORT).show();
        }
    });
}

    public void me(){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<MeResponse> call = service.doMe(meRequest);
        call.enqueue((new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
                if(response != null) {
//                    Toast.makeText(MainActivity.this, response.body().getName(), Toast.LENGTH_SHORT).show();
//                    setId(MainActivity.this, response.body().getId());
//                    setName(MainActivity.this, response.body().getName());
//                    setEmail(MainActivity.this, response.body().getEmail());

//                    int keyId = getId(MainActivity.this);
//                    String keyName = getName(MainActivity.this);
//                    String keyEmail = getEmail(MainActivity.this);
//                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    intent.putExtra(String.valueOf(ID_KEY), keyId);
//                    intent.putExtra(KEY_NAME, keyName);
//                    intent.putExtra(EMAIL_KEY, keyEmail);
//                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Koneksi Ke Server", Toast.LENGTH_SHORT).show();
            }
        }));

    }

    public static String getToken(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(KEY_NAME, "name");
    }

    public static void setToken(Context context, String keyName) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(KEY_NAME, keyName).apply();
    }

    public static String getName(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(KEY_NAME, "name");
    }

    public static void setName(Context context, String keyName) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(KEY_NAME, keyName).apply();
    }
    public static String getEmail(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(EMAIL_KEY, "email");
    }

    public static void setEmail(Context context, String keyEmail) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(EMAIL_KEY, keyEmail).apply();
    }
    public static int getId(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(String.valueOf(ID_KEY), 0);
    }

    public static void setId(Context context, int keyId) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(String.valueOf(ID_KEY), keyId).apply();
    }

    public void handlerLogin(View view) {
        String user = etEmail.getText().toString();
        String pass = etPassword.getText().toString();
        loginRequest = new LoginRequest(user, pass);
        boolean check1, check2;
        if (user.equals("")) {
            Toast.makeText(this, "Isi Email Dahulu", Toast.LENGTH_SHORT).show();
            check1 = false;
        } else {
            check1 = true;
        }
        if (pass.equals("")) {
            Toast.makeText(this, "Isi Password Dahulu", Toast.LENGTH_SHORT).show();
            check2 = false;
        } else {
            check2 = true;
        }
        if (check1 == true && check2 == true) {
            login();
        }
    }

    public void handleRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
