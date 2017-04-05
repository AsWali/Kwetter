package dao.impl;

import dao.inter.HeartsDAO;
import dao.inter.UserDAO;
import domain.Hearts;
import domain.User;
import exception.NoExistingUser;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Asror on 16-3-2017.
 */
@Stateless
public class HeartsDAOImpl extends DAOFacade<Hearts> implements HeartsDAO {

    public HeartsDAOImpl() {
        super(Hearts.class);
    }

    @Override
    public Hearts findbyUserTweet(Long userid, Long tweetid) {
        return (Hearts) em.createNamedQuery("hearts.find").setParameter("tweetid", tweetid).setParameter("userid", userid).getSingleResult();
    }
}
