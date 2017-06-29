package agh.edu.pl.tai.lineup.api.controllers;

import agh.edu.pl.tai.lineup.api.ApiDomainConverter;
import agh.edu.pl.tai.lineup.api.requests.user.UserAuthenticationRequest;
import agh.edu.pl.tai.lineup.api.requests.user.UserEditDetailsRequest;
import agh.edu.pl.tai.lineup.api.requests.user.UserRegistrationRequest;
import agh.edu.pl.tai.lineup.api.responses.IdResponse;
import agh.edu.pl.tai.lineup.api.responses.user.UserDetailsResponse;
import agh.edu.pl.tai.lineup.api.responses.user.UserTokenResponse;
import agh.edu.pl.tai.lineup.api.security.AuthenticatedUser;
import agh.edu.pl.tai.lineup.api.security.LoggedUser;
import agh.edu.pl.tai.lineup.domain.user.TokenAuthenticator;
import agh.edu.pl.tai.lineup.domain.user.UserRepository;
import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.Department;
import agh.edu.pl.tai.lineup.domain.user.valueobject.FieldOfStudy;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.domain.valueobject.Technology;
import agh.edu.pl.tai.lineup.infrastructure.RandomIdGenerator;
import agh.edu.pl.tai.lineup.infrastructure.utils.PasswordHasher;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ResourceForbiddenException;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ResourceNotFoundException;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static agh.edu.pl.tai.lineup.infrastructure.utils.Mapper.filterCollection;
import static agh.edu.pl.tai.lineup.infrastructure.utils.Mapper.mapCollection;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final TokenAuthenticator tokenAuthenticator;

    @Autowired
    public UserController(UserRepository userRepository, TokenAuthenticator tokenAuthenticator) {
        this.userRepository = userRepository;
        this.tokenAuthenticator = tokenAuthenticator;
    }

    @RequestMapping(value = "/users", method = POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompletableFuture<UserTokenResponse> registerUser(@RequestBody UserRegistrationRequest request) {
        return userRepository.findByEmail(request.getEmail()).thenApplyAsync(userOpt -> {
            if (!userOpt.isPresent()) {
                return new User(UserId.of(RandomIdGenerator.next()), request.getEmail(), PasswordHasher.encrypt(request.getPassword()), request.getFirstName(),
                        request.getLastName(), request.getTechnologies(), request.getDepartment(), request.getFieldOfStudy());
            } else throw new ValidationException("email_already_in_use");
        }).thenCompose(user -> userRepository.save(user).thenApply(userId -> new UserTokenResponse(userId.getValue(), tokenAuthenticator.provideToken(userId))));
    }

    @RequestMapping(value = "/users/auth", method = POST)
    public CompletableFuture<UserTokenResponse> authenticateUser(@RequestBody UserAuthenticationRequest request) {
        // TODO check if request.email does not contain any JS code ... hole security that can crash our database :(
        return userRepository.findByEmail(request.getEmail()).thenApplyAsync(userOpt ->
                userOpt
                        .filter(user -> user.getEmail().equals(request.getEmail()))
                        .filter(user -> user.getHashedPassword().equals(PasswordHasher.encrypt(request.getPassword())))
                        .map(user -> new UserTokenResponse(user.getUserId().getValue(), tokenAuthenticator.provideToken(user.getUserId())))
                        .orElseThrow(() -> new ValidationException("invalid_credentials")));
    }

    @RequestMapping(value = "/users", method = GET)
    public CompletableFuture<List<UserDetailsResponse>> getUsers(@LoggedUser AuthenticatedUser performer) {
        return userRepository
                .findAll()
                .thenApplyAsync(users -> filterCollection(users, user -> !user.getUserId().equals(performer.getUserId()), Collectors.toList()))
                .thenApplyAsync(users -> mapCollection(users, ApiDomainConverter::toUserResponse, Collectors.toList()));
    }

    @RequestMapping(value = "/users/{userId}", method = GET)
    public CompletableFuture<UserDetailsResponse> getUserDetails(@PathVariable("userId") String userId) {
        return userRepository
                .load(UserId.of(userId))
                .thenApplyAsync(userOpt -> userOpt.orElseThrow(ResourceNotFoundException::new))
                .thenApplyAsync(ApiDomainConverter::toUserResponse);
    }

    @RequestMapping(value = "/users/{userId}", method = PUT)
    public CompletableFuture<IdResponse> editUserDetails(@PathVariable("userId") String userId, @LoggedUser AuthenticatedUser performer, @RequestBody UserEditDetailsRequest request) {
        return userRepository
                .load(UserId.of(userId))
                .thenApplyAsync(user -> user.orElseThrow(ResourceNotFoundException::new))
                .thenApplyAsync(user -> {
                    if (!user.getUserId().equals(performer.getUserId())) throw new ResourceForbiddenException();
                    user.editUserDetails(
                            request.getFirstName(),
                            request.getLastName(),
                            mapCollection(request.getTechnologies(), Technology::valueOf, Collectors.toSet()),
                            FieldOfStudy.valueOf(request.getFieldOfStudy()),
                            Department.valueOf(request.getDepartment()));
                    return user;
                })
                .thenComposeAsync(userRepository::save)
                .thenApplyAsync(UserId::getValue)
                .thenApplyAsync(IdResponse::new);
    }

}