package endpoints;

import annotation.Secured;
import domain.Hearts;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;
import exception.NotAuthorized;
import io.swagger.annotations.Api;
import service.impl.TweetServiceImpl;
import service.impl.UserServiceImpl;
import utils.PermissionsEnum;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Asror on 15-3-2017.
 */
@Path("/tweet")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Tweet")
public class TweetController {

    @Inject
    private TweetServiceImpl tservice;

    /**
     * Method that finds and returns an tweet based on tweet id
     * @param tweetid Tweet id
     * @return the found Tweet
     */
    @GET
    @Path("{tweetid}")
    public Tweet getTweet(@PathParam("tweetid") Long tweetid)  {
        return tservice.findById(tweetid);
    }

    /**
     * Method that creates an Tweet.
     * @param tweet Tweet object containing an User object with id
     */
    @POST
    @Path("create")
    @Consumes("application/json")
    public void addTweet(Tweet tweet) throws NoExistingUser {
        tservice.addTweet(tweet);
    }

    /**
     * Method that deletes an Tweet. Tweet will only be deleted if its an Tweet that belongs to the User that wants to delete it.
     * Otherwise it will throw an NotAuthorized Exception
     * @param tweet Tweet object with user id
     * @return true or false
     * @throws NotAuthorized
     */
    @DELETE
    @Path("delete")
    @Consumes("application/json")
    public boolean deleteTweet(Tweet tweet) throws NotAuthorized {
        Tweet t = tservice.findById(tweet.getId());
        if(tweet.getUser().getId().equals(t.getUser().getId())) {
            tservice.removeTweet(t.getId());
        } else {
            throw new NotAuthorized("You are not allowed !");
        }
        return true;
    }

    /**
     * Method that creates and Heart object with links to an Tweet and User
     * @param tweet tweet the User wants to Heart
     * @return Heart object
     */
    @POST
    @Path("heart")
    @Consumes("application/json")
    public Hearts heartTweet(Tweet tweet, User user){
        return tservice.heartTweet(tweet, user);
    }

    /**
     * Method that deletes an Hearth object.
     * @param tweet tweet the User wants to unheart
     * @return true of false
     * @throws NotAuthorized
     */
    @DELETE
    @Path("heart")
    @Consumes("application/json")
    public boolean unheartTweet(Tweet tweet) throws NotAuthorized {
        tservice.unheartTweet(tweet);
        return true;
    }
}
