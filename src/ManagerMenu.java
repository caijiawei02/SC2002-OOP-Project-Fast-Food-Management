
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * This class is to open up and use the AdminMenu
 * @author Ruparaj
 * @version 2.0
 */
public class ManagerMenu extends StaffMenu implements MenuInterface{
	
	private String[] managerInfo; 
	private ArrayList<String[]> menuItems;
	private HashMap<String, Integer> csvIndexMap;
	
	public ManagerMenu(String[] manager) {
	    super(manager);
	    managerInfo = manager;
	    menuItems = new ArrayList<>();
	    csvIndexMap = new HashMap<>();
		MenuList menuList = new MenuList();
	    int csvRowIndex = 1; // Start from the first data row
	    for (int row = 1; row < menuList.sizeOfFile(); row++) {
	        String name = menuList.readDataRow(row, 0);
	        String price = menuList.readDataRow(row, 1);
	        String branch = menuList.readDataRow(row, 2);
	        String category = menuList.readDataRow(row, 3);
	        String availability = menuList.readDataRow(row, 4);
	        
	        if (branch.equals(managerInfo[6])) {
	            menuItems.add(new String[] {name, price, category, availability});
	            // Map the index of the item in the ArrayList to the CSV file's row index
	            csvIndexMap.put(name, csvRowIndex);
	        }
	        csvRowIndex++;
	    }	    
	}

