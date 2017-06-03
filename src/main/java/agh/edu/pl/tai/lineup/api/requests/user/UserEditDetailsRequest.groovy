package agh.edu.pl.tai.lineup.api.requests.user

class UserEditDetailsRequest {

    private String firstName;
    private String lastName;
    private Set<String> technologies;
    private String department;
    private String fieldOfStudy;

    UserEditDetailsRequest(String firstName, String lastName, Set<String> technologies, String department, String fieldOfStudy) {
        this.firstName = firstName
        this.lastName = lastName
        this.technologies = technologies
        this.department = department
        this.fieldOfStudy = fieldOfStudy
    }

    UserEditDetailsRequest() {}

    String getFirstName() {
        return firstName
    }

    void setFirstName(String firstName) {
        this.firstName = firstName
    }

    String getLastName() {
        return lastName
    }

    void setLastName(String lastName) {
        this.lastName = lastName
    }

    Set<String> getTechnologies() {
        return technologies
    }

    void setTechnologies(Set<String> technologies) {
        this.technologies = technologies
    }

    String getDepartment() {
        return department
    }

    void setDepartment(String department) {
        this.department = department
    }

    String getFieldOfStudy() {
        return fieldOfStudy
    }

    void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy
    }
}
