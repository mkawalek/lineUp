package agh.edu.pl.tai.lineup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
<<<<<<< HEAD
=======
import de.flapdoodle.embed.process.runtime.Network;
>>>>>>> 6029fbd3add4762aa1cd53ff72ec91b811c3611b
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
<<<<<<< HEAD
=======
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
>>>>>>> 6029fbd3add4762aa1cd53ff72ec91b811c3611b
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class TestUtils {

    private static MongodExecutable mongodExecutable;

    public static String convertObjectToJSONFormat(Object content) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(content);
    }

    public static String retrieveIdFromJSONResponse(MvcResult JSONResponse) throws UnsupportedEncodingException {
        String JSONResponseAsString = JSONResponse.getResponse().getContentAsString();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(JSONResponseAsString);

        matcher.find();
        return matcher.group();
    }

    public static void arrangeEmbeddedMongo(int embeddedMongoMinPort, int embeddedMongoMaxPort) throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        Random randomPortGenerator = new Random();

        int port = randomPortGenerator.nextInt(embeddedMongoMaxPort - embeddedMongoMinPort) + embeddedMongoMinPort;

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
<<<<<<< HEAD
                .net(new Net(port, false))
                .build();

        System.out.println("WSTAJE MONGO");

=======
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

>>>>>>> 6029fbd3add4762aa1cd53ff72ec91b811c3611b
        try {
            mongodExecutable = starter.prepare(mongodConfig);
            mongodExecutable.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopEmbeddedMongo() {
        if (mongodExecutable != null)
            mongodExecutable.stop();
    }

    public static void setUpMockMvc(WebApplicationContext webApplicationContext) {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    private static MockMvc mockMvc;

    private static MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    public static ResultActions performPostWithJSON(String url, String postRequestInJSON) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(contentType)
                .content(postRequestInJSON));
    }

    public static ResultActions performPost(String url) throws Exception {
        return mockMvc.perform(post(url).contentType(contentType));
    }

    public static ResultActions performGet(String url) throws Exception {
        return mockMvc.perform(get(url)
                .contentType(contentType));
    }

    public static ResultActions performPut(String url) throws Exception {
        return mockMvc.perform(put(url)
                .contentType(contentType));
    }

    public static ResultActions performDelete(String url) throws Exception {
        return mockMvc.perform(delete(url)
                .contentType(contentType));
    }

    public static ResultActions performPutWithJSON(String url, String putRequestInJSON) throws Exception {
        return mockMvc.perform(put(url)
                .contentType(contentType)
                .content(putRequestInJSON));
    }

}
