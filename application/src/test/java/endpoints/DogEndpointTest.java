package endpoints;

import dog.broker.endpoints.DogEndpoint;
import dog.broker.info.Dog;
import dog.broker.service.DogKennelMemory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Created by morric67 on 13/09/2017.
 */
public class DogEndpointTest {
    private Dog trevor = Dog.builder().name("Trevor").build();
    private Dog trevorUpdate = Dog.builder().name("Trevor").age(2).build();
//    private Dog julia = Dog.builder().name("Juila").age(1).build();
//    private Dog fred =  Dog.builder().name("Fred").build();

    @Mock
    private DogKennelMemory dogKennelMemory;

    private DogEndpoint underTest;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getDogOkForValidRequest() throws Exception {
        given(dogKennelMemory.get(0)).willReturn(trevor);
        underTest = new DogEndpoint(dogKennelMemory);

        Response response = underTest.getDog(0);

        assertThat(response.getStatus(), is(equalTo(200)));
    }

    @Test
    public void getDogReturnsAnErrorWhenTheIdIsInvalid() throws Exception {
        given(dogKennelMemory.get(1)).willReturn(null);
        underTest = new DogEndpoint(dogKennelMemory);

        Response response = underTest.getDog(1);

        assertThat(response.getStatus(), is(equalTo(404)));
    }

    @Test
    public void getAllDogsOkForValidRequest() throws Exception {
        given(dogKennelMemory.getAll()).willReturn(null);
        underTest = new DogEndpoint(dogKennelMemory);

        Response response = underTest.getDogs();

        assertThat(response.getStatus(), is(equalTo(200)));
    }

    @Test
    public void postDogOkForValidRequest() throws Exception {
        given(dogKennelMemory.insert(trevor)).willReturn(trevor);
        underTest = new DogEndpoint(dogKennelMemory);

        Response response = underTest.postDog(trevor);

        assertThat(response.getStatus(), is(equalTo(200)));
    }

    @Test
    public void putDogOkForValidRequest() throws Exception {
        given(dogKennelMemory.get(0)).willReturn(trevor);
        given(dogKennelMemory.update(0, trevorUpdate)).willReturn(trevorUpdate);
        underTest = new DogEndpoint(dogKennelMemory);

        Response response = underTest.putDog(0, trevorUpdate);

        assertThat(response.getStatus(), is(equalTo(200)));
    }

    @Test
    public void putDogReturnsAnErrorWhenTheIdIsInvalid() throws Exception {
        given(dogKennelMemory.get(0)).willReturn(null);
        underTest = new DogEndpoint(dogKennelMemory);

        Response response = underTest.putDog(0, trevorUpdate);

        assertThat(response.getStatus(), is(equalTo(404)));
    }

    @Test
    public void deleteDogOkForValidRequest() throws Exception {
        given(dogKennelMemory.get(0)).willReturn(trevor);
        given(dogKennelMemory.delete(0)).willReturn(true);
        underTest = new DogEndpoint(dogKennelMemory);

        Response response = underTest.deleteDog(0);

        assertThat(response.getStatus(), is(equalTo(200)));
    }

    @Test
    public void deleteDogReturnsAnErrorWhenTheIdIsInvalid() throws Exception {
        given(dogKennelMemory.get(0)).willReturn(null);
        underTest = new DogEndpoint(dogKennelMemory);

        Response response = underTest.deleteDog(0);

        assertThat(response.getStatus(), is(equalTo(404)));
    }

}
