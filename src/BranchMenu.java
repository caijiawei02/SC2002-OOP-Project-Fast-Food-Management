import java.util.Scanner;

/**
 * Menu for interaction with branchlist
 * @author Kent
 * @version 1.0
 */
public class BranchMenu {
    private BranchList branch;

    public BranchMenu() {
        branch = new BranchList();
    }

    /**
     * selects a branch
     * @return name of the selected branch as per the branchlist CSV file
     */
    public String selectBranch() {
        String branchName = null;
        Scanner sc = new Scanner(System.in);

        System.out.println("====== Branch Selection ======");
        System.out.println("Select branch:");

        // While loop to repeat if invalid option is selected
        while (branchName == null) {
            // For loop to print all branches
            for (int x = 1; x <= branch.sizeOfFile(); x++) {
                System.out.print("(" + x + ") ");
                branch.printDataRow(x, 0);
            }
            int choice = sc.nextInt();

            if (choice > branch.sizeOfFile() || choice <= 0) {
                System.out.println("Please select a valid option");
            } else {
                branchName = branch.readDataRow(choice, 0);
                System.out.println("Selected branch: " + branchName);
            }
        }

        return branchName;
    }

    /**
     * get the number of branches
     * @return the number of branches
     */
    public int noOfBranch(){
        return branch.sizeOfFile();
    }

    /**
     * get the branch name
     * @param branchNo
     * @return the String name of branch
     */
    public String getBranchName(int branchNo){
       return branch.readDataRow(branchNo,0);
    }
    
}
