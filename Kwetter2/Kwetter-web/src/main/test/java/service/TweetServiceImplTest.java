package service;

import dao.inter.TweetDAO;
import dao.inter.UserDAO;
import domain.Tweet;
import domain.User;
import exception.NoExistingUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import service.impl.TweetServiceImpl;
import service.impl.UserServiceImpl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Asror on 19-3-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TweetServiceImplTest {

    private UserServiceImpl uservice;

    private TweetServiceImpl tservice;

    @Mock
    private UserDAO ud;

    @Mock
    private TweetDAO td;

    public TweetServiceImplTest() {
    }

    @Before
    public void setUp() {
        uservice = new UserServiceImpl();
        uservice.setUd(ud);

        tservice = new TweetServiceImpl();
        tservice.setTd(td);
    }

    @Test
    public void getTweetbyId() {
        User user = new User("Emma", "emmarino","", "Emma", "Hoi, ik ben emma", "Tilburg", "Walicorp.com");
        Tweet tweet = new Tweet("Dit is een test spring", user);
        when(tservice.findById(tweet.getId())).thenReturn(tweet);
        Tweet found = tservice.findById(tweet.getId());
        assertThat(found, is(tweet));
    }

    @Test
    public void addTweet() throws NoExistingUser {
        User user = new User("Asror", "password" ,"", "Asror", "Hallo, ik ben Asror", "Rijen", "walicorp,com");
        Tweet tweet = new Tweet("Dit is een test spring", user);
        tservice.addTweet(tweet);
        Mockito.verify(td).create(tweet);
    }

    @Test
    public void removeTweet() throws NoExistingUser {
        User user = new User("Asror", "password", "", "Asror", "Hallo, ik ben Asror", "Rijen", "walicorp,com");
        Tweet tweet = new Tweet("Dit is een test spring", user);
        tservice.addTweet(tweet);
        Mockito.verify(td).create(tweet);

        tservice.removeTweet(tweet.getId());
        Mockito.verify(td).delete(tweet.getId());
    }
}
