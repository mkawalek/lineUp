package agh.edu.pl.tai.lineup.infrastructure;

import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.dto.UserDTO;

public class DTODomainConverter {

    public static User fromUserDTO(UserDTO userDTO) {
        return new User(UserId.of(userDTO.getUserId()), userDTO.getEmail(), userDTO.getPassword());
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUserId().getValue(), user.getEmail(), user.getHashedPassword());
    }

}
