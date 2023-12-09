import java.io.*;

public class IDGenerator {

    private static final String ID_FILE = "idGenerator.txt";
    private static int nextIDForCustomer = 1;
    private static int nextIDForVendor = 1;
    private static int nextIDForRunner = 1;
    private static int nextIDForCredit = 1;
    private static int nextIDForOrder = 1;
    private static int nextIDForMenu = 1;
    private static int nextIDForCustomerNotification = 1;
    private static int nextIDForVendorNotification = 1;
    private static int nextIDForRunnerNotification = 1;

    static {
        // Load the last used IDs from the file, if available
        String[] lastIDs = loadLastIDs();
        if (lastIDs.length >= 2) {
            nextIDForCustomer = Integer.parseInt(lastIDs[0]);
            nextIDForVendor = Integer.parseInt(lastIDs[1]);
            nextIDForRunner = Integer.parseInt(lastIDs[2]);
            nextIDForCredit = Integer.parseInt(lastIDs[3]);
            nextIDForOrder = Integer.parseInt(lastIDs[4]);
            nextIDForMenu = Integer.parseInt(lastIDs[5]);
            nextIDForCustomerNotification = Integer.parseInt(lastIDs[6]);
            nextIDForVendorNotification = Integer.parseInt(lastIDs[7]);
            nextIDForRunnerNotification = Integer.parseInt(lastIDs[8]);
        }
    }

    private static String[] loadLastIDs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ID_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                return line.split(",");
            }
        } catch(FileNotFoundException e){

        } catch(IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return new String[]{"1", "1", "1", "1", "1", "1", "1", "1", "1"};
    }

    private static void saveLastIDs() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ID_FILE))) {
            writer.write(nextIDForCustomer + "," + nextIDForVendor + "," + nextIDForRunner + "," + nextIDForCredit + "," + nextIDForOrder + "," + nextIDForMenu + "," + nextIDForCustomerNotification + "," + nextIDForVendorNotification + "," + nextIDForRunnerNotification);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateIDForCustomer() {
        String id = "CA" + nextIDForCustomer++;
        saveLastIDs();
        return id;
    }

    public static String generateIDForVendor() {
        String id = "VA" + nextIDForVendor++;
        saveLastIDs();
        return id;
    }

    public static String generateIDForRunner() {
        String id = "RA" + nextIDForRunner++;
        saveLastIDs();
        return id;
    }

    public static String generateIDForCredit() {
        String id = "CT" + nextIDForCredit++;
        saveLastIDs();
        return id;
    }

    public static String generateIDForOrder() {
        String id = "CO" + nextIDForOrder++;
        saveLastIDs();
        return id;
    }

    public static String generateIDForMenu() {
        String id = "MI" + nextIDForMenu++;
        saveLastIDs();
        return id;
    }

    public static String generateIDForCustomerNotification() {
        String id = "CN" + nextIDForCustomerNotification++;
        saveLastIDs();
        return id;
    }

    public static String generateIDForVendorNotification() {
        String id = "VN" + nextIDForVendorNotification++;
        saveLastIDs();
        return id;
    }

    public static String generateIDForRunnerNotification() {
        String id = "RN" + nextIDForRunnerNotification++;
        saveLastIDs();
        return id;
    }

}
