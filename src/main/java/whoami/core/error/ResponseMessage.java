package whoami.core.error;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Test;

@Getter
@Setter
@ToString
public class ResponseMessage {
    private String status;
    private String message;
    private String errorMessage;
    private String errorCode;

    public ResponseMessage() {}

    public ResponseMessage(String status, String message, String errorMessage, String errorCode) {
        this.status = status;
        this.message = message;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
