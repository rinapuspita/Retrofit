package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Data;
import id.putraprima.retrofit.api.models.MeResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String EMAIL_KEY = "emailKey";
    private static final String KEY_NAME = "nameKey";
    private static final int ID_KEY = 0;
    TextView textId, textName, textEmail;
    String nama, email, token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        textId = findViewById(R.id.id);
        textName = findViewById(R.id.name);
        textEmail = findViewById(R.id.email);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // TODO: display value here
            System.out.println(token);
            token = extras.getString("token");
            getData(token);
        }
    }
    public void getData(String token){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Data<MeResponse>> call = service.getProfile(token);
        call.enqueue((new Callback<Data<MeResponse>>() {
            @Override
            public void onResponse(Call<Data<MeResponse>> call, Response<Data<MeResponse>> response) {
                if(response != null) {
                    textName.setText(response.body().data.name);
                    textEmail.setText(response.body().data.email);
                    nama = response.body().data.name;
                    email = response.body().data.email;
                }
            }

            @Override
            public void onFailure(Call<Data<MeResponse>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal Koneksi Ke Server", Toast.LENGTH_SHORT).show();
            }
        }));

    }

    public void handlerEProfile(View view) {
        Intent intent = new Intent(LoginActivity.this, EProfileActivity.class);
        intent.putExtra("nama", nama);
        intent.putExtra("email", email);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void handlerEPass(View view) {
        Intent intent = new Intent(LoginActivity.this, EPassActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void handlerLogout(View view) {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
