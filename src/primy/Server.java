package primy;

import primy.helpers.ClientHandler;
import primy.helpers.ClientResult;
import primy.helpers.ClientWorkingData;
import primy.helpers.ConnectionInfo;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Server code for checking primality of large numbers
 * This will send the numners to clients
 */
public class Server {

    private ServerSocket server = null;
    private BufferedReader termIn = null;
    private BigInteger largeNum;
    private final int clients = 2;
    private final int iterations = 10;
    private List<ConnectionInfo> connectionInfos = new ArrayList<>();
    private ClientResult clientResult;

    //starts the server and waits for client conn
    public Server(int port) {

        try{
            //connect servers input stream to terminal
            termIn = new BufferedReader(new InputStreamReader(System.in));

            //take the large number i/p from user
            this.largeNum = new BigInteger(termIn.readLine());
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
            boolean finalAns = true;
            while(k < s.iterations) {

                s.clientResult = new ClientResult();
                Random rand = new Random();
                BigInteger clientNum = new BigInteger(s.largeNum.intValue() - 4, rand).add(new BigInteger("2"));
                BigInteger power = s.largeNum.subtract(new BigInteger("1")).divide(new BigInteger("2"));

                //create data objs to be passed onto clients
                ClientWorkingData wDataClient1 = new ClientWorkingData(clientNum, s.largeNum, power);
                ClientWorkingData wDataClient2 = new ClientWorkingData(clientNum, s.largeNum, power);

                //create worker threads for each client and send the data resp
                threads[0] = new ClientHandler(wDataClient1, s.connectionInfos.get(0), s.iterations, s.clientResult);
                threads[0].start();

                threads[1] = new ClientHandler(wDataClient2, s.connectionInfos.get(1), s.iterations, s.clientResult);
                threads[1].start();
                k++;

                //wait for all threads to finish before exiting main thread
                System.out.println("Waiting for clients to finish");
                for (Thread t: threads){
                    t.join();
                }
                //calculate the final ans
                //formula: (a*b) mod m = ((a mod m) * (b mod m)) mod m
                System.out.println("Multiplication ans " + s.clientResult.get());
                finalAns = finalAns & (s.clientResult.get().mod(s.largeNum).intValue()) == 1;
            }
            System.out.println("The number " + s.largeNum + " is a " +
                    (finalAns ? "prime" : "not prime"));

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
