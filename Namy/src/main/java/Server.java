package main.java;

import com.google.gson.Gson;
import main.java.helperDTO.ResponseObject;
import main.java.helperDTO.Status;
import main.java.helperDTO.SystemInfo;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

public class Server {
    public List<SystemInfo> children;
    Server parent = null;
    Map<String, String> identity = null;
    int timeToLive = 0;
    boolean host;
    String ip;

    //conn vars
    DatagramSocket socket;
    byte[] buffer;

    public Server(boolean host, int port) throws SocketException {
        this.host = host;
        this.socket = new DatagramSocket(port);
        this.buffer = new byte[256];
    }

    /**
     * get current server registered to parent
     * @param host
     * @param port
     */
    public void register (InetAddress host, int port) {

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

        //if its the host, we are at the end of lookup line
        //send the ip of the current system
        if(this.host) {
            //create a response obj in proper format
            ResponseObject obj = new ResponseObject(Status.OK, ip);
            buffer = new Gson().toJson(obj).getBytes();
            DatagramPacket response = new DatagramPacket(buffer,
                    buffer.length, senderAddress, senderPort);
            socket.send(response);
        }
    }

    public static void main(String[] args) {
        try {
            Server s = new Server(Boolean.getBoolean(args[1]), 17); //pass whether host or server while starting

        } catch (SocketException e) {
            System.out.println("Cannot create a server on specified port " + e.getMessage());
        }
    }

}
