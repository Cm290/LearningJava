package dog.broker.ioc;

import dog.broker.endpoints.DogEndpoint;
import dog.broker.service.DogKennelMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointConfig {
    @Bean
    public DogKennelMemory dogKennelMemory() {
        return new DogKennelMemory();
    }

    @Bean
    public DogEndpoint dogEndpoint(DogKennelMemory dogKennelMemory){
        return new DogEndpoint(dogKennelMemory);
    }
}
