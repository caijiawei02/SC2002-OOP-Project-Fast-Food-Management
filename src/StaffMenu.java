

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is to open up and use the AdminMenu
 *
 *  @author Avanish
 *  @version 1.0
 */
public class StaffMenu implements MenuInterface{
	private ArrayList<String[]> orders;
	private String[] staff;
	
	public StaffMenu(String[] staff) {
		this.staff = staff;
		this.orders = new ArrayList<>();
        OrderList orderList = new OrderList();
		for (int order = 1; order <= orderList.sizeOfFile(); order++) {
            String status = orderList.readDataRow(order, 5);
            String orderID = orderList.readDataRow(order, 0);
            String item = orderList.readDataRow(order, 1);
            String quantity = orderList.readDataRow(order, 2);
            String price = orderList.readDataRow(order, 3);
            String branch = orderList.readDataRow(order, 4);
            
            // Collect order details in the array
            this.orders.add(new String[]{orderID, item, quantity, price, branch, status});
        }
	}

    /**
     * load up the menu
     */
	public void loadMenuOptions() {
		boolean isDone = false;
		
		do {
			System.out.println("====== FOMS Staff Menu ======");
	        System.out.println("Select:");
	        System.out.println("(1) Display New Orders");
	        System.out.println("(2) View Order Details");
	        System.out.println("(3) Mark Order as Ready");
            System.out.println("(4) Mark Order as Completed");
	        System.out.println("(0) Exit");
	        System.out.println("Choice: ");
	        Scanner sc = new Scanner(System.in);
	        int choice = sc.nextInt();
	        
	        switch(choice) {
		        case 1: 
		        	//Display New this.orders
		        	displayNewOrders();
		        	break;
		        case 2: 
		        	//View Order Details
		        	viewOrderDetails();
		        	break;
		        case 3:
		        	//Process Order
		        	readyOrder();
		        	break;
                case 4:
                    //Complete Order
                    completeOrder();
                    break;
		        case 0:
		        	isDone = true;
		        	break;
	        }
		}while(!isDone);
	}

    /**
     * display orders that are marked as new depending on branch of staff
     */
	protected void displayNewOrders() {
		System.out.println("====== FOMS Display Orders Menu ======");
		System.out.println("Select:");

        // Iterate through collected this.orders and print grouped this.orders by orderID
        String currentOrderID = "";
        for (String[] orderDetails : this.orders) {
            String orderID = orderDetails[0];
            String item = orderDetails[1];
            String quantity = orderDetails[2];
            String price = orderDetails[3];
            String branch = orderDetails[4];
            String status = orderDetails[5];
            
            if (!branch.equals(staff[6]) || !status.equals("new"))
            	continue;
            
            // Print order ID
            if (!orderID.equals(currentOrderID)) {
                System.out.println("\nOrder ID: " + orderID);
                currentOrderID = orderID;
            }

            // Print order details
            System.out.printf("  Item: %-15s | Quantity: %-3s | Price: %-8s | Branch: %-10s\n", item, quantity, price, branch);

        }
	}

    /**
     * see the specific details of selected order
     */
	protected void viewOrderDetails() {
		System.out.println("====== FOMS Order Details Menu ======");
		System.out.println("Select:");      

        String currentOrderID = "";
        for (String[] orderDetails : this.orders) {
            String orderID = orderDetails[0];
            String branch = orderDetails[4];
            
            if (!branch.equals(staff[6]))
            	continue;
            
            // Print order ID
            if (!orderID.equals(currentOrderID)) {
                System.out.println("Order ID: " + orderID);
                currentOrderID = orderID;
            }
        }
        
        boolean isValidID = false;
        String id = "";
        do {
        	System.out.println("Choice: ");
            Scanner sc = new Scanner(System.in);
            id = sc.nextLine();
            
            for(String[] orderDetails: this.orders) {
            	String orderID = orderDetails[0];
            	if(id.equals(orderID))
            		isValidID = true;
            }
            
            if(!isValidID)
            	System.out.println("Please enter a valid OrderID!");
        }while(!isValidID);
        
        
        for (String[] orderDetails : this.orders) {
        	String orderID = orderDetails[0];
            String item = orderDetails[1];
            String quantity = orderDetails[2];
            String price = orderDetails[3];
            String branch = orderDetails[4];
            String status = orderDetails[5];
            
            if (orderID.equals(id)) {
            	System.out.printf("Item: %-15s | Quantity: %-3s | Price: %-8s | Branch: %-10s | Status: %-10s\n", item, quantity, price, branch, status);
            }
        }
	}

