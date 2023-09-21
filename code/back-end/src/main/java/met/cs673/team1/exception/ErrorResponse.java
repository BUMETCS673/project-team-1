package met.cs673.team1.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ErrorResponse {

    private HttpStatus status;

    private String message;

    private Exception exception;
}
