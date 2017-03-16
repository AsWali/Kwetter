package endpoints;

import domain.Tweet;
import domain.User;
import exception.NotAuthorized;
import service.impl.TweetServiceImpl;
import service.impl.UserServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Asror on 15-3-2017.
 */
@Path("/tweet")
@Produces(MediaType.APPLICATION_JSON)
public class TweetController {

    @Inject
    private TweetServiceImpl tservice;

    @GET
    @Path("{tweetid}")
    public Tweet getTweet(@PathParam("tweetid") Long tweetid)  {
        return tservice.findById(tweetid);
    }

    @POST
    @Path("create")
    @Consumes("application/json")
    public void addTweet(Tweet tweet){
        tservice.addTweet(tweet);
    }

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
}
