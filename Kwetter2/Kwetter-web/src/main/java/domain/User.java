package domain;

import converter.PasswordConverter;
import org.hibernate.annotations.*;
import utils.PermissionsEnum;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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

    /**
     * Unique Handle that users have on twitter. example. @Asror
     * Its public unlike the id.
     */
    @Column(unique = true)
    private String handle;
    //@Convert(converter = PasswordConverter.class)
    private String password;

    /**
     * Which Role an user have. Either User or Admin.
     * Based on the Role an User has different permissions
     */
    @Enumerated(EnumType.STRING)
    private PermissionsEnum permission;

    /** BIO */
    @Column(length = 160)
    private String bio;
    private String location;
    private String website;

    /**
     * List containing all the tweets the user has submitted. OrphanRemoval = true meaning all the tweets will be removed
     * if the user is removed.
     */
    @OneToMany(orphanRemoval=true, mappedBy="user", fetch = FetchType.EAGER)
    private Set<Tweet> tweets;

    /**
     * List containing all the tweet the user has hearted. If the user gets deleted all the tweets that have a heart from this user.
     * Lose an heart.
     */
    @OneToMany(orphanRemoval=true, mappedBy="user", fetch = FetchType.EAGER)
    private Set<Hearts> hearts;

    /**
     * All the users that follow this User object
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "followed_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<User> followers;

    /**
     * All the users this User object follows.
     */
    @ManyToMany(mappedBy = "followers", fetch = FetchType.EAGER)
    private Set<User> following;

    /**
     * A collection of tweets where this user is mentioned in.
     */
    @ElementCollection
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "mentions")
    private Set<Tweet> mentions;


    public User() {
    }

    /**
     * Method that get executed before the User object get removed.
     * Removes the user from the following and followers list form other users.
     */
    @PreRemove
    private void removeAssociationsWithChilds() {
        this.FollowClear();
    }

    private void FollowClear() {
        this.followers.clear();
        this.following.clear();
    }

    public User(String name, String password, String picture, String handle, String bio, String location, String website) {
        this.name = name;
        this.password = password;
        this.picture = picture;
        this.handle = handle;
        this.bio = bio;
        this.location = location;
        this.website = website;
        this.followers = new HashSet<User>();
        this.following = new HashSet<User>();
        this.tweets = new HashSet<Tweet>();
        this.hearts = new HashSet<Hearts>();
        this.mentions = new HashSet<>();
        this.permission = PermissionsEnum.USER;
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

    public PermissionsEnum getPermission() {
        return permission;
    }

    public void setPermission(PermissionsEnum permission) {
        this.permission = permission;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public Set<Tweet> getMentions() {
        return mentions;
    }

    public void addMentions(Tweet tweet) {
        mentions.add(tweet);
    }

    public void setMentions(Set<Tweet> mentions) {
        this.mentions = mentions;
    }
}
