package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.ApiError;
import id.putraprima.retrofit.api.models.Data;
import id.putraprima.retrofit.api.models.ErrorUtils;
import id.putraprima.retrofit.api.models.MeResponse;
import id.putraprima.retrofit.api.models.PasswordRequest;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EPassActivity extends AppCompatActivity {
    private EditText newPass, newConPass;
    String token, newPassVal, newConPassVal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_pass);
        newPass = findViewById(R.id.pass);
        newConPass = findViewById(R.id.cpass);
        Button btnUpdate = findViewById(R.id.btnEPass);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            token = bundle.getString("token");
        }
    }
    private void updatePassword() {
        newPassVal = newPass.getText().toString();
        newConPassVal = newConPass.getText().toString();
//        if (!newConPassVal.equals(newPassVal)) {
//            Toast.makeText(this, "Password harus sama!", Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (newPassVal.trim().length() < 8) {
//            Toast.makeText(this, "Minimal 8 karakter!", Toast.LENGTH_SHORT).show();
//            return false;
//        }else{
            ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
            Call<Data<MeResponse>> call = service.getUpdatePass(token, new PasswordRequest(newPassVal, newConPassVal));
            call.enqueue(new Callback<Data<MeResponse>>() {
                @Override
                public void onResponse(Call<Data<MeResponse>> call, Response<Data<MeResponse>> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(EPassActivity.this, "Update Password Berhasil! :)", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EPassActivity.this, LoginActivity.class);
                        i.putExtra("token", token);
                        startActivity(i);
                    }
                    else{
                        ApiError error = ErrorUtils.parseError(response);
                        if(newPass.getText().toString().isEmpty()){
                            newPass.setError(error.getError().getPassword().get(0));
                        } else if(newPass.length()<8){
                            newPass.setError(error.getError().getPassword().get(0));
                        } else if(newConPass.getText().toString().isEmpty()){
                            newConPass.setError(error.getError().getPassword().get(0));
                        } else if(!newConPass.equals(newPass)){
                            newConPass.setError(error.getError().getPassword().get(0));
                        } else if(newConPass.length() < 8){
                            newConPass.setError(error.getError().getPassword().get(0));
                        }
                        else {
                            Toast.makeText(EPassActivity.this, error.getError().getPassword().get(0), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Data<MeResponse>> call, Throwable t) {
                    Toast.makeText(EPassActivity.this, "Update Password Gagal.. :(", Toast.LENGTH_SHORT).show();
                }
            });
}

    public void handleUpPas(View view) {
        updatePassword();
    }
}