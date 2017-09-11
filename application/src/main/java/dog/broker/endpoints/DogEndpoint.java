package dog.broker.endpoints;

import dog.broker.info.Dog;
import dog.broker.info.Measurements;
import dog.broker.info.Message;
import dog.broker.service.DogKennelMemory;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.springframework.util.StringUtils.isEmpty;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/dogEndpoint")
public class DogEndpoint {
    private DogKennelMemory dogKennelMemory;

    public DogEndpoint(DogKennelMemory dogKennelMemory) {
        this.dogKennelMemory = dogKennelMemory;
    }

//    Dog trevor = Dog.builder().name("Trevor").age(2).measurements(Measurements.builder().height(24).build()).build();

    @GET
    public Response getDogs() {
        return Response
                .ok(dogKennelMemory.getAll())
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getDog(@PathParam("id") Integer id) {
        Dog dog = dogKennelMemory.get(id);
        if(dog == null){
            return dogNotFound();
        }
        return Response
                .ok(dog)
                .build();
    }

    @GET
    @Path("/{id}/birthdayParty")
    public Response getBirthdayParty(@PathParam("id") Integer id) {
        Dog dog = dogKennelMemory.get(id);
        if(dog == null){
            return dogNotFound();
        }
        dog.hadBirthday();
        return Response
                .ok(dog)
                .build();
    }

    @POST
    public Response postDog(Dog dog) {
        return Response
                .ok(dogKennelMemory.insert(dog))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response putDog(@PathParam("id") Integer id, Dog dog) {
        if(dogKennelMemory.get(id) == null){
            return dogNotFound();
        }
        return Response
                .ok(dogKennelMemory.update(id, dog))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDog(@PathParam("id") Integer id) {
        if(dogKennelMemory.get(id) == null){
            return dogNotFound();
        }

        return Response
                .ok(Message.builder().message("Success: " + dogKennelMemory.delete(id)).build())
                .build();
    }

    private Response dogNotFound() {
        return Response
                .status(404)
                .entity(Message.builder().message("oh no your dog doesn't exist").build())
                .build();
    }
}
