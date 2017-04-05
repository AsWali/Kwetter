package doa;

import dao.impl.UserDAOImpl;
import dao.inter.UserDAO;
import domain.Hearts;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.UserServiceImplTest;
import utils.DatabaseCleaner;

import javax.inject.Inject;
import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Asror on 16-3-2017.
 */
public class UserDAOTest {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    @Inject
    private UserDAO userDao;


    public UserDAOTest() {
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
        userDao = new UserDAOImpl();

        userDao.setEm(em);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void savingUserSuccessful() {
        User user = new User("Trump", "trumperino","", "Trump", "Hoi, ik ben Trump", "Tilburg", "Walicorp.com");
        Integer expectedResult = 1;
        tx.begin();
        userDao.create(user);
        tx.commit();
        tx.begin();
        int aantal = userDao.getAll().toArray().length;
        tx.commit();
        assertThat(aantal, is(expectedResult));
    }


    @Test
    public void findUserSuccessful() throws NoExistingUser {
        User user = new User("Trump", "trumperino", "", "Trump", "Hoi, ik ben Trump", "Tilburg", "Walicorp.com");
        tx.begin();
        userDao.create(user);
        User stud = userDao.findbyHandle("Trump");
        tx.commit();
        assertThat(stud, is(user));

        tx.begin();
        stud = userDao.find(user.getId());
        tx.commit();
        assertThat(stud, is(user));
    }

    @Test
    public void UpdateUserSuccessful() throws NoExistingUser {
        User user = new User("Trump", "trumperino","", "Trump", "Hoi, ik ben Trump", "Tilburg", "Walicorp.com");
        tx.begin();
        userDao.create(user);
        User stud = userDao.findbyHandle("Trump");
        tx.commit();
        assertThat(stud, is(user));

        tx.begin();
        user.setWebsite("Trump.com");
        userDao.update(user);
        stud = userDao.findbyHandle("Trump");
        tx.commit();
        assertThat(stud, is(user));
    }

    @Test(expected = NoExistingUser.class)
    public void removeUserSuccessful() throws NoExistingUser {
        User user = new User("Trump", "trumperino","", "Trump", "Hoi, ik ben Trump", "Tilburg", "Walicorp.com");
        tx.begin();
        userDao.create(user);
        User stud = userDao.findbyHandle("Trump");
        tx.commit();
        assertThat(stud, is(user));

        tx.begin();
        userDao.delete(user.getId());
        tx.commit();
        stud = userDao.findbyHandle("Trump");
    }

    @Test
    public void followUserSuccessfull() throws NoExistingUser {
        User user = new User("Trump", "trumperino","", "Trump", "Hoi, ik ben Trump", "Tilburg", "Walicorp.com");
        User user2 = new User("Jennifer", "trumperino","", "Lopez", "Jlo", "Tilburg", "Jezus.com");

        tx.begin();
        userDao.create(user);
        userDao.create(user2);
        userDao.followUser(user.getId(), user2.getId());
        tx.commit();

        Assert.assertTrue(user.getFollowing().contains(user2));
        Assert.assertTrue(user2.getFollowers().contains(user));

    }

    @Test
    public void unfollowUserSuccesfull() throws Exception {
        User user = new User("Trump", "trumperino","", "Trump", "Hoi, ik ben Trump", "Tilburg", "Walicorp.com");
        User user2 = new User("Jennifer","trumperino", "", "Lopez", "Jlo", "Tilburg", "Jezus.com");

        tx.begin();
        userDao.create(user);
        userDao.create(user2);
        userDao.followUser(user.getId(), user2.getId());
        tx.commit();

        Assert.assertTrue(user.getFollowing().contains(user2));
        Assert.assertTrue(user2.getFollowers().contains(user));

        tx.begin();
        userDao.unfollowUser(user.getId(), user2.getId());
        tx.commit();

        Assert.assertTrue(user.getFollowing().isEmpty());
        Assert.assertTrue(user2.getFollowers().isEmpty());

    }


}
