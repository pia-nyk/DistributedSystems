package main.java;

import com.google.gson.Gson;
import main.java.helperDTO.RequestObject;
import main.java.helperDTO.ResponseObject;
import main.java.helperDTO.Status;
import main.java.helperDTO.SystemInfo;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    public List<SystemInfo> children;
    //identity consists of the type of server & port number
    Map<String, String> identity = null;
    int timeToLive = 0;
    boolean host;
    String hostname;
    String ip;

    //conn vars
    DatagramSocket socket;
    byte[] buffer;

    public Server(boolean host, int port) throws SocketException {
        this.host = host;
        this.socket = new DatagramSocket(port);
        this.buffer = new byte[256];

        //data whether the system is a server or host
        //for recursor to make decision while iteration
        identity = new HashMap<>();
        String systemType = "server";
        if(this.host) {
            systemType = "host";
        }
        identity.put("type", systemType);
        identity.put("port", String.valueOf(port));
    }

    /**
     * get current server registered to parent
     * @param host
     * @param port
     */
    public void register (InetAddress host, int port) throws IOException {
        RequestObject requestObject = new RequestObject(hostname, ip, identity, true);
        buffer = new Gson().toJson(requestObject).getBytes();
        DatagramPacket request = new DatagramPacket(buffer, buffer.length, host, port);
        socket.send(request);
    }

    /**
     * iterate the children to check if given hostname is present
     * if not return null, dont check recursively
     * @param hostname
     * @return
     */
    public void lookUp(String hostname, DatagramPacket request) throws IOException {
        InetAddress senderAddress = request.getAddress();
        int senderPort = request.getPort();
        ResponseObject obj = null;
        //if its the host, we are at the end of lookup line
        //send the ip of the current system
        if(this.host) {
            //create a response obj in proper format
            obj = new ResponseObject(Status.OK, ip, hostname, identity);
        } else {
            //check if hostname is a child of current system
            for(SystemInfo child: children) {
                if(hostname.equals(child.getHostname())) {
                    obj = new ResponseObject(Status.OK, ip, child.getHostname(), child.getIdentity());
                    break;
                }
            }
            //if requested host info not in children
            //return null
            if(obj == null) {
                obj = new ResponseObject(Status.UNKNOWN, null, null, null);
            }
        }
        buffer = new Gson().toJson(obj).getBytes();
        DatagramPacket response = new DatagramPacket(buffer,
                buffer.length, senderAddress, senderPort);
        //send data to resolver
        socket.send(response);
    }

    public static void main(String[] args) {
        try {
            Server s = new Server(Boolean.getBoolean(System.getProperty("isHost")),
                    Integer.parseInt(System.getProperty("serverNo"))); //pass whether host or server while starting
            InetAddress host = InetAddress.getByName(System.getProperty("hostname"));
            int port = Integer.parseInt(System.getProperty("port"));

            //send registration request to the parent
            s.register(host, port);

        } catch (SocketException e) {
            System.out.println("Cannot create a server on specified port: " + e.getMessage());
        } catch (UnknownHostException e) {
            System.out.println("Parent host not found " + System.getProperty("hostname")
                    + " Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Couldn't send registration request to parent: " + e.getMessage());
        }
    }

}
