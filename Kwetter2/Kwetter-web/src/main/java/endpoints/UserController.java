package endpoints;

import domain.Hearts;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;
import exception.NotAuthorized;
import io.swagger.annotations.Api;
import service.impl.UserServiceImpl;
import utils.PermissionsEnum;
import annotation.Secured;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

/**
 * Created by Asror on 12-3-2017.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "User")
@RolesAllowed("USER")
public class UserController {

    @Inject
    private UserServiceImpl uservice;

    /**
     * Return an User object without the tweets, hearts, followers and following.
     * Search is done on the User Handle.
     * @param handle the Users handle
     * @return User Object
     * @throws NoExistingUser
     */
    @GET
    @Path("{handle}/profile")
    public Object getProfile(@PathParam("handle") String handle) throws NoExistingUser {
        try {
            User t = uservice.getUserbyHandle(handle);
            return t;
        } catch(NoExistingUser ex) {
            throw ex;
        }
    }

    /**
     * Method that adds an user.
     * @param user User Object
     * @return the created User
     */
    @POST
    @Path("")
    @Consumes("application/json")
    public User addUser(User user) {
            return uservice.addUser(user);
    }

    /**
     * Method that removes an user. Only executes when the Target User and Sender User are the same.
     * @param user Target User
     * @return true or false
     * @throws NotAuthorized
     */
    @DELETE
    @Path("")
    @Consumes("application/json")
    public boolean removeUser(User user) throws NotAuthorized {
        if(uservice.getLoggedUser().getId().equals(user.getId())) {
            uservice.removeUser(user.getId());
        } else {
            throw new NotAuthorized("You are not allowed !");
        }
        return true;
    }

    /**
     * Method that updates and user object. Mostly bio, name, webste and name.
     * @param user User Object
     * @return
     */
    @PUT
    @Path("")
    @Consumes("application/json")
    public User updateUser(User user){
        return uservice.updateUser(user);
    }

    /**
     * Method that returns all the tweets that belong to the userid
     * @param userid User Id
     * @return a list of tweets
     */
    @GET
    @Path("{userid}/alltweets")
    public Set<Tweet> getAllTweets(@PathParam("userid") Long userid) {
        return uservice.getTweets(userid);
    }

    /**
     * Method that add an user to another user followers list.
     * @param user Sender Owner and Target Owner
     */
    @POST
    @Path("follow")
    @Consumes("application/json")
    public void addFollower(User user){
        uservice.addFollower(uservice.getLoggedUser().getId(), user.getId());
    }

    /**
     * Method that unfollows an user
     * @param targetid Sender Target Id
     * @return true or false
     */
    @DELETE
    @Path("unfollow/{targetid}")
    public boolean removeFollower(@PathParam("targetid") Long targetid ) {
        uservice.removeFollower(uservice.getLoggedUser().getId(), targetid);
        return true;
    }

    /**
     * Method that returns all the followers of an User
     * @param userid User id
     * @return list of users
     */
    @GET
    @Path("{userid}/followers")
    public Set<User> getFollowers(@PathParam("userid") Long userid)  {
        return uservice.getFollowers(userid);
    }

    /**
     * Method that return all the User the user follows
     * @param userid User id
     * @return list of users
     */
    @GET
    @Path("{userid}/following")
    public Set<User> getFollowing(@PathParam("userid") Long userid)  {
        return uservice.getFollowing(userid);
    }

    /**
     * Method that return the most recent tweets of an user
     * @param handle User Handle
     * @return a list of tweets
     */
    @GET
    @Path("{handle}/tweets")
    public List<Tweet> getTweets(@PathParam("handle") String handle) {
        return uservice.getRecentTweets(handle);
    }

    /**
     * Method that returns all the Hearts an user has given.
     * @param userid User id
     * @return list of Hearts
     */
    @GET
    @Path("{userid}/hearts")
    public Set<Hearts> getHearted(@PathParam("userid") Long userid) {
        return uservice.getHearted(userid);
    }


}
