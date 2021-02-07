package primy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server code for checking primality of large numbers
 * This will send the numners to clients
 */
public class Server {

    private Socket socket = null;
    private ServerSocket server = null;
    private BufferedReader termIn = null;
    private DataInputStream clientIn = null;
    private DataOutputStream clientOut = null;
    private int largeNum;
    private final int iterations = 2;

    //starts the server and waits for client conn
    public Server(int port) {

        try{
            //connect servers input stream to terminal
            termIn = new BufferedReader(new InputStreamReader(System.in));

            //take the large number i/p from user
            this.largeNum = Integer.valueOf(termIn.readLine());
            System.out.println("Integer choosen by server: " + this.largeNum);

            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for client conn");

        } catch (Exception e) {
            System.out.println("Exception occured while initializing server " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        Server server = new Server(5000);
        Thread[] threads = new Thread[2];
        try {
            //keep on looping for incoming client connections
            int k = 0;
            while (k < server.iterations) {
                server.socket = server.server.accept();
                System.out.println("Client accepted");
                //make communication setup with client
                server.clientIn = new DataInputStream(server.socket.getInputStream());
                server.clientOut = new DataOutputStream(server.socket.getOutputStream());

                //get a random number from 2 to largeNum-2
                int clientNum = 2 + (int) (Math.random() % (server.largeNum - 4));
                System.out.println("Integer chosen for client: " + clientNum);

                //create a new thread for each client
                threads[k] = new ClientHandler(clientNum, server.largeNum, server.clientIn, server.clientOut, server.socket);
                threads[k].start();
            }

            System.out.println("Waiting for clients to finish");
            threads[0].join();
            threads[1].join();

        } catch (Exception e) {
            System.out.println("Exception occurred while reading/writing to the client " + e.getMessage());
            e.printStackTrace();
        } finally {
            server.socket.close();
        }
    }
}

/**
 * handler for each client connected
 * to the server
 */
class ClientHandler extends Thread {
    private final int clientNum;
    private final int largeNum;
    private DataInputStream clis = null;
    private DataOutputStream clos = null;
    private String result = "";
    private Socket socket = null;

    public ClientHandler(int clientNum, int largeNum, DataInputStream clis, DataOutputStream clos, Socket socket) {
        this.clientNum = clientNum;
        this.largeNum = largeNum;
        this.clis = clis;
        this.clos = clos;
        this.socket = socket;
    }
    //this method will run on invoking thread.start()
    @Override
    public void run() {
        try {
            //send the number to client for testing
            clos.writeUTF(String.valueOf(clientNum));
            clos.writeUTF(String.valueOf(largeNum));

            //get the results back from client
            this.result = clis.readUTF();
            System.out.println("The number "  + this.largeNum + " is :" + this.result);

            clos.close();
            clis.close();

        } catch (IOException e) {
            System.out.println("Exception while reading/writing to data " + e.getMessage());
        }
    }
}
