package agh.edu.pl.tai.lineup.api.responses.project;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public class ProjectResponse {

    private String projectId;
    private String name;
    private String description;
    private String versionControlUrl;
    private Set<String> projectTechnologies;
    private Set<String> projectParticipants;
    private String projectStatus;
    private String owner;
    private LocalDateTime createdAt;
    private Optional<LocalDateTime> ended;

    public ProjectResponse(String projectId, String name, String description, String versionControlUrl, Set<String> projectTechnologies, Set<String> projectParticipants, String projectStatus, String owner, LocalDateTime createdAt, Optional<LocalDateTime> ended) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.versionControlUrl = versionControlUrl;
        this.projectTechnologies = projectTechnologies;
        this.projectParticipants = projectParticipants;
        this.projectStatus = projectStatus;
        this.owner = owner;
        this.createdAt = createdAt;
        this.ended = ended;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVersionControlUrl() {
        return versionControlUrl;
    }

    public Set<String> getProjectTechnologies() {
        return projectTechnologies;
    }

    public Set<String> getProjectParticipants() {
        return projectParticipants;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public String getOwner() {
        return owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Optional<LocalDateTime> getEnded() {
        return ended;
    }
}
