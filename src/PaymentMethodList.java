/**
 * Class for all the items in menu_list.csv
 * Retrieving or modifying items inside menu_list.csv needs methods from ReadWriteCSV class
 * Thus, the class inherits these methods from ReadWriteCSV class
 * @author Kent 
 * @version 1.0
 */
public class PaymentMethodList extends ReadWriteCSV {
    private ReadWriteCSV paymentMethodList;

    /**
     * sets paymentMethodList to the paymentmethod.csv file
     */
    public PaymentMethodList() {
        paymentMethodList = new ReadWriteCSV();
        paymentMethodList.setReadWriteCSV("paymentmethod.csv");
    }
}
