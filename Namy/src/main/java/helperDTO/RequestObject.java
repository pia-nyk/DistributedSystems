package main.java.helperDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RequestObject {
    String hostname;
    String ip;
    Map<String, String> identity;
    boolean shouldRegister;
    long timeToLive;

    public RequestObject(String hostname, String ip,
                         Map<String, String> identity, boolean shouldRegister, long timeToLive) {
        this.hostname = hostname;
        this.ip = ip;
        this.identity = identity;
        this.shouldRegister = shouldRegister;
        this.timeToLive = timeToLive;
    }

    public RequestObject(String hostname, boolean shouldRegister) {
        this.hostname = hostname;
        this.shouldRegister = shouldRegister;
    }
}
