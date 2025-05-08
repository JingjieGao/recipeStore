package com.jingjiegao.rs.controller;
/*
 * Inspired by: https://stackoverflow.com/questions/52144721/how-to-get-access-token-using-client-credentials-using-java-code
 */
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingjiegao.rs.auth.*;
import com.jingjiegao.rs.entity.User;
import com.jingjiegao.rs.persistence.GenericDao;
import com.jingjiegao.rs.util.PropertiesLoader;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * The type Auth.
 */
@WebServlet(
        urlPatterns = {"/auth"}
)
public class Auth extends HttpServlet implements PropertiesLoader {
    /**
     * The Properties.
     */
    Properties properties;
    /**
     * The Client id.
     */
    String CLIENT_ID;
    /**
     * The Client secret.
     */
    String CLIENT_SECRET;
    /**
     * The Oauth url.
     */
    String OAUTH_URL;
    /**
     * The Login url.
     */
    String LOGIN_URL;
    /**
     * The Redirect url.
     */
    String REDIRECT_URL;
    /**
     * The Region.
     */
    String REGION;
    /**
     * The Pool id.
     */
    String POOL_ID;
    /**
     * The Jwks.
     */
    Keys jwks;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        loadProperties();
        loadKey();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authCode = req.getParameter("code");

        // Check for missing the authorization code
        if (authCode == null) {
            logger.error("Auth code is missing.");
            req.setAttribute("errorMessage", "Login failed: No auth code received.");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        try {
            // Get the Token from the authorization code
            TokenResponse tokenResponse = getToken(buildAuthRequest(authCode));
            // After validation, the user information is extracted
            User user = validate(tokenResponse, req);
        } catch (IOException | InterruptedException e) {
            logger.error("Token validation failed: " + e.getMessage(), e);
            req.setAttribute("errorMessage", "Login failed due to token validation error.");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        // The request is forwarded to the home page if the token was successfully validated
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    private TokenResponse getToken(HttpRequest authRequest) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<?> response = client.send(authRequest, HttpResponse.BodyHandlers.ofString());

        logger.debug("Response headers: " + response.headers().toString());
        logger.debug("Response body: " + response.body().toString());

        ObjectMapper mapper = new ObjectMapper();
        TokenResponse tokenResponse = mapper.readValue(response.body().toString(), TokenResponse.class);
        logger.debug("Id token: " + tokenResponse.getIdToken());

        return tokenResponse;
    }

    private User validate(TokenResponse tokenResponse, HttpServletRequest req) throws IOException {
        // Extract and parse the header of the JWT received in the tokenResponse
        ObjectMapper mapper = new ObjectMapper();
        CognitoTokenHeader tokenHeader = mapper.readValue(CognitoJWTParser.getHeader(tokenResponse.getIdToken()).toString(), CognitoTokenHeader.class);

        // Get the key id and the algorithm used for signing the JWT
        String keyId = tokenHeader.getKid();
        String alg = tokenHeader.getAlg();

        // Fetch and Decode the Public Key
        BigInteger modulus = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getN()));
        BigInteger exponent = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getE()));

        // Create the Public Key
        PublicKey publicKey = null;
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.error("Key generation error: " + e.getMessage(), e);
        }

        // Set up JWT Verification Algorithm
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);

        // Build JWT Verifier
        String iss = String.format("https://cognito-idp.%s.amazonaws.com/%s", REGION, POOL_ID);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(iss)
                .withClaim("token_use", "id")
                .build();

        // Verify the JWT
        DecodedJWT jwt = verifier.verify(tokenResponse.getIdToken());

        // Extract user-related claims from the decoded JWT
        String cognitoSub = jwt.getClaim("sub").asString();
        String username = jwt.getClaim("cognito:username").asString();
        String email = jwt.getClaim("email").asString();
        String firstName = jwt.getClaim("given_name").asString();
        String lastName = jwt.getClaim("family_name").asString();

        // Check if the User exists in the Database
        GenericDao<User> userDao = new GenericDao<>(User.class);
        List<User> users = userDao.getByPropertyEqual("cognitoSub", cognitoSub);
        User user;

        // Insert a new user, or retrieve the existing one.
        if (users.isEmpty()) {
            user = new User(cognitoSub, username, email, firstName, lastName);
            int userId = userDao.insert(user);
            logger.info("New user inserted with ID: " + userId);
        } else {
            user = users.get(0);
        }

        // Store the user object in the HTTP session.
        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        // Return the User Object
        return user;
    }

    private HttpRequest buildAuthRequest(String authCode) {
        String keys = CLIENT_ID + ":" + CLIENT_SECRET;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("client-secret", CLIENT_SECRET);
        parameters.put("client_id", CLIENT_ID);
        parameters.put("code", authCode);
        parameters.put("redirect_uri", REDIRECT_URL);

        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoding = Base64.getEncoder().encodeToString(keys.getBytes());

        return HttpRequest.newBuilder().uri(URI.create(OAUTH_URL))
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic " + encoding)
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();
    }

    private void loadKey() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            URL jwksURL = new URL(String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", REGION, POOL_ID));
            File jwksFile = new File("jwks.json");
            FileUtils.copyURLToFile(jwksURL, jwksFile);
            jwks = mapper.readValue(jwksFile, Keys.class);
            logger.debug("Keys loaded: e = " + jwks.getKeys().get(0).getE());
        } catch (IOException e) {
            logger.error("Cannot load JWKS: " + e.getMessage(), e);
        }
    }

    private void loadProperties() {
        try {
            properties = loadProperties("/cognito.properties");
            CLIENT_ID = properties.getProperty("client.id");
            CLIENT_SECRET = properties.getProperty("client.secret");
            OAUTH_URL = properties.getProperty("oauthURL");
            LOGIN_URL = properties.getProperty("loginURL");
            REDIRECT_URL = properties.getProperty("redirectURL");
            REGION = properties.getProperty("region");
            POOL_ID = properties.getProperty("poolId");
        } catch (Exception e) {
            logger.error("Error loading properties: " + e.getMessage(), e);
        }
    }
}
