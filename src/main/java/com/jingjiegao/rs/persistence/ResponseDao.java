package com.jingjiegao.rs.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingjiegao.rs.recipeApi.Response;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * The type Response dao.
 */
public class ResponseDao {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private static final Dotenv dotenv = Dotenv.load();

    /**
     * The Search url.
     */
    String searchUrl = dotenv.get("RECIPE_API_QUERY_URL");


    /**
     * Search recipe string response.
     *
     * @param searchString the search recipe string
     * @return the response
     * @throws JsonProcessingException the json processing exception
     */
    public Response searchRecipe(String searchString) throws JsonProcessingException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(searchUrl + searchString);
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);

        ObjectMapper mapper = new ObjectMapper();
        logger.debug(response);
        return mapper.readValue(response, Response.class);
    }
}
