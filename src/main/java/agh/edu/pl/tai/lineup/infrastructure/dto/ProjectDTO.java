package agh.edu.pl.tai.lineup.infrastructure.dto;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public class ProjectDTO {

    @Id
    private final String id;
    private String name;
    private String description;
    private Optional<String> versionControlUrl;
    private Set<String> projectTechnologies;
    private Set<String> projectParticipants;
    private String projectStatus;
    private String owner;
    private LocalDateTime createdAt;
    private Optional<LocalDateTime> ended;


    public ProjectDTO(String id, String name, String description, Optional<String> versionControlUrl, Set<String> projectTechnologies, Set<String> projectParticipants, String projectStatus, String owner, LocalDateTime createdAt, Optional<LocalDateTime> ended) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Optional<String> getVersionControlUrl() {
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
