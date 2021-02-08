package primy.helpers;

/**
 * data to be passed to the clients for primality check
 */
public class ClientWorkingData {
    private int clientNum;
    private int largeNum;
    private int power;

    public ClientWorkingData(int clientNum, int largeNum, int power) {
        this.clientNum = clientNum;
        this.largeNum = largeNum;
        this.power = power;
    }

    public int getClientNum() {
        return clientNum;
    }

    public void setClientNum(int clientNum) {
        this.clientNum = clientNum;
    }

    public int getLargeNum() {
        return largeNum;
    }

    public void setLargeNum(int largeNum) {
        this.largeNum = largeNum;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
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
