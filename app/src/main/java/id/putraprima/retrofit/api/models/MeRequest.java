package id.putraprima.retrofit.api.models;

public class MeRequest {

    public String token;

    public MeRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
