package id.putraprima.retrofit.api.services;


import id.putraprima.retrofit.api.models.AppVersion;
import id.putraprima.retrofit.api.models.Data;
import id.putraprima.retrofit.api.models.LoginRequest;
import id.putraprima.retrofit.api.models.LoginResponse;
import id.putraprima.retrofit.api.models.MeRequest;
import id.putraprima.retrofit.api.models.MeResponse;
import id.putraprima.retrofit.api.models.PasswordRequest;
import id.putraprima.retrofit.api.models.ProfileRequest;
import id.putraprima.retrofit.api.models.RegisterRequest;
import id.putraprima.retrofit.api.models.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface{
    @GET("/")
    Call<AppVersion> getAppVersion();

    @POST("/api/auth/login")
    Call<LoginResponse> doLogin(@Body LoginRequest loginRequest);

    @POST("/api/auth/register")
    Call<RegisterResponse> doRegister(@Body RegisterRequest registerRequest);

    @GET("/api/auth/me/{token}")
    Call<MeResponse> doMe(@Path("token") MeRequest meRequest);

    @GET("/api/auth/me/")
    Call<Data<MeResponse>> getProfile(@Header("Authorization") String token);

    @PATCH("/api/account/profile")
    Call<Data<MeResponse>> getUpdate(@Header("Authorization") String token, @Body ProfileRequest profileRequest);

    @PATCH("/api/account/password")
    Call<Data<MeResponse>> getUpdatePass(@Header("Authorization") String token, @Body PasswordRequest passwordRequest);
}
