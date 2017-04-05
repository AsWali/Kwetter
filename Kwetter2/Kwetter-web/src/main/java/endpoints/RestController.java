package endpoints;

import org.shipstone.swagger.integration.annotation.SwaggerUIConfiguration;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/**
 * Created by Asror on 12-3-2017.
 */

@SwaggerUIConfiguration
@ApplicationPath("api")
public class RestController extends Application{

}
