package dog.broker.features;

import com.google.common.base.Strings;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dog.broker.info.Dog;
import dog.broker.ioc.CucumberStepsDefinition;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

/**
 * Created by morric67 on 14/09/2017.
 */

@CucumberStepsDefinition
public class DogSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> requestResult = null;
    private static final Dog trevor = Dog.builder().name("Trevor").build();
    private static final Dog updatedTrevor = Dog.builder().name("NewTrevor").build();
    private static final Dog julia = Dog.builder().name("Julia").build();
    private static final Dog fred = Dog.builder().name("Fred").build();

    @Given("^that there are (\\d+) dogs in the database$")
    public void postToDogEndpoint(int amount) throws Exception {
        ResponseEntity<String> trevorExists = restTemplate.getForEntity("/dogEndpoint/0", String.class);
        ResponseEntity<String> juliaExists = restTemplate.getForEntity("/dogEndpoint/1", String.class);
        ResponseEntity<String> fredExists = restTemplate.getForEntity("/dogEndpoint/2", String.class);

        if(trevorExists.getStatusCodeValue() == 404) {
            restTemplate.postForEntity("/dogEndpoint", trevor, String.class);
        }
        if(juliaExists.getStatusCodeValue() == 404) {
            restTemplate.postForEntity("/dogEndpoint", julia, String.class);
        }

        if(amount == 3 && fredExists.getStatusCodeValue() == 404 ) {
            restTemplate.postForEntity("/dogEndpoint", fred, String.class);
        }
        ResponseEntity<String> allDogs = restTemplate.getForEntity("/dogEndpoint", String.class);
        List<String> dogList = JsonPath.compile("$").read(allDogs.getBody());

        assertThat(trevorExists, is(notNullValue()));
        assertThat(juliaExists, is(notNullValue()));
        assertThat(dogList.size(), is(equalTo(amount)));
    }

    @When("^the dog endpoint is requested with a valid ID$")
    public void requestDogEndpointWithAValidID() throws Throwable {
        ResponseEntity<String> requestResult = restTemplate.getForEntity("/dogEndpoint/0", String.class);

        assertThat(requestResult, is(notNullValue()));
        this.requestResult = requestResult;
    }

    @When("^the dog endpoint is requested$")
    public void requestDogEndpointGetAll() throws Throwable {
        ResponseEntity<String> requestResult = restTemplate.getForEntity("/dogEndpoint", String.class);

        assertThat(requestResult, is(notNullValue()));
        this.requestResult = requestResult;
    }

    @When("^the dog endpoint is requested with a valid ID and updated dog information$")
    public void updateAnExistingDog() throws Throwable {
        HttpEntity<?> updatedDog = new HttpEntity<>(updatedTrevor);
        ResponseEntity<String> requestResult = restTemplate.exchange("/dogEndpoint/0", HttpMethod.PUT, updatedDog, String.class);

        assertThat(requestResult, is(notNullValue()));
        this.requestResult = requestResult;
    }

    @When("^a user gives us data about a new dog$")
    public void postNewDogToTheDatabase() throws Throwable {
        ResponseEntity<String> requestResult = restTemplate.postForEntity("/dogEndpoint", fred, String.class);

        assertThat(requestResult, is(notNullValue()));
        this.requestResult = requestResult;
    }

    @When("^a user deletes a dog with a valid ID$")
    public void deletesADogFromTheDatabase() throws Throwable {
        HttpEntity<?> httpEntity = new HttpEntity<>(null);
        ResponseEntity<String> requestResult = restTemplate.exchange("/dogEndpoint/2", HttpMethod.DELETE, httpEntity, String.class);

        assertThat(requestResult, is(notNullValue()));
        assertThat(requestResult.getBody(), is(equalTo(("{\"message\":\"Success: true\"}"))));
        this.requestResult = requestResult;
    }

    @When("^the dog endpoint is requested with a (null|invalid) ID$")
    public void requestDogEndpointWithAnInvalidID(String idType) throws Throwable {
        switch(idType) {
            case "null":
                ResponseEntity<String> requestResultNull = restTemplate.getForEntity("/dogEndpoint/null", String.class);

                assertThat(requestResultNull, is(notNullValue()));
                this.requestResult = requestResultNull;
                break;

            case "invalid":
                ResponseEntity<String> requestResultInvalid = restTemplate.getForEntity("/dogEndpoint/null", String.class);

                assertThat(requestResultInvalid, is(notNullValue()));
                this.requestResult = requestResultInvalid;
                break;
        }

    }

    @When("^the dog endpoint is requested with a (null|invalid) ID and updated dog information$")
    public void requestDogEndpointWithAInvalidIDWhenDoingAPutRequest(String idType) throws Throwable {
        HttpEntity<?> updatedDog = new HttpEntity<>(updatedTrevor);
        switch(idType) {
            case "null":
                ResponseEntity<String> requestResultNull = restTemplate.exchange("/dogEndpoint/null", HttpMethod.PUT, updatedDog, String.class);

                assertThat(requestResultNull, is(notNullValue()));
                this.requestResult = requestResultNull;
                break;

            case "invalid":
                ResponseEntity<String> requestResultInavlid = restTemplate.exchange("/dogEndpoint/7", HttpMethod.PUT, updatedDog, String.class);

                assertThat(requestResultInavlid, is(notNullValue()));
                this.requestResult = requestResultInavlid;
                break;
        }
    }

    @When("^the user attempts to delete a dog with a (null|invalid) ID$")
    public void attemptingToDeleteAnDogWithAnInvalidId(String idType) throws Throwable {
        HttpEntity<?> httpEntity = new HttpEntity<>(null);

        switch(idType) {
            case "null":
                ResponseEntity<String> requestResultNull = restTemplate.exchange("/dogEndpoint/null", HttpMethod.DELETE, httpEntity, String.class);

                assertThat(requestResultNull, is(notNullValue()));
                this.requestResult = requestResultNull;
                break;

            case "invalid":
                ResponseEntity<String> requestResultInavlid = restTemplate.exchange("/dogEndpoint/7", HttpMethod.DELETE, httpEntity, String.class);

                assertThat(requestResultInavlid, is(notNullValue()));
                this.requestResult = requestResultInavlid;
                break;
        }
    }

    @Then("^the response status code is (\\d+)$")
    public void theResponseStatusCodeIs(int statusCode) throws Throwable {
        assertThat(requestResult.getStatusCodeValue(),equalTo(statusCode));
    }

    @And("^the (?:response|database) contains (\\d+) (?:dog|dogs)$")
    public void theResponseBodyIs(int amount) throws Exception {
        switch (amount) {
            case 1:
                ResponseEntity<String> requestResultCase1 = restTemplate.getForEntity("/dogEndpoint/0", String.class);
                String dog = JsonPath.compile("$.name").read(requestResultCase1.getBody());
                assertThat(dog, is(equalTo("Trevor")));
                break;
            default:
                ResponseEntity<String> requestResultCase2 = restTemplate.getForEntity("/dogEndpoint", String.class);
                List<String> dogList = JsonPath.compile("$").read(requestResultCase2.getBody());
                System.out.println(dogList);
                assertThat(dogList.size(), is(equalTo(amount)));
                break;
        }
    }

    @And("^the dog has been updated$")
    public void CheckDogHasBeenUpdatedInDatabase() throws Throwable {
        ResponseEntity<String> requestResultUpdatedDog = restTemplate.getForEntity("/dogEndpoint/0", String.class);
        String updatedDog = JsonPath.compile("$.name").read(requestResultUpdatedDog.getBody());

        assertThat(requestResult, is(notNullValue()));
        assertThat(updatedDog, is(equalTo("NewTrevor")));
    }

    @And("^the new dog is added to the database$")
    public void CheckNewDogExistsInDatabase() throws Throwable {
        ResponseEntity<String> requestResultNewDog = restTemplate.getForEntity("/dogEndpoint/2", String.class);
        String newDog = JsonPath.compile("$.name").read(requestResultNewDog.getBody());

        assertThat(requestResult, is(notNullValue()));
        assertThat(newDog, is(equalTo("Fred")));
    }

    @And("^the dog has been deleted$")
    public void CheckDogIsDeletedFromDatabase() throws Throwable {
        ResponseEntity<String> requestResultDog = restTemplate.getForEntity("/dogEndpoint/2", String.class);

        assertThat(requestResult, is(notNullValue()));
        assertThat(requestResultDog.getStatusCodeValue(), equalTo(404));
    }
}
