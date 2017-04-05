package dao.impl;

import dao.inter.UserDAO;
import domain.Hearts;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;
import utils.PermissionsEnum;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Asror on 11-3-2017.
 */
@Stateless
public class UserDAOImpl extends DAOFacade<User> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public List<Tweet> getRecent(String handle) {
        return (List<Tweet>) em.createNamedQuery("tweet.recent").setParameter("handle", handle).setMaxResults(10).getResultList();
    }

    @Override
    public List<User> getAll() {
        return (List<User>) em.createNamedQuery("user.all").getResultList();
    }

    @Override
    public User findbyHandle(String handle) throws NoExistingUser {
        List q = em.createNamedQuery("user.byhandle").setParameter("handle", handle).getResultList();
        if(!q.isEmpty()) {
            User u = (User) q.get(0);
            return u;
        } else {
            throw new NoExistingUser("No name is filled in");
        }
    }

    @Override
    public boolean followUser(Long id, Long targetid) {
        User u = this.find(targetid);
        u.addFollower(this.find(id));
        return true;
    }

    @Override
    public boolean unfollowUser(Long id, Long targetid) {
        User u = this.find(targetid);
        u.removeFollower(this.find(id));
        return true;
    }

    @Override
    public Set<User> getFollowers(Long id) {
        User t = this.find(id);
        return t.getFollowers();
    }

    @Override
    public Set<User> getFollowing(Long id) {
        User t = this.find(id);
        return t.getFollowing();
    }

    @Override
    public Set<Tweet> getTweets(Long id) {
        User t = this.find(id);
        return t.getTweets();
    }

    @Override
    public Set<Hearts> getHearted(Long id) {
        User t = this.find(id);
        return t.getHearts();
    }

    @Override
    public PermissionsEnum getUserPermission(String handle) {
        User user = (User) em.createNamedQuery("user.byhandle").setParameter("handle", handle).getSingleResult();
        return user.getPermission();
    }

}
