package main.java;

import com.google.gson.Gson;
import main.java.helperDTO.RequestObject;
import main.java.helperDTO.SystemInfo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class RegistrationHelper implements Runnable{

    DatagramSocket socket;
    byte[] buffer;
    Server s;

    public RegistrationHelper(Server s, int port) throws SocketException {
        socket = new DatagramSocket(port);
        buffer = new byte[256];
        this.s = s;
    }

    /**
     * determines if its a registration
     * or un-register request
     */
    public void processRequest() {
        RequestObject requestObject = new Gson().fromJson(new String(buffer), RequestObject.class);
        boolean shouldRegister = requestObject.isShouldRegister();
        if(shouldRegister) {
            registerServer(requestObject);
        } else {
            unregisterServer(requestObject);
        }
    }

    /**
     * get the systeminfo from jsonobject
     * and add to servers list
     * @param obj
     */
    public void registerServer(RequestObject obj) {
        SystemInfo child = new SystemInfo(obj.getHostname(), obj.getIp(), obj.getIdentity());
        s.children.add(child);
    }

    /**
     * find child using hostname and remove from
     * servers list
     * @param obj
     */
    public void unregisterServer(RequestObject obj) {
        String hostname = obj.getHostname();
        for(SystemInfo child: s.children) {
            if(hostname.equals(child.getHostname())) {
                s.children.remove(child);
                return;
            }
        }
    }

    public void run() {
        try {
            //wait for incoming request
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            //once request arrives process it
            processRequest();

        } catch (IOException e) {
            System.out.println("Couldn't listen to UDP port " + e.getMessage());
        }
    }
}
