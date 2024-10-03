import java.util.Scanner;

/**
 * This class is to open up and use the AdminMenu
 *
 * @author Jia Wei
 * @version 3.0
 */
public class AdminMenu  implements MenuInterface{

    /**
     * constructor
     */
    public AdminMenu(){

    }

    /**
     * loads menu options
     */
    public void loadMenuOptions() {
        boolean isDone = false;

        do {
            System.out.println("====== FOMS Admin Menu ======");
            System.out.println("Select:");
            System.out.println("Choose an option:");
            System.out.println("(1) Add Staff Account");
            System.out.println("(2) Remove Staff Account");
            System.out.println("(3) Edit Staff Account Details");
            System.out.println("(4) Display Staff List");
            System.out.println("(5) Assign Manager to Branch");
            System.out.println("(6) Promote Staff to Manager");
            System.out.println("(7) Transfer Staff/Manager");
            System.out.println("(8) Add Payment Method");
            System.out.println("(9) Remove Payment Method");
            System.out.println("(10) Open Branch");
            System.out.println("(11) Close Branch");
            System.out.println("(0) Exit");
            System.out.println("Choice: ");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            BranchMenu branch = new BranchMenu();

            switch(choice) {
                case 1:
                    System.out.println("Adding Staff Account:");
                    addStaffAccount();
                    break;
                case 2:
                    System.out.println("Removing Staff Account:");
                    removeStaffAccount(selectAccount());
                    break;
                case 3:
                    System.out.println("Editing Staff Account Details:");
                    editStaffAccount(selectAccount());
                    break;
                case 4:
                    System.out.println("Displaying Staff List:");
                    displayStaffList();
                    break;
                case 5:
                    System.out.println("Assigning Manager to Branch:");

                    managertoBranch(selectBranchlessM(),branch.selectBranch());
                    break;
                case 6:
                    System.out.println("Promoting Staff to Manager:");
                    promoteStoM(selectStaff());
                    break;
                case 7:
                    System.out.println("Transferring Staff/Manager:");
                    transferEmployee(selectAccount(),branch.selectBranch());
                    break;
                case 8:
                    System.out.println("Adding Payment Method:");
                    addPaymentMethod();
                    break;
                case 9:
                    System.out.println("Removing Payment Method:");
                    removePaymentMethod();
                    break;
                case 10:
                    System.out.println("Opening Branch:");
                    openBranch();
                    break;
                case 11:
                    System.out.println("Closing Branch:");
                    closeBranch(branch.selectBranch());
                    break;
                case 0:
                    isDone = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }while(!isDone);
    }



    /**
     * adds new staff with its details
     */
    private void addStaffAccount(){
        Scanner sc = new Scanner(System.in);
        StaffList staffList = new StaffList();
        BranchMenu branch = new BranchMenu();


        System.out.println("Insert Name:");
        String staffName = sc.nextLine();

        System.out.println("Insert Staff Login ID:");
        String staffID = sc.nextLine();

        int staffrchoice = 3;
        while (staffrchoice != 1 && staffrchoice != 2){
            System.out.println("Select Staff Role (S/M):");
            System.out.println("(1) S");
            System.out.println("(2) M");
            staffrchoice = sc.nextInt();

            if (staffrchoice != 1 && staffrchoice != 2){
                System.out.println("Please select a valid option");
            }
        }
        String staffRole;
        if (staffrchoice ==1){ //S
            staffRole = "S";
        }
        else {
            staffRole = "M";
        }
        int staffgchoice = 3;
        while (staffgchoice != 1 && staffgchoice != 2){
            System.out.println("Select Staff Gender (F/M):");
            System.out.println("(1) M");
            System.out.println("(2) F");
            staffgchoice = sc.nextInt();

            if (staffgchoice != 1 && staffgchoice != 2){
                System.out.println("Please select a valid option");
            }
        }
        String staffGender;
        if (staffgchoice ==1){ //M
           staffGender = "M";
        }
        else {
            staffGender = "F";
        }


        System.out.println("Insert Staff Age:");
        int staffage = sc.nextInt();
        while ((staffage <= 0) || (100 <= staffage)){
            System.out.println("Invalid Staff Age");
            System.out.println("Insert Staff Age):");
            staffage = sc.nextInt();
        }

        System.out.println("Select Branch:");
        String branchName = branch.selectBranch();

        if (isBelowQuota(branchName)){
            String dataToAdd = staffName + "," + staffID + ",," + staffRole + "," + staffGender + "," + staffage + "," + branchName;
            staffList.addData(dataToAdd);
            System.out.println("Successfully added new Staff Account");
        }
        else {
            System.out.println("Unable to add new Staff Account to " + branchName + " due to Quota");
        }



    }

    /**
     * selects staff account
     * @return selected Account name
     */
    private String selectAccount(){
        String staffAccount = null;
        StaffList staffList = new StaffList();
        Scanner sc = new Scanner(System.in);

        System.out.println("====== Staff Account Selection ======");
        System.out.println("Select Staff:");

        // While loop to repeat if invalid option is selected
        while (staffAccount == null) {
            // For loop to print all branches
            for (int x = 1; x <= staffList.sizeOfFile(); x++) {
                System.out.print("(" + x + ") ");
                staffList.printDataRow(x, 0);
            }
            int choice = sc.nextInt();

            if (choice > staffList.sizeOfFile() || choice <= 0) {
                System.out.println("Please select a valid option");
            } else {
                staffAccount = staffList.readDataRow(choice, 0);
                System.out.println("Selected Staff Account: " + staffAccount);
            }
        }

        return staffAccount;

    }

    /**
     * removes data row upon entering staffAccount name
     * @param staffAccount
     */
    private void removeStaffAccount(String staffAccount){
        StaffList staffList = new StaffList();
        int count=0;
        while (!staffList.readDataRow(count, 0).equals(staffAccount)){
            count++;
        }
        staffList.removeDataRow(count);

        System.out.println("Successfully removed Staff Account: " + staffAccount);


    }

    /**
     * select column and edit specified column of specified staffAccount
     * @param staffAccount
     */
    private void editStaffAccount(String staffAccount){
        StaffList staffList = new StaffList();

        // get column index of selected staff
        int count=0;
        while (!staffList.readDataRow(count, 0).equals(staffAccount)){
            count++;
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("Select details to edit:");
        System.out.println("(1) Name");
        System.out.println("(2) Staff Login ID");
        System.out.println("(3) Password");
        System.out.println("(4) Gender");
        System.out.println("(5) Age");

        int choice = sc.nextInt();

        switch(choice){
            case 1:
                System.out.println("Insert Name:");
                sc.nextLine();
                String staffName = sc.nextLine();
                staffList.editData(count,0,staffName);
                break;
            case 2:
                System.out.println("Insert Staff Login ID:");
                sc.nextLine();
                String staffID = sc.nextLine();
                staffList.editData(count,1,staffID);
                break;
            case 3:
                System.out.println("Insert Staff Password:");
                sc.nextLine();
                String staffpass = sc.nextLine();
                staffList.editData(count,2,staffpass);
                break;

            case 4:
                System.out.println("Select Staff Gender (M/F):");
                System.out.println("(1) M");
                System.out.println("(2) F");
                int staffgchoice = sc.nextInt();
                while (staffgchoice != 1 || staffgchoice != 2){
                    System.out.println("Please select a valid option");
                    System.out.println("Select Staff Gender (M/F):");
                    staffgchoice = sc.nextInt();
                }
                if (staffgchoice ==1){ //M
                    staffList.editData(count,4,"M");
                }
                else {
                    staffList.editData(count,4,"F");
                }

                break;
            case 5:

                System.out.println("Insert Staff Age:");
                int staffage = sc.nextInt();
                while ((staffage <= 0) || (100 <= staffage)){
                    System.out.println("Invalid Staff Age");
                    System.out.println("Insert Staff Age):");
                    staffage = sc.nextInt();
                }
                String staffageString = "" + staffage;
                staffList.editData(count,5,staffageString);
                break;
            default:
                System.out.println("Invalid option, please choose again.");
        }

    }

    /**
     * displays staff list based on selected filters
     */
    private void displayStaffList(){
        StaffList staffList = new StaffList();
        Scanner sc = new Scanner(System.in);

        System.out.println("Select filter:");
        System.out.println("(1) None");
        System.out.println("(2) Branch");
        System.out.println("(3) Role");
        System.out.println("(4) Gender");
        System.out.println("(5) Age");

        int choice = sc.nextInt();
        BranchMenu branch_ = new BranchMenu();

        switch(choice){
            case 1: // No filter
                System.out.println("====== Staff List ======");
                for(int row = 1; row <= staffList.sizeOfFile(); row++) {
                    String name = staffList.readDataRow(row, 0);
                    String gender = staffList.readDataRow(row, 4);
                    String age = staffList.readDataRow(row, 5);
                    String role = staffList.readDataRow(row, 3);
                    String branch = staffList.readDataRow(row, 6);

                    //if(role.equals("S") && branch.equals(managerBranch)) {
                    System.out.printf("Name: %-15s | Age: %-3s | Gender: %-2s | Branch: %-5s\n", name, age, gender, branch);
                    // }
                }
                break;
            case 2: // Branch
                String selectedBranch = branch_.selectBranch();

                for(int row = 1; row <= staffList.sizeOfFile(); row++) {
                    String name = staffList.readDataRow(row, 0);
                    String gender = staffList.readDataRow(row, 4);
                    String age = staffList.readDataRow(row, 5);
                    String role = staffList.readDataRow(row, 3);
                    String branch = staffList.readDataRow(row, 6);

                    if(branch.equals(selectedBranch)) {
                    System.out.printf("Name: %-15s | Age: %-3s | Gender: %-2s | Branch: %-5s\n", name, age, gender, branch);
                    }
                }

                break;
            case 3: // Role
                System.out.println("Select Staff Role (S/M):");
                System.out.println("(1) S");
                System.out.println("(2) M");
                int staffrchoice = sc.nextInt();
                String staffRole;

                while (staffrchoice != 1 && staffrchoice != 2) { // Corrected the logical condition
                    System.out.println("Please select a valid option");
                    System.out.println("Select Staff Role (S/M):");
                    staffrchoice = sc.nextInt();
                }

                if (staffrchoice == 1) { // S
                    staffRole = "S";
                } else {
                    staffRole = "M";
                }

                for (int row = 1; row <= staffList.sizeOfFile(); row++) {
                    String name = staffList.readDataRow(row, 0);
                    String gender = staffList.readDataRow(row, 4);
                    String age = staffList.readDataRow(row, 5);
                    String role = staffList.readDataRow(row, 3);
                    String branch = staffList.readDataRow(row, 6);

                    if (role.equals(staffRole)) {
                        System.out.printf("Name: %-15s | Age: %-3s | Gender: %-2s | Branch: %-5s\n", name, age, gender, branch);
                    }
                }


                break;

            case 4: // Gender
                System.out.println("Select Staff Gender (M/F):");
                System.out.println("(1) M");
                System.out.println("(2) F");
                int staffgchoice = sc.nextInt();
                String staffGender;
                while (staffgchoice != 1 && staffgchoice != 2){
                    System.out.println("Please select a valid option");
                    System.out.println("Select Staff Gender (M/F):");
                    staffgchoice = sc.nextInt();
                }
                if (staffgchoice ==1){ //M
                    staffGender = "M";
                }
                else {
                    staffGender = "F";
                }
                for(int row = 1; row <= staffList.sizeOfFile(); row++) {
                    String name = staffList.readDataRow(row, 0);
                    String gender = staffList.readDataRow(row, 4);
                    String age = staffList.readDataRow(row, 5);
                    String role = staffList.readDataRow(row, 3);
                    String branch = staffList.readDataRow(row, 6);

                    if(gender.equals(staffGender)) {
                        System.out.printf("Name: %-15s | Age: %-3s | Gender: %-2s | Branch: %-5s\n", name, age, gender, branch);
                    }
                }

                break;
            case 5: //Age
                System.out.println("Enter minimum age:");
                int minage = sc.nextInt();
                System.out.println("Enter maximum age:");
                int maxage = sc.nextInt();

                for(int row = 1; row <= staffList.sizeOfFile(); row++) {
                    String name = staffList.readDataRow(row, 0);
                    String gender = staffList.readDataRow(row, 4);
                    String age = staffList.readDataRow(row, 5);
                    String role = staffList.readDataRow(row, 3);
                    String branch = staffList.readDataRow(row, 6);

                    int ageint = Integer.parseInt(age);

                    if(minage <= ageint && ageint <= maxage) {
                        System.out.printf("Name: %-15s | Age: %-3s | Gender: %-2s | Branch: %-5s\n", name, age, gender, branch);
                    }
                }


                break;
            default:
                System.out.println("Invalid option, please choose again.");
        }



    }


    /**
     * select a manager with "null" branch if exist
     * @return manager name if branchless, if no manager is branchless, then return null
     */
    private String selectBranchlessM(){
        String selectedManager = null;
        StaffList staffList = new StaffList();
        Scanner sc = new Scanner(System.in);

        boolean emptyb = false;

        System.out.println("====== Manager Account Selection ======");
        for(int row = 1; row <= staffList.sizeOfFile(); row++) {
            String name = staffList.readDataRow(row, 0);
            String gender = staffList.readDataRow(row, 4);
            String age = staffList.readDataRow(row, 5);
            String role = staffList.readDataRow(row, 3);
            String branch = staffList.readDataRow(row, 6);

            if (role.equals("M") && branch.equals("null")) {
                emptyb = true;
                System.out.printf("Name: %-15s | Age: %-3s | Gender: %-2s | Branch: %-5s\n", name, age, gender, branch);
            }
        }

        if (!emptyb){
            System.out.println("No manager to assign");
            return null;
        }

        else{
            System.out.println("Select Manager (Enter Manager Name):");
            selectedManager = sc.nextLine().replaceAll("\\s+", "");

            boolean valid = false;


            while (!valid) {
                for (int row1 = 1; row1 <= staffList.sizeOfFile(); row1++) {
                    String name1 = staffList.readDataRow(row1, 0);
                    String name2 = name1.replaceAll("\\s+", "");

                    if (selectedManager.equals(name2)) {
                        valid = true;
                        return name1;
                    }
                }
                System.out.println("Invalid Manager Name");
                System.out.println("Select Manager (Enter Manager Name):");
                selectedManager = sc.nextLine().replaceAll("\\s+", "");
            }
        }



        return null;

    }

    /**
     * assigns specified manager to specified branch if within quota
     * @param managerName
     * @param branchName
     */
    private void managertoBranch(String managerName, String branchName){
        StaffList staffList = new StaffList();
        BranchList branchList = new BranchList();

        // Get staff count
        int staffCount = 0;
        for (int row = 1; row <= staffList.sizeOfFile(); row++) {
            if (branchName.equals(staffList.readDataRow(row, 6)) && "S".equals(staffList.readDataRow(row, 3))) {
                staffCount++;
            }
        }

        // Initalise manager quota
        int requiredManagers = 0;
        if (staffCount <= 4) {
            requiredManagers = 1;
        } else if (staffCount <= 8) {
            requiredManagers = 2;
        } else if (staffCount <= 15) {
            requiredManagers = 3;
        }

        // Get manager count
        int currentManagers = 0;
        for (int row = 1; row <= staffList.sizeOfFile(); row++) {
            if (branchName.equals(staffList.readDataRow(row, 6)) && "M".equals(staffList.readDataRow(row, 3))) {
                currentManagers++;
            }
        }

        if (currentManagers < requiredManagers) {

            int count = 0;
            while (!staffList.readDataRow(count, 0).equals(managerName)) {
                count++;
            }
            staffList.editData(count, 6, branchName);
            System.out.println("Assigned manager " + managerName + " to " + branchName);
        } else {
            System.out.println("Unable to assign manager to " + branchName + ". Maximum managers limit reached.");
        }
    }

    /**
     * select staff (non manager)
     * @return staff name
     */
    private String selectStaff() {
        String selectedStaff = null;
        StaffList staffList = new StaffList();
        Scanner sc = new Scanner(System.in);

        System.out.println("====== Staff Account Selection ======");
        for (int row = 1; row <= staffList.sizeOfFile(); row++) {
            String name = staffList.readDataRow(row, 0);
            String gender = staffList.readDataRow(row, 4);
            String age = staffList.readDataRow(row, 5);
            String role = staffList.readDataRow(row, 3);
            String branch = staffList.readDataRow(row, 6);

            if (role.equals("S")) {
                System.out.printf("Name: %-15s | Age: %-3s | Gender: %-2s | Branch: %-5s\n", name, age, gender, branch);
            }
        }
        System.out.println("Select Staff (Enter Staff Name):");
        selectedStaff = sc.nextLine().trim();

        boolean valid = false;

        // Check if the selected staff name is valid
        for (int row1 = 1; row1 <= staffList.sizeOfFile(); row1++) {
            String name1 = staffList.readDataRow(row1, 0);
            if (selectedStaff.equalsIgnoreCase(name1.trim())) { // Compare ignoring upper/lower case and trim whitespace
                valid = true;
                return name1;
            }
        }

        // Prompt again if not valid
        while (!valid) {
            System.out.println("Invalid Staff Name");
            System.out.println("Select Staff (Enter Staff Name):");
            selectedStaff = sc.nextLine().trim();
            for (int row1 = 1; row1 <= staffList.sizeOfFile(); row1++) {
                String name1 = staffList.readDataRow(row1, 0);
                if (selectedStaff.equalsIgnoreCase(name1.trim())) {
                    valid = true;
                    return name1;
                }
            }
        }

        return null;
    }


    /**
     * promote staff to manager
     * @param staffName
     */
    private void promoteStoM(String staffName){
        StaffList staffList = new StaffList();

        String branchName = "";

        for (int row = 1; row <= staffList.sizeOfFile(); row++) {
            if (staffName.equals(staffList.readDataRow(row,1))){
                 branchName = staffList.readDataRow(row,6);
            }
        }


        int staffCount = 0;
        for (int row = 1; row <= staffList.sizeOfFile(); row++) {
            if (branchName.equals(staffList.readDataRow(row, 6)) && "S".equals(staffList.readDataRow(row, 3))) {
                staffCount++;
            }
        }


        int requiredManagers = 0;
        if (staffCount <= 4) {
            requiredManagers = 1;
        } else if (staffCount <= 8) {
            requiredManagers = 2;
        } else if (staffCount <= 15) {
            requiredManagers = 3;
        }


        int currentManagers = 0;
        for (int row = 1; row <= staffList.sizeOfFile(); row++) {
            if (branchName.equals(staffList.readDataRow(row, 6)) && "M".equals(staffList.readDataRow(row, 3))) {
                currentManagers++;
            }
        }

        // Check if promoting the staff member exceeds the limit
        if (currentManagers < requiredManagers) {

            int count = 0;
            while (!staffList.readDataRow(count, 0).equals(staffName)) {
                count++;
            }
            staffList.editData(count, 3, "M");
            System.out.println("Promoted staff " + staffName + " to manager.");
        } else {
            System.out.println("Unable to promote staff " + staffName + ". Maximum managers limit reached for the branch.");
        }
    }

    /**
     * transfer employee to another branch
     * @param account
     * @param branchName
     */
    private void transferEmployee(String account, String branchName){
        StaffList staffList = new StaffList();

        // Get the branch of the employee
        String originalBranch ="";

        for (int row = 1; row <= staffList.sizeOfFile(); row++) {
            if (account.equals(staffList.readDataRow(row,1))){
                originalBranch = staffList.readDataRow(row,6);
            }
        }

        // Get the number of managers currently assigned to the original branch
        int originalManagers = 0;
        for (int row = 1; row <= staffList.sizeOfFile(); row++) {
            if (originalBranch.equals(staffList.readDataRow(row, 6)) && "M".equals(staffList.readDataRow(row, 3))) {
                originalManagers++;
            }
        }


        // Get the number of managers currently assigned to the destination branch
        int destinationManagers = 0;
        for (int row = 1; row <= staffList.sizeOfFile(); row++) {
            if (branchName.equals(staffList.readDataRow(row, 6)) && "M".equals(staffList.readDataRow(row, 3))) {
                destinationManagers++;
            }
        }

        // Check if transferring the employee affects the manager limit
        if (originalManagers > 0 && destinationManagers < 3) {
            int count = 0;
            while (!staffList.readDataRow(count, 0).equals(account)) {
                count++;
            }
            staffList.editData(count, 6, branchName);
            System.out.println("Transferred employee " + account + " to " + branchName);
        } else {
            System.out.println("Unable to transfer employee " + account + ". Manager limit exceeded for one of the branches.");
        }
    }
    /**
     * checks whether branch is below their quota
     * @param branchName
     * @return true if is below quota
     */
    private boolean isBelowQuota(String branchName) {
        BranchList branchList = new BranchList();

        StaffList staffList = new StaffList();

        int count = 0;
        int quotatotal = 99;

        for (int row = 1; row <= branchList.sizeOfFile(); row++) {
            if (branchName.equals(branchList.readDataRow(row, 0))) {
                quotatotal = Integer.parseInt(branchList.readDataRow(row, 2));
                break; // Stop searching once the branch is found
            }
        }

        int quotamanager=0;

        if (quotatotal <= 4) quotamanager = 1;
        else if (quotatotal >4 && quotatotal <= 8) quotamanager = 2;
        else if (quotatotal >8 && quotatotal <= 15) quotamanager = 3;


        for (int row = 1; row <= staffList.sizeOfFile(); row++) {
            if (branchName.equals(staffList.readDataRow(row, 6)) && "M".equals(staffList.readDataRow(row, 3))) {
                count++;
            }
        }

        if (count>=quotamanager) return false;
        else return true;
    }

    /**
     * Adds new payment method and updates paymentmethod.csv
     */
    private void addPaymentMethod() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Insert Payment Method:");
        String paymentname = sc.nextLine();

        // assumes that new payment method is already coded
        PaymentMethodList paymentMethodList = new PaymentMethodList();

        paymentMethodList.addData(paymentname);

        System.out.println("New payment method " + paymentname +" added");

        System.out.println("List of payment methods:");
        for (int row=1;row<=paymentMethodList.sizeOfFile();row++){

            paymentMethodList.printDataRow(row,0);

        }

    }

    /**
     * removes payment method and updates paymentmethod.csv
     */
    private void removePaymentMethod() {
        Scanner sc = new Scanner(System.in);
        PaymentMethodList paymentMethodList = new PaymentMethodList();

        for (int x = 1; x <= paymentMethodList.sizeOfFile() ; x++) {
            System.out.print("(" + x + ") ");
            paymentMethodList.printDataRow(x,0);
        }

        System.out.print("Select a payment method to remove: ");
        int choice = sc.nextInt();

        paymentMethodList.removeDataRow(choice);

        System.out.println("Payment method removed.");

    }

    /**
     * adds new branch and updates branch_list.csv
     */
    private void openBranch(){
        BranchList branchList = new BranchList();
        Scanner sc = new Scanner(System.in);

        System.out.println("Insert Branch Name:");
        String branchName = sc.nextLine();

        System.out.println("Insert Branch Location:");
        String branchLoc = sc.nextLine();

        System.out.println("Insert Staff Quota:");
        int quota = sc.nextInt();
        String quotaS = "" + quota;

        String newData = branchName + "," + branchLoc + "," + quotaS;

        branchList.addData(newData);

        System.out.println("Opened new branch " + branchName);
    }

    /**
     * removes selected branch and updates branch_list.csv
     * @param branchName
     */
    private void closeBranch(String branchName){
        BranchList branchList = new BranchList();

        // get column index of selected staff
        int count=0;
        while (!branchList.readDataRow(count, 0).equals(branchName)){
            count++;
        }

        branchList.removeDataRow(count);
    }

}
