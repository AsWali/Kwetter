package domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Asror on 12-3-2017.
 */
@Entity
@Table(name = "tweet")
@NamedQueries({
        @NamedQuery(name = "tweet.recent", query ="SELECT u FROM Tweet u WHERE u.user.handle = :handle ORDER BY u.date DESC"),
        @NamedQuery(name = "tweet.all", query ="SELECT u FROM Tweet u WHERE u.user.handle = :handle")
})
public class Tweet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 140)
    private String tweet;
    private Calendar date;
    private int hearts;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    public Tweet() {
        this.date = new GregorianCalendar();
    }

    public Tweet(String tweet, User user, Calendar date) {
        this.tweet = tweet;
        this.date = new GregorianCalendar();
        this.user = user;
        this.hearts = 0;
    }

    public Tweet(String tweet, User user) {
        this.tweet = tweet;
        this.date = new GregorianCalendar();
        this.user = user;
        this.hearts = 0;
    }

    public Long getId() {
        return id;
    }

    public String getTweet() {
        return tweet;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public Calendar getDate() {
        return date;
    }

    public int getHearts() {
        return hearts;
    }

    public void updateHearts(boolean add) {
        if(add){
            hearts++;
        } else {
            hearts--;
        }
    }

    public User getUser() {
        return user;
    }
}
