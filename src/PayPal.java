
import java.util.Scanner;

/**
 * PayPal payment method
 * @author Jia Wei
 * @version 3.0
 */
public class PayPal extends OnlinePayment {
    Scanner sc = new Scanner(System.in);

    /**
     * simulation of paypal
     * @param amount
     */
    @Override
    public void processPayment(float amount) {
        
        //Simulate
        System.out.println("### This is a simulation of PayPal ###");
        System.out.println("Insert paypal username:");
        super.setUsername(sc.nextLine());
        System.out.println("Insert paypal password:");
        super.setPassword(sc.nextLine());
        //This is only for simulation purposes. Obviously, some checking needs to be done in reality
        System.out.println("Processing...");
        System.out.println("Payment successful,");
    }

}
