package domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Asror on 12-3-2017.
 */
@Entity
@Table(name = "hearts")
public class Hearts  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Long tweetid;

    public Hearts() {
    }

    public Hearts(User user, Long tweetid) {
        this.user = user;
        this.tweetid = tweetid;
    }
}
