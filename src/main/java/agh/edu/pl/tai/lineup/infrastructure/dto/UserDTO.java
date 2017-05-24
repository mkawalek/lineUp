package agh.edu.pl.tai.lineup.infrastructure.dto;

import org.springframework.data.annotation.Id;

import java.util.Set;

public class UserDTO {

    @Id
    private final String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
    private Set<String> technologies;
    private String department;
    private String fieldOfStudy;

    public UserDTO(String id, String email, String password, String firstName, String lastName, Integer age, Set<String> technologies, String department, String fieldOfStudy) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
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
