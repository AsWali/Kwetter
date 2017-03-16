package service.impl;

import dao.inter.TweetDAO;
import domain.Tweet;
import domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Created by Asror on 12-3-2017.
 */
@Stateless
public class TweetServiceImpl {

    @Inject
    private TweetDAO td;

    public Tweet addTweet(Tweet t) {
        return td.create(t);
    }

    public void removeTweet(Long tweetid) {
        td.delete(tweetid);
    }

    public Tweet findById(Long tweetid) {
        return td.find(tweetid);
    }



}
