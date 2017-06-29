package agh.edu.pl.tai.lineup.domain.project.entity;

import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;

import java.util.Set;

public class ProjectParticipants {

    private Set<UserId> projectParticipants;

    public ProjectParticipants(Set<UserId> projectParticipants) {
        this.projectParticipants = projectParticipants;
    }

    public Set<UserId> getProjectParticipants() {
        return projectParticipants;
    }

    public void addParticipant(UserId participant) {
        projectParticipants.add(participant);
    }

    public void removeParticipant(UserId participant) {
        projectParticipants.remove(participant);
    }
}
