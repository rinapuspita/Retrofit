package id.putraprima.retrofit.api.models;

public class LoginRequest {
    public String UserId, Password;

    public LoginRequest(String userId, String password) {
        UserId = userId;
        Password = password;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
