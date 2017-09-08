package dog.broker.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created by morric67 on 07/09/2017.
 */

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Measurements {
    private Integer neck;
    private Integer height;
    private Integer length;
    private Integer torso;
}
