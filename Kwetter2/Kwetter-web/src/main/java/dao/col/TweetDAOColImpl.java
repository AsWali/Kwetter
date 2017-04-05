package dao.col;

import dao.impl.DAOFacade;
import dao.inter.TweetDAO;
import domain.Tweet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Asror on 23-3-2017.
 */
public class TweetDAOColImpl extends DAOFacade<Tweet> implements TweetDAO {

    List<Tweet> tweets = new ArrayList<>();

    public TweetDAOColImpl(Class<Tweet> entityClass) {
        super(entityClass);
    }

    @Override
    public List<Tweet> getAll() {
        return tweets;
    }

    @Override
    public List<String> getTrends() {
        return null;
    }

    public Tweet createTweet(Tweet t){
        tweets.add(t);
        return t;
    }

    public boolean removeTweet(Long tweetid) {
        for (Iterator<Tweet> iter = tweets.listIterator(); iter.hasNext(); ) {
            Tweet a = iter.next();
            if (a.getId().equals(tweetid)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }
}
