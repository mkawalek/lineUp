package agh.edu.pl.tai.lineup.domain.user.aggregate;

import agh.edu.pl.tai.lineup.domain.user.valueobject.Department;
import agh.edu.pl.tai.lineup.domain.user.valueobject.FieldOfStudy;
import agh.edu.pl.tai.lineup.domain.user.valueobject.Skill;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.utils.PasswordHasher;
import agh.edu.pl.tai.lineup.infrastructure.utils.Validator;

import java.util.List;

public class User {

    private UserId userId;
    private String email;
    private String hashedPassword;
    private String firstName;
    private String lastName;
    private Integer age;
    private List<Skill> skills;
    private Department department;
    private FieldOfStudy fieldOfStudy;

    public User(UserId userId, String email, String password, String firstName, String lastName, Integer age, List<Skill> skills, Department department, FieldOfStudy fieldOfStudy) {
        validate(email, password, firstName, lastName, age, skills, department, fieldOfStudy);
        this.userId = userId;
        this.email = email;
        this.hashedPassword = PasswordHasher.encrypt(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.skills = skills;
        this.department = department;
        this.fieldOfStudy = fieldOfStudy;
    }

    private void validate(String email, String password, String firstName, String lastName, Integer age, List<Skill> skills, Department department, FieldOfStudy fieldOfStudy) {
        new Validator()
                .onNull(email, "user.email")
                .onNull(password, "user.password")
                .onNull(firstName, "user.firstName")
                .onNull(lastName, "user.lastName")
                .onNull(age, "user.age")
                .onNull(skills, "user.skills")
                .onNull(department, "user.department")
                .onNull(fieldOfStudy, "user.fieldOfStudy")
                .validateAndThrow();

        new Validator()
                .on(!email.isEmpty(), "user.name", "empty")
                .on(!password.isEmpty(), "user.password", "empty")
                .on(!firstName.isEmpty(), "user.firstName", "empty")
                .on(!lastName.isEmpty(), "user.lastName", "empty")
                .on(age > 0, "user.age", "tooLow")
                .on(!skills.isEmpty(), "user.skills", "empty") // TODO check wether department and field of study is OK
                .validateAndThrow();
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

    public Integer getAge() {
        return age;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public Department getDepartment() {
        return department;
    }

    public FieldOfStudy getFieldOfStudy() {
        return fieldOfStudy;
    }
}
