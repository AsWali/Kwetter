package endpoints;

import domain.Tweet;
import domain.User;
import exception.NoExistingUser;
import exception.NotAuthorized;
import service.impl.TweetServiceImpl;
import service.impl.UserServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Asror on 12-3-2017.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    private UserServiceImpl uservice;

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

    @POST
    @Path("create")
    @Consumes("application/json")
    public void addUser(User user){
        uservice.addUser(user);
    }

    @PUT
    @Path("update")
    @Consumes("application/json")
    public void updateUser(User user){
        User d = uservice.getUser(user.getId());
        d.setName(user.getName());
        d.setPicture(user.getPicture());
        d.setBio(user.getBio());
        d.setLocation(user.getLocation());
        d.setWebsite(user.getWebsite());
        uservice.updateUser(user);
    }

    @GET
    @Path("{userid}/alltweets")
    public Set<Tweet> getAllTweets(@PathParam("userid") Long userid) {
        return uservice.getTweets(userid);
    }

    @POST
    @Path("follower")
    @Consumes("application/json")
    public void addFollower(List<User> users){
        uservice.addFollower(users.get(0).getId(), users.get(1).getId());
    }

    @DELETE
    @Path("{userid}/unfollow/{targetid}")
    public boolean removeFollower(@PathParam("userid") Long userid, @PathParam("targetid") Long targetid ) {
        uservice.removeFollower(userid, targetid);
        return true;
    }

    @GET
    @Path("{userid}/followers")
    public Set<User> getFollowers(@PathParam("userid") Long userid)  {
        return uservice.getFollowers(userid);
    }

    @GET
    @Path("{userid}/following")
    public Set<User> getFollowing(@PathParam("userid") Long userid)  {
        return uservice.getFollowing(userid);
    }

    @GET
    @Path("{handle}/tweets")
    public List<Tweet> getTweets(@PathParam("handle") String handle) {
        return uservice.getRecentTweets(handle);
    }


}
