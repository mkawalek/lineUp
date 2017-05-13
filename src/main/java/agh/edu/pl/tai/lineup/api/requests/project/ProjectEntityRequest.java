package agh.edu.pl.tai.lineup.api.requests.project;

import java.util.Optional;
import java.util.Set;

public class ProjectEntityRequest {

    private String name;
    private String description;
    private Optional<String> versionControlUrl;
    private Set<String> projectTechnologies;

    public ProjectEntityRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getProjectTechnologies() {
        return projectTechnologies;
    }

    public void setProjectTechnologies(Set<String> projectTechnologies) {
        this.projectTechnologies = projectTechnologies;
    }

    public Optional<String> getVersionControlUrl() {
        return versionControlUrl;
    }

    public void setVersionControlUrl(Optional<String> versionControlUrl) {
        this.versionControlUrl = versionControlUrl;
    }
}
