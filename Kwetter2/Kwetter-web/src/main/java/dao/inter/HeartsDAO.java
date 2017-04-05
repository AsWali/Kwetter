package dao.inter;

import domain.Hearts;
import domain.User;
import exception.NoExistingUser;

import javax.persistence.EntityManager;

/**
 * Created by Asror on 16-3-2017.
 */
public interface HeartsDAO extends GenericDAO<Hearts>  {

    /**
     * Method that looks if an user has already hearted an Tweet.
     * @param userid User id
     * @param tweetid Tweet id
     * @return Heart object
     */
    Hearts findbyUserTweet(Long userid, Long tweetid);
}
