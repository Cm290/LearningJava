package dog.broker.endpoints;

import dog.broker.info.Dog;
import dog.broker.info.Measurements;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.springframework.util.StringUtils.isEmpty;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/dogEndpoint")
public class DogEndpoint {

    Dog trevor = Dog.builder().name("Trevor").age(2).measurements(Measurements.builder().height(24).build()).build();

    @GET
    public Response getDog() {
        if (isEmpty(trevor.getName())) {
            return dogHasName();
        }
        return Response
                .ok(trevor)
                .build();
    }

    @GET
    @Path("/birthdayParty")
    public Response getBirthdayParty() {
        if (isEmpty(trevor.getName())) {
            return dogHasName();
        }
        trevor.hadBirthday();
        return Response
                .ok(trevor)
                .build();
    }

    private Response dogHasName() {
            return Response
                    .status(500)
                    .entity("Give your dog a name")
                    .build();
    }
}
