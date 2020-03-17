package id.putraprima.retrofit.api.services;


import id.putraprima.retrofit.api.models.AppVersion;
import id.putraprima.retrofit.api.models.LoginRequest;
import id.putraprima.retrofit.api.models.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface{
    @GET("/")
    Call<AppVersion> getAppVersion();

    @POST("/api/auth/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
