package main.java.helperDTO;

import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;
import java.util.Map;

@Getter
@Setter
public class SystemInfo {
    String hostname;
    String ip;
    Map<String, String> identity;
    long timeToLive;

    public SystemInfo(String hostname, String ip, Map<String, String> identity, long timeToLive) {
        this.hostname = hostname;
        this.ip = ip;
        this.identity = identity;
        this.timeToLive = timeToLive;
    }
}
