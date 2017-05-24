package agh.edu.pl.tai.lineup.api.requests.user;

public class UserAuthenticationRequest {

    private String email;
    private String password;

    public UserAuthenticationRequest() {}

    public UserAuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

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
