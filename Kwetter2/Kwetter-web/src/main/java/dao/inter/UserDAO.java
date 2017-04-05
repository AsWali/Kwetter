package dao.inter;

import domain.Hearts;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;
import utils.PermissionsEnum;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;


/**
 * Created by Asror on 11-3-2017.
 */

public interface UserDAO extends GenericDAO<User>  {

    /**
     * Method that return all users.
     * @return a list of all users
     */
    List<User> getAll();

    /**
     * Method that finds an User based on his Handle.
     * @param handle
     *              An Users unique handle
     * @return The User Entity with the searched handle.
     * @throws NoExistingUser
     */
    User findbyHandle(String handle) throws NoExistingUser;

    /**
     * Method that adds an User to another Users followers list.
     * And to his own following list.
     * @param id User that does the following
     * @param targetid User that he wants to follow
     * @return true of false
     */
    boolean followUser(Long id, Long targetid);

    /**
     * Method that unfollows an User.
     * @param id User that unfollows
     * @param targetid User that gets unfollowed
     * @return true of false
     */
    boolean unfollowUser(Long id, Long targetid);

    /**
     * Method that return the followers of an User.
     * @param id User id
     * @return list of Users that follows the parameter
     */
    Set<User> getFollowers(Long id);

    /**
     * Method that return the Users that the user follows.
     * @param id User id
     * @return list of Users that the user follows
     */
    Set<User> getFollowing(Long id);

    /**
     * Method that return the all Tweets of the user.
     * @param id User id
     * @return list of Tweets
     */
    Set<Tweet> getTweets(Long id);

    /**
     * Method that returns most recent 10 tweets.
     * @param handle User Handle
     * @return list of 10 recent tweets
     */
    List<Tweet> getRecent(String handle);

    /**
     * Method that return the tweets that the User has given an Heart too
     * @param id User id
     * @return List of Tweets that have an Heart
     */
    Set<Hearts> getHearted(Long id);


    /**
     * Method that return the users permission based on his handle.
     * @param handle users twitter handle
     * @return
     */
    PermissionsEnum getUserPermission(String handle);
}