    /**
     * mark order as ready
     */
	protected void readyOrder() {
		System.out.println("====== FOMS Order Process Menu ======");
		System.out.println("Select:");

        String currentOrderID = "";
        for (String[] orderDetails : this.orders) {
            String orderID = orderDetails[0];
            String status = orderDetails[5];
            String branch = orderDetails[4];

            if (!branch.equals(staff[6]))
            	continue;

            // Print order ID
            if (!orderID.equals(currentOrderID) && status.equals("new")) {
                System.out.println("Order ID: " + orderID);
                currentOrderID = orderID;
            }
        }

        boolean isValidID = false;
        String id = "";
        do {
        	System.out.println("Choice: ");
            Scanner sc = new Scanner(System.in);
            id = sc.nextLine();

            for(String[] orderDetails: this.orders) {
            	String orderID = orderDetails[0];
            	String status = orderDetails[5];

            	if(status.equals("new") && id.equals(orderID))
            		isValidID = true;
            }

            if(!isValidID)
            	System.out.println("Please enter a valid OrderID!");
        }while(!isValidID);

        //Change to ready
        OrderList orderList = new OrderList();
        for (int order = 1; order <= orderList.sizeOfFile(); order++) {
            String orderID = orderList.readDataRow(order, 0);
            if(orderID.equals(id)) {
            	orderList.editData(order, 5, "ready");
            }
        }
        reloadOrders();
        System.out.printf("Order %s is ready to pickup!\n", id);
	}

    /**
     * mark order as completed
     */
    protected void completeOrder() {
        System.out.println("====== FOMS Order Process Menu ======");
        System.out.println("Select:");

        String currentOrderID = "";
        for (String[] orderDetails : this.orders) {
            String orderID = orderDetails[0];
            String status = orderDetails[5];
            String branch = orderDetails[4];

            if (!branch.equals(staff[6]))
                continue;

            // Print orderID
            if (!orderID.equals(currentOrderID) && status.equals("new") || !orderID.equals(currentOrderID) && (status.equals("ready")))
            {
                System.out.println("Order ID: " + orderID);
                currentOrderID = orderID;
            }
        }

        boolean isValidID = false;
        String id = "";
        do {
            System.out.println("Choice: ");
            Scanner sc = new Scanner(System.in);
            id = sc.nextLine();

            for(String[] orderDetails: this.orders) {
                String orderID = orderDetails[0];
                String status = orderDetails[5];

                if((status.equals("new") || status.equals("ready")) && id.equals(orderID))
                    isValidID = true;
            }

            if(!isValidID)
                System.out.println("Please enter a valid OrderID!");
        }while(!isValidID);

        //Change to ready
        OrderList orderList = new OrderList();
        for (int order = 1; order <= orderList.sizeOfFile(); order++) {
            String orderID = orderList.readDataRow(order, 0);
            if(orderID.equals(id)) {
                orderList.editData(order, 5, "completed");
            }
        }
        reloadOrders();
        System.out.printf("Order %s is marked as completed!\n", id);
    }

    /**
     * reloads order
     */
    protected void reloadOrders() {
	    orders.clear();
	    
	    // Initialize the CSV file
        OrderList orderList = new OrderList();
	    
	    // Iterate through each row in the CSV file
	    for (int order = 1; order <= orderList.sizeOfFile(); order++) {
	        String orderID = orderList.readDataRow(order, 0);
	        String item = orderList.readDataRow(order, 1);
	        String quantity = orderList.readDataRow(order, 2);
	        String price = orderList.readDataRow(order, 3);
	        String branch = orderList.readDataRow(order, 4);
	        String status = orderList.readDataRow(order, 5);
	        
	        // Add  order details to orders list
	        orders.add(new String[] { orderID, item, quantity, price, branch, status });
	    }
	}
}
