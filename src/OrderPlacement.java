import java.util.ArrayList;
import java.util.Scanner;



/**
 * This class is to open up and use the AdminMenu
 *  class for customer interactions
 * @author Kent
 * @version 2.0
 *
 */
public class OrderPlacement implements MenuInterface{
    String branchName;
    int orderID;
    String type;
    Scanner sc = new Scanner(System.in);

    public PaymentMethod[] paymentMethods;
    /**
     * constructor
     * @param branchName
     */
    public OrderPlacement(String branchName, int orderID){
        this.branchName = branchName;
        this.orderID = orderID;

        boolean dinein = dineOption();

        if (dinein == true) this.type = "dinein";
        else this.type = "takeaway";
    }

    /**
     * loads up the menu
     */
    @Override
    public void loadMenuOptions() {
        boolean isDone = false;
        //boolean dinein = dineOption();

        do {
            System.out.println("====== Customer Menu ======");
            System.out.println("Select:");
            System.out.println("(1) Display Menu");
            System.out.println("(2) Add Order");
            System.out.println("(3) Customise Order");
            System.out.println("(4) Proceed to Checkout");
            System.out.println("(0) Exit");
            System.out.println("Choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    menuDisplay(branchName);
                    break;
                case 2:
                    orderAdd();
                    break;
                case 3:
                    orderCustomiseMenu();
                    break;
                case 4:
                    orderSummary();
//                    System.out.printf("Your total order is $%d\n. Please proceed to payment.", orderList.getTotalOrderCost());
                    isDone = true;
                    break;
                case 0:
                    isDone = true;
            }
        } while (!isDone);
    }

    /**
     * select the dine option or track order
     * @return true if dine in, false if takeaway
     */
    protected boolean dineOption(){
        System.out.println("\n Choose one of the following options: \n");
        System.out.println("1. Dine-in \n" + "2. Takeaway \n" + "3. Track order \n");

        //Exception handling missing
        int dineChoice = sc.nextInt();
        if (dineChoice == 1) return true;
        else if (dineChoice == 2) return false;
        else if (dineChoice == 3) trackOrder();

        return false;
    }

    /**
     * customer is track order
     */
    protected void trackOrder(){
        System.out.println("Insert orderID to track");
        int orderID = sc.nextInt();
        String orderIDString = "" + orderID;
        
        OrderList orderList = new OrderList();

        System.out.println("\nOrderID: " +orderIDString);

        for (int x =1; x<=orderList.sizeOfFile();x++ ) {
            if (orderIDString.equals(orderList.readDataRow(x, 0))) {
                String name = orderList.readDataRow(x, 1);
                String quantity = orderList.readDataRow(x, 2);
                String status = orderList.readDataRow(x, 5);

                System.out.printf("Name: %-15s | Status: %-8s \n", name,status);
            }
        }

    }

    /**
     * customer to display menu based on selected branch
     * @param branchName
     */
    protected void menuDisplay(String branchName) {
        MenuList menuList = new MenuList();

        System.out.printf("\nMenu for %s branch:\n", branchName);

        for (int x =1; x<=menuList.sizeOfFile();x++ ){
            if (branchName.equals(menuList.readDataRow(x,2))){
                String name = menuList.readDataRow(x, 0);
                String price = menuList.readDataRow(x, 1);
                //String branch = menuList.readDataRow(x, 2);
                String category = menuList.readDataRow(x, 3);
                String availability = menuList.readDataRow(x, 4);

                System.out.printf("Name: %-15s | Price: $%-8s | Category: %-10s | Availability: %-5s\n", name, price, category, availability);
            }
        }

    }

