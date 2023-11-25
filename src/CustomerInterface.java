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
        System.out.print("Press enter to exit");
        String proceed = input.nextLine();
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

    public static void accessMenu(Customer customer){
        Scanner input = new Scanner(System.in);
        int repeat = 1;

        System.out.println("------------------------------------------------");
        System.out.println("|                   MENU PAGE                  |");
        System.out.println("------------------------------------------------");
        System.out.println();
        SerializationOperation operation = new SerializationOperation("Vendor.ser");
        ArrayList<Vendor> allVendor = operation.readAllObjects(Vendor.class);
        if(!allVendor.isEmpty()) {
            for (Vendor vendor : allVendor) {
                System.out.println("====================================================================================================");
                System.out.println(String.format("%-5s", vendor.getID())+String.format("%-60s", vendor.getVendorName()) + String.format("%30s", vendor.getCategory()));
                System.out.println("====================================================================================================");
                System.out.println();
                MenuItem menu = new MenuItem(vendor);
                menu.printMenu();
                System.out.println();
            }
            System.out.print("Do you have anything to order? (Note: You can only purchase from selected vendor in an order.)\nEnter 1 to proceed / Enter any other value to exit:");
            try{
                int proceed = input.nextInt();
                if(proceed!=1){
                    System.out.println();
                    System.out.println("Proceeding to main menu......");
                    System.out.println();
                }else{
                    input.nextLine();
                    System.out.println();
                    while(repeat == 1) {
                        System.out.print("Please enter the vendor ID (eg. VA1) :");
                        String vendorID = input.nextLine();
                        System.out.println();
                        ArrayList<Vendor> foundVendor = operation.searchObjects(vendorID, Vendor.class);
                        if (foundVendor.size() == 1) {
                            Vendor vendor = foundVendor.get(0);
                            accessCart(customer,vendor);
                        } else {
                            System.out.println("Invalid vendor ID. Enter 1 to try again / Enter other value to exit.");
                            try {
                                repeat = input.nextInt();
                            }catch (InputMismatchException e){
                                repeat = 0;
                            }
                            System.out.println();
                            input.nextLine();
                        }
                    }
                    System.out.println("Proceeding to main menu......");
                    System.out.println();
                }
            }catch (InputMismatchException e){
                input.nextLine();
                System.out.println();
                System.out.println("Proceeding to main menu......");
                System.out.println();
            }
        }else
            System.out.println("Sorry! There is currently no vendor available.");
    }

    public static void accessCart(Customer customer,Vendor vendor){
        int repeat = 1;
        Scanner input = new Scanner(System.in);

        MenuItem menu = new MenuItem(vendor);
        menu.printMenu();
        System.out.println();
        while(repeat == 1) {
            System.out.println("Current cart item:");
            ArrayList<Cart> cartItems = customer.getCartItems();
            if(!cartItems.isEmpty()) {
                for (Cart cart : cartItems) {
                    System.out.println(String.format("%-30s", cart.getItem().getItemName()) + String.format("%-3s", cart.getQuantity()));
                }
            }else
                System.out.println("(Empty cart.)");
            System.out.println();
            System.out.print("Enter the index no. of item to add to cart / remove from cart (Enter 0 to exit): ");
            try {
                int item = input.nextInt();
                if(item == 0){
                    System.out.println();
                } else if ((item > menu.getVendorItems().size()) || (item < 0)) {
                    input.nextLine();
                    System.out.println("Please enter a number in range of " + menu.getVendorItems().size() + ".");
                    System.out.println();
                    System.out.print("Enter 1 to continue / Enter other value to exit:");
                    try{
                        repeat = input.nextInt();
                        if(repeat == 2)
                            repeat = 0;
                    }catch(InputMismatchException e){
                        repeat = 0;
                    }
                    System.out.println();
                    input.nextLine();
                } else {
                    int quantity;
                    MenuItem selectedItem = menu.getDetail(item);
                    input.nextLine();
                    System.out.println("You have selected: "+selectedItem.getItemName());
                    System.out.print("Enter the quantity:");
                    try{
                        quantity = input.nextInt();
                    }catch(InputMismatchException e){
                        quantity = 0;
                    }
                    if(quantity>0){
                        customer.addToCart(selectedItem,quantity);
                        System.out.println();
                        System.out.println("Successfully added "+quantity+" "+selectedItem.getItemName()+" to cart.");
                    }else{
                        customer.removeFromCart(selectedItem,quantity);
                        System.out.println("Successfully remove from cart.");
                    }
                    System.out.println();
                    System.out.print("Do you have any other item to add on? Enter 1 to continue / Enter 2 to place order / Enter other value to exit:");
                    try{
                        repeat = input.nextInt();
                    }catch(InputMismatchException e){
                        repeat = 0;
                    }
                    input.nextLine();
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println();
                System.out.println("Please enter an integer number.");
                System.out.println();
                System.out.print("Enter 1 to continue / Enter other value to exit:");
                try{
                    repeat = input.nextInt();
                    if(repeat == 2)
                        repeat = 0;
                }catch(InputMismatchException ex){
                    repeat = 0;
                }
                System.out.println();
                input.nextLine();
            }
            if (repeat == 2){
                int method = 0;

                System.out.println();
                System.out.println(String.format("%-15s", "1. Dine-in") + String.format("%-15s", "2. Take Away") + String.format("%-15s", "3. Delivery"));
                System.out.println("(Enter other value to exit.)");
                System.out.print("Please select the serving method for your order:");
                try {
                    method = input.nextInt();
                } catch (InputMismatchException e) {

                }
                System.out.println();
                if(method==1 || method==2 || method==3) {
                    System.out.println("Order confirmation:");
                    if (!cartItems.isEmpty()) {
                        for (Cart cart : cartItems) {
                            System.out.println(String.format("%-30s", cart.getItem().getItemName()) + String.format("%-3s", cart.getQuantity()));
                        }
                        System.out.println();
                        System.out.println("Enter 1 to edit cart / Enter 0 to confirm order (Enter other value to exit) :");
                        try {
                            repeat = input.nextInt();
                            if(repeat == 0){
                                customer.placeOrder(vendor,method);
                                System.out.println("Successfully placed the order.");
                                System.out.println();
                            }
                        } catch (InputMismatchException e) {
                            repeat = 9;
                            System.out.println();
                        }
                    }
                }
            }
        }
    }

    public static void accessTransactionHistory(Customer customer) {
        Scanner input = new Scanner(System.in);
        ArrayList<Credit> creditHistory = customer.getCreditRecords();

        System.out.println("----------------------------------------------------");
        System.out.println("|                TRANSACTION HISTORY               |");
        System.out.println("----------------------------------------------------");
        System.out.println();
        System.out.println("==========================================================================================");
        System.out.println(String.format("%-30s", "DATE") + String.format("%-30s", "TRANSACTION TYPE") + String.format("%-30s", "AMOUNT (RM)"));
        System.out.println("==========================================================================================");
        if (!creditHistory.isEmpty()) {
            for (Credit credit : creditHistory) {
                System.out.println(String.format("%-30s", credit.getCurrentDate()) + String.format("%-30s", credit.getTransactionType()) + String.format("%-30s", credit.getTransaction()));
            }
            System.out.println();
        }else{
            System.out.println("(No Transaction Record Exist.)");
            System.out.println();
        }
        System.out.print("Press Enter to exit.");
        String proceed = input.nextLine();
        System.out.println();
    }


}
