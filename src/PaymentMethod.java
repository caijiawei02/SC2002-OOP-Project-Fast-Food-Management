import java.awt.*;

/**
 * interface to implement for the different payment methods
 * @author Jia Wei
 * @version 2.0
 */
public interface PaymentMethod {
    /**
     * all payment methods should implement this
     * @param amount
     */
    default void processPayment(float amount) {
        System.out.println("Processing...");
        System.out.println("Payment successful,");
    }
}

