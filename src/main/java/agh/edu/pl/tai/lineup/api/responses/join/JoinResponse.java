package agh.edu.pl.tai.lineup.api.responses.join;

public class JoinResponse {

    private String joinId;
    private String who;
    private String projectId;
    private String createdBy;

    public JoinResponse(String joinId, String who, String projectId, String createdBy) {
        this.joinId = joinId;
        this.who = who;
        this.projectId = projectId;
        this.createdBy = createdBy;
    }

    public String getJoinId() {
        return joinId;
    }

    public String getWho() {
        return who;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getCreatedBy() {
        return createdBy;
    }
}
