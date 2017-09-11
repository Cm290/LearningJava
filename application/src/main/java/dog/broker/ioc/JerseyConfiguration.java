package dog.broker.ioc;

import dog.broker.endpoints.DogEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Created by morric67 on 07/09/2017.
 */

@Configuration
public class JerseyConfiguration extends ResourceConfig{
    public JerseyConfiguration(DogEndpoint dogEndpoint) {
        register(dogEndpoint);
    }
}
