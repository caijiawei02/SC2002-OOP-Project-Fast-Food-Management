
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 * This class is responsible for the reading and writing of our CSV files
 * it is important to ensure that there are no errors in our CSV file before running
 * @author Jia Wei
 * @version 4.0
 */
public class ReadWriteCSV {
    Scanner sc = new Scanner(System.in);
    String file;
    String filetemp = "temp.csv";
    BufferedReader reader = null;
    BufferedWriter writer = null;
    String line = "";

    /**
     * Set file to csv file path
     */
    public void setReadWriteCSV(String file){
        this.file = file;
    }


    /**
     * get size of file (no of rows)
     * @return the number of rows (excluding header) in CSV file
     */
    public int sizeOfFile(){
        int size = 0;

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                size++;

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //System.out.println("size =" + (size-1));
        return size-1;
    }

    /**
     * get number of cols in file
     * @return no of cols in csv file
     */
    public int sizeOfFileCol(){
        int size = 0;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            if (line != null) {
                String[] columns = line.split(",");
                size = columns.length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return size;
    }

    /**
     *   print based on the row index and col index in csv file
     * @param rowIndex
     * @param colIndex
     */
    public void printDataRow(int rowIndex, int colIndex) {
        int count = 0;

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {


                if (count == rowIndex) {
                    String[] row = line.split(",");
                    if (row.length > 0) {
                        System.out.printf("%-20s%n", row[colIndex]);
                        break;
                    }

                }

                count++;

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * reads data based on row and col index
     * @param rowIndex in csv file
     * @param colIndex in csv file
     * @return the String of the corresponding rowIndex and colIndex in csv file
     */
    public String readDataRow(int rowIndex, int colIndex) {
        int count = 0;

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {


                if (count == rowIndex) {
                    String[] row = line.split(",");
                    if (row.length > 0) {
                        return row[colIndex];

                    }

                }

                count++;

            }
        }
        catch (ArrayIndexOutOfBoundsException e1){
            e1.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     * adds data to new row
     */
    public void addData(String newData) {
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.newLine();
            writer.write(newData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






    /**
     * edit data based on row and col index
     * @param rowIndex of csv to edit
     * @param colIndex of csv to edit
     * @param newInput to replace at corresponding rowIndex x colIndex
     * @return 1 if success, 0 if unsuccessful
     */
    public int editData(int rowIndex, int colIndex, String newInput) {

        if (rowIndex < 0 || rowIndex >= sizeOfFile()+1) {
            System.out.println("Invalid row index.");
            return 0;
        }

        if (colIndex < 0 || colIndex >= sizeOfFileCol()) {
            System.out.println("Invalid column index.");
            return 0;
        }

        if (newInput == null || newInput.isEmpty()) {
            System.out.println("New input is empty or null.");
            return 0;
        }

        int count = -1;

        if (rowIndex >= 0 && rowIndex < sizeOfFile()+1) {
            try {
                writer = new BufferedWriter(new FileWriter(filetemp));
                reader = new BufferedReader(new FileReader(file));
                PrintWriter pWriter = new PrintWriter(writer);

                while ((line = reader.readLine()) != null) {
                    count++;

                    if (count != rowIndex) {
                        writer.write(line + "\n");
                    } else {
                        String[] row = line.split(",");
                        if (colIndex >= 0 && colIndex < row.length) {
                            row[colIndex] = newInput;
                            for (int x = 0; x < row.length; x++) {
                                writer.write(row[x]);
                                if (x != row.length - 1) {
                                    writer.write(",");
                                }
                            }
                            writer.write("\n");
                        } else {
                            System.out.println("Column index out of bounds.");
                            return 0;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            File originalFile = new File(file);
            File temp = new File(filetemp);
            if (originalFile.delete()) {
                if (!temp.renameTo(originalFile)) {
                    System.out.println("Error renaming file");
                    return 0;
                }
            } else {
                System.out.println("Error deleting file");
                return 0;
            }

            return 1;
        } else {
            System.out.println("Row index out of bounds.");
            return 0;
        }
    }




    /**
     * removes data based on row index
     * @param index of row to delete
     * @return returns 1 if successful, 0 if unsuccessful
     */
    public int removeDataRow(int index){

        if (index < 0 || index >= sizeOfFile()+1) {
            System.out.println("Invalid row index.");
            return 0;
        }

        int rowIndex=-1;


        if (index > 0 && index <= sizeOfFile()+1){
            try {
                writer = new BufferedWriter(new FileWriter(filetemp));
                reader = new BufferedReader(new FileReader(file));
                PrintWriter pWriter = new PrintWriter(writer);

                while ((line = reader.readLine()) != null) {
                    rowIndex++;

                    if (rowIndex!=index){
                        writer.write(line + "\n");
                    }
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    reader.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            File originalFile = new File(file);
            File temp = new File(filetemp);
            if (originalFile.delete()) {
                if (!temp.renameTo(originalFile)) {
                    System.out.println("Error renaming file");
                    return 0;
                }
            } else {
                System.out.println("Error deleting file");
                return 0;
            }

            return 1;
        }

        else return 0;
    }




}
