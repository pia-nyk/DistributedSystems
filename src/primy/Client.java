package primy;

import java.io.*;
import java.net.Socket;

public class Client {

    //initialize conn of client with server
    public Client(String address, int port) {
        try {
            Socket socket = new Socket(address, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            //read incoming data from server - both number from client and large number
            int N = Integer.valueOf(in.readUTF());
            int K = Integer.valueOf(in.readUTF());
            System.out.println("Numbers received from server - ClientNum " + N + " largeNum: " + K);
            boolean ans = this.fermatAlgo(N, K);

            //send ans to server
            out.writeUTF(String.valueOf(ans));

            Thread.sleep(10000);
            socket.close();
            in.close();
            out.close();

        } catch (Exception e) {
            System.out.println("Exception occurred while connecting to server " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean fermatAlgo (int N, int K) {
        return Math.pow(N, K-1) % K == 1;
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 5000);
    }
}
