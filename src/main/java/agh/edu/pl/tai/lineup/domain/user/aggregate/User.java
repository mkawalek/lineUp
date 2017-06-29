package agh.edu.pl.tai.lineup.domain.user.aggregate;

import agh.edu.pl.tai.lineup.domain.user.valueobject.Department;
import agh.edu.pl.tai.lineup.domain.user.valueobject.FieldOfStudy;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.domain.valueobject.Technology;
import agh.edu.pl.tai.lineup.infrastructure.utils.Validator;

import java.util.Set;

public class User {

    private UserId userId;
    private String email;
    private String hashedPassword;
    private String firstName;
    private String lastName;
    private Set<Technology> technologies;
    private Department department;
    private FieldOfStudy fieldOfStudy;


    public User(UserId userId, String email, String hashedPassword, String firstName, String lastName, Set<Technology> technologies, Department department, FieldOfStudy fieldOfStudy) {
        validate(email, hashedPassword, firstName, lastName, technologies, department, fieldOfStudy);
        this.userId = userId;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.technologies = technologies;
        this.department = department;
        this.fieldOfStudy = fieldOfStudy;
    }

    private void validate(String email, String hashedPassword, String firstName, String lastName, Set<Technology> technologies, Department department, FieldOfStudy fieldOfStudy) {
        validateOnEdit(firstName, lastName, technologies, fieldOfStudy, department);

        new Validator()
                .onNull(email, "user.email")
                .onNull(hashedPassword, "user.password")
                .validateAndThrow();

        new Validator()
                .on(!email.isEmpty(), "user.email", "empty")
                .on(!hashedPassword.isEmpty(), "user.password", "empty")
                .on(email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"), "user.email", "invalid")
                .validateAndThrow();
    }

    private void validateOnEdit(String firstName, String lastName, Set<Technology> technologies, FieldOfStudy fieldOfStudy, Department department) {
        new Validator()
                .onNull(firstName, "user.firstName")
                .onNull(lastName, "user.lastName")
                .onNull(technologies, "user.technologies")
                .onNull(department, "user.department")
                .onNull(fieldOfStudy, "user.fieldOfStudy")
                .validateAndThrow();

        new Validator()
                .on(!firstName.isEmpty(), "user.firstName", "empty")
                .on(!lastName.isEmpty(), "user.lastName", "empty")
                .validateAndThrow();
    }

    public void editUserDetails(String firstName, String lastName, Set<Technology> technologies, FieldOfStudy fieldOfStudy, Department department) {
        validateOnEdit(firstName, lastName, technologies, fieldOfStudy, department);

        this.firstName = firstName;
        this.lastName = lastName;
        this.technologies = technologies;
        this.fieldOfStudy = fieldOfStudy;
        this.department = department;
    }

    public UserId getUserId() {
        return userId;
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

    public Set<Technology> getTechnologies() {
        return technologies;
    }

    public Department getDepartment() {
        return department;
    }

    public FieldOfStudy getFieldOfStudy() {
        return fieldOfStudy;
    }
}
