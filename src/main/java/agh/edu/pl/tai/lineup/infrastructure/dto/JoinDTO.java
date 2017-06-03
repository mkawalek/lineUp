package agh.edu.pl.tai.lineup.infrastructure.dto;

import org.springframework.data.annotation.Id;

public class JoinDTO {

    @Id
    private String id;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Boolean getInvitation() {
        return invitation;
    }

    public void setInvitation(Boolean invitation) {
        this.invitation = invitation;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
