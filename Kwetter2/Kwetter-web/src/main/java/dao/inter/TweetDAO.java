package dao.inter;

import domain.Tweet;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Asror on 13-3-2017.
 */
public interface TweetDAO extends GenericDAO<Tweet>  {
    /**
     * Method that returns all the Tweets.
     * @return list of tweets
     */
    List<Tweet> getAll();

    List<String> getTrends();
}

