package agh.edu.pl.tai.lineup.api.responses;

public class IdResponse {

    private String value;

    public IdResponse(String value) {
        this.value = value;
    }

    public String getId() {
        return value;
    }
}
