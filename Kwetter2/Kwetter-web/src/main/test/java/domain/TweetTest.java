package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Asror on 16-3-2017.
 */
public class TweetTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private User user;
    private Tweet tweet;


    public TweetTest() {
    }

    @Before
    public void setUp() {
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            Logger.getLogger(UserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        em = emf.createEntityManager();
        tx = em.getTransaction();
        user = new User("Emma", "emmarino","", "Emma", "Hoi, ik ben emma", "Tilburg", "Walicorp.com");
        tweet = new Tweet("Dit is een test spring", user);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void verifyTweetCreation() {
        tx.begin();
        em.persist(user);
        em.persist(tweet);
        tx.commit();
    }

    @Test
    public void verifyTweetFind() {
        tx.begin();
        em.persist(user);
        em.persist(tweet);
        tx.commit();

        tx.begin();
        em.find(Tweet.class, tweet.getId());
        tx.commit();
    }

    @Test
    public void verifyTweetDelete() {
        tx.begin();
        em.persist(user);
        em.persist(tweet);
        tx.commit();

        tx.begin();
        em.remove(tweet);
        tx.commit();
    }
}
