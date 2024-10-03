import java.util.Scanner;

/**
 * this is only to be usable after adding payment method under admin menu (to test the functionality of addpaymentmethod in admin menu
 * @author Jia Wei
 * @version 1.0
 */
public class bitcoin extends OnlinePayment {
    Scanner sc = new Scanner(System.in);

    /**
     * this simulates the payment process
     * @param amount
     */
    @Override
    public void processPayment(float amount) {
        //Simulate

        System.out.println("### This is a simulation of bitcoin ###");
        System.out.println("Insert bitcoin username:");
        super.setUsername(sc.nextLine());
        System.out.println("Insert bitcoin password:");
        super.setPassword(sc.nextLine());
        //This is only for simulation purposes. Obviously, some checking needs to be done in reality
        System.out.println("Processing...");
        System.out.println("Payment successful,");
    }

}
