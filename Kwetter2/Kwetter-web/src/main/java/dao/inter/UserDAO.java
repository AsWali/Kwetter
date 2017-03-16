package dao.inter;

import domain.Tweet;
import domain.User;
import exception.NoExistingUser;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Set;


/**
 * Created by Asror on 11-3-2017.
 */

public interface UserDAO extends GenericDAO<User>  {

    List<User> getAll();

    User findbyHandle(String handle) throws NoExistingUser;

    boolean followUser(Long id, Long targetid);
    boolean unfollowUser(Long id, Long targetid);

    Set<User> getFollowers(Long id);
    Set<User> getFollowing(Long id);
    Set<Tweet> getTweets(Long id);
    List<Tweet> getRecent(String handle);

}
