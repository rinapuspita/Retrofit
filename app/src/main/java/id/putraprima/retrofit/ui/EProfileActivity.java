package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.ApiError;
import id.putraprima.retrofit.api.models.Data;
import id.putraprima.retrofit.api.models.ErrorUtils;
import id.putraprima.retrofit.api.models.MeRequest;
import id.putraprima.retrofit.api.models.MeResponse;
import id.putraprima.retrofit.api.models.ProfileRequest;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EProfileActivity extends AppCompatActivity {
    private EditText txtName, txtEmail;
    private Button btnUpdate;
    String token,nama,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_profile);
        txtName = findViewById(R.id.user);
        txtEmail = findViewById(R.id.email);
        btnUpdate = findViewById(R.id.btnEProfile);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            token = bundle.getString("token");
            nama = bundle.getString("nama");
            email = bundle.getString("email");

            System.out.println(nama);
            System.out.println(email);
            System.out.println(token);
        }
        txtName.setText(nama);
        txtEmail.setText(email);
    }
    private void updateData(){
        String nama,email;
        nama = txtName.getText().toString();
        email = txtEmail.getText().toString();
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Data<MeResponse>> call = service.getUpdate(token, new ProfileRequest(nama, email));
        call.enqueue(new Callback<Data<MeResponse>>() {
            @Override
            public void onResponse(Call<Data<MeResponse>> call, Response<Data<MeResponse>> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(EProfileActivity.this, "Update Profile Berhasil", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(EProfileActivity.this, LoginActivity.class);
                    i.putExtra("token", token);
                    startActivity(i);
                }
                else{
                    ApiError error = ErrorUtils.parseError(response);
                    if(txtName.getText().toString().isEmpty()){
                        txtName.setError(error.getError().getName().get(0));
                    } else if(txtEmail.getText().toString().isEmpty()){
                        txtEmail.setError(error.getError().getEmail().get(0));
                    } else {
                        Toast.makeText(EProfileActivity.this, error.getError().getEmail().get(0), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Data<MeResponse>> call, Throwable t) {
                Toast.makeText(EProfileActivity.this, "Update Data Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handleUpdate(View view) {
        updateData();

    }
}
