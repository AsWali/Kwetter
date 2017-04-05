package service.impl;

import dao.inter.HeartsDAO;
import dao.inter.TweetDAO;
import dao.inter.UserDAO;
import domain.Hearts;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.management.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Asror on 12-3-2017.
 */
@Stateless
public class TweetServiceImpl {

    @Inject
    private TweetDAO td;

    @Inject
    private HeartsDAO hd;

    @Inject
    private UserDAO ud;

    public Tweet addTweet(Tweet t) throws NoExistingUser {
        Tweet tweet = td.create(t);
        findMentions(tweet);
        tweet = findHashtags(t);
        return tweet;
    }

    public void removeTweet(Long tweetid) {
        td.delete(tweetid);
    }

    public Tweet findById(Long tweetid) {
        return td.find(tweetid);
    }

    public Hearts heartTweet(Tweet tweet, User user) {
        Hearts h = new Hearts(user, tweet);
        hd.create(h);
        return h;
    }

    public void unheartTweet(Tweet tweet) {
        Hearts h = hd.findbyUserTweet(tweet.getUser().getId(), tweet.getId());
        hd.delete(h.getId());
    }

    public void setTd(TweetDAO td) {
        this.td = td;
    }

    public Tweet findHashtags(Tweet tweet){
        String str = tweet.getTweet();
        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        Matcher mat = MY_PATTERN.matcher(str);
        List<String> strs = new ArrayList<String>();
        while (mat.find()) {
            strs.add(mat.group(1));
        }
        for(String s : strs) {
            tweet.addTrend(s);
        }

        return tweet;
    }

    public void findMentions(Tweet tweet) throws NoExistingUser {
        String str = tweet.getTweet();
        Pattern MY_PATTERN = Pattern.compile("@(\\S+)");
        Matcher mat = MY_PATTERN.matcher(str);
        List<String> strs=new ArrayList<String>();
        while (mat.find()) {
            strs.add(mat.group(1));
        }

        for(String handle : strs) {
            User u = ud.findbyHandle(handle);
            u.addMentions(tweet);
        }
    }

    public List<String> getTrends(){
        return td.getTrends();
    }


}
