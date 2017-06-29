package agh.edu.pl.tai.lineup.api.responses.user;

import java.util.Set;

public class UserDetailsResponse {

    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> technologies;
    private String department;
    private String fieldOfStudy;

    public UserDetailsResponse(String userId, String email, String firstName, String lastName, Set<String> technologies, String department, String fieldOfStudy) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.technologies = technologies;
        this.department = department;
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
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
