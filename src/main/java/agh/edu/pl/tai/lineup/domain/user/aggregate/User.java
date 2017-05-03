package agh.edu.pl.tai.lineup.domain.user.aggregate;

import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.utils.Validator;

public class User {

    private UserId userId;
    private String email;
    private String password;

    public User(UserId userId, String email, String password) {
        validate(email, password);
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    private void validate(String email, String password) {
        new Validator()
                .onNull(email, "user.email")
                .onNull(password, "user.password")
                .validateAndThrow();

        new Validator()
                .on(!email.isEmpty(), "user.name", "empty")
                .on(!password.isEmpty(), "user.password", "empty")
                .validateAndThrow();
    }

    public UserId getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
