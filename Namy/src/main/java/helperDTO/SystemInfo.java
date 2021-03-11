package main.java.helperDTO;

import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;
import java.util.Map;

@Getter
@Setter
public class SystemInfo {
    String hostname;
    int port;
    String ip;
    Map<String, String> identity;

    public SystemInfo(String hostname, int port, String ip, Map<String, String> identity) {
        this.hostname = hostname;
        this.port = port;
        this.ip = ip;
        this.identity = identity;
    }
}
