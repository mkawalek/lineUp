package agh.edu.pl.tai.lineup.api.requests;

public class UserCreationRequest {

    private String email;
    private String password;

    public UserCreationRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
