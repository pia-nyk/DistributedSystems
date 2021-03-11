package main.java.helperDTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class CacheEntry {
    String domainName;
    String host;
    int port;
    String ip;
    long createdTime;
    int timeToLive;

    public CacheEntry(String domainName, String ip, long createdTime, int timeToLive) {
        this.domainName = domainName;
        this.ip = ip;
        this.createdTime = createdTime;
        this.timeToLive = timeToLive;
    }
}
