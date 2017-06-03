package agh.edu.pl.tai.lineup.infrastructure.dto;

import org.springframework.data.annotation.Id;

public class JoinDTO {

    @Id
    private final String id;
    private String who;
    private String projectId;
    private Boolean invitation;
    private String createdBy;
    private String status;

    public JoinDTO(String id, String who, String projectId, Boolean invitation, String createdBy, String status) {
        this.id = id;
        this.who = who;
        this.projectId = projectId;
        this.invitation = invitation;
        this.createdBy = createdBy;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getWho() {
        return who;
    }

    public String getProjectId() {
        return projectId;
    }

    public Boolean getInvitation() {
        return invitation;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getStatus() {
        return status;
    }

}
