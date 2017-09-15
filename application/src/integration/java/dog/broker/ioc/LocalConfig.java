package dog.broker.ioc;

import dog.broker.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@Profile("!remote")
@Import(Application.class)
@TestConfiguration
@ActiveProfiles("local")
public class LocalConfig {

    @Autowired
    private TestRestTemplate restTemplate;

}
