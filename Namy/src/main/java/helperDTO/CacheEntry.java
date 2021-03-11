package main.java.helperDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CacheEntry {
    String domainName;
    String host;
    int port;
    String ip;
    long createdTime;
    int timeToLive;

    public CacheEntry(String domainName, String host, int port, String ip, long createdTime, int timeToLive) {
        this.domainName = domainName;
        this.host = host;
        this.port = port;
        this.ip = ip;
        this.createdTime = createdTime;
        this.timeToLive = timeToLive;
    }
}
