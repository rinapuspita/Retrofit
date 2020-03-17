package id.putraprima.retrofit.api.models;

public class LoginResponse {
    public String UserId,FirstName,LastName,ProfilePicture;

    public LoginResponse(String userId, String firstName, String lastName, String profilePicture) {
        UserId = userId;
        FirstName = firstName;
        LastName = lastName;
        ProfilePicture = profilePicture;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }
}
