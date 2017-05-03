package agh.edu.pl.tai.lineup.api;

import agh.edu.pl.tai.lineup.api.requests.UserCreationRequest;
import agh.edu.pl.tai.lineup.api.responses.EmailResponse;
import agh.edu.pl.tai.lineup.api.responses.IdResponse;
import agh.edu.pl.tai.lineup.domain.user.UserRepository;
import agh.edu.pl.tai.lineup.domain.user.aggregate.User;
import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;
import agh.edu.pl.tai.lineup.infrastructure.RandomIdGenerator;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ResourceNotFoundException;
import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ValidationException;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public CompletableFuture<IdResponse> createSubmitWorkContest(@RequestBody UserCreationRequest request) throws HttpResponseException {
        return userRepository.findByEmail(request.getEmail()).thenApplyAsync(users -> {
            if (users.isEmpty()) {
                User user = new User(UserId.of(RandomIdGenerator.next()), request.getEmail(), request.getPassword());

                userRepository.save(user);

                return new IdResponse(user.getUserId().getValue());
            } else throw new ValidationException("email already in use");
        });
    }

    @RequestMapping(value = "/users/{userId}")
    public CompletableFuture<EmailResponse> getUsersEmail(@PathVariable("userId") String userId) {
        return userRepository
                .load(UserId.of(userId))
                .thenApplyAsync(user -> user
                        .map(User::getEmail)
                        .map(EmailResponse::new)
                        .orElseThrow(ResourceNotFoundException::new));
    }

}