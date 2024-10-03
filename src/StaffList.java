/**
 * Class for all the items in staff_list.csv
 * Retrieving or modifying items inside staff_list.csv needs methods from ReadWriteCSV class
 * Thus, the class inherits these methods from ReadWriteCSV class
 * @author Kent 
 * @version 1.0
 */
public class StaffList extends ReadWriteCSV {
    private ReadWriteCSV staffList;

    /**
     * sets staffList to the staff_list.csv file
     */
    public StaffList() {
        staffList = new ReadWriteCSV();
        staffList.setReadWriteCSV("staff_list.csv");
    }
}
