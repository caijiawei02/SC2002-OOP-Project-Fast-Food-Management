/**
 * Class for all the items in menu_list.csv
 * Retrieving or modifying items inside menu_list.csv needs methods from ReadWriteCSV class
 * Thus, the class inherits these methods from ReadWriteCSV class
 * @author Kent 
 * @version 1.0
 */
public class MenuList extends ReadWriteCSV {
    private ReadWriteCSV menuList;

    /**
     * sets menuList to the menu_list.csv file
     */
    public MenuList() {
        menuList = new ReadWriteCSV();
        menuList.setReadWriteCSV("menu_list.csv");
    }
}
