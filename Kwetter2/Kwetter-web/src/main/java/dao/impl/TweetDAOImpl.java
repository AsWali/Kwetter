package dao.impl;

import dao.inter.TweetDAO;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Set;

/**
 * Created by Asror on 13-3-2017.
 */
@Stateless
public class TweetDAOImpl extends DAOFacade<Tweet> implements TweetDAO {

    @Override
    public List<Tweet> getAll() {
        return (List<Tweet>) em.createNamedQuery("tweet.all").getResultList();
    }

    public TweetDAOImpl() {
        super(Tweet.class);
    }

    public List<String> getTrends(){
        List<String> trends = em.createNamedQuery("tweet.trends").setMaxResults(10).getResultList();
        return trends;
    }

}
