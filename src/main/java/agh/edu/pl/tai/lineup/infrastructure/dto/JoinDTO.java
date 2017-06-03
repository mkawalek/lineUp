package agh.edu.pl.tai.lineup.infrastructure.dto;

import org.springframework.data.annotation.Id;

public class JoinDTO {

    @Id
    private String id;
    private String who;
    private String projectId;
    private Boolean invitation;
    private String createdBy;
    private Boolean accepted = false;

    public JoinDTO(String id, String who, String projectId, Boolean invitation, String createdBy, Boolean accepted) {
        this.id = id;
        this.who = who;
        this.projectId = projectId;
        this.invitation = invitation;
        this.createdBy = createdBy;
        this.accepted = accepted;
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

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
