package agh.edu.pl.tai.lineup.infrastructure;

import agh.edu.pl.tai.lineup.domain.joins.aggregate.Join;
import agh.edu.pl.tai.lineup.domain.joins.valueobject.JoinId;
import agh.edu.pl.tai.lineup.domain.project.aggregate.Project;
import agh.edu.pl.tai.lineup.domain.project.entity.ProjectParticipants;
import agh.edu.pl.tai.lineup.domain.project.entity.ProjectTechnologies;
import agh.edu.pl.tai.lineup.domain.project.valueobject.ProjectId;
import agh.edu.pl.tai.lineup.domain.project.valueobject.ProjectStatus;
import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.Department;
import agh.edu.pl.tai.lineup.domain.user.valueobject.FieldOfStudy;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.domain.valueobject.Technology;
import agh.edu.pl.tai.lineup.infrastructure.dto.JoinDTO;
import agh.edu.pl.tai.lineup.infrastructure.dto.ProjectDTO;
import agh.edu.pl.tai.lineup.infrastructure.dto.UserDTO;

import java.util.stream.Collectors;

public class DTODomainConverter {

    public static User fromUserDTO(UserDTO userDTO) {
        return new User(
                UserId.of(userDTO.getId()),
                userDTO.getEmail(),
                userDTO.getHashedPassword(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getTechnologies().stream().map(Technology::valueOf).collect(Collectors.toSet()),
                Department.valueOf(userDTO.getDepartment()),
                FieldOfStudy.valueOf(userDTO.getFieldOfStudy())
        );
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getUserId().getValue(),
                user.getEmail(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getTechnologies().stream().map(Enum::name).collect(Collectors.toSet()),
                user.getDepartment().name(),
                user.getFieldOfStudy().name()
        );
    }

    public static Project fromProjectDTO(ProjectDTO projectDTO) {
        return new Project(
                ProjectId.of(projectDTO.getId()),
                projectDTO.getTitle(),
                projectDTO.getDescription(),
                projectDTO.getVersionControlUrl(),
                UserId.of(projectDTO.getOwner()),
                new ProjectTechnologies(projectDTO.getTechnologies().stream().map(Technology::valueOf).collect(Collectors.toSet())),
                new ProjectParticipants(projectDTO.getParticipants().stream().map(UserId::of).collect(Collectors.toSet())),
                ProjectStatus.valueOf(projectDTO.getStatus()),
                projectDTO.getCreatedAt(),
                projectDTO.getEnded()
        );
    }

    public static ProjectDTO toProjectDTO(Project project) {
        return new ProjectDTO(
                project.getProjectId().getValue(),
                project.getName(),
                project.getDescription(),
                project.getVersionControlUrl(),
                project.getProjectTechnologies().getTechnologies().stream().map(Enum::name).collect(Collectors.toSet()),
                project.getProjectParticipants().getProjectParticipants().stream().map(UserId::getValue).collect(Collectors.toSet()),
                project.getStatus().name(),
                project.getOwner().getValue(),
                project.getCreatedAt(),
                project.getEnded()
        );
    }

    public static JoinDTO toJoinDTO(Join join) {
        return new JoinDTO(
                join.getJoinId().getValue(),
                join.getWho().getValue(),
                join.getProjectId().getValue(),
                join.getInvitation(),
                join.getCreatedBy().getValue(),
                join.getAccepted()
        );
    }

    public static Join fromJoinDTO(JoinDTO joinDTO) {
        return new Join(
                JoinId.of(joinDTO.getId()),
                UserId.of(joinDTO.getWho()),
                ProjectId.of(joinDTO.getProjectId()),
                joinDTO.getInvitation(),
                UserId.of(joinDTO.getCreatedBy())
        );

    }

}
