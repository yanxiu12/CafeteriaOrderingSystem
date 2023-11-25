import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerInterface {

    public static Customer login() {
        String usernameInput = null, passwordInput = null;
        boolean status;
        Customer customer=null;
        Customer loginCustomer=null;
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
            SerializationOperation operation = new SerializationOperation("Customer.ser");
            ArrayList<Customer> foundCustomer = operation.searchObjects(usernameInput,Customer.class);
            if(foundCustomer.size() != 1){
                customer = foundCustomer.get(0);
            }
            if (customer == null) {
                System.out.print("User ID not exist. Enter 1 to try again or proceed to admin.");
                try {
                    repeat = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid integer.");
                    input.nextLine();
                }
                System.out.println();
            }else if(!usernameInput.equals(customer.getID()) || !passwordInput.equals(customer.getPassword())){
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
                loginCustomer = new Customer(customer.getID(),customer.getPassword(),customer.getName(),customer.getDob(),customer.getContact(),customer.getAddress(),String.valueOf(customer.getWalletBalance()));
            }
        }
        return loginCustomer;
    }

    public static void CustomerMain(Customer customer) {
        System.out.println("------------------------------------------");
        System.out.println("|                MAIN MENU               |");
        System.out.println("------------------------------------------");
        System.out.println();
        System.out.println("Welcome, "+customer.getName()+".");
        System.out.println();
        System.out.println("Please choose which you would like to proceed.");
        System.out.println();
        System.out.println(String.format("%-30s", "1. View Profile") + String.format("%-30s", "2. View Menu & Order"));
        System.out.println(String.format("%-30s", "3. Order History")+String.format("%-30s", "4. Transaction History"));
        System.out.println(String.format("%-30s", "5. Check Current Order Status")+String.format("%-30s", "6. Check Notification"));
        System.out.println();
        System.out.println("(Enter 0 to log out.)");
        System.out.println();
    }

    public static void accessProfile(Customer customer){
        Scanner input = new Scanner(System.in);

        System.out.println("------------------------------------------------");
        System.out.println("|                PROFILE DETAILS               |");
        System.out.println("------------------------------------------------");//48
        System.out.println();
        System.out.println(String.format("%-24s", "Customer ID") + String.format("%24s", customer.getID()));
        System.out.println(String.format("%-24s", "Customer Name") + String.format("%24s", customer.getName()));
        System.out.println(String.format("%-24s", "Customer Birthday") + String.format("%24s", customer.getDob()));
        System.out.println(String.format("%-24s", "Customer Contact") + String.format("%24s", customer.getContact()));
        System.out.println(String.format("%-24s", "Customer Address") + String.format("%24s", customer.getAddress()));
        System.out.println(String.format("%-24s", "Customer Password") + String.format("%24s", "*****"+customer.getPassword().substring(customer.getPassword().length()-3)));
        System.out.println();
        System.out.println("(To modify the details, please proceed to admin.)");
        System.out.println();
        System.out.print("Press enter to proceed");
        String proceed = input.nextLine();
        System.out.println();
    }

    public void accessMenu(){

    }


}
