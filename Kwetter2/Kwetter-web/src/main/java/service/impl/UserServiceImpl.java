package service.impl;

import dao.impl.UserDAOImpl;
import dao.inter.UserDAO;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;


/**
 * Created by Asror on 12-3-2017.
 */
@Stateless
public class UserServiceImpl {

    @Inject
    private UserDAO ud;

    public User getUserbyHandle(String handle) throws NoExistingUser {
        return ud.findbyHandle(handle);
    }

    public User getUser(Long id) { return ud.find(id);}

    public User addUser(User user) {
        return ud.create(user);
    }

    public void removeUser(Long id) {
        ud.delete(id);
    }

    public User updateUser(User user) { return ud.update(user);}

    public List<User> getAll() {
        return ud.getAll();
    }

    public void setUd(UserDAO ud) {
        this.ud = ud;
    }

    public boolean addFollower(Long id, Long targetid) { return ud.followUser(id, targetid);}

    public boolean removeFollower(Long id, Long targetid) { return ud.unfollowUser(id, targetid); }

    public Set<User> getFollowers(Long id) { return ud.getFollowers(id);}

    public Set<User> getFollowing(Long id) { return ud.getFollowing(id);}

    public Set<Tweet> getTweets(Long id) { return ud.getTweets(id);}

    public List<Tweet> getRecentTweets(String handle) { return ud.getRecent(handle);
    }
}
