package primy.helpers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * consolidated conn info with the clients
 */
public class ConnectionInfo {
    private DataInputStream clis;
    private DataOutputStream clos;
    private Socket socket;

    public ConnectionInfo(Socket socket) throws Exception{
        this.socket = socket;
        this.clis = new DataInputStream(this.socket.getInputStream());
        this.clos = new DataOutputStream(this.socket.getOutputStream());
    }

    public DataInputStream getClis() {
        return clis;
    }

    public DataOutputStream getClos() {
        return clos;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
