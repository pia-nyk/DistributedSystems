package main.java;

import main.java.helperDTO.CacheEntry;
import main.java.helperDTO.ResponseObject;
import java.util.List;

public class Resolver {

    String domainName;
    List<String> domainNameParts;
    List<CacheEntry> cache;

    /**
     * initialize cache with root nameserver
     */
    public Resolver() {

    }

    /**
     * iteratively go through domainNameParts
     * to get the host Ip
     * @param domainName
     * @return
     */
    public ResponseObject resolve (String domainName) {

    }

    /**
     * validates if the entry in cache is still valid
     * based on timetolive
     * @param time
     * @return
     */
    public boolean checkValidCacheEntry (int time) {

    }

    /**
     * looksup in the cache if the entry is present
     * @param domainName
     * @return
     */
    public ResponseObject lookup (String domainName) {

    }
}


