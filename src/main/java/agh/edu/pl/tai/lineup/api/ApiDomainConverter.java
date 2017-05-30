package agh.edu.pl.tai.lineup.api;

import agh.edu.pl.tai.lineup.api.responses.join.JoinResponse;
import agh.edu.pl.tai.lineup.api.responses.project.ProjectResponse;
import agh.edu.pl.tai.lineup.api.responses.user.UserDetailsResponse;
import agh.edu.pl.tai.lineup.domain.joins.aggregate.Join;
import agh.edu.pl.tai.lineup.domain.project.aggregate.Project;
import agh.edu.pl.tai.lineup.domain.project.entity.ProjectTechnologies;
import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.domain.valueobject.Technology;

import java.util.Set;
import java.util.stream.Collectors;

public class ApiDomainConverter {

    public static Set<String> fromProjectTechnologies(ProjectTechnologies projectTechnologies) {
        return projectTechnologies.getTechnologies().stream().map(Enum::name).collect(Collectors.toSet());
    }

    // TODO if technology strings will be invalid - we will have big failures over here :/
    public static ProjectTechnologies toProjectTechnologies(Set<String> projectTechnologies) {
        return new ProjectTechnologies(projectTechnologies.stream().map(Technology::valueOf).collect(Collectors.toSet()));
    }

    public static ProjectResponse toProjectResponse(Project project) {
        return new ProjectResponse(
                project.getProjectId().getValue(),
                project.getName(),
                project.getDescription(),
                project.getVersionControlUrl(),
                project.getProjectTechnologies().getTechnologies().stream().map(Enum::name).collect(Collectors.toSet()),
                project.getProjectParticipants().getProjectParticipants().stream().map(UserId::getValue).collect(Collectors.toSet()),
                project.getProjectStatus().name(),
                project.getOwner().getValue(),
                project.getCreatedAt(),
                project.getEnded()
        );
    }

    public static UserDetailsResponse toUserResponse(User user) {
        return new UserDetailsResponse(
            user.getUserId().getValue(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getTechnologies().stream().map(Enum::name).collect(Collectors.toSet()),
                user.getDepartment().name(),
                user.getFieldOfStudy().name()
        );
    }

    public static JoinResponse toJoinResponse(Join join) {
        return new JoinResponse(
                join.getJoinId().getValue(),
                join.getWho().getValue(),
                join.getProjectId().getValue(),
                join.getCreatedBy().getValue()
        );
    }

}
