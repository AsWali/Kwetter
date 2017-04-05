package dao.col;

import dao.impl.DAOFacade;
import dao.inter.UserDAO;
import domain.Hearts;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;
import utils.PermissionsEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Asror on 23-3-2017.
 */
public class UserDAOColImpl extends DAOFacade<User> implements UserDAO {

    List<User> users = new ArrayList<>();

    public UserDAOColImpl(Class<User> entityClass) {
        super(entityClass);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User findbyHandle(String handle) throws NoExistingUser {
        for(User u : users){
            if(u.getHandle().equals(handle)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean followUser(Long id, Long targetid) {
        for(User u : users){
            if(u.getId().equals(targetid)) {
                u.addFollower(u);
            }
        }
        return false;
    }

    @Override
    public boolean unfollowUser(Long id, Long targetid) {
        for(User u : users){
            if(u.getId().equals(targetid)) {
                u.removeFollower(u);
            }
        }
        return false;
    }

    @Override
    public Set<User> getFollowers(Long id) {
        for(User u : users){
            if(u.getId().equals(id)){
                return u.getFollowers();
            }
        }
        return null;
    }

    @Override
    public Set<User> getFollowing(Long id) {
        for(User u : users){
            if(u.getId().equals(id)){
                return u.getFollowing();
            }
        }
        return null;
    }

    @Override
    public Set<Tweet> getTweets(Long id) {
        for(User u : users){
            if(u.getId().equals(id)){
                return u.getTweets();
            }
        }
        return null;
    }

    @Override
    public List<Tweet> getRecent(String handle) {
        return null;
    }

    @Override
    public Set<Hearts> getHearted(Long id) {
        for(User u : users){
            if(u.getId().equals(id)){
                return u.getHearts();
            }
        }
        return null;
    }

    @Override
    public PermissionsEnum getUserPermission(String handle) {
        for(User u : users){
            if(u.getHandle().equals(handle)){
                return u.getPermission();
            }
        }
        return null;
    }

}
