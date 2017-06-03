package agh.edu.pl.tai.lineup.domain.project.aggregate;

import agh.edu.pl.tai.lineup.domain.project.entity.ProjectParticipants;
import agh.edu.pl.tai.lineup.domain.project.entity.ProjectTechnologies;
import agh.edu.pl.tai.lineup.domain.project.valueobject.ProjectId;
import agh.edu.pl.tai.lineup.domain.project.valueobject.ProjectStatus;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.utils.Validator;
import com.google.common.collect.Sets;

import java.time.LocalDateTime;
import java.util.Optional;

public class Project {

    private ProjectId projectId;
    private String name;
    private String description;
    private String versionControlUrl;
    private UserId owner;
    private ProjectTechnologies technologies;
    private ProjectParticipants participants;
    private ProjectStatus status = ProjectStatus.INACTIVE;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Optional<LocalDateTime> ended = Optional.empty();

    public Project(ProjectId projectId, String name, String description, String versionControlUrl, UserId owner, ProjectTechnologies technologies) {
        validate(name, description, versionControlUrl, technologies);
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.technologies = technologies;
        this.versionControlUrl = versionControlUrl;
        this.participants = new ProjectParticipants(Sets.newHashSet(owner));
    }

    public Project(ProjectId projectId, String name, String description, String versionControlUrl, UserId owner, ProjectTechnologies technologies, ProjectParticipants participants, ProjectStatus status, LocalDateTime createdAt, Optional<LocalDateTime> ended) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.versionControlUrl = versionControlUrl;
        this.owner = owner;
        this.technologies = technologies;
        this.participants = participants;
        this.status = status;
        this.createdAt = createdAt;
        this.ended = ended;
    }

    private void validate(String name, String description, String versionControlUrl, ProjectTechnologies projectTechnologies) {
        new Validator()
                .onNull(name, "project.name")
                .onNull(description, "project.description")
                .onNull(versionControlUrl, "project.version.control.url")
                .onNull(projectTechnologies, "project.technologies");

        new Validator()
                .on(!name.isEmpty(), "project.name", "empty")
                .on(!description.isEmpty(), "project.description", "empty")
                .on(!projectTechnologies.getTechnologies().isEmpty(), "project.version.control.url", "empty");
    }

    public void editProject(String name, String description, String versionControlUrl, ProjectTechnologies projectTechnologies) {
        validate(name, description, versionControlUrl, projectTechnologies);
        this.name = name;
        this.description = description;
        this.versionControlUrl = versionControlUrl;
        this.technologies = projectTechnologies;
    }


    public void endProject() {
        this.ended = Optional.of(LocalDateTime.now());
        this.status = ProjectStatus.INACTIVE;
    }

    public void setAsInactive() {
        this.status = ProjectStatus.INACTIVE;
    }

    public void activate() {
        this.ended = Optional.empty(); // if we use endProject and then we would like to reactive'ate it, we should clear 'ended' field
        this.status = ProjectStatus.ACTIVE;
    }

    public void addParticipant(UserId participant) {
        this.participants.addParticipant(participant);
    }

    public void removeParticipant(UserId participant) {
        this.participants.removeParticipant(participant);
    }

    public ProjectId getProjectId() {
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

    public UserId getOwner() {
        return owner;
    }

    public ProjectTechnologies getTechnologies() {
        return technologies;
    }

    public ProjectParticipants getParticipants() {
        return participants;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Optional<LocalDateTime> getEnded() {
        return ended;
    }
}
