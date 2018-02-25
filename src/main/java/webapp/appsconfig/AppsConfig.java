package webapp.appsconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 
 * As of now this service can't be run on distributed fashion because of Lucene Index Writer 
 * Until closed, Lucene Index Writers can be created only once and in distributed system, every instnace will try to create those writers and will fail
 * @author Sabir Khan
 *
 */


@SpringBootApplication
public class AppsConfig 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(AppsConfig.class, args);
    }
}
