package main.java.helperDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ResponseObject {
    Status status;
    String reply;
    String hostname;
    Map<String, String> identity;
    long timeToLive;

    public ResponseObject(Status status, String reply,
                          String hostname, Map<String, String> identity, long timeToLive) {
        this.status = status;
        this.reply = reply;
        this.hostname = hostname;
        this.identity = identity;
        this.timeToLive = timeToLive;
    }
}

