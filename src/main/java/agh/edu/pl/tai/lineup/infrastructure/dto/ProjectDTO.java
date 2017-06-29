package agh.edu.pl.tai.lineup.infrastructure.dto;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public class ProjectDTO {

    @Id
    private final String id;
    private String title;
    private String description;
    private String versionControlUrl;
    private Set<String> technologies;
    private Set<String> participants;
    private String status;
    private String owner;
    private LocalDateTime createdAt;
    private Integer gitHubCollaborators;
    private Optional<LocalDateTime> ended;


    public ProjectDTO(String id, String title, String description, String versionControlUrl, Set<String> technologies, Set<String> participants, String status, String owner, LocalDateTime createdAt, Integer gitHubCollaborators, Optional<LocalDateTime> ended) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.versionControlUrl = versionControlUrl;
        this.technologies = technologies;
        this.participants = participants;
        this.status = status;
        this.owner = owner;
        this.createdAt = createdAt;
        this.gitHubCollaborators = gitHubCollaborators;
        this.ended = ended;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVersionControlUrl() {
        return versionControlUrl;
    }

    public Set<String> getTechnologies() {
        return technologies;
    }

    public Set<String> getParticipants() {
        return participants;
    }

    public String getStatus() {
        return status;
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

    public Integer getGitHubCollaborators() {
        return gitHubCollaborators;
    }

    public void setGitHubCollaborators(Integer gitHubCollaborators) {
        this.gitHubCollaborators = gitHubCollaborators;
    }
}
