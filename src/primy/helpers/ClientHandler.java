package primy.helpers;

import java.io.IOException;

/**
 * handler for each client connected
 * to the server
 */
public class ClientHandler extends Thread{
    private ConnectionInfo cInfo;
    private ClientWorkingData wData;
    private int iterations;
    private static int iterInfo = 0;
    private ClientResult clientResult;

    //check primality of (clientNum^power % largeNum) on the clientside
    public ClientHandler(ClientWorkingData wData, ConnectionInfo cInfo, int iterations, ClientResult clientResult) {
        this.cInfo = cInfo;
        this.wData = wData;
        this.iterations = iterations;
        this.clientResult = clientResult;
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
            int ans = Integer.valueOf(cInfo.getClis().readUTF());

            synchronized (this.clientResult) {
                this.clientResult.update(ans);
            }

        } catch (IOException e) {
            System.out.println("Exception while reading/writing to data " + e.getMessage());
        }
    }
}
