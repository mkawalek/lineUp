package agh.edu.pl.tai.lineup.api.requests.user;

import java.util.Set;

public class UserEditDetailsRequest {

    private String firstName;
    private String lastName;
    private Set<String> technologies;
    private String department;
    private String fieldOfStudy;

    UserEditDetailsRequest(String firstName, String lastName, Set<String> technologies, String department, String fieldOfStudy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.technologies = technologies;
        this.department = department;
        this.fieldOfStudy = fieldOfStudy;
    }

    UserEditDetailsRequest() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<String> technologies) {
        this.technologies = technologies;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }
}
