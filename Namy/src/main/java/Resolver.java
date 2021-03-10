package main.java;

import main.java.helpers.Reply;

import java.util.List;
import java.util.Map;

public class Resolver {

    String domainName;
    List<String> domainNameParts;
    Map<Map<String, String>, Integer> cache;

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
    public Reply resolve (String domainName) {

    }

    /**
     * looksup in the cache if the entry is present
     * @param domainName
     * @return
     */
    public Reply lookup (String domainName) {

    }
}


