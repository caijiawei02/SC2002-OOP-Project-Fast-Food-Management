/**
 * One of the ways users pay is through online payment
 * @author Kent 
 * @version 1.0
 */
public abstract class OnlinePayment implements PaymentMethod {
    /*
     * Online payment log in needs username and password
     */
    private String username;
    private String password;

    /**
     * class is abstract as the payment procedure depends on the different online payment platforms
     * @param amount
     */
    public abstract void processPayment(float amount);

    /**
     * set methods for the username and password
     * @param amount
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
