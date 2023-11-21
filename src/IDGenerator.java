import java.io.*;
import java.util.Scanner;

public class IDGenerator {
    private String filename;
    private String prefix;

    public IDGenerator(String filename,String prefix) {
        this.filename = filename;
        this.prefix = prefix;
    }

    public String generateID(){
        String latestID = null;
        int IDNumber = 1;
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                latestID = fields[0];
            }
        } catch (FileNotFoundException ex) {

        }
        if (latestID != null) {
            IDNumber = Integer.parseInt(latestID.substring(2));
            IDNumber++;
        }
        return prefix + IDNumber;
    }

    public String generateSerializedID() {
        int IDNumber = 1;
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj;
            while ((obj = objectIn.readObject()) != null) {
                // Your logic to handle each object when reading from the serialized file
                // Update IDNumber based on existing objects
                // For example, assuming the objects have a method to retrieve their IDs:
                if (obj instanceof Customer) {
                    String id = ((Customer) obj).getID();
                    int number = Integer.parseInt(id.substring(prefix.length()));
                    if (number >= IDNumber) {
                        IDNumber = number + 1;
                    }
                }else if (obj instanceof Vendor) {
                    String id = ((Vendor) obj).getID();
                    int number = Integer.parseInt(id.substring(prefix.length()));
                    if (number >= IDNumber) {
                        IDNumber = number + 1;
                    }
                }else if (obj instanceof Runner) {
                    String id = ((Runner) obj).getID();
                    int number = Integer.parseInt(id.substring(prefix.length()));
                    if (number >= IDNumber) {
                        IDNumber = number + 1;
                    }
                }
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading objects: " + e.getMessage());
        }

        return prefix + IDNumber;
    }
}
