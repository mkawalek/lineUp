package agh.edu.pl.tai.lineup.api;

import agh.edu.pl.tai.lineup.api.requests.HelloRequest;
import org.apache.http.client.HttpResponseException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String createSubmitWorkContest(@RequestBody HelloRequest request) throws HttpResponseException {
        return "Hello " + request.getName() + " !";
    }
}