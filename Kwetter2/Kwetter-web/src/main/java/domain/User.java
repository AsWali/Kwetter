package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Asror on 11-3-2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "user.all", query ="SELECT u FROM User u"),
        @NamedQuery(name = "user.byhandle", query ="SELECT u FROM User u WHERE u.handle = :handle"),
})
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String picture;
    @Column(unique = true)
    private String handle;

    /** BIO */
    @Column(length = 160)
    private String bio;
    private String location;
    private String website;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user", fetch = FetchType.EAGER)
    private Set<Tweet> tweets;

    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private Set<Hearts> hearts;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "followed_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<User> followers;

    @ManyToMany(mappedBy = "followers", fetch = FetchType.EAGER)
    private Set<User> following;

    public User() {
    }

    public User(String name, String picture, String handle, String bio, String location, String website) {
        this.name = name;
        this.picture = picture;
        this.handle = handle;
        this.bio = bio;
        this.location = location;
        this.website = website;
        this.followers = new HashSet<User>();
        this.following = new HashSet<User>();
        this.tweets = new HashSet<Tweet>();
        this.hearts = new HashSet<Hearts>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @XmlTransient
    public Set<Tweet> getTweets() {
        return tweets;
    }

    @XmlTransient
    public Set<Hearts> getHearts() {
        return hearts;
    }

    @XmlTransient
    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public void addFollower(User follower) {
        followers.add(follower);
        follower.following.add(this);
    }

    public void removeFollower(User follower) {
        followers.remove(follower);
        follower.following.remove(this);
    }

    @XmlTransient
    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

}
