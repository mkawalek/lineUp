package agh.edu.pl.tai.lineup.api.responses.user;

public class UserTokenResponse {

    private String id;
    private String token;

    public UserTokenResponse(String id, String token) {
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
