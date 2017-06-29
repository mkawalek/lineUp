package agh.edu.pl.tai.lineup.infrastructure.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GitHubApiRequester {

    @Autowired
    private Environment environment;

    private OkHttpClient client = new OkHttpClient();

    public Integer getCollaborators(String gitHubUrl) throws IOException {
        Pattern p = Pattern.compile("(?:https|http)://github.com/([a-zA-Z0-9]*)/([a-zA-Z0-9]*)");
        Matcher m = p.matcher(gitHubUrl);

        if (m.matches()) {
            Request request = new Request.Builder()
                    .url("https://api.github.com/repos/" + m.group(1) + "/" + m.group(2) + "/collaborators")
                    .addHeader("Authorization", "token " + environment.getProperty("github-token"))
                    .build();

            Response response = client.newCall(request).execute();
            return new ObjectMapper().readValue(response.body().string(), List.class).size();

        } else return -1;
    }

}
