import java.util.Scanner;

/**
 * this is a type of payment method ie. cardpayment
 * @author Jia Wei
 * @version 1.0
 */
public class CardPayment implements PaymentMethod{
    Scanner sc = new Scanner(System.in);
    private String cardID;
    private String cvv;
    /**
     * this simulates the payment process
     * @param amount
     */
    @Override
    public void processPayment(float amount) {
        //simulation
        System.out.println("### This is a simulation of Card Payment ###");
        System.out.println("Insert Card Number:");
        cardID = sc.nextLine();
        System.out.println("Insert security code:");
        cvv = sc.nextLine();
        System.out.println("Processing...");
        System.out.println("Payment successful,");
    }

}
