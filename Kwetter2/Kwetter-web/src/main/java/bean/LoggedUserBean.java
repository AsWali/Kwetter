package bean;

import domain.Hearts;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;
import service.impl.TweetServiceImpl;
import service.impl.UserServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Asror on 2-4-2017.
 */
@ManagedBean(name = "LoggedUserBean")
@SessionScoped
public class LoggedUserBean implements Serializable {

    @Inject
    private UserServiceImpl userService;

    @Inject
    private TweetServiceImpl tweetService;

    private User user;
    private int tweetsamount;
    private int followersamount;
    private int followingamount;
    private int mentionsamount;
    private int heartsamount;

    private ArrayList<Tweet> recenttweets;
    private String tweetcontent;
    private ArrayList<Hearts> heartedtweets;
    private ArrayList<Tweet> timelinetweets;
    private ArrayList<Tweet> mentionedtweets;

    private ArrayList<String> trends;
    private ArrayList<User> following;
    private ArrayList<User> followers;

    public User getUser() throws NoExistingUser {
        if (user == null) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String userName = context.getUserPrincipal().getName();
            user = userService.getUserbyHandle(userName);
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTweetsamount() {
        Set<Tweet> tweets = userService.getTweets(user.getId());
        return tweets.size();
    }

    public void setTweetsamount(int tweetsamount) {
        this.tweetsamount = tweetsamount;
    }

    public int getFollowersamount() {
        Set<User> followers = userService.getFollowers(user.getId());
        return followers.size();
    }

    public void setFollowersamount(int followersamount) {
        this.followersamount = followersamount;
    }

    public int getFollowingamount() {
        Set<User> following = userService.getFollowing(user.getId());
        return following.size();
    }

    public void setFollowingamount(int followingamount) {
        this.followingamount = followingamount;
    }

    public String register(String username, String password){
        User d = new User(username, password, "", username, "Please edit ur bio", "Uknown", "Unkwown");
        userService.addUser(d);
        return "/home.xhtml?faces-redirect=true";
    }

    public String logout(){
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "/home.xhtml?faces-redirect=true";
    }

    public String createTweet() throws NoExistingUser {
        tweetService.addTweet(new Tweet(tweetcontent, user));
        tweetcontent = "";
        return "viewid?faces-redirect=true";
    }

    public void heartTweet(){
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("id");
        Tweet tweet = tweetService.findById(Long.valueOf(id).longValue());
        tweetService.heartTweet(tweet, user);
    }

    public String getTweetcontent() {
        return tweetcontent;
    }

    public void setTweetcontent(String tweetcontent) {
        this.tweetcontent = tweetcontent;
    }

    public List<Tweet> getTimelinetweets() {
        timelinetweets = new ArrayList<>();
        for (User u : userService.getFollowing(user.getId())) {
            timelinetweets.addAll(userService.getTweets(u.getId()));
        }
        timelinetweets.addAll(userService.getTweets(user.getId()));

        Collections.sort(timelinetweets, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet o1, Tweet o2) {
                if (o1.getDate().before(o2.getDate()))
                    return 1;
                if (o1.getDate().after(o2.getDate()))
                    return -1;
                return 0;
            }
        });

        return timelinetweets;
    }

    public ArrayList<Tweet> getRecenttweets() {
        recenttweets = new ArrayList<>();
        recenttweets.addAll(userService.getRecentTweets(user.getHandle()));
        return recenttweets;
    }

    public void setRecenttweets(ArrayList<Tweet> recenttweets) {
        this.recenttweets = recenttweets;
    }

    public ArrayList<Hearts> getHeartedtweets() {
        heartedtweets = new ArrayList<>();
        heartedtweets.addAll(userService.getHearted(user.getId()));
        return heartedtweets;
    }

    public void setHeartedtweets(ArrayList<Hearts> heartedtweets) {
        this.heartedtweets = heartedtweets;
    }

    public void setTimelinetweets(ArrayList<Tweet> timelinetweets) {
        this.timelinetweets = timelinetweets;
    }

    public int getMentionsamount() throws NoExistingUser {
        User u = userService.getUserbyHandle(user.getHandle());
        Set<Tweet> mentions = u.getMentions();
        return mentions.size();
    }

    public void setMentionsamount(int mentionsamount) {
        this.mentionsamount = mentionsamount;
    }

    public ArrayList<Tweet> getMentionedtweets() throws NoExistingUser {
        mentionedtweets = new ArrayList<>();
        User u = userService.getUserbyHandle(user.getHandle());
        mentionedtweets.addAll(u.getMentions());

        return mentionedtweets;
    }

    public void setMentionedtweets(ArrayList<Tweet> mentionedtweets) {
        this.mentionedtweets = mentionedtweets;
    }

    public int getHeartsamount() throws NoExistingUser {
        User u = userService.getUserbyHandle(user.getHandle());
        Set<Hearts> hearts = u.getHearts();
        return hearts.size();
    }

    public void setHeartsamount(int heartsamount) {
        this.heartsamount = heartsamount;
    }

    public ArrayList<User> getFollowing() throws NoExistingUser {
        following = new ArrayList<>();
        User u = userService.getUserbyHandle(user.getHandle());
        following.addAll(u.getFollowing());

        return following;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }

    public ArrayList<User> getFollowers() throws NoExistingUser {
        followers = new ArrayList<>();
        User u = userService.getUserbyHandle(user.getHandle());
        followers.addAll(u.getFollowers());

        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public List<String> getTrends() {
        List<String> trends = new ArrayList<>();
        trends.addAll(tweetService.getTrends());

        return trends;
    }

    public void setTrends(ArrayList<String> trends) {
        this.trends = trends;
    }
}
