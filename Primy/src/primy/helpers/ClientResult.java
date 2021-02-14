package primy.helpers;

import java.math.BigInteger;

public class ClientResult {
    private BigInteger result = new BigInteger("1");
    //this method uses synchronized block
    public void update(BigInteger val) {
        this.result = this.result.multiply(val);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception caught: " + e.getMessage());
        }
    }

    public BigInteger get () {
        return result;
    }

}
