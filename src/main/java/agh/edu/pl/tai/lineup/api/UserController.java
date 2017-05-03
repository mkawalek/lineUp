package agh.edu.pl.tai.lineup.api;

import agh.edu.pl.tai.lineup.api.requests.UserCreationRequest;
import agh.edu.pl.tai.lineup.domain.user.UserRepository;
import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.RandomIdGenerator;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import agh.edu.pl.tai.lineup.api.responses.IdResponse;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public IdResponse createSubmitWorkContest(@RequestBody UserCreationRequest request) throws HttpResponseException {
        User user = new User(UserId.of(RandomIdGenerator.next()), request.getEmail(), request.getPassword());

        userRepository.save(user);

        return new IdResponse(user.getUserId().getValue());
    }
}