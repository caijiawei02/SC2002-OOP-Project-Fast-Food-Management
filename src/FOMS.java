
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  Main functions for FOMS
 * @author Jia Wei, Ruparaj
 */
public class FOMS {

    int orderID = 0;
    private Timer timer;


    /**
     *  starts the FOMs
     */
    public void start(){
        this.timer = new Timer();
        startOrderMonitoring();
        boolean end = false;
        do{


            int interfaceChoice = -1;
            String[] staff;

            while (interfaceChoice == -1){
                interfaceChoice = selectInterface();
                switch(interfaceChoice){
                    case 1: // Customer
                        // select branch
                        BranchMenu branch = new BranchMenu();
                        String branchName = branch.selectBranch();
                        //System.out.println(branchName);
                        orderID++;
                        OrderPlacement order = new OrderPlacement(branchName, orderID);


                        // select dinein or takeaway
                        order.loadMenuOptions();
                        // select branch specific menu items

                        // while not check out, add, edit or delete items from cart

                        // select payment method (simulate payment)
                        // print receipt with order ID
                        // check order status w order ID
                        // collected food -> changes order status to completed
                        break;
                    case 2: // Staff;

                        // Login with accountID and password (default: password) -> initialised at startup
                        staff =  staffLogin();

                        // option to change password
                        if(staff[3].equals("S")) {
	                    	MenuInterface menu = new StaffMenu(staff);
	                      	menu.loadMenuOptions();
                       }
						else if(staff[3].equals("M")) {
							MenuInterface menu = new ManagerMenu(staff);
						  	menu.loadMenuOptions();
						}
						else if(staff[3].equals("A")) {
							MenuInterface menu = new AdminMenu();
						  	menu.loadMenuOptions();
						}
						else
							System.out.println("Error: Invalid Role!");
                        break;
                    default:
                        System.out.println("Please select a valid option");
                }
            }
        }while (!end);
    }

    /**
     * select either customer or staff interface
     * @return 1 for customer interface, 2 if staff interface
     */
    public int selectInterface() {
        Scanner sc = new Scanner(System.in);

        int choice;

        System.out.println("====== FOMS Main Menu ======");
        System.out.println("Select:");
        System.out.println("(1) Customer");
        System.out.println("(2) Staff");

        choice = sc.nextInt();

        while (choice != 1 || choice!= 2){
            switch (choice) {
                case 1: // Customer
                    return 1;
                case 2: // Staff
                    return 2;
                default:
                    System.out.println("Please select a valid option");
                    return -1;
            }
        }

        return -1;
    }

    /**
     * for staff to login
     * @return staff name
     */
    public String[] staffLogin() {
        StaffList stafflist = new StaffList();
        Scanner sc = new Scanner(System.in);
        String loginID, password;
        loginID = null;
        boolean validID = false;
        String[] staff = new String[7];

        System.out.println("====== Staff Login ======");

        // loops to check whether loginID is valid
        while (!validID){
            System.out.println("Enter LoginID:");
            loginID = sc.nextLine();
            for (int x = 1; x <= stafflist.sizeOfFile(); x++){
                if (loginID.equals(stafflist.readDataRow(x,1))){
                    validID = true;
                }
                else {
                    if (x == stafflist.sizeOfFile() && !validID){
                        System.out.println("Invalid loginID");
                    }
                }
            }
        }

        // loops to check whether password is valid
        while (true){
            for (int x = 1; x <= stafflist.sizeOfFile(); x++) {
                // LoginID matches
                if (loginID.equals(stafflist.readDataRow(x,1))){
                    System.out.println("Enter password:");
                    password = sc.nextLine();

                    // Check whether password matches
                    if (stafflist.readDataRow(x,2).equals("")){
                        if (! password.equals("password")){
                            System.out.println("Invalid password");
                        }
                        else {
                            System.out.println("Logged in to " + loginID + " (" + stafflist.readDataRow(x,3) + ")");
                            staffChangePassword(loginID);
                            staff[0] = stafflist.readDataRow(x, 0);
                            staff[1] = stafflist.readDataRow(x, 1);
                            staff[2] = stafflist.readDataRow(x, 2);
                            staff[3] = stafflist.readDataRow(x, 3);
                            staff[4] = stafflist.readDataRow(x, 4);
                            staff[5] = stafflist.readDataRow(x, 5);
                            staff[6] = stafflist.readDataRow(x, 6);
                            return staff;
                        }
                    }
                    else {
                        if (password.equals(stafflist.readDataRow(x,2))){
                            System.out.println("Logged in to " + loginID + " (" + stafflist.readDataRow(x,3) + ")");
                            staffChangePassword(loginID);
                            staff[0] = stafflist.readDataRow(x, 0);
                            staff[1] = stafflist.readDataRow(x, 1);
                            staff[2] = stafflist.readDataRow(x, 2);
                            staff[3] = stafflist.readDataRow(x, 3);
                            staff[4] = stafflist.readDataRow(x, 4);
                            staff[5] = stafflist.readDataRow(x, 5);
                            staff[6] = stafflist.readDataRow(x, 6);
                            return staff;
                        }
                        else {
                            System.out.println("Invalid password");
                        }
                    }

                }

            }
        }

    }

