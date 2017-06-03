package agh.edu.pl.tai.lineup.api.requests.project;

import java.util.Optional;
import java.util.Set;

public class ProjectEntityRequest {

    private String title;
    private String description;
    private Optional<String> versionControlUrl;
    private Set<String> technologies;

    public ProjectEntityRequest() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<String> technologies) {
        this.technologies = technologies;
    }

    public Optional<String> getVersionControlUrl() {
        return versionControlUrl;
    }

    public void setVersionControlUrl(Optional<String> versionControlUrl) {
        this.versionControlUrl = versionControlUrl;
    }
}
