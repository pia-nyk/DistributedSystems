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

    public RequestObject(String hostname, String ip,
                         Map<String, String> identity, boolean shouldRegister) {
        this.hostname = hostname;
        this.ip = ip;
        this.identity = identity;
        this.shouldRegister = shouldRegister;
    }
}
