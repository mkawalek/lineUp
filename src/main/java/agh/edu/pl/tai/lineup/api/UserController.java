package agh.edu.pl.tai.lineup.api;

import agh.edu.pl.tai.lineup.api.requests.UserCreationRequest;
import agh.edu.pl.tai.lineup.api.responses.RegistrationResponse;
import agh.edu.pl.tai.lineup.domain.user.TokenAuthenticator;
import agh.edu.pl.tai.lineup.domain.user.UserRepository;
import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.RandomIdGenerator;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ValidationException;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final TokenAuthenticator tokenAuthenticator;

    @Autowired
    public UserController(UserRepository userRepository, TokenAuthenticator tokenAuthenticator) {
        this.userRepository = userRepository;
        this.tokenAuthenticator = tokenAuthenticator;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public CompletableFuture<RegistrationResponse> createSubmitWorkContest(@RequestBody UserCreationRequest request) throws HttpResponseException {
        return userRepository.findByEmail(request.getEmail()).thenApplyAsync(users -> {
            if (users.isEmpty()) {
                User user = new User(UserId.of(RandomIdGenerator.next()), request.getEmail(), request.getPassword(), request.getFirstName(),
                request.getLastName(), request.getAge(), request.getSkills(), request.getDepartment(), request.getFieldOfStudy());

                userRepository.save(user);
                return new RegistrationResponse(user.getUserId().getValue(), tokenAuthenticator.provideToken(user.getUserId()));
            } else throw new ValidationException("email already in use");
        });
    }

}