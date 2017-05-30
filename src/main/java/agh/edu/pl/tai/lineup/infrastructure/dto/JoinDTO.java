package agh.edu.pl.tai.lineup.infrastructure.dto;

public class JoinDTO {

    private String joinId;
    private String who;
    private String projectId;
    private Boolean isInvitation;
    private String createdBy;
    private Boolean accepted = false;

    public JoinDTO(String joinId, String who, String projectId, Boolean isInvitation, String createdBy, Boolean accepted) {
        this.joinId = joinId;
        this.who = who;
        this.projectId = projectId;
        this.isInvitation = isInvitation;
        this.createdBy = createdBy;
        this.accepted = accepted;
    }

    public String getJoinId() {
        return joinId;
    }

    public void setJoinId(String joinId) {
        this.joinId = joinId;
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
        return isInvitation;
    }

    public void setInvitation(Boolean invitation) {
        isInvitation = invitation;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
