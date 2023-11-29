import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerInterface extends MainInterface{

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
        System.out.println("(You have currently "+customer.getNotifications().size()+" notification(s).)");
        System.out.println();
        System.out.println("Please choose which you would like to proceed.");
        System.out.println();
        System.out.println(String.format("%-30s", "1. View Profile") + String.format("%-30s", "2. View Menu & Order"));
        System.out.println(String.format("%-30s", "3. Order History")+String.format("%-30s", "4. Transaction History"));
        System.out.println(String.format("%-30s", "5. Check Active Order Status")+String.format("%-30s", "6. Check Notification"));
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
        int repeat;

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
            System.out.print("Do you have anything to order? (Note: You can only purchase from selected vendor in an order.)\nEnter 1 to proceed / Enter 2 to view reviews / Enter any other value to exit:");
            try{
                int proceed = input.nextInt();
                input.nextLine();
                System.out.println();
                if(proceed==1 ) {
                    repeat = 1;
                    while (repeat == 1) {
                        System.out.print("Please enter the vendor ID (eg. VA1) :");
                        String vendorID = input.nextLine();
                        System.out.println();
                        ArrayList<Vendor> foundVendor = operation.searchObjects(vendorID, Vendor.class);
                        if (foundVendor.size() == 1) {
                            Vendor vendor = foundVendor.get(0);
                            accessCart(customer, vendor);
                            repeat = 0;
                        } else {
                            System.out.println("Invalid vendor ID. Enter 1 to try again / Enter other value to exit.");
                            try {
                                repeat = input.nextInt();
                            } catch (InputMismatchException e) {
                                repeat = 0;
                            }
                            System.out.println();
                            input.nextLine();
                        }
                    }
                }else if (proceed == 2) {
                    repeat = 1;
                    while (repeat == 1) {
                        System.out.print("Please enter the vendor ID (eg. VA1) :");
                        String vendorID = input.nextLine();
                        System.out.println();
                        ArrayList<Vendor> foundVendor = operation.searchObjects(vendorID, Vendor.class);
                        if (foundVendor.size() == 1) {
                            Vendor vendor = foundVendor.get(0);
                            OrderReview orderReview = new OrderReview("OrderReview.txt");
                            ArrayList<String> foundReview = orderReview.getReviewByVendor(vendor);
                            System.out.println("Customer Reviews for " + vendor.getVendorName() + ":");
                            System.out.println();
                            if (!foundReview.isEmpty()) {
                                System.out.println(String.format("%-20s", "CUSTOMER NAME") + String.format("%-10s", "| RATING")+ "| REVIEW");
                                for (String review : foundReview) {
                                    System.out.println(review);
                                }
                            } else {
                                System.out.println("(Sorry. There is currently no review for this vendor.)");
                            }
                            repeat = 0;
                        } else {
                            System.out.println("Invalid vendor ID. Enter 1 to try again / Enter other value to exit.");
                            try {
                                repeat = input.nextInt();
                            } catch (InputMismatchException e) {
                                repeat = 0;
                            }
                            System.out.println();
                            input.nextLine();
                        }
                    }
                }
                System.out.println();
                System.out.println("Proceeding to main menu......");
                System.out.println();
            }catch (InputMismatchException e){
                input.nextLine();
                System.out.println();
                System.out.println("Proceeding to main menu......");
                System.out.println();
            }
        }else
            System.out.println("Sorry! There is currently no vendor available.");
    }

    public static void accessCart(Customer customer,Vendor vendor){//if possible check cart got things first
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
                    input.nextLine();
                } catch (InputMismatchException e) {
                    input.nextLine();
                }
                System.out.println();
                if(method==1 || method==2 || method==3) {
                    System.out.println("Order confirmation:");
                    double totalPrice = customer.printCart(method);
                    System.out.println("Enter 1 to edit cart / Enter 0 to confirm order (Enter other value to exit) :");
                    try {
                        repeat = input.nextInt();
                        if(repeat == 0){
                            Credit credit = new Credit(customer);
                            if(credit.isBalanceEnough(totalPrice)) {
                                customer.placeOrder(vendor, method);
                                credit.deductAmount(totalPrice);
                                customer.resetCartItems();
                                System.out.println("Successfully placed the order.");
                            }else
                                System.out.println("Insufficient balance! Please proceed to admin to top-up.");
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

    public static void accessOrderHistory(Customer customer){
        Scanner input = new Scanner(System.in);
        ArrayList<Order> orderHistory = customer.getOrderHistory();

        System.out.println("----------------------------------------------");
        System.out.println("|                ORDER HISTORY               |");
        System.out.println("----------------------------------------------");
        System.out.println();
        System.out.println("Number of orders: "+orderHistory.size());
        System.out.println();
        System.out.println("================================================================================================================================================================================================================================================");
        System.out.println(String.format("%-30s", "ORDER ID") + String.format("%-30s", "VENDOR") + String.format("%-30s", "SERVING METHOD") + String.format("%-30s", "STATUS") + String.format("%-30s", "REVIEW") + String.format("%-30s", "ITEM")+String.format("%-30s", "QUANTITY") + String.format("%-30s", "TOTAL PRICE"));
        System.out.println("================================================================================================================================================================================================================================================");
        if (!orderHistory.isEmpty()) {
            int counter = 1;
            for (Order order : orderHistory) {
                for(Cart item:order.getShoppingCart()){
                    if(counter == 1) {
                        if(order.getStatus() == Order.Status.Completed){
                            if(order.getReview(1).isEmpty())
                                System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getVendor().getVendorName()) + String.format("%-30s", order.getOrderType()) + String.format("%-30s", order.getStatus()) + String.format("%-30s", "REVIEWED") + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                            else
                                System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getVendor().getVendorName()) + String.format("%-30s", order.getOrderType()) + String.format("%-30s", order.getStatus()) + String.format("%-30s", "NOT REVIEWED") + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                        }else
                            System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getVendor().getVendorName()) + String.format("%-30s", order.getOrderType()) + String.format("%-30s", order.getStatus()) + String.format("%-30s", "") + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));

                    }else{
                        System.out.println(String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") +  String.format("%-30s", "") + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", ""));
                    }
                    counter++;
                }
            }
            System.out.println();
        }else{
            System.out.println("(No Order History Exist.)");
            System.out.println();
        }
        System.out.print("Would you like to take any action from your history? Enter 1 to re-order / Enter 2 to review / Enter any other value to exit:");
        try{
            int proceed = input.nextInt();
            if(proceed == 1){
                while(true) {
                    System.out.println("Enter the order ID:");
                    String orderID = input.nextLine();
                    if (orderID.isEmpty()) {
                        System.out.println("Input cannot be null.");
                    } else if (!orderHistory.contains(new Order(orderID))) {
                        System.out.println("Please enter a valid ID.");
                    } else {
                        ArrayList<Cart> cart = customer.getCartItems();
                        int selection = 0;
                        if (!cart.isEmpty()) {
                            System.out.println("Your cart is not empty. If you continue your cart and selection will be removed.");
                            System.out.print("Enter 1 to remove / Enter other value to exit.");
                            try {
                                selection = input.nextInt();
                                input.nextLine();
                            } catch (InputMismatchException e) {
                                input.nextLine();
                            }
                        }
                        if(selection == 1){
                            Order order = new Order(orderID);
                            ArrayList<Cart> orderItems = order.getShoppingCart();
                            for(Cart items:orderItems){
                                customer.addToCart(items.getItem(),items.getQuantity());
                            }
                            System.out.println("Successfully add to cart!");
                            System.out.println();
                            accessCart(customer,order.getVendor());
                        }
                        break;
                    }
                }
            }else if(proceed == 2){
                while(true) {
                    System.out.println("Enter the order ID:");
                    String orderID = input.nextLine();
                    if (orderID.isEmpty()) {
                        System.out.println("Input cannot be null.");
                        System.out.println();
                    } else if (!orderHistory.contains(new Order(orderID))) {
                        System.out.println("Please enter a valid order ID.");
                        System.out.println();
                    } else {
                        boolean reviewVendor=false,reviewRunner=false;
                        Order order = new Order(orderID);
                        String vendorReview = order.getReview(1);
                        String runnerReview = order.getReview(2);
                        if(vendorReview!=null && runnerReview!=null){
                            System.out.println();
                            System.out.println("You've already submitted both vendor and runner review for this order.");
                            System.out.println("Vendor Review : "+vendorReview);
                            System.out.println("Runner Review : "+runnerReview);
                        }else {
                            if (vendorReview == null && runnerReview == null) {
                                System.out.println("Would you like to share your experience with both vendor and runner? Your review will be anonymous on their page.");
                                System.out.print("Enter 'yes' or 'no': ");
                                String choice = input.nextLine().toLowerCase();
                                if (choice.equals("yes")) {
                                    reviewVendor = true;
                                    reviewRunner = true;
                                }else if(choice.equals("no")){
                                    System.out.println("Would you like to share your experience for either one?");
                                    System.out.println("1. Vendor\n2. Runner");
                                    System.out.println();
                                    System.out.println("(Enter any other value to exit.)");
                                    System.out.println();
                                    System.out.print("Enter the number:");
                                    try{
                                        int share = input.nextInt();
                                        if(share == 1){
                                            reviewVendor = true;
                                        }else if(share == 2){
                                            reviewRunner = true;
                                        }
                                        input.nextLine();
                                    }catch(InputMismatchException e){
                                        input.nextLine();
                                    }
                                }
                            } else if (runnerReview != null) {
                                System.out.println();
                                System.out.println("You've already submitted a runner review for this order.");
                                System.out.println("Runner Review : " + runnerReview);
                                System.out.println();
                                System.out.println("Would you like to share your experience with this vendor? Your review will be anonymous on the vendor page.");
                                System.out.print("Enter 'yes' or 'no': ");
                                String choice = input.nextLine().toLowerCase();
                                if (choice.equals("yes")) {
                                    reviewVendor = true;
                                }
                            } else{
                                System.out.println();
                                System.out.println("You've already submitted a vendor review for this order.");
                                System.out.println("Vendor Review : " + vendorReview);
                                System.out.println();
                                System.out.println("Would you like to share your experience with the runner? Your review will be anonymous on the runner page.");
                                System.out.print("Enter 'yes' or 'no': ");
                                String choice = input.nextLine().toLowerCase();
                                if (choice.equals("yes")) {
                                    reviewRunner = true;
                                }
                            }
                        }
                        if(reviewVendor){
                            System.out.println();
                            System.out.println("Please share your experience with this vendor? Your review will be anonymous on the vendor page.");
                            System.out.print("How will you rate your experience with this vendor? Rate from 1-5:");
                            int rate;
                            while (true) {
                                try {
                                    rate = input.nextInt();
                                    if (rate > 5 || rate < 1) {
                                        System.out.println("Please enter a valid rating (range from 1 to 5). ");
                                        System.out.println();
                                        System.out.print("Rate from 1-5:");
                                    } else
                                        break;

                                } catch (InputMismatchException e) {
                                    System.out.println("Please enter a valid rating (range from 1 to 5). ");
                                    System.out.println();
                                    System.out.print("Rate from 1-5:");
                                }
                            }
                            System.out.print("Give a feedback on this vendor:");
                            String rev = input.nextLine();
                            while (rev.isEmpty()) {
                                System.out.println("Unable to submit an empty feedback. Please submit again.");
                                System.out.println();
                                System.out.print("Feedback: ");
                                rev = input.nextLine();
                            }
                            order.setReview(rate, rev,1);
                            System.out.println();
                            System.out.println("Successfully submitted the review!");
                        }
                        if(reviewRunner){
                            System.out.println();
                            System.out.println("Please share your experience with the runner? Your review will be anonymous on the runner page.");
                            System.out.print("How will you rate your experience with the runner? Rate from 1-5:");
                            int rate;
                            while (true) {
                                try {
                                    rate = input.nextInt();
                                    if (rate > 5 || rate < 1) {
                                        System.out.println("Please enter a valid rating (range from 1 to 5). ");
                                        System.out.println();
                                        System.out.print("Rate from 1-5:");
                                    } else
                                        break;

                                } catch (InputMismatchException e) {
                                    System.out.println("Please enter a valid rating (range from 1 to 5). ");
                                    System.out.println();
                                    System.out.print("Rate from 1-5:");
                                }
                            }
                            System.out.print("Give a feedback on the runner:");
                            String rev = input.nextLine();
                            while (rev.isEmpty()) {
                                System.out.println("Unable to submit an empty feedback. Please submit again.");
                                System.out.println();
                                System.out.print("Feedback: ");
                                rev = input.nextLine();
                            }
                            order.setReview(rate, rev,2);
                            System.out.println();
                            System.out.println("Successfully submitted the review!");
                        }
                        break;
                    }
                }
            }
        }catch(InputMismatchException e){
            input.nextLine();
        }
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
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
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

    public static void checkActiveOrderStatus(Customer customer) {
        Scanner input = new Scanner(System.in);

        System.out.println("-----------------------------------------------------");
        System.out.println("|                 ACTIVE ORDER STATUS               |");
        System.out.println("-----------------------------------------------------");
        System.out.println();
        ArrayList<Order> orders = customer.getOrders();
        System.out.println("====================================================================================================================================================================================");
        System.out.println(String.format("%-30s", "ORDER ID") + String.format("%-30s", "VENDOR") + String.format("%-30s", "SERVING METHOD") + String.format("%-30s", "STATUS") + String.format("%-30s", "ITEM") + String.format("%-30s", "QUANTITY"));
        System.out.println("====================================================================================================================================================================================");
        if (!orders.isEmpty()) {
            int counter = 1;
            for (Order order : orders) {
                for (Cart item : order.getShoppingCart()) {
                    if (counter == 1) {
                        System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getVendor().getVendorName()) + String.format("%-30s", order.getOrderType()) + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()));
                    } else {
                        System.out.println(String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()));
                    }
                    counter++;
                }
            }
            System.out.println();
        } else {
            System.out.println("(You have currently no active order.)");
            System.out.println();
        }
        System.out.println();
        System.out.print("Would you like to take any action on your order? (Enter 'yes' to proceed) ");
        String userInput = input.nextLine().trim().toLowerCase();
        if (userInput.equals("yes")) {
            System.out.println("What action would you like to take?");
            System.out.println("1. Cancel an order");
            System.out.println();
            System.out.println("(Enter other value to exit.)");
            System.out.println();
            System.out.print("Enter the number:");
            try{
                int action = input.nextInt();
                if(action == 1){
                    System.out.print("Enter the order ID you wish to cancel:");
                    String orderID = input.nextLine();
                    Order order = new Order(orderID);
                    if (orders.contains(order)) {
                        if (order.getStatus() == Order.Status.PendingAccepted) {
                            customer.cancelVendorOrder(order);
                            Credit credit = new Credit(customer);
                            credit.addAmount(order.getTotalPrice(),2);
                            System.out.println("Successfully canceled the order. The refund will be credited to your account");
                        } else {
                            System.out.println("Unable to cancel the order due to [Order Status : " + order.getStatus() + "]");
                        }
                    }else{
                        System.out.println("Order ID not found. Your orders will remain unchanged.");
                    }
                }
            }catch(InputMismatchException e){
                input.nextLine();
            }
        } else {
            System.out.println("Your orders will remain unchanged.");
        }
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

    public static void checkNotification(Customer customer){
        Scanner input = new Scanner(System.in);
        int counter=1;
        System.out.println("-------------------------------------------------------");
        System.out.println("|                     NOTIFICATIONS                   |");
        System.out.println("|-----------------------------------------------------|");
        if(!customer.getNotifications().isEmpty()) {
            for (CustomerNotification notification : customer.getNotifications()) {
                System.out.println("|" + String.format("%-3s", counter) + notification.getMessage() + "|");
                counter++;
            }
            System.out.println("-------------------------------------------------------");
            System.out.println();
            System.out.print("Enter 1 to check notification detail / Enter any other value to exit:");
            try {
                int proceed = input.nextInt();
                if(proceed == 1){
                    input.nextLine();
                    int repeat = 1;
                    while (repeat == 1) {
                        System.out.println();
                        System.out.print("Which notification you would like to access? Enter the number:");
                        try {
                            int number = input.nextInt();
                            if(number<1 || number>customer.getNotifications().size()){
                                System.out.print("Invalid number. Enter 1 to try again / Enter any other value to exit:");
                                try{
                                    repeat = input.nextInt();
                                }catch (InputMismatchException e){
                                    repeat=0;
                                }
                            }else{
                                System.out.println();
                                System.out.println("(The notification will be removed after viewing.)");
                                System.out.println("Message:");
                                CustomerNotification notification = customer.getNotifications().get(number-1);
                                int code = notification.getCode();
                                if(code == 3){
                                    FileOperation file = new FileOperation("CustomerCredit.txt");
                                    ArrayList<String> foundCredit = file.search(notification.getObjectID());
                                    if(foundCredit.size()==1){
                                        String[] credit = foundCredit.get(0).split(",");
                                        notification.transactionReceipt(new Credit(credit[0],customer,credit[2],credit[3],credit[4]));
                                        System.out.println();
                                        System.out.print("Enter any key to exit.");
                                        String exit = input.nextLine();
                                        notification.deleteNotification();
                                    }else{
                                        System.out.println("Unable to retrieve the notification.");
                                    }
                                }else if(code == 2 || code == 4 || code == 5){
                                    Order order = new Order(notification.getObjectID());
                                    if(code == 2)
                                        notification.notifyOrderStatusUpdate(order);
                                    if(code == 4)
                                        notification.notifyCancelOrder(order);
                                    if(code == 5)
                                        notification.notifyRunnerDetail(order);
                                    System.out.println();
                                    System.out.print("Enter any key to exit.");
                                    String exit = input.nextLine();
                                    notification.deleteNotification();
                                }else if(code == 1){
                                    Order order = new Order(notification.getObjectID());
                                    int method = notification.notifyRunnerNotAvailable(order);
                                    order.setOrderType(method);
                                    customer.modifyOrderFile(order.getID(),order.toString());
                                    VendorNotification vendorNotification = new VendorNotification("Serving method has been changed!",order.getVendor(),3,order.getID());
                                    vendorNotification.saveNotification();
                                    System.out.println("Your order has been updated and notified the vendor.");
                                    System.out.println();
                                    System.out.print("Enter any key to exit.");
                                    String exit = input.nextLine();
                                    notification.deleteNotification();
                                }else{
                                    System.out.println("Unable to retrieve the notification.");
                                }
                                System.out.println();
                                repeat=0;
                            }
                        } catch (InputMismatchException e) {
                            System.out.print("Invalid input, please enter an integer input. Enter 1 to try again / Enter any other value to exit:");
                            try{
                                repeat = input.nextInt();
                            }catch (InputMismatchException ex){
                                repeat=0;
                            }
                        }
                        input.nextLine();
                    }
                }
            }catch(InputMismatchException e){
                input.nextLine();
            }
        }else {
            System.out.println("|" + String.format("%-53s", "(No Notification.)") + "|");
            System.out.println("-------------------------------------------------------");
            System.out.println();
            System.out.print("Press enter to exit");
            String proceed = input.nextLine();
        }
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

}
