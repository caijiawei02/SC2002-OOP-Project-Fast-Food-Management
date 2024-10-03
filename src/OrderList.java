/**
 * Class for all the items in orders.csv
 * Retrieving or modifying items inside orders.csv needs methods from ReadWriteCSV class
 * Thus, the class inherits these methods from ReadWriteCSV class
 * @author Kent 
 * @version 1.0
 */
public class OrderList extends ReadWriteCSV {
    private ReadWriteCSV orderList;

     /**
     * sets orderList to the orders.csv file
     */
    public OrderList() {
        orderList = new ReadWriteCSV();
        orderList.setReadWriteCSV("orders.csv");
    }
}
