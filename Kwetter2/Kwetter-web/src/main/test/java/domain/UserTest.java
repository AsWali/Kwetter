package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLException;
import utils.DatabaseCleaner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by Asror on 16-3-2017.
 */
public class UserTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private User user;


    public UserTest() {
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
    }

    @After
    public void tearDown() {
    }

    @Test
    public void verifyUserCreation() {
        tx.begin();
        em.persist(user);
        tx.commit();
    }

    @Test
    public void verifyUserUpdate() {
        tx.begin();
        em.persist(user);
        tx.commit();

        user.setName("NotEmmo");
        tx.begin();
        em.merge(user);
        tx.commit();
    }

    @Test
    public void verifyUserFind() {
        tx.begin();
        em.persist(user);
        tx.commit();

        tx.begin();
        em.find(User.class, user.getId());
        tx.commit();
    }

    @Test
    public void verifyUserDelete() {
        tx.begin();
        em.persist(user);
        tx.commit();

        tx.begin();
        em.remove(user);
        tx.commit();
    }
}