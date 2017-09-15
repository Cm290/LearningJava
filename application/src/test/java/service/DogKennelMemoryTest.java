package service;

import dog.broker.info.Dog;
import dog.broker.service.DogKennelMemory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class DogKennelMemoryTest {
    private static final Dog trevor = Dog.builder().name("Trevor").build();
    private static final Dog trevorUpdate = Dog.builder().name("Trevor").age(2).build();
    private static final Dog julia = Dog.builder().name("Juila").age(1).build();
    private static final Dog fred =  Dog.builder().name("Fred").build();

    @Mock
    private DogKennelMemory undertest;

    @Before
    public void setup() throws Exception {
        undertest = new DogKennelMemory();
        undertest.insert(trevor);
        undertest.insert(julia);
    }


    @Test
    public void getDogKennelMemory() throws Exception {

        final Dog dog = undertest.get(0);

        assertThat(dog, is(equalTo(trevor)));
    }

    @Test
    public void getIsPassedNullShouldReturnNull() throws Exception {
        final Dog dog = undertest.get(null);

        assertThat(dog, is(equalTo(null)));
    }

    @Test
    public void getAllDogKennelMemory() throws Exception {
        final List<Dog> dogs = undertest.getAll();

        assertThat(dogs, contains(trevor, julia));
        assertThat(dogs, hasSize(2));
    }

    @Test
    public void updateDogKennelMemory() throws Exception {
        undertest.update(0, trevorUpdate);

        final List<Dog> dogs = undertest.getAll();
        final Dog dog = undertest.get(0);

        assertThat(dog, is(equalTo(trevorUpdate)));
        assertThat(dogs, contains(trevorUpdate, julia));
    }

    @Test
    public void insertIntoDogKennelMemory() throws Exception {
        undertest.insert(fred);

        final List<Dog> dogs = undertest.getAll();

        assertThat(dogs, contains(trevor, julia, fred));
        assertThat(dogs, hasSize(3));
    }

    @Test
    public void deleteDogFromDogKennelMemory() throws Exception {
        undertest.delete(1);

        final List<Dog> dogs = undertest.getAll();

        assertThat(dogs, contains(trevor));
        assertThat(dogs, hasSize(1));
    }
}
