package dao.impl;

import dao.inter.TweetDAO;
import domain.Tweet;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Asror on 13-3-2017.
 */
@Stateless
public class TweetDAOImpl extends DAOFacade<Tweet> implements TweetDAO {

    public TweetDAOImpl() {
        super(Tweet.class);
    }

}
