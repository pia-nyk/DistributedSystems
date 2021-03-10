package main.java.helpers;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Reply {
    Status status;
    String reply;

    public Reply(Status status, String reply) {
        this.status = status;
        this.reply = reply;
    }
}

/**
 * types of status possible for lookup & resolve methods
 */
enum Status {
    UNKNOWN,
    INVALID,
    OK;
}