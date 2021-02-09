package primy;

import primy.helpers.ClientWorkingData;

import java.io.*;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Client {

    private ClientWorkingData wData;

    //initialize conn of client with server
    public Client(String address, int port) throws Exception{
        Socket socket = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            socket = new Socket(address, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            //read incoming data from server - both number from client and large number
            int N = Integer.valueOf(in.readUTF());
            System.out.println(N);

            while (N > 0) {
                String temp = in.readUTF();

                //data preprocessing
                //todo: use json instead
                temp = temp.replace('}', ' ');
                temp = temp.replace('{', ' ');
                temp = temp.trim();

                System.out.println(temp);
                Map<String, String> hMapData = Arrays.stream(temp.split(","))
                        .map(s -> s.split(":"))
                        .collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim()));

                System.out.println("Numbers received from server - ClientNum " + hMapData.get("clientNum") + " largeNum: " + hMapData.get("largeNum")
                + " power " + hMapData.get("power"));
                int ans = (int) this.fermatAlgo(Integer.valueOf(hMapData.get("clientNum"))
                        , Integer.valueOf(hMapData.get("largeNum")),
                        Integer.valueOf(hMapData.get("power")));
                System.out.println("Answer calculated by client : " + ans);

                //send ans to server
                out.writeUTF(String.valueOf(ans));

                Thread.sleep(10000);
                N--;
            }

        } catch (ConnectException ce){
            System.out.println("Server not ready for connection. Please try later! " + ce.getMessage());
        } catch (Exception e) {
            System.out.println("Exception occurred while connecting to server " + e.getMessage());
            e.printStackTrace();
        } finally {
            socket.close();
            in.close();
            out.close();
        }
    }

    public BigInteger fermatAlgo (int N, int K, int pow) {
        return ((BigInteger)Math.pow(N, pow)) % K;
    }

    public static void main(String[] args) throws Exception{
        Client client = new Client("127.0.0.1", 5000);
    }
}
