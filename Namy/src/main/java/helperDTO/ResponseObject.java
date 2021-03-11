package main.java.helperDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseObject {
    Status status;
    String reply;

    public ResponseObject(Status status, String reply) {
        this.status = status;
        this.reply = reply;
    }
}

