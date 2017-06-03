package agh.edu.pl.tai.lineup.domain.joins.aggregate;

import agh.edu.pl.tai.lineup.domain.joins.valueobject.JoinId;
import agh.edu.pl.tai.lineup.domain.joins.valueobject.JoinStatus;
import agh.edu.pl.tai.lineup.domain.project.valueobject.ProjectId;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;

public class Join {

    private JoinId joinId;
    private UserId who;
    private ProjectId projectId;
    private Boolean isInvitation;
    private UserId createdBy;
    private JoinStatus status = JoinStatus.PENDING;

    public Join(JoinId joinId, UserId who, ProjectId projectId, Boolean isInvitation, UserId createdBy) {
        this.joinId = joinId;
        this.who = who;
        this.projectId = projectId;
        this.isInvitation = isInvitation;
        this.createdBy = createdBy;
    }

    public Join(JoinId joinId, UserId who, ProjectId projectId, Boolean isInvitation, UserId createdBy, JoinStatus status) {
        this.joinId = joinId;
        this.who = who;
        this.projectId = projectId;
        this.isInvitation = isInvitation;
        this.createdBy = createdBy;
        this.status = status;
    }

    public void markAsAccepted() {
        status = JoinStatus.ACCEPTED;
    }

    public void markAsDeclined() {
        status = JoinStatus.DECLINED;
    }

    public UserId getWho() {
        return who;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public Boolean getInvitation() {
        return isInvitation;
    }

    public JoinStatus getStatus() {
        return status;
    }

    public JoinId getJoinId() {
        return joinId;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }
}
