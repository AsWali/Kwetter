package domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * Created by Asror on 12-3-2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "hearts.find", query ="select h from Hearts h Where h.tweet.id =:tweetid and h.user.id =:userid"),
})
@Table(name = "hearts")
public class Hearts  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User that belongs to this Heart Object
     */
    @ManyToOne
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Hearts.class)
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    /**
     * Tweet that the User has hearted
     */
    @ManyToOne
    private Tweet tweet;

    public Hearts() {
    }

    public Hearts(User user, Tweet tweet) {
        this.user = user;
        this.tweet = tweet;
    }

    public Long getId() {
        return id;
    }

    /**
     * Heart doesnt return an User Entity. The only use case where we return Hearts
     * Is when an user ask for his own Hearts. It would be redudant to include this.
     * @return nothing
     */
    @XmlTransient
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Return the tweet that is hearted so we can display it immediately when asking for hearts.
     * @return Tweet
     */
    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }
}
