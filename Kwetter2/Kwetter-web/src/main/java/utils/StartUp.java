package utils;

import domain.Tweet;
import domain.User;
import service.impl.TweetServiceImpl;
import service.impl.UserServiceImpl;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Asror on 12-3-2017.
 */
@Startup
@Singleton
public class StartUp {

    @Inject
    private UserServiceImpl uservice;

    @Inject
    private TweetServiceImpl tservice;

    @PostConstruct
    private void initData() {
        User u = new User("Asror", "", "Asror", "Hallo, ik ben Asror", "Rijen", "walicorp,com");
        uservice.addUser(u);

        Tweet t1 = new Tweet("What a nice day!", u, new GregorianCalendar(2017, 2, 1));
        Tweet t2 = new Tweet("What a shit day!", u, new GregorianCalendar(2017,2, 1));
        Tweet t3 = new Tweet("What a meh day!", u, new GregorianCalendar(2017,3, 1));
        Tweet t4 = new Tweet("I liked the movie National Treasure.!", u, new GregorianCalendar(2017,4, 1));
        Tweet t5 = new Tweet("I disliked the movie National Treasure.!", u, new GregorianCalendar(2017,5, 1));
        Tweet t6 = new Tweet("I am okay with the movie National Treasure.!", u, new GregorianCalendar(2017,6, 1));
        Tweet t7 = new Tweet("I have my own opinions", u, new GregorianCalendar(2017,7, 1));
        Tweet t8 = new Tweet("I am loving twitter right now", u, new GregorianCalendar(2017,8, 1));
        Tweet t9 = new Tweet("I have my own opinions", u, new GregorianCalendar(2017,9, 1));
        Tweet t10 = new Tweet("I am loving twitter right now", u, new GregorianCalendar(2017,10, 1));
        Tweet t11 = new Tweet("I am loving twitter right now", u, new GregorianCalendar(2017,11, 1));
        Tweet t12 = new Tweet("I am loving twitter right now", u, new GregorianCalendar(2017,0, 1));

        tservice.addTweet(t1);
        tservice.addTweet(t2);
        tservice.addTweet(t3);
        tservice.addTweet(t4);
        tservice.addTweet(t5);
        tservice.addTweet(t6);
        tservice.addTweet(t7);
        tservice.addTweet(t8);
        tservice.addTweet(t9);
        tservice.addTweet(t10);
        tservice.addTweet(t11);
        tservice.addTweet(t12);

        User u2 = new User("Jon", "", "Jon", "Hallo, ik ben Jon", "Rijen", "jon,com");
        uservice.addUser(u2);

        Tweet t13 = new Tweet("What a nice day!", u2, new GregorianCalendar(2017,1, 1));
        Tweet t14 = new Tweet("What a shit day!", u2, new GregorianCalendar(2017,2, 1));
        Tweet t15 = new Tweet("What a meh day!", u2, new GregorianCalendar(2017,3, 1));
        Tweet t16 = new Tweet("I liked the movie National Treasure.!", u2, new GregorianCalendar(2017,4, 1));
        Tweet t17 = new Tweet("I disliked the movie National Treasure.!", u2, new GregorianCalendar(2017,5, 1));
        Tweet t18 = new Tweet("I am okay with the movie National Treasure.!", u2, new GregorianCalendar(2017,6, 1));
        Tweet t19 = new Tweet("I have my own opinions", u2, new GregorianCalendar(2017,7, 1));
        Tweet t20 = new Tweet("I am loving twitter right now", u2, new GregorianCalendar(2017,8, 1));
        Tweet t21 = new Tweet("I have my own opinions", u2, new GregorianCalendar(2017,9, 1));
        Tweet t22 = new Tweet("I am loving twitter right now", u2, new GregorianCalendar(2017,10, 1));
        Tweet t23 = new Tweet("I am loving twitter right now", u2, new GregorianCalendar(2017,11, 1));
        Tweet t24 = new Tweet("I am loving twitter right now", u2, new GregorianCalendar(2017,0, 1));

        tservice.addTweet(t13);
        tservice.addTweet(t14);
        tservice.addTweet(t15);
        tservice.addTweet(t16);
        tservice.addTweet(t17);
        tservice.addTweet(t18);
        tservice.addTweet(t19);
        tservice.addTweet(t20);
        tservice.addTweet(t21);
        tservice.addTweet(t22);
        tservice.addTweet(t23);
        tservice.addTweet(t24);


        User u3 = new User("Jake", "", "Jake", "Jake Jones ", "Manhatten", "Jake.com");
        uservice.addUser(u3);

        User u4 = new User("Will", "", "Will", "Will is the name", "Unknown", "will,com");
        uservice.addUser(u4);
    }
}
