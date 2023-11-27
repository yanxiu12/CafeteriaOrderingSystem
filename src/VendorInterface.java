import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VendorInterface extends MainInterface{

    public static Vendor login() {
        String usernameInput = null, passwordInput = null;
        boolean status;
        Vendor vendor=null;
        Vendor loginVendor=null;
        int repeat=1;
        Scanner input = new Scanner(System.in);

        System.out.println("--------------------------------------");
        System.out.println("|                LOGIN               |");
        System.out.println("--------------------------------------");
        System.out.println();
        while (repeat==1) {
            status=true;
            while (status) {
                System.out.print("User ID: ");
                usernameInput = input.nextLine();
                if (usernameInput.isEmpty())
                    System.out.println("Username cannot be empty.");
                else
                    status=false;
            }
            status = true;
            while (status) {
                System.out.print("Password: ");
                passwordInput = input.nextLine();
                if (passwordInput.isEmpty())
                    System.out.println("Password cannot be empty.");
                else
                    status = false;
            }
            System.out.println();
            System.out.println("Checking user credential......");
            System.out.println();
            SerializationOperation operation = new SerializationOperation("Vendor.ser");
            ArrayList<Vendor> foundVendor = operation.searchObjects(usernameInput,Vendor.class);
            if(foundVendor.size() != 1){
                vendor = foundVendor.get(0);
            }
            if (vendor == null) {
                System.out.print("User ID not exist. Enter 1 to try again or proceed to admin.");
                try {
                    repeat = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid integer.");
                    input.nextLine();
                }
                System.out.println();
            }else if(!usernameInput.equals(vendor.getID()) || !passwordInput.equals(vendor.getPassword())){
                System.out.print("Incorrect user ID and password. Enter 1 to try again.");
                try {
                    repeat = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid integer.");
                    input.nextLine();
                }
                System.out.println();
            }else{
                loginVendor = new Vendor(vendor.getID(),vendor.getPassword(),vendor.getVendorName(),vendor.getCategory(),vendor.getAddress());
            }
        }
        return loginVendor;
    }

    public static void VendorMain(Vendor vendor) {
        System.out.println("------------------------------------------");
        System.out.println("|                MAIN MENU               |");
        System.out.println("------------------------------------------");
        System.out.println();
        System.out.println("Welcome, "+vendor.getVendorName()+".");
        System.out.println();
        System.out.println("Please choose which you would like to proceed.");
        System.out.println();
        System.out.println(String.format("%-30s", "1. View Profile") + String.format("%-30s", "2. Access Menu"));
        System.out.println(String.format("%-30s", "3. View Active Order Detail")+String.format("%-30s", "4. Order History"));
        System.out.println(String.format("%-30s", "5. Check Notification")+String.format("%-30s", "6. Sales & Revenue Dashboard"));
        System.out.println();
        System.out.println("(Enter 0 to log out.)");
        System.out.println();
    }
}
