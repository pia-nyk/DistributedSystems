package primy.helpers;

import java.math.BigInteger;

/**
 * data to be passed to the clients for primality check
 */
public class ClientWorkingData {
    private BigInteger clientNum;
    private BigInteger largeNum;
    private BigInteger power;

    public ClientWorkingData(BigInteger clientNum, BigInteger largeNum, BigInteger power) {
        this.clientNum = clientNum;
        this.largeNum = largeNum;
        this.power = power;
    }

    public BigInteger getClientNum() {
        return clientNum;
    }

    public void setClientNum(BigInteger clientNum) {
        this.clientNum = clientNum;
    }

    public BigInteger getLargeNum() {
        return largeNum;
    }

    public void setLargeNum(BigInteger largeNum) {
        this.largeNum = largeNum;
    }

    public BigInteger getPower() {
        return power;
    }

    public void setPower(BigInteger power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "{" +
                "clientNum:" + clientNum +
                ", largeNum:" + largeNum +
                ", power:" + power +
                '}';
    }
}
