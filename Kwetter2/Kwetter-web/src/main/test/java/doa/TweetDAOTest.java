package doa;

import dao.impl.TweetDAOImpl;
import dao.impl.UserDAOImpl;
import dao.inter.TweetDAO;
import dao.inter.UserDAO;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.UserServiceImplTest;
import utils.DatabaseCleaner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Asror on 16-3-2017.
 */
public class TweetDAOTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    @Inject
    private TweetDAO tweetDAO;


    public TweetDAOTest() {
    }

    @Before
    public void setUp() {
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tweetDAO = new TweetDAOImpl();

        tweetDAO.setEm(em);
    }

    @After
    public void tearDown() {
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void savingTweetUnsuccessful() {
        Tweet tweet = new Tweet("Dit is een test spring", null);
        Integer expectedResult = 1;

        tx.begin();
        tweetDAO.create(tweet);
        tx.commit();

        tx.begin();
        int aantal = tweetDAO.getAll().toArray().length;
        tx.commit();

        assertThat(aantal, is(expectedResult));
    }

    @Test
    public void savingTweetSuccessful() {
        User user = new User("Trump", "trumperino", "", "Trump", "Hoi, ik ben Trump", "Tilburg", "Walicorp.com");
        Tweet tweet = new Tweet("Dit is een test spring", user);
        Integer expectedResult = 1;

        tx.begin();
        em.persist(user);
        tweetDAO.create(tweet);
        tx.commit();

        tx.begin();
        int aantal = tweetDAO.getAll().toArray().length;
        tx.commit();

        assertThat(aantal, is(expectedResult));
    }

    @Test
    public void findTweetSuccessful() throws NoExistingUser {
        User user = new User("Trump", "trumperino", "", "Trump", "Hoi, ik ben Trump", "Tilburg", "Walicorp.com");
        Tweet tweet = new Tweet("Dit is een test spring", user);

        tx.begin();
        em.persist(user);
        tweetDAO.create(tweet);
        Tweet stud = tweetDAO.find(tweet.getId());
        tx.commit();
        assertThat(stud, is(tweet));
    }

    @Test
    public void removeTweetSuccessful() throws NoExistingUser {
        User user = new User("Trump", "trumperino", "", "Trump", "Hoi, ik ben Trump", "Tilburg", "Walicorp.com");
        Tweet tweet = new Tweet("Dit is een test spring", user);

        tx.begin();
        em.persist(user);
        tweetDAO.create(tweet);
        Tweet stud = tweetDAO.find(tweet.getId());
        tx.commit();
        assertThat(stud, is(tweet));

        tx.begin();
        tweetDAO.delete(tweet.getId());
        tx.commit();
        stud = tweetDAO.find(tweet.getId());
        assertThat(stud, is(nullValue()));
    }
}
