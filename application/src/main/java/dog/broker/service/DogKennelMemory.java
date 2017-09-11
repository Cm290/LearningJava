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
        return dogKennel.get(id);
    }

    public List<Dog> getAll() {
        return new ArrayList<>(dogKennel.values());
    }

    public Dog update(Integer id, Dog newDog) {
        if (dogKennel.containsKey(id)) {
        return dogKennel.replace(id, newDog);
        }
        return null;
    }

    public Dog insert(Dog newDog) {
        Dog dog = dogKennel.put(currentId, newDog);
        currentId++;
        return dog;
    }

    public Boolean delete(Integer id) {
        Dog dog = dogKennel.remove(id);

        return dog != null;
    }

}
