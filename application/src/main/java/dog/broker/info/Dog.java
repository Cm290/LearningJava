package dog.broker.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dog {
    private String name;
    private Integer age;
    private Measurements measurements;

    public void hadBirthday() {
       age ++;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
