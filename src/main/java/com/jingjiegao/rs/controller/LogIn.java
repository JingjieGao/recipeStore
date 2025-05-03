package com.jingjiegao.rs.controller;

import com.jingjiegao.rs.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * The type Log in.
 * Begins the authentication process using AWS Cognito
 */
@WebServlet(
        urlPatterns = {"/logIn"}
)
public class LogIn extends HttpServlet implements PropertiesLoader {
    private boolean propertiesLoaded = false;
    private final Logger logger = LogManager.getLogger(this.getClass());
    /**
     * The constant CLIENT_ID.
     */
    public static String CLIENT_ID;
    /**
     * The constant LOGIN_URL.
     */
    public static String LOGIN_URL;
    /**
     * The constant REDIRECT_URL.
     */
    public static String REDIRECT_URL;

    @Override
    public void init() throws ServletException {
        super.init();
        loadProperties();
    }

    /**
     * Read in the cognito props file and get the client id and required urls
     * for authenticating a user.
     */
    private void loadProperties() {
        try {
            Properties properties = loadProperties("/cognito.properties");
            CLIENT_ID = properties.getProperty("client.id");
            LOGIN_URL = properties.getProperty("loginURL");
            REDIRECT_URL = properties.getProperty("redirectURL");

            if (CLIENT_ID != null && LOGIN_URL != null && REDIRECT_URL != null) {
                propertiesLoaded = true;
            } else {
                logger.error("One or more properties are missing in cognito.properties");
                propertiesLoaded = false;
            }
        } catch (Exception e) {
            logger.error("Error loading properties: " + e.getMessage(), e);
            propertiesLoaded = false;
        }
    }

    /**
     * Route to the aws-hosted cognito login page.
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if forwarding to the error page fails
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!propertiesLoaded) {
            req.setAttribute("errorMessage", "Unable to load login configuration. Please contact support.");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }

        String url = LOGIN_URL + "?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL;
        resp.sendRedirect(url);
    }
}
