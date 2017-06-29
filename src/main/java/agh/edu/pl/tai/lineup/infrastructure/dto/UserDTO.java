package agh.edu.pl.tai.lineup.infrastructure.dto;

import org.springframework.data.annotation.Id;

import java.util.Set;

public class UserDTO {

    @Id
    private final String id;
    private String email;
    private String hashedPassword;
    private String firstName;
    private String lastName;
    private Set<String> technologies;
    private String department;
    private String fieldOfStudy;

    public UserDTO(String id, String email, String hashedPassword, String firstName, String lastName, Set<String> technologies, String department, String fieldOfStudy) {
        this.id = id;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.technologies = technologies;
        this.department = department;
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<String> getTechnologies() {
        return technologies;
    }

    public String getDepartment() {
        return department;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }
}