    /**
     * add order to order.csv
     */
    protected void orderAdd(){
        OrderList orderList = new OrderList();
        MenuList menuList = new MenuList();

        boolean isDone = false;
        int choice=0;
        int itemNo=0;

        String item, itemtest;
        boolean validItem;
        int quantity;
        String price;
        String itemName;

        do {
            System.out.println("(1) Set Meal");
            System.out.println("(2) Burger");
            System.out.println("(3) Side");
            System.out.println("(4) Drink");
            System.out.println("(0) Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            // Clear input buffer
            sc.nextLine();

            switch(choice) {
                case 1:
                    validItem = false;

                    printMenu(branchName, "set meal");
                    System.out.println("Which item would you like to add to the cart? (Insert 0 to quit):");


                    do {
                        itemName = sc.nextLine().trim().toLowerCase();
                        if (itemName.equals("0")) break;

                        for (int row1 = 1; row1 <= menuList.sizeOfFile(); row1++) {
                            String name1 = menuList.readDataRow(row1, 0);
                            String name = menuList.readDataRow(row1, 0).trim().toLowerCase();
                            if (itemName.equals(name) && branchName.equals(menuList.readDataRow(row1,2))) {
                                if ("no".equals(menuList.readDataRow(row1, 4))) {
                                    System.out.println("Sorry, the item is currently unavailable.");

                                } else {
                                    validItem = true;
                                    System.out.println("Insert quantity:");
                                    quantity = sc.nextInt();

                                    price = menuList.readDataRow(row1, 1);
                                    String newItem = "" + orderID  + "," + itemName + "," + quantity + "," + price + "," + branchName + "," + "new" +"," + type;
                                    orderList.addData(newItem);
                                    System.out.println(quantity + "x " + name1 + " added to cart.");
                                }
                                break;
                            }
                        }

                        if (!validItem) {
                            System.out.println("Invalid menu item. Please try again or insert 0 to quit:");
                        }

                    } while (!validItem);
                    break;

                case 2:
                    validItem = false;

                    printMenu(branchName, "burger");
                    System.out.println("Which item would you like to add to the cart? (Insert 0 to quit):");

                    do {
                        itemName = sc.nextLine().trim().toLowerCase();
                        if (itemName.equals("0")) break;

                        for (int row1 = 1; row1 <= menuList.sizeOfFile(); row1++) {
                            String name1 = menuList.readDataRow(row1, 0);
                            String name = menuList.readDataRow(row1, 0).trim().toLowerCase();
                            if (itemName.equals(name) && branchName.equals(menuList.readDataRow(row1,2))) {
                                if ("no".equals(menuList.readDataRow(row1, 4))) {
                                    System.out.println("Sorry, the item is currently unavailable.");
                                } else {
                                    validItem = true;
                                    System.out.println("Insert quantity:");
                                    quantity = sc.nextInt();

                                    price = menuList.readDataRow(row1, 1);
                                    String newItem = "" + orderID  + "," + itemName + "," + quantity + "," + price + "," + branchName + "," + "new" +"," + type;
                                    orderList.addData(newItem);
                                    System.out.println(quantity + "x " + name1 + " added to cart.");
                                }
                                break;
                            }
                        }

                        if (!validItem) {
                            System.out.println("Invalid menu item. Please try again or insert 0 to quit:");
                        }

                    } while (!validItem);
                    break;
                case 3:
                    validItem = false;

                    printMenu(branchName, "side");
                    System.out.println("Which item would you like to add to the cart? (Insert 0 to quit):");

                    do {
                        itemName = sc.nextLine().trim().toLowerCase();
                        if (itemName.equals("0")) break;

                        for (int row1 = 1; row1 <= menuList.sizeOfFile(); row1++) {
                            String name1 = menuList.readDataRow(row1, 0);
                            String name = menuList.readDataRow(row1, 0).trim().toLowerCase();
                            if (itemName.equals(name) && branchName.equals(menuList.readDataRow(row1,2))) {
                                if ("no".equals(menuList.readDataRow(row1, 4))) {
                                    System.out.println("Sorry, the item is currently unavailable.");
                                } else {
                                    validItem = true;
                                    System.out.println("Insert quantity:");
                                    quantity = sc.nextInt();

                                    price = menuList.readDataRow(row1, 1);
                                    String newItem = "" + orderID  + "," + itemName + "," + quantity + "," + price + "," + branchName + "," + "new" +"," + type;
                                    orderList.addData(newItem);
                                    System.out.println(quantity + "x " + name1 + " added to cart.");
                                }
                                break;
                            }
                        }

                        if (!validItem) {
                            System.out.println("Invalid menu item. Please try again or insert 0 to quit:");
                        }

                    } while (!validItem);
                    break;
                case 4:
                    validItem = false;

                    printMenu(branchName, "drink");
                    System.out.println("Which item would you like to add to the cart? (Insert 0 to quit):");

                    do {
                        itemName = sc.nextLine().trim().toLowerCase();
                        if (itemName.equals("0")) break;

                        for (int row1 = 1; row1 <= menuList.sizeOfFile(); row1++) {
                            String name1 = menuList.readDataRow(row1, 0);
                            String name = menuList.readDataRow(row1, 0).trim().toLowerCase();
                            if (itemName.equals(name) && branchName.equals(menuList.readDataRow(row1,2))) {
                                if ("no".equals(menuList.readDataRow(row1, 4))) {
                                    System.out.println("Sorry, the item is currently unavailable.");
                                } else {
                                    validItem = true;
                                    System.out.println("Insert quantity:");
                                    quantity = sc.nextInt();

                                    price = menuList.readDataRow(row1, 1);
                                    String newItem = orderID  + "," + itemName + "," + quantity + "," + price + "," + branchName + "," + "new" +"," + type;
                                    orderList.addData(newItem);
                                    System.out.println(quantity + "x " + name1 + " added to cart.");
                                }
                                break;
                            }
                        }

                        if (!validItem) {
                            System.out.println("Invalid menu item. Please try again or insert 0 to quit:");
                        }

                    } while (!validItem);
                    break;
                case 0:
                    isDone = true;
                    break;
            }
        } while(!isDone);
    }

    /**
     * print based on food category
     * @param branchName
     * @param foodcat
     */
    protected void printMenu(String branchName, String foodcat){
        MenuList menuList = new MenuList();

        for (int x =1; x<=menuList.sizeOfFile();x++ ){
            if (branchName.equals(menuList.readDataRow(x,2)) && foodcat.equals(menuList.readDataRow(x,3))){
                String name = menuList.readDataRow(x, 0);
                String price = menuList.readDataRow(x, 1);
                //String branch = menuList.readDataRow(x, 2);
                String category = menuList.readDataRow(x, 3);
                String availability = menuList.readDataRow(x, 4);

                System.out.printf("Name: %-15s | Price: $%-8s | Category: %-10s | Availability: %-5s\n", name, price, category, availability);
            }
        }
    }

    /**
     * customise created order
     */
    protected void orderCustomiseMenu(){
        OrderList orderList = new OrderList();

        int choice;

        String orderIDString = "" + this.orderID;

        System.out.println("Select an order to customise:");
        System.out.println("");

        System.out.println("OrderID: " + this.orderID);
        System.out.println("Order Type: " + this.type);
        for (int x =1; x<=orderList.sizeOfFile();x++ ) {
            if (orderIDString.equals(orderList.readDataRow(x, 0)) && this.branchName.equals(orderList.readDataRow(x,4))) {
                String name = orderList.readDataRow(x, 1);
                String quantity = orderList.readDataRow(x, 2);
                String price = orderList.readDataRow(x, 3);

                System.out.printf("Name: %-15s | Quantity: %-8s | Price: $%-8s \n", name, quantity, price);
            }
        }
        sc.nextLine();

        String itemName = sc.nextLine().trim().toLowerCase();

        for (int x =1; x<=orderList.sizeOfFile();x++ ) {
            if (orderIDString.equals(orderList.readDataRow(x, 0))) {
                String name1 = orderList.readDataRow(x, 1);
                String name = orderList.readDataRow(x, 1).trim().toLowerCase();
                if (itemName.equals(name)) {
                    itemName = name1;
                    System.out.println("Selected " + itemName);

                }
            }
        }

        System.out.println("(1) Edit quantity");
        System.out.println("(2) Remove order");
        System.out.print("Choice: ");
        choice = sc.nextInt();

        // Clear input buffer
        sc.nextLine();

        switch (choice){
            case 1: //edit
                editOrderQuantity(this.orderID,itemName);
                break;

            case 2: //remove
                removeOrder(this.orderID,itemName);
                break;
        }

    }

    /**
     * remove selected order
     * @param orderID
     * @param itemName
     */
    protected void removeOrder(int orderID, String itemName){
        OrderList orderList = new OrderList();

        String orderIDString = "" + orderID;

        for (int x=0;x<=orderList.sizeOfFile()+1;x++ ){
            if (orderIDString.equals(orderList.readDataRow(x, 0)) && itemName.equals(orderList.readDataRow(x,1))) {
                orderList.removeDataRow(x);
                System.out.println("Removed " + itemName + "from order");
                break;
            }
        }

    }

    /**
     * edit quantity of order
     * @param orderID
     * @param itemName
     */
    protected void editOrderQuantity(int orderID, String itemName){
        OrderList orderList = new OrderList();

        String orderIDString = "" + orderID;

        System.out.printf("Insert new quantity for " + itemName + ":");
        int quantity = sc.nextInt();

        for (int x=0;x<=orderList.sizeOfFile()+1;x++ ){
            if (orderIDString.equals(orderList.readDataRow(x, 0)) && itemName.equals(orderList.readDataRow(x,1))) {
                orderList.editData(x,2,""+quantity);
                break;
            }
        }

    }

    /**
     * get the order summary and invoice, includes payment selection
     */
    protected void orderSummary() {
        OrderList orderList = new OrderList();
        PaymentMethodList paymentlist = new PaymentMethodList();

        String orderIDString = "" + orderID;
        float totalprice = 0;
        boolean itemsSelected = false;

        System.out.println("OrderID: " + this.orderID);
        System.out.println("Order Type: " + this.type);
        for (int x = 1; x <= orderList.sizeOfFile(); x++) {
            if (orderIDString.equals(orderList.readDataRow(x, 0))) {
                String name = orderList.readDataRow(x, 1);
                String quantity = orderList.readDataRow(x, 2);
                String price = orderList.readDataRow(x, 3);

                totalprice += Float.parseFloat(price) * Float.parseFloat(quantity);

                System.out.printf("Name: %-15s | Quantity: %-8s | Price: $%-8s \n", name, quantity, price);
                itemsSelected = true;
            }
        }

        if (!itemsSelected) {
            System.out.println("Error: No items selected. Please select items before proceeding to checkout.");
            return; // Exit the method if no items are selected
        }

        System.out.printf("Total price: $%.2f\n", totalprice);

        PaymentMethod[] paymentMethods = new PaymentMethod[]{
                new PayPal(),
                new CardPayment(),
                new bitcoin()
        };

        System.out.println("====== Payment Menu ======");
        for (int x = 1; x <= paymentMethods.length; x++) {
            String paymentname = paymentlist.readDataRow(x,0);
            if (paymentname==null) break;
            System.out.print("(" + x + ") ");
            System.out.println(paymentlist.readDataRow(x,0));
        }

        System.out.print("Select a payment method: ");
        int choice = sc.nextInt();

        // Process the payment
        if (choice >= 1 && choice <= paymentMethods.length) {
            PaymentMethod selectedPaymentMethod = paymentMethods[choice - 1];
            selectedPaymentMethod.processPayment(totalprice);
        } else {
            System.out.println("Invalid payment method choice.");
        }

        ++this.orderID;
    }


}
