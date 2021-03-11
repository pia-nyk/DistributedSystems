package main.java;

import main.java.helperDTO.CacheEntry;
import main.java.helperDTO.ResponseObject;
import main.java.helperDTO.Status;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

public class Resolver {

    String domainName;
    List<String> domainNameParts;
    List<CacheEntry> cache;
    DatagramSocket socket;
    byte[] buffer;

    /**
     * initialize cache with root nameserver
     */
    public Resolver(String nameServerHost, int nameServerPort, int resolverPort) throws SocketException {
        //assuming root nameserver remains same, this entry will remain
        // in cache as long as possible
        CacheEntry cacheEntry = new CacheEntry("", nameServerHost, nameServerPort, null,
                System.currentTimeMillis(), Integer.MAX_VALUE);
        cache.add(cacheEntry);

        //initailize conn data
        socket = new DatagramSocket(resolverPort);
        buffer = new byte[256];
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
     * validates the entry in cache
     * based on timetolive
     * @param time
     * @return
     */
    public Status checkValidCacheEntry (String subdomain, long time) {
        for(CacheEntry e: cache) {
            if(subdomain.equals(e.getDomainName())) {
                if(e.getCreatedTime() + time < e.getTimeToLive()) {
                    return Status.OK;
                }
                return Status.INVALID;
            }
        }
        return Status.UNKNOWN;
    }

    /**
     * looks up in the cache if the entry is present
     * @param domainName
     * @return
     */
    public ResponseObject lookup (String domainName) {

    }

    /**
     * process the requested domain name into different sub-domains
     */
    public void processClientRequest () {
        //convert from byte array to string & split into sub-domains
        String domainName = new String(buffer);
        domainNameParts = List.of(domainName.split("."));
    }

    public static void main(String[] args) {
        //todo: currently resolver can handle only 1 request. Create multiple request handler
        try {
            Resolver r = new Resolver(System.getProperty("nameserverHost"), Integer.parseInt(System.getProperty("nameserverPort")),
                    Integer.parseInt(System.getProperty("resolverPort")));
            //listen for request from client
            DatagramPacket request = new DatagramPacket(r.buffer, r.buffer.length);
            r.socket.receive(request);

            //after receiving the request, process it
            r.processClientRequest();

        } catch (SocketException e) {
            System.out.println("Resolver port throws error while starting up. Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error occured while receiving request from client. Error: " + e.getMessage());
        }

    }
 }


