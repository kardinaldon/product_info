package exception;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CustomException extends RuntimeException {

    private int errorCode;
    private String errorMessage;

}
