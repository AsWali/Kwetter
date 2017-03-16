package exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Asror on 15-3-2017.
 */
public class NoExistingUser extends Exception{
    public NoExistingUser(String message) { super(message);}
}
