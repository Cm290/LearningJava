package dog.broker.ioc;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This defines the sprint testing context to run the cucumber tests with.
 * The ContextConfiguration (spring config) shows where to pick up the correct beans and
 * allows overriding of the beans or exposing them to the tests.
 * SpringBootTest makes sure that a SpringBoot application is launched in a separate context,
 * it should also make sure that the same application is retained across all tests with this
 * annotation.
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(classes = {LocalConfig.class}, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public @interface CucumberStepsDefinition {
}
