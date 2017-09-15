package dog.broker.service;

import dog.broker.info.Dog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DogKennelMemory {
    private Map<Integer, Dog> dogKennel = new HashMap<>();
    private Integer currentId = 0;

    public Dog get(Integer id) {
        Dog dog = null;
        if (id != null && dogKennel.containsKey(id)) {
        return dogKennel.get(id);
        }
        return dog;
    }

    public List<Dog> getAll() {
        return new ArrayList<>(dogKennel.values());
    }

    // returns empty list

    public Dog update(Integer id, Dog newDog) {
        if (id != null && dogKennel.containsKey(id)) {
            return dogKennel.replace(id, newDog);
        }
        return null;
    }

    // returns null if given either id is null and newDog != null and invalid id

    public Dog insert(Dog newDog) {
        if (newDog != null) {
            Dog dog = dogKennel.put(currentId, newDog);
            currentId++;
            return dog;
        }
        return null;
    }

    // returns null if new is null

    public Boolean delete(Integer id) {
        Dog dog = null;
        if (dogKennel.containsKey(id)) {
            dog = dogKennel.remove(id);
        }

        return dog != null;
    }

    // returns false if id is null or invalid

}
