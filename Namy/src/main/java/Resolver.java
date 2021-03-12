package main.java;

import com.google.gson.Gson;
import main.java.helperDTO.CacheEntry;
import main.java.helperDTO.RequestObject;
import main.java.helperDTO.ResponseObject;
import main.java.helperDTO.Status;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.List;

public class Resolver {

    List<String> domainNameParts;
    List<CacheEntry> cache;
    DatagramSocket socket;
    byte[] buffer;

    /**
     * initialize cache with root nameserver & conn variables
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
    public ResponseObject resolve (String domainName) throws UnknownHostException, IOException{
        int i = 0; //sublist to ignore for resolving
        boolean found = false;
        ResponseObject responseObject = null;
        while(!found && i < domainNameParts.size()) {
            String domainToSearch = String.join(".", domainNameParts.subList(i,
                    domainNameParts.size()-1));
            if(Status.OK.equals(lookUp(domainToSearch))) {
                found = true;
            }
            i++;
        }

        //iterate the subdomains to get the main domain ip
        while(i >= 0) {
            //ping server i to end and get data for server i-1 to end
            CacheEntry temp = cache.get(cache.size()-1);
            responseObject = pingServer(temp, domainNameParts.get(i));
            CacheEntry e = new CacheEntry(domainNameParts.get(i), responseObject.getHostname(),
                    Integer.parseInt(responseObject.getIdentity().get(1)), responseObject.getReply(),
                    System.currentTimeMillis(), responseObject.getTimeToLive());
            cache.add(e);
            i--;
        }
        return responseObject;
    }

    /**
     * ping the intermediary servers for host & ports
     * @param entry
     * @param childDomain
     * @return
     * @throws UnknownHostException
     */
    public ResponseObject pingServer(CacheEntry entry, String childDomain) throws UnknownHostException, IOException {
        //request the child domain data from cached server
        RequestObject requestObject = new RequestObject(childDomain, false);
        buffer = new Gson().toJson(requestObject).getBytes();
        DatagramPacket request = new DatagramPacket(buffer, buffer.length,
                InetAddress.getByName(entry.getHost()), entry.getPort());
        socket.send(request);

        //receive response
        Arrays.fill(buffer, (byte)0); //clearing the array for response
        DatagramPacket response = new DatagramPacket(buffer, buffer.length, request.getAddress(), request.getPort());
        socket.receive(response);
        return new Gson().fromJson(new String(buffer), ResponseObject.class);
    }

    /**
     * validates the entry in cache
     * based on timetolive
     * @return
     */
    public Status lookUp (String subdomain) {
        for(CacheEntry e: cache) {
            if(subdomain.equals(e.getDomainName())) {
                if(e.getCreatedTime() + e.getTimeToLive() <
                        System.currentTimeMillis()) {
                    return Status.OK;
                }
                return Status.INVALID;
            }
        }
        return Status.UNKNOWN;
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

            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();

            //resolve the request & send result to client
            ResponseObject responseObject = r.resolve(new String(r.buffer));
            //send only the ip as required by client
            r.buffer = responseObject.getReply().getBytes();
            DatagramPacket response = new DatagramPacket(r.buffer, r.buffer.length, clientAddress, clientPort);
            r.socket.send(response);

        } catch (SocketException e) {
            System.out.println("Resolver port throws error while starting up. Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error occured while receiving request from client. Error: " + e.getMessage());
        }

    }
 }


