package met.cs673.team1.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ErrorResponse {

    @JsonProperty
    private HttpStatus status;

    @JsonProperty
    private String message;

    @JsonProperty
    private String exception;
}
