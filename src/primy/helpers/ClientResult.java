package primy.helpers;

public class ClientResult {
    private int result = 1;
    //this method uses synchronized block
    public void update(int val) {
        this.result = this.result * val;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted exception caught: " + e.getMessage());
        }
    }

    public int get () {
        return result;
    }

}
