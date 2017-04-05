package service.impl;

import dao.inter.UserDAO;
import domain.Token;
import domain.User;
import exception.AuthenticationException;
import exception.NoExistingUser;
import org.apache.commons.lang3.time.DateUtils;
import utils.AuthState;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;

/**
 * Created by Asror on 3/21/2017.
 */
@Stateless
public class AuthenticationServiceImpl {

    @Inject
    UserDAO userDAO;

    @Inject
    AuthState authState;

    /**
     * Tries to see if the user already has a token, if not it tries to authenticate the user
     * (This method doesn't create a token, look at issueToken().
     * @param handle
     * @param password
     * @throws AuthenticationException
     */
    public void authenticateUser(String handle, String password) throws AuthenticationException, NoExistingUser {
        //Check if the authenticating user already has a valid token
        //if it doesn't create a new token for this user
        User user = userDAO.findbyHandle(handle);

        //Check if password is invalid
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Incorrect Credentials");
        }
    }

    /**
     * Creates a token and adds it to the requesting user
     * @param username
     * @param token
     */
    public void issueToken(String username, String token) {
        int tokenDuration = 10;
        Date expiryDate = DateUtils.addMinutes(new Date(), tokenDuration);

        authState.addToken(new Token(username, token, expiryDate));
    }
}
