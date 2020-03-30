package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.ApiError;
import id.putraprima.retrofit.api.models.ErrorUtils;
import id.putraprima.retrofit.api.models.RegisterRequest;
import id.putraprima.retrofit.api.models.RegisterResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private RegisterRequest registerRequest;
    EditText namaInput, emailInput, passwordInput, conPassInput;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        namaInput = findViewById(R.id.eName);
        emailInput = findViewById(R.id.eEmail);
        passwordInput=findViewById(R.id.ePassword);
        conPassInput = findViewById(R.id.eConfirm);
        btnRegister = findViewById(R.id.btnSubmit);
    }

    private void register() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<RegisterResponse> call = service.doRegister(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
//                Toast.makeText(RegisterActivity.this, response.body().getName(), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(RegisterActivity.this, response.body().getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    if(namaInput.getText().toString().isEmpty()){
                        namaInput.setError(error.getError().getName().get(0));
                    } else if(emailInput.getText().toString().isEmpty()){
                        emailInput.setError(error.getError().getEmail().get(0));
                    } else if(passwordInput.getText().toString().isEmpty()){
                        passwordInput.setError(error.getError().getPassword().get(0));
                    } else if(passwordInput.getText().toString().length()<8){
                        passwordInput.setError(error.getError().getPassword().get(0));
                    } else if(!conPassInput.getText().toString().equals(passwordInput)){
                        conPassInput.setError(error.getError().getPassword().get(0));
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, error.getError().getEmail().get(0), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Gagal Koneksi Ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handleRegisterProses(View view) {
        String name = namaInput.getText().toString();
        String email= emailInput.getText().toString();
        String pass = passwordInput.getText().toString();
        String pass_con = conPassInput.getText().toString();
        registerRequest = new RegisterRequest(name, email, pass, pass_con);

        //Validasi
//        boolean check1, check2, check3, check4, check5;
//
//        if(name.equals("")) {
//            Toast.makeText(this, "Masukkan nama dahulu", Toast.LENGTH_SHORT).show();
//            check1 = false;
//        } else {
//            check1 = true;
//        }
//        if (email.equals("")) {
//            Toast.makeText(this, "Masukkan email dahulu", Toast.LENGTH_SHORT).show();
//            check2 = false;
//        } else {
//            check2 = true;
//        }
//        if (pass.equals("")){
//            Toast.makeText(this, "Masukkan password dahulu", Toast.LENGTH_SHORT).show();
//            check3 = false;
//        } else if(pass.length()<8){
//            Toast.makeText(this, "Password minimal 8 karakter", Toast.LENGTH_SHORT).show();
//            check3 = false;
//        }
//        else {
//            check3 = true;
//        }
//        if (pass_con.equals("")) {
//            Toast.makeText(this, "Masukkan password confirm dahulu", Toast.LENGTH_SHORT).show();
//            check4 = false;
//        } else {
//            check4 = true;
//        }
//        if (!pass.equals(pass_con)) {
//            Toast.makeText(this, "Password dan confirm password harus sama", Toast.LENGTH_SHORT).show();
//            check5 = false;
//        } else {
//            check5 = true;
//        }
        //check
//        if((check1 == true && check2 == true && check3 == true && check4) == (true && check5 == true)) {
            register();
//        }
    }
}
