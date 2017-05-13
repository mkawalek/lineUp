package agh.edu.pl.tai.lineup.api.responses.user;

public class RegistrationResponse {

    private String id;
    private String token;

    public RegistrationResponse(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
