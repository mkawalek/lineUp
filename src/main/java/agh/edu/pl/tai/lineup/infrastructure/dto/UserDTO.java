package agh.edu.pl.tai.lineup.infrastructure.dto;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class UserDTO {

    @PrimaryKey
    private String userId;
    private String email;
    private String password;

    public UserDTO(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
