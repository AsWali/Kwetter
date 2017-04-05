package service;//package service.service;

import dao.inter.TweetDAO;
import dao.inter.UserDAO;
import domain.Hearts;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Asror on 12-3-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private UserServiceImpl uservice;

    private TweetServiceImpl tservice;

    @Mock
    private UserDAO ud;

    @Mock
    private TweetDAO td;

    public UserServiceImplTest() {
    }

    @Before
    public void setUp() {
        uservice = new UserServiceImpl();
        uservice.setUd(ud);

        tservice = new TweetServiceImpl();
        tservice.setTd(td);
    }

    @Test
    public void getUserbyId(){
        User t = new User("Emma", "emmarino","", "Emma", "Hoi, ik ben emma", "Tilburg", "Walicorp.com");

        when(uservice.getUser(t.getId())).thenReturn(t);
        User found = uservice.getUser(t.getId());
        assertThat(found, is(t));
    }

    @Test
    public void getUserbyHandle() throws NoExistingUser {
        User t = new User("Emma", "emmarino","", "Emma", "Hoi, ik ben emma", "Tilburg", "Walicorp.com");

        when(uservice.getUserbyHandle(t.getHandle())).thenReturn(t);
        User found = uservice.getUserbyHandle(t.getHandle());
        assertThat(found, is(t));
    }

    @Test
    public void addUser() {
        User u = new User("Asror", "password" ,"", "Asror", "Hallo, ik ben Asror", "Rijen", "walicorp,com");
        uservice.addUser(u);
        Mockito.verify(ud).create(u);
    }

    @Test
    public void updateUser() {
        User u = new User("Asror", "password", "", "Asror", "Hallo, ik ben Asror", "Rijen", "walicorp,com");
        uservice.addUser(u);
        u.setName("GeenAsror");
        uservice.updateUser(u);
        Mockito.verify(ud).update(u);
    }

    @Test
    public void getTweets() throws NoExistingUser {
        User user = new User("Asror", "password" ,"", "Asror", "Hallo, ik ben Asror", "Rijen", "walicorp,com");
        uservice.addUser(user);
        Mockito.verify(ud).create(user);

        Tweet tweet = new Tweet("Dit is een test spring", user);
        Tweet tweet2 = new Tweet("Dit is een test spring", user);
        tservice.addTweet(tweet);
        tservice.addTweet(tweet2);
        Set<Tweet> tweets = new HashSet<>();
        tweets.add(tweet);
        tweets.add(tweet2);

        when(uservice.getTweets(user.getId())).thenReturn(tweets);
        Set<Tweet> found = uservice.getTweets(user.getId());
        assertThat(found, is(tweets));

    }
}