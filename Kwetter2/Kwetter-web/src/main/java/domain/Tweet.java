package domain;

import com.fasterxml.jackson.annotation.*;
import com.ocpsoft.pretty.time.PrettyTime;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Asror on 12-3-2017.
 */
@Entity
@Table(name = "tweet")
@NamedQueries({
        @NamedQuery(name = "tweet.recent", query ="SELECT u FROM Tweet u WHERE u.user.handle = :handle ORDER BY u.date DESC"),
        @NamedQuery(name = "tweet.all", query ="SELECT u FROM Tweet u")
})
@NamedNativeQuery(name = "tweet.trends", query ="SELECT * FROM new_view")
public class Tweet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 140)
    private String tweet;
    private Calendar date;
    private String timeago;

    /**
     * Amount of hearts and tweet has. Returns the size of hearts attribute.
     */
    @Transient
    private int amounthearts;

    @ManyToOne(optional = false)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Tweet.class)
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    /**
     * All the hearts object that belong to this tweet.
     */
    @OneToMany(orphanRemoval=true, mappedBy="tweet", fetch = FetchType.EAGER)
    private Set<Hearts> hearts;

    /**
     * Which hastags this tweet uses. Can later see which one has occured the most in the last time period.
     */
    @ElementCollection
    @CollectionTable(name = "trend")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<String> trends;

    public Tweet() {
        this.date = new GregorianCalendar();
        this.trends = new HashSet<>();
    }

    public Tweet(String tweet, User user, Calendar date) {
        this.tweet = tweet;
        this.date = new GregorianCalendar();
        this.user = user;
        this.amounthearts = 0;
        this.hearts = new HashSet<>();
        this.trends = new HashSet<>();
    }

    public Tweet(String tweet, User user) {
        this.tweet = tweet;
        this.date = new GregorianCalendar();
        this.user = user;
        this.amounthearts = 0;
        this.hearts = new HashSet<>();
        this.trends = new HashSet<>();
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

    public int getAmounthearts() {
        return this.hearts.size();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @XmlTransient
    public Set<Hearts> getHearts() {
        return hearts;
    }

    public void setHearts(Set<Hearts> hearts) {
        this.hearts = hearts;
    }

    public User getUser() {
        return user;
    }

    public Set<String> getTrends() {
        return trends;
    }

    public void addTrend(String tag) {
        trends.add(tag);
    }

    public void setTrends(Set<String> trends) {
        this.trends = trends;
    }

    public String getTimeago() {
        PrettyTime p = new PrettyTime();
        return p.format(this.date.getTime());
    }

    public void setTimeago(String timeago) {
        this.timeago = timeago;
    }
}
