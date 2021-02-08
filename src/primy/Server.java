package primy;

import primy.helpers.ClientHandler;
import primy.helpers.ClientWorkingData;
import primy.helpers.ConnectionInfo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Server code for checking primality of large numbers
 * This will send the numners to clients
 */
public class Server {

    private ServerSocket server = null;
    private BufferedReader termIn = null;
    private int largeNum;
    private final int clients = 2;
    private final int iterations = 10;
    private List<ConnectionInfo> connectionInfos = new ArrayList<>();

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
            System.out.println("Exception occurred while initializing server " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        Server s = new Server(5000);
        Thread[] threads = new Thread[s.clients];
        try {
            //keep on looping for incoming client connections
            //for specified number of clients
            int k = 0;
            //make the connections & store in array
            while (k < s.clients) {
                Socket socket = s.server.accept();
                System.out.println("Client accepted");

                //add current client conn in the servers list
                ConnectionInfo cInfo = new ConnectionInfo(socket);
                s.connectionInfos.add(cInfo);
                k++;
            }

            //send data to each client
            k = 0;
            while(k < s.iterations) {

                int clientNum = (int) (Math.random() * (s.largeNum-4) + 2);
                int power1 = 0;
                int power2 = 0;
                //if largeNum-1 is even
                if((s.largeNum-1)%2 == 0) {
                    power1 = (s.largeNum-1)/2;
                    power2 = (s.largeNum-1)/2;

                } else {
                    power1 = s.largeNum-1;
                    power2 = 1;
                }
                //create data objs to be passed onto clients
                ClientWorkingData wDataClient1 = new ClientWorkingData(clientNum, s.largeNum, power1);
                ClientWorkingData wDataClient2 = new ClientWorkingData(clientNum, s.largeNum, power2);

                //create worker threads for each client and send the data resp
                threads[0] = new ClientHandler(wDataClient1, s.connectionInfos.get(0), s.iterations);
                threads[0].start();

                threads[1] = new ClientHandler(wDataClient2, s.connectionInfos.get(1), s.iterations);
                threads[1].start();
                k++;

                //wait for all threads to finish before exiting main thread
                System.out.println("Waiting for clients to finish");
                for (Thread t: threads){
                    t.join();
                }
            }

        } catch (Exception e) {
            System.out.println("Exception occurred while connecting to clients " + e.getMessage());
            e.printStackTrace();
        } finally {
            //close sockets and data streams with all clients
            for(ConnectionInfo tempInfo : s.connectionInfos) {
                tempInfo.getSocket().close();
                tempInfo.getClis().close();
                tempInfo.getClos().close();
            }
        }
    }
}
