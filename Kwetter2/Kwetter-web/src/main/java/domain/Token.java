package domain;

import java.util.Date;

/**
 * Created by Dennis on 3/21/2017.
 */
public class Token {

    private String username;
    private String token;
    private Date expirationDate;

    public Token(String username, String token, Date expirationDate) {
        this.username = username;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
