/**
 * Class for all the items in branch_list.csv
 * Retrieving or modifying items inside branch_list.csv needs methods from ReadWriteCSV class
 * Thus, the class inherits these methods from ReadWriteCSV class
 * @author Kent 
 * @version 1.0
 */
public class BranchList extends ReadWriteCSV {
    private ReadWriteCSV branchList;

    /**
     * sets branchList to the branch_list.csv file
     */
    public BranchList() {
        branchList = new ReadWriteCSV();
        branchList.setReadWriteCSV("branch_list.csv");
    }
}
