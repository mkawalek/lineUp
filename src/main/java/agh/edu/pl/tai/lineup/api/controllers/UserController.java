package agh.edu.pl.tai.lineup.api.controllers;

import agh.edu.pl.tai.lineup.api.requests.user.UserAuthenticationRequest;
import agh.edu.pl.tai.lineup.api.requests.user.UserRegistrationRequest;
import agh.edu.pl.tai.lineup.api.responses.user.UserTokenResponse;
import agh.edu.pl.tai.lineup.domain.user.TokenAuthenticator;
import agh.edu.pl.tai.lineup.domain.user.UserRepository;
import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.RandomIdGenerator;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompletableFuture<UserTokenResponse> registerUser(@RequestBody UserRegistrationRequest request) {
        return userRepository.findByEmail(request.getEmail()).thenApplyAsync(users -> {
            if (users.isEmpty()) {
                return new User(UserId.of(RandomIdGenerator.next()), request.getEmail(), request.getPassword(), request.getFirstName(),
                        request.getLastName(), request.getTechnologies(), request.getDepartment(), request.getFieldOfStudy());
            } else throw new ValidationException("email_already_in_use");
        }).thenCompose(user -> userRepository.save(user).thenApply(userId -> new UserTokenResponse(userId.getValue(), tokenAuthenticator.provideToken(userId))));
    }

    @RequestMapping(value = "/users/auth", method = RequestMethod.POST)
    public CompletableFuture<UserTokenResponse> authenticateUser(@RequestBody UserAuthenticationRequest request) {
        // TODO check if request.email does not contain any JS code ... hole security that can crash our database :(
        return userRepository.findByEmail(request.getEmail()).thenApplyAsync(users -> {
            if (!users.isEmpty()) {
                return users
                        .stream()
                        .filter(user -> user.getEmail().equals(request.getEmail()))
                        .findFirst()
                        .map(user -> new UserTokenResponse(user.getUserId().getValue(), tokenAuthenticator.provideToken(user.getUserId())))
                        .orElseThrow(() -> new ValidationException("invalid_credentials"));
            } else throw new ValidationException("invalid_credentials");
        });
    }

}