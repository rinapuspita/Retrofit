package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.AppVersion;
import id.putraprima.retrofit.api.models.LoginResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.putraprima.retrofit.ui.SplashActivity.getAppName;
import static id.putraprima.retrofit.ui.SplashActivity.getAppVersion;

public class MainActivity extends AppCompatActivity {
    private static final String APP_NAME = "appName";
    private static final String APP_VERSION = "appVersion";
    TextView appName, appVersion;
    private AppVersion app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appName = findViewById(R.id.mainTxtAppName);
        appVersion = findViewById(R.id.mainTxtAppVersion);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // TODO: display value here
            appName.setText(extras.getString(APP_NAME));
            appVersion.setText(extras.getString(APP_VERSION));
        }

    }


}