    /**
     * to change default password for first time login
     * @param loginID
     */
    public void staffChangePassword(String loginID){
        Scanner sc = new Scanner(System.in);
        StaffList stafflist = new StaffList();
        
        // Check if is default password
        int num = 1;
        for (int x = 1; x <= stafflist.sizeOfFile(); x++) {
            if (loginID.equals(stafflist.readDataRow(x, 1))) {
                break;
            }
            num++;
        }
        if (stafflist.readDataRow(num,2).isEmpty()) {
            int choice = 0;
            int count = 1;
            String newPassword;

            while (choice == 0) {

                System.out.println("Do you want to change your password?");
                System.out.println("Select:");
                System.out.println("(1) Yes");
                System.out.println("(2) No");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1: // Change password
                        System.out.println("Enter new password");
                        newPassword = sc.nextLine();

                        for (int x = 1; x <= stafflist.sizeOfFile(); x++) {
                            if (loginID.equals(stafflist.readDataRow(x, 1))) {
                                break;
                            }
                            count++;
                        }

                        //System.out.println("edit");
                        stafflist.editData(count, 2, newPassword);
                        System.out.println("Password has been updated");
                        break;
                    case 2:
                        break;
                    default:
                        choice = 0;
                        System.out.println("Please select a valid option");
                }

            }
        }
            else return;


    }


    /**
     * start timer for order monitoring
     */
    private void startOrderMonitoring() {
        long readyTime = System.currentTimeMillis();
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cancelUnCollectedOrders(readyTime);
            }
        }, 300000, 300000); // Repeat every 5 minute
    }

    /**
     * cancels order if time limit is reached
     * @param readyTime
     */
    private void cancelUnCollectedOrders(long readyTime) {
        OrderList orderList = new OrderList();
        long currentTime = System.currentTimeMillis(); // Current time in milliseconds

        // Check if the order list is empty
        if (orderList.sizeOfFile() == 0) {
            System.out.println("\nChecking for uncollected orders: No orders found in the file.");
            return; // Exit the method if there are no orders
        }

        // Iterate through each order
        for (int order = 1; order <= orderList.sizeOfFile(); order++) {
            String orderID = orderList.readDataRow(order, 0);
            String status = orderList.readDataRow(order, 5);

            // Check if the order is not marked as completed
            if (!status.equals("completed")) {
                // Check if the order has been ready for at least 5 minutes
                if (currentTime - readyTime >= 300000) {
                    // Cancel the order
                    orderList.editData(order, 5, "canceled");
                    System.out.println("Order " + orderID + " has been automatically canceled.");
                }
            }
        }
    }



}
