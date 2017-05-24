package agh.edu.pl.tai.lineup.api.requests.user;

import agh.edu.pl.tai.lineup.domain.user.valueobject.Department;
import agh.edu.pl.tai.lineup.domain.user.valueobject.FieldOfStudy;
import agh.edu.pl.tai.lineup.domain.valueobject.Technology;

import java.util.Set;

public class UserRegistrationRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<Technology> technologies;
    private Department department;
    private FieldOfStudy fieldOfStudy;

    public UserRegistrationRequest() {}

    public UserRegistrationRequest(String email, String password, String firstName, String lastName, Set<Technology> technologies, Department department, FieldOfStudy fieldOfStudy) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.technologies = technologies;
        this.department = department;
        this.fieldOfStudy = fieldOfStudy;
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

    public Set<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<Technology> technologies) {
        this.technologies = technologies;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public FieldOfStudy getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }
}
