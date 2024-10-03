
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *  Run the main program here
 * @author Jia Wei
 * @version 1.0
 */
public class FOMS_App {
    /**
     * run the main prog here
     * @param args
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // add start() FOM here
        FOMS foms = new FOMS();
        foms.start();

    }
}
