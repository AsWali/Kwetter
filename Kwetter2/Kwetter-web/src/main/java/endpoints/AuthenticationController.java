package endpoints;

import domain.UserCredentials;
import exception.AuthenticationException;
import io.swagger.annotations.Api;
import service.impl.AuthenticationServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Asror on 3/21/2017.
 */
@Path("/authentication")
@Api(value = "Authentication")
public class AuthenticationController {

    @Inject
    AuthenticationServiceImpl authenticationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserCredentials test(){
        UserCredentials u = new UserCredentials("A", "b");
        return u;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response authenticateUser(UserCredentials userCredentials) {

        try {

            // Authenticate the user using the credentials provided
            authenticate(userCredentials.getUsername(), userCredentials.getPassword());

            // Issue a token for the user
            String token = issueToken(userCredentials.getUsername());

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.ok(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
        authenticationService.authenticateUser(username, password);
    }

    private String issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        SecureRandom random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        authenticationService.issueToken(username, token);
        return token;
    }
}
