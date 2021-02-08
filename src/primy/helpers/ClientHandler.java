package primy.helpers;

import java.io.IOException;

/**
 * handler for each client connected
 * to the server
 */
public class ClientHandler extends Thread{
    private ConnectionInfo cInfo;
    private ClientWorkingData wData;
    private String result = "";
    private int iterations;
    private static int iterInfo = 0;

    //check primality of (clientNum^power % largeNum) on the clientside
    public ClientHandler(ClientWorkingData wData, ConnectionInfo cInfo, int iterations) {
        this.cInfo = cInfo;
        this.wData = wData;
        this.iterations = iterations;
    }
    //this method will run on invoking thread.start()
    @Override
    public void run() {
        try {
            if(iterInfo < 2) {
                //send client the nos of iterations to expect - only once
                cInfo.getClos().writeUTF(String.valueOf(iterations));
                iterInfo++;
            }

            cInfo.getClos().writeUTF(wData.toString());
            //get the results back from client
            this.result = cInfo.getClis().readUTF();
            System.out.println("The number "  + this.wData.getLargeNum() + " is :" + this.result);


        } catch (IOException e) {
            System.out.println("Exception while reading/writing to data " + e.getMessage());
        }
    }
}
