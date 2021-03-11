package main.java;

import main.java.helperDTO.SystemInfo;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;

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
        JSONObject entry = new JSONObject(new String(buffer));
        String value = entry.get("shouldRegister").toString();
        if(value != null && "true".equals(value)) {
            registerServer(entry);
        } else {
            unregisterServer(entry);
        }
    }

    /**
     * get the systeminfo from jsonobject
     * and add to servers list
     * @param o
     */
    public void registerServer(JSONObject o) {
        SystemInfo child = new SystemInfo(o.get("hostname").toString(),
                Integer.parseInt(o.get("port").toString()),
                o.get("ip").toString(),(Map<String, String>)o.get("identity"));
        s.children.add(child);

    }

    /**
     * find child using hostname and remove from
     * servers list
     * @param o
     */
    public void unregisterServer(JSONObject o) {
        String hostname = o.get("hostname").toString();
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
