package rest;

import domain.User;
import domain.UserCredentials;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Asror on 16-3-2017.
 */
public class UserControllerTest  {

    Client client;
    WebTarget root;
    static final String PATH = "/Kwetter-web-1.0-SNAPSHOT/api/";
    static final String BASEURL = "http://localhost:8080" + PATH;
    String Token;

    public UserControllerTest() {
    }

    @Before
    public void setUp() {
        this.client = ClientBuilder.newClient();
        this.root = this.client.target(BASEURL);
        String mediaType = MediaType.APPLICATION_JSON;
        UserCredentials uc = new UserCredentials("Asror", "Password");
        String newtarget = BASEURL + "authentication";
        this.root =  this.client.target(newtarget);
        final Entity<UserCredentials> entity = Entity.entity(uc, mediaType);
        Token = "Bearer " + this.root.request().post(entity, String.class);
    }


    @Test
    public void create() {
        String mediaType = MediaType.APPLICATION_JSON;
        User user = new User("Emma", "Emma", "", "Emma", "Hoi, ik ben emma", "Tilburg", "Walicorp.com");

        final Entity<User> entity = Entity.entity(user, mediaType);
        String newtarget = BASEURL + "user";
        this.root =  this.client.target(newtarget);
        User studResult = this.root.request().header("Authorization", Token).header("Content-Type", "application/json").post(entity, User.class);
        assertThat(studResult, is(user));

    }

    @Test
    public void get() {
        String mediaType = MediaType.APPLICATION_JSON;
        User user = new User("Emma", "Emma", "", "Emma", "Hoi, ik ben emma", "Tilburg", "Walicorp.com");

        String newtarget = BASEURL + "user/emma/profile";
        this.root =  this.client.target(newtarget);
        User studResult = this.root.request().header("Authorization", Token).header("Content-Type", "application/json").get(User.class);
        assertThat(studResult, is(user));
    }


}