	/**
	 * loads up the menu
	 */
	public void loadMenuOptions() {
		boolean isDone = false;
		
		do {
			System.out.println("====== FOMS Manager Menu ======");
	        System.out.println("Select:");
	        System.out.println("(1) Display New Orders");
	        System.out.println("(2) View Order Details");
	        System.out.println("(3) Mark Order as Ready");
			System.out.println("(4) Mark Order as Completed");
	        System.out.println("(5) Display Staff List");
	        System.out.println("(6) Manage Menu Items");
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
					//Process Order
					completeOrder();
					break;
		        case 5:
		        	//Display Staff List
		        	displayStaffList();
		        	break;
		        case 6:
		        	//Manage Menu Items
		        	manageMenuItems();
		        	break;
		        case 0:
		        	isDone = true;
		        	break;
	        }
		}while(!isDone);
	}

	/**
	 * displays the staff list depending on manager branch
	 */
	private void displayStaffList() {
		String managerBranch = this.managerInfo[6];
		StaffList staffList = new StaffList();
		System.out.println("====== Staff List ======");
		for(int row = 1; row <= staffList.sizeOfFile(); row++) {
			String name = staffList.readDataRow(row, 0);
			String gender = staffList.readDataRow(row, 4);
			String age = staffList.readDataRow(row, 5);
			String role = staffList.readDataRow(row, 3);
			String branch = staffList.readDataRow(row, 6);
			
			if(role.equals("S") && branch.equals(managerBranch)) {
	            System.out.printf("Name: %-15s | Age: %-3s | Gender: %-2s\n", name, age, gender);
			}
		}
	}

	/**
	 * loads up menu to edit menu items
	 */
	private void manageMenuItems() {
		boolean isDone = false;
		do {
			System.out.println("====== Manage Menu Options ======");
			System.out.printf("\nMenu for %s branch:\n", managerInfo[6]);
			for (String[] items: menuItems) {
	            System.out.printf("Name: %-15s | Price: %-8s | Category: %-10s | Availability: %-5s\n", items[0],items[1],items[2],items[3]);
			}
			System.out.println("Select:");
	        System.out.println("(1) Add Menu Item");
	        System.out.println("(2) Edit Menu Item");
	        System.out.println("(3) Remove Menu Item");
	        System.out.println("(0) Exit");
	        System.out.println("Choice: ");
	        Scanner sc = new Scanner(System.in);
	        int choice = sc.nextInt();
	        
	        switch(choice) {
		        case 1: 
		        	addMenuItem();
		        	break;
		        case 2: 
		        	editMenuItem();
		        	break;
		        case 3:
		        	removeMenuItem();
		        	break;
		        case 0:
		        	isDone = true;
		        	break;
	        }
		}while(!isDone);
	}

	/**
	 * add menu items
	 */
	private void addMenuItem() {
		//We need from them
			//Menu Name
			//Menu Price
			//Category
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter item name: ");
		String itemName = sc.nextLine();
		while (itemName.equals("") || itemName.equals(" ") || itemName.trim().isEmpty() || !itemName.matches(".*\\w.*")){
			System.out.println("Invalid item name. Please enter valid item name.");
			itemName = sc.nextLine();
		}

		// Check for duplicate names, if duplicate, invalid, enter new name
		boolean isDuplicate = checkForDuplicateName(itemName);
		while (isDuplicate) {
			System.out.println("Item name already exists. Please enter a different name.");
			itemName = sc.nextLine();
			isDuplicate = checkForDuplicateName(itemName);
		}

		
		float itemPrice = getValidPrice();
		
		String itemCategory = getValidCategory();
		
		String[] newMenuItem = {itemName, String.valueOf(itemPrice), managerInfo[6], itemCategory, "yes"};
		
		
		//Add to the ArrayList
		menuItems.add(new String[] {newMenuItem[0], newMenuItem[1], newMenuItem[3], newMenuItem[4]});
		
		//Add to the CSV
		addToCSV(newMenuItem);
	}

	/**
	 * check whether there are duplicates for the menu items if adding new item
	 * @param itemName
	 * @return
	 */
	private boolean checkForDuplicateName(String itemName) {
		for (String[] menuItem : menuItems) {
			if (menuItem[0].equalsIgnoreCase(itemName)) {
				return true; // Found a duplicate name
			}
		}
		return false; // No duplicate name found
	}
	private void editMenuItem() {
		System.out.println("====== Edit Menu Items ======");
	    System.out.printf("\nMenu for %s branch:\n", managerInfo[6]);
	    int i = 1;
	    for (String[] items: menuItems) {
	        System.out.printf("(%d) Name: %-15s | Price: %-8s | Category: %-10s | Availability: %-5s\n", i, items[0],items[1],items[2],items[3]);
	        i++;
	    }
	    
	    System.out.println("Menu item to edit: ");
	    int itemToEdit = getValidMenuItem();
	    
	    //Edit the menu item info
	    boolean isDone = false;
	    do {
	    	System.out.println("Select:");
	        System.out.println("(1) Edit Name");
	        System.out.println("(2) Edit Price");
	        System.out.println("(3) Edit Category");
	        System.out.println("(4) Edit Availability");
	        System.out.println("(0) Exit");
	        System.out.println("Choice: ");
	        Scanner sc = new Scanner(System.in);
	        int choice = sc.nextInt();
			
			MenuList menuList = new MenuList();

	        switch(choice) {
		        case 1: 
		        	System.out.println("Enter New Name: ");
		        	Scanner sc2 = new Scanner(System.in);
		        	String newName = sc2.nextLine();
		        	
		        	String oldName = menuItems.get(itemToEdit - 1)[0];
		        	//Update the Array List
		        	menuItems.get(itemToEdit-1)[0] = newName;

		        	//Update HashMap
		        	int csvIndexToEdit = csvIndexMap.get(oldName);
		        	csvIndexMap.remove(oldName);
		        	csvIndexMap.put(newName, csvIndexToEdit);	



		        	//Update the CSV
		        	menuList.editData(csvIndexToEdit, 0, newName);
		        	break;
		        case 2: 
		        	float price = getValidPrice();
		        	
		        	//Update the Array List
		        	menuItems.get(itemToEdit-1)[1] = Float.toString(price);
		        	
		        	//Update the CSV
		        	String itemName = menuItems.get(itemToEdit - 1)[0];
		        	csvIndexToEdit = csvIndexMap.get(itemName);
		        	menuList.editData(csvIndexToEdit, 1, Float.toString(price));
		        	
		        	break;
		        case 3: 
		        	
		        	String itemCategory = getValidCategory();
		        	
		        	//Update the Array List
		        	menuItems.get(itemToEdit-1)[2] = itemCategory;
		        	
		        	//Update the CSV
		        	itemName = menuItems.get(itemToEdit - 1)[0];
		        	csvIndexToEdit = csvIndexMap.get(itemName);
		        	menuList.editData(csvIndexToEdit, 3, itemCategory);
		        	break;
		        case 4: 
		        	String itemAvailability = getValidAvailability();
		        	
		        	//Update the Array List
		        	menuItems.get(itemToEdit-1)[3] = itemAvailability;
		        	
		        	//Update the CSV
		        	itemName = menuItems.get(itemToEdit - 1)[0];
		        	csvIndexToEdit = csvIndexMap.get(itemName);
		        	menuList.editData(csvIndexToEdit, 4, itemAvailability);
		        	break;
		        case 0: 
		        	isDone = true;
		        	break;
		    	default:
		    		System.out.println("Please enter a valid input");
		    		break;
	        }
	    }while(!isDone);
	}

	/**
	 * removal of menu item
	 */
	private void removeMenuItem() {
	    System.out.println("====== Remove Menu Items ======");
	    System.out.printf("\nMenu for %s branch:\n", managerInfo[6]);
	    int i = 1;
	    for (String[] items: menuItems) {
	        System.out.printf("(%d) Name: %-15s | Price: %-8s | Category: %-10s | Availability: %-5s\n", i, items[0],items[1],items[2],items[3]);
	        i++;
	    }
	    
	    System.out.println("Menu item to remove: ");
	    int itemIndexToRemove = getValidMenuItem();
	    
	    String itemNameToRemove = menuItems.get(itemIndexToRemove - 1)[0];
	    
	    // Remove from the ArrayList
	    menuItems.remove(itemIndexToRemove - 1);
	    
	    // Map the itemToRemove to the CSV file's row index using csvIndexMap
	    int csvRowIndexToRemove = csvIndexMap.get(itemNameToRemove);
	    
	    // If we have a valid CSV row index, remove the row from the CSV file
		MenuList menuList = new MenuList();
        int removalSuccess = menuList.removeDataRow(csvRowIndexToRemove);
        
        // Check if the removal from the CSV file was successful
        if (removalSuccess == 1) {
            System.out.println("Menu item removed successfully.");
        } else {
            System.out.println("Error removing menu item from the CSV file.");
        }
	}

	/**
	 * check whether input price is valid input
	 * @return the price
	 */
	private float getValidPrice() {
		Scanner sc = new Scanner(System.in);
		// Initialize itemPrice and a flag for input validation
	    float itemPrice = 0.0f;
	    boolean isValidPrice = false;
	    
	    // Prompt for and validate item price
	    while (!isValidPrice) {
	        try {
	            System.out.println("Enter item price: ");
	            itemPrice = sc.nextFloat();
	            
	            // Check if the price is greater than zero (if required)
	            if (itemPrice > 0) {
	                isValidPrice = true; // Price is valid, exit loop
	            } else {
	                System.out.println("Price must be greater than zero. Please try again.");
	            }
	        } catch (InputMismatchException e) {
	            // Handle invalid input (not a number)
	            System.out.println("Invalid input. Please enter a valid number.");
	            // Clear the invalid input
	            sc.next(); // Consume the invalid input
	        }
	    }
	    sc.nextLine();
	    return itemPrice;
	}

	/**
	 * check whether the input cat is valid
	 * @return cat
	 */
	private String getValidCategory() {
		Scanner sc = new Scanner(System.in);
		// Initialize itemCategory and a flag for input validation
	    String itemCategory = "";
	    boolean isValidCategory = false;
	    
	    // Define a list of valid categories
	    String[] validCategories = {"side", "set meal", "burger", "drink"};
	    
	    // Prompt for and validate item category
	    while (!isValidCategory) {
	        System.out.println("Enter item category (side, set meal, burger, drink): ");
	        itemCategory = sc.nextLine().toLowerCase(); // Convert input to lowercase
	        
	        // Check if the input category is valid
	        for (String validCategory : validCategories) {
	            if (itemCategory.equals(validCategory)) {
	                isValidCategory = true; // Category is valid, exit loop
	                break;
	            }
	        }
	        
	        if (!isValidCategory) {
	            System.out.println("Invalid category. Please enter one of the following: side, set meal, burger, or drink.");
	        }
	    }
	    
	    return itemCategory;
	}

	/**
	 * check whether the item is either yes or no availability
	 * @return yes or no
	 */
	private String getValidAvailability() {
		Scanner sc = new Scanner(System.in);
		// Initialize itemCategory and a flag for input validation
	    String itemCategory = "";
	    boolean isValidCategory = false;
	    
	    // Define a list of valid categories
	    String[] validCategories = {"yes", "no"};
	    
	    // Prompt for and validate item category
	    while (!isValidCategory) {
	        System.out.println("Enter item availability (yes or no): ");
	        itemCategory = sc.nextLine().toLowerCase();
	        
	        // Check if the input category is valid
	        for (String validCategory : validCategories) {
	            if (itemCategory.equals(validCategory)) {
	                isValidCategory = true;
	                break;
	            }
	        }
	        
	        if (!isValidCategory) {
	            System.out.println("Invalid availability. Please enter one of the following: yes or no.");
	        }
	    }
	    
	    return itemCategory;
	}

	/**
	 * adds menu item to CSV
	 * @param newMenuItem
	 */
	private void addToCSV(String[] newMenuItem) {
		// Open the menu list CSV file in append mode
	    BufferedWriter writer = null;
	    try {
	        writer = new BufferedWriter(new FileWriter("menu_list.csv", true));

	        // Convert the new menu item array to a comma-separated string
	        String newMenuItemString = String.join(",", newMenuItem);
	        
	        // Write the new menu item to the CSV file
	        writer.write(newMenuItemString);
	        writer.newLine(); // Add a newline after the new row

	        System.out.println("Menu item added successfully.");
	    } catch (IOException e) {
	        System.out.println("Failed to add the menu item. Please try again.");
	        e.printStackTrace();
	        // Remove the item from the ArrayList if it wasn't added to the CSV file
	        menuItems.remove(newMenuItem);
	    } finally {
	        // Close the writer to save the data and release resources
	        if (writer != null) {
	            try {
	                writer.close();
	             
	                // Update the HashMap
					MenuList menuList = new MenuList();
	    	        int csvRowIndex = menuList.sizeOfFile();
	    	        csvIndexMap.put(newMenuItem[0], csvRowIndex);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

	/**
	 * check whether input is valid
	 * @return menu item index
	 */
	private int getValidMenuItem() {

	    Scanner sc = new Scanner(System.in);
	    int menuIndex;
	    

	    while (true) {
	        try {
	            menuIndex = sc.nextInt();
	            if (menuIndex < 1 || menuIndex > menuItems.size()) {
	                throw new InputMismatchException();
	            }
	            
	            break;
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a valid menu item number.");
	            sc.nextLine();
	        }
	    }
	    
	    return menuIndex;
	}
}
