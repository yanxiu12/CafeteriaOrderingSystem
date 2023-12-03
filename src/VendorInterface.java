import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

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
            if(foundVendor.size() == 1){
                vendor = foundVendor.get(0);
            }
            if (vendor == null) {
                System.out.print("User ID not exist. Enter 1 to try again / Enter other value to exit.");
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
                repeat = 0;
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
        System.out.println("(You have currently "+vendor.getNotifications().size()+" notification(s).)");
        System.out.println();
        System.out.println("Please choose which you would like to proceed.");
        System.out.println();
        System.out.println(String.format("%-30s", "1. View Profile") + String.format("%-30s", "2. Access Menu Items"));
        System.out.println(String.format("%-30s", "3. Access Active Order")+String.format("%-30s", "4. Check Order History & Reviews"));
        System.out.println(String.format("%-30s", "5. Check Notification")+String.format("%-30s", "6. Sales & Revenue Dashboard"));
        System.out.println();
        System.out.println("(Enter 0 to log out.)");
        System.out.println();
    }

    public static void accessProfile(Vendor vendor){
        Scanner input = new Scanner(System.in);

        System.out.println("------------------------------------------------");
        System.out.println("|                PROFILE DETAILS               |");
        System.out.println("------------------------------------------------");//48
        System.out.println();
        System.out.println(String.format("%-24s", "Vendor ID") + String.format("%24s", vendor.getID()));
        System.out.println(String.format("%-24s", "Vendor Name") + String.format("%24s", vendor.getVendorName()));
        System.out.println(String.format("%-24s", "Vendor Category") + String.format("%24s", vendor.getCategory()));
        System.out.println(String.format("%-24s", "Vendor Address") + String.format("%24s", vendor.getAddress()));
        System.out.println(String.format("%-24s", "Vendor Password") + String.format("%24s", "*****"+vendor.getPassword().substring(vendor.getPassword().length()-3)));
        System.out.println();
        System.out.println("(To modify the details, please proceed to admin.)");
        System.out.println();
        System.out.print("(Press Enter to exit.)");
        String proceed = input.nextLine();
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

    public static void accessMenu(Vendor vendor) {
        Scanner input = new Scanner(System.in);

        System.out.println("------------------------------------------------");
        System.out.println("|                   MENU PAGE                  |");
        System.out.println("------------------------------------------------");
        System.out.println();
        MenuItem menu = new MenuItem(vendor);
        menu.setMenu(vendor);
        menu.printMenu();
        System.out.println();
        System.out.println("Access Menu:");
        System.out.println(String.format("%-30s", "1. Create Item")+String.format("%-30s", "2. Edit Item")+String.format("%-30s", "3. Delete Item"));
        System.out.println();
        System.out.println("Would you like to take any action of your menu? ");
        System.out.print("Select from option 1-3 / Enter any other value to exit:");
        try {
            int proceed = input.nextInt();
            if(proceed == 1){
                createItem(vendor);
            }else if(proceed == 2){
                editItem(vendor);
            }else if(proceed == 3){
                deleteItem();
            }
            input.nextLine();
        }catch (InputMismatchException e){
            input.nextLine();
        }
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

    public static void createItem(Vendor vendor){
        Scanner input = new Scanner(System.in);
        boolean status;
        String itemID,itemName = null;
        double price = 0;

        System.out.println("------------------------------------------------");
        System.out.println("|                  CREATE ITEM                 |");
        System.out.println("------------------------------------------------");
        System.out.println();
        itemID = IDGenerator.generateIDForMenu();
        System.out.println("Item ID:"+itemID);
        status = true;
        while(status){
            System.out.print("Item Name:");
            itemName = input.nextLine();
            if(!itemName.isEmpty()){
                status = false;
            }else{
                System.out.println("Please enter a valid name.");
            }
        }
        status = true;
        while(status){
            System.out.print("Item Price (RM):");
            try {
                price = input.nextDouble();
                input.nextLine();
                if(price <= 0){
                    System.out.println("Price must be greater than 0.00");
                }else
                    status = false;
            }catch(InputMismatchException e){
                System.out.println("Please enter a numeric value.");
                input.nextLine();
            }
        }
        String itemPrice = String.format("%.2f", price);
        MenuItem menu = new MenuItem(itemID,itemName,itemPrice,vendor);
        menu.write2file(menu.toString());
        System.out.println();
        System.out.println("Successfully created the item.");
    }

    public static void editItem(Vendor vendor){
        Scanner input = new Scanner(System.in);
        boolean status,repeat,update=true;
        int choice=0;
        String idToUpdate;
        MenuItem menu = null;

        System.out.println("------------------------------------------------");
        System.out.println("|                  UPDATE ITEM                 |");
        System.out.println("------------------------------------------------");
        System.out.println();
        while(update){
            System.out.print("Enter Item ID:");
            idToUpdate = input.nextLine();
            if(idToUpdate.isEmpty())
                System.out.println("Input cannot be null.");
            else {
                System.out.println();
                FileOperation file = new FileOperation("MenuItem.txt");
                ArrayList<String> foundItem = file.search(idToUpdate);
                System.out.println("==========================================================================================");
                System.out.println(String.format("%-30s", "ITEM ID") + String.format("%-30s", "ITEM NAME") + String.format("%-30s", "ITEM PRICE"));
                System.out.println("==========================================================================================");
                if (!foundItem.isEmpty()) {
                    for (String item : foundItem) {
                        String[] part = item.split(";");
                        System.out.println(String.format("%-30s", part[0]) + String.format("%-30s", part[1]) + String.format("%-30s", part[2]));
                        menu = new MenuItem(part[0],part[1],part[2],vendor);
                    }
                    System.out.println();
                    repeat = true;
                    while(repeat){
                        System.out.println("Which would you like to update?");
                        System.out.println();
                        System.out.println(String.format("%-20s", "1. Item Name")+String.format("%-20s", "2. Item Price"));
                        System.out.println();
                        status=true;
                        while(status){
                            System.out.print("Enter the number:");
                            try {
                                choice = input.nextInt();
                                if(choice==1 || choice==2) {
                                    status = false;
                                }else {
                                    System.out.println("Please enter a valid input.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Please enter a valid input.");
                            }
                            input.nextLine();
                        }
                        System.out.println();
                        switch(choice){
                            case 1:
                                status = true;
                                while (status) {
                                    System.out.println("Item Name:"+menu.getItemName());
                                    System.out.print("New Item Name:");
                                    String newName = input.nextLine();
                                    if (newName.isEmpty()) {
                                        System.out.println("Input cannot be empty.");
                                    }else if (newName.equals(menu.getItemName())) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    }else {
                                        status = false; // Exit loop if name format is valid
                                        menu.setItemName(newName);
                                        System.out.println("The updated information has been stored.");
                                    }
                                }
                                break;
                            case 2:
                                status = true;
                                while(status){
                                    System.out.println("Item Price (RM):"+menu.getItemPrice());
                                    System.out.print("New Item Price (RM):");
                                    try {
                                        double newPrice = input.nextDouble();
                                        input.nextLine();
                                        if (newPrice <= 0) {
                                            System.out.println("Price must be greater than 0.00");
                                        } else if (newPrice == menu.getItemPrice()) {
                                            status = false;
                                            System.out.println("The update was not processed. The new value matches the existing value.");
                                        } else {
                                            status = false;
                                            menu.setItemPrice(newPrice);
                                            System.out.println("The updated information has been stored.");
                                        }
                                    }catch(InputMismatchException e){
                                        System.out.println("Please enter a numeric value.");
                                        input.nextLine();
                                    }
                                }
                                break;
                        }
                        System.out.println();
                        System.out.print("Do you have anything more to update? \nEnter 1 to continue / Enter any other value to finish updating:");
                        try {
                            int more = input.nextInt();
                            if(more != 1)
                                repeat = false;
                        } catch (InputMismatchException e) {
                            repeat = false;
                        }
                        input.nextLine();//clear the scanner's buffer
                        System.out.println();
                    }
                    menu.modifyFile(idToUpdate,menu.toString());
                    System.out.println();
                    System.out.println("Successfully updated the item.");
                    update=false;
                } else {
                    System.out.println("(No Item Record Exist.)");
                    System.out.println();
                    System.out.print("Enter 1 to re-try again.");
                    try {
                        int retry=input.nextInt();
                        if (retry!=1)
                            update=false;
                    } catch (InputMismatchException e) {
                        update = false;
                    }
                    input.nextLine();//clear the scanner's buffer
                    System.out.println();
                }
            }
        }
    }

    public static void deleteItem(){
        Scanner input = new Scanner(System.in);
        boolean status,repeat;
        int choice;
        String idToDelete;

        System.out.println("------------------------------------------------");
        System.out.println("|                  DELETE ITEM                 |");
        System.out.println("------------------------------------------------");
        System.out.println();
        repeat = true;
        while(repeat){
            System.out.print("Enter Item ID to delete:");
            idToDelete = input.nextLine();
            if(idToDelete.isEmpty())
                System.out.println("Input cannot be null.");
            else{
                System.out.println();
                FileOperation file = new FileOperation("MenuItem.txt");
                ArrayList<String> foundItem = file.search(idToDelete);
                System.out.println("==========================================================================================");
                System.out.println(String.format("%-30s", "ITEM ID") + String.format("%-30s", "ITEM NAME") + String.format("%-30s", "ITEM PRICE"));
                System.out.println("==========================================================================================");
                if (!foundItem.isEmpty()) {
                    for (String item : foundItem) {
                        String[] part = item.split(";");
                        System.out.println(String.format("%-30s", part[0]) + String.format("%-30s", part[1]) + String.format("%-30s", part[2]));
                    }
                    System.out.println();
                    System.out.println("Are you sure you want to delete?");
                    System.out.println("1. Yes\n2. No");
                    System.out.println();
                    status = true;
                    while(status){
                        System.out.print("Enter the number:");
                        try {
                            choice = input.nextInt();
                            System.out.println();
                            if(choice==1){
                                status = false;
                                file.delete(idToDelete);
                                System.out.println("Successfully deleted the item.");
                                repeat=false;
                            }
                            else if(choice==2){
                                status = false;
                                System.out.print("Do you want to delete other item? Enter 1 to re-try.");
                                try {
                                    int retry=input.nextInt();
                                    if (retry!=1)
                                        repeat=false;
                                } catch (InputMismatchException e) {
                                    repeat = false;
                                }
                                input.nextLine();
                            }
                            else {
                                System.out.println("Please enter a valid input.");
                                input.nextLine();
                                System.out.println();
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid input.");
                            input.nextLine();//clear the scanner's buffer
                            System.out.println();
                        }
                    }
                }else {
                    System.out.println("(No Item Record Exist.)");
                    System.out.println();
                    System.out.print("Do you want to delete other item? Enter 1 to re-try.");
                    try {
                        int retry=input.nextInt();
                        if (retry!=1)
                            repeat=false;
                    } catch (InputMismatchException e) {
                        repeat = false;
                    }
                    input.nextLine();
                    System.out.println();
                }
            }
        }
    }

    public static void checkActiveOrderStatus(Vendor vendor) {
        Scanner input = new Scanner(System.in);

        System.out.println("-----------------------------------------------------");
        System.out.println("|                 ACTIVE ORDER STATUS               |");
        System.out.println("-----------------------------------------------------");
        System.out.println();
        ArrayList<Order> orders = vendor.getReceivedOrders();
        System.out.println("====================================================================================================================================================================================");
        System.out.println(String.format("%-30s", "ORDER ID") + String.format("%-30s", "CUSTOMER") + String.format("%-30s", "SERVING METHOD") + String.format("%-30s", "STATUS") + String.format("%-30s", "ITEM") + String.format("%-30s", "QUANTITY"));
        System.out.println("====================================================================================================================================================================================");
        if (!orders.isEmpty()) {
            int counter = 1;
            for (Order order : orders) {
                for (Cart item : order.getShoppingCart()) {
                    if (counter == 1) {
                        if(order.getOrderType()==1)
                            System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Dine-In") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                        else if(order.getOrderType()==2)
                            System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Take Away") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                        else
                            System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Delivery") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                    } else {
                        System.out.println(String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()));
                    }
                    counter++;
                }
                counter = 1;
            }
            System.out.println();
            System.out.println();
            System.out.print("Would you like to take any action on your order? (Enter 'yes' to proceed) ");
            String userInput = input.nextLine().trim().toLowerCase();
            if (userInput.equals("yes")) {
                System.out.println("What action would you like to take?");
                System.out.println("1. Update status of an order");
                System.out.println();
                System.out.println("(Enter other value to exit.)");
                System.out.println();
                System.out.print("Enter the number:");
                try{
                    int action = input.nextInt();
                    input.nextLine();
                    if (action == 1) {
                        boolean validOrderID = false;
                        System.out.print("Enter the order ID you wish to update: ");
                        String orderID = input.nextLine();
                        Order order = new Order(orderID);
                        for(Order receiveOrder:orders) {
                            if (receiveOrder.getID().equals(order.getID())) {
                                validOrderID = true;
                                break;
                            } else {
                                System.out.println("Order ID not found.");
                            }
                        }
                        if(validOrderID) {
                            System.out.println();
                            System.out.println("Current Order Status: " + order.getStatus());
                            if (order.getStatus().equals(Order.Status.PendingAccepted)) {
                                System.out.println("Please proceed to notification to Accept / Reject the order.");
                            } else if (order.getStatus().equals(Order.Status.VendorAccepted)) {
                                System.out.println("|");
                                System.out.println("v");
                                System.out.println("New Order Status: " + Order.Status.VendorIsReady);
                                System.out.println();

                                System.out.print("Is the information correct? (Enter 'yes' to proceed): ");
                                String confirmation = input.nextLine().trim().toLowerCase();

                                if (confirmation.equals("yes")) {
                                    vendor.updateOrder(order, 3);
                                    System.out.println("Successfully updated the order. We have notify the customer for lastest status.");
                                } else {
                                    System.out.println("Update canceled. Please review the information and try again.");
                                }
                            } else if (order.getOrderType() != 3 && order.getStatus().equals(Order.Status.VendorIsReady)) {
                                System.out.println("|");
                                System.out.println("v");
                                System.out.println("New Order Status: " + Order.Status.Completed);
                                System.out.println();

                                System.out.print("Is the information correct? (Enter 'yes' to proceed): ");
                                String confirmation = input.nextLine().trim().toLowerCase();

                                if (confirmation.equals("yes")) {
                                    vendor.updateOrder(order, 4);
                                    System.out.println("Successfully updated the order. We have notify the customer for lastest status.");
                                } else {
                                    System.out.println("Update canceled. Please review the information and try again.");
                                }
                            } else if (order.getOrderType() == 3) {
                                System.out.println("Unable to proceed with the update due to [Serving Method : Delivery] ");
                            } else {
                                System.out.println("Unable to proceed with the update due to [Order Status : " + order.getStatus() + "]");
                            }
                        }
                    }
                }catch(InputMismatchException e){
                    input.nextLine();
                }
            } else {
                System.out.println("The order will remain unchanged.");
            }
        } else {
            System.out.println("(You have currently no active order.)");
        }
        System.out.println();
        System.out.print("(Press enter to exit.)");
        String exit = input.nextLine();
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

    public static void checkOrderHistory(Vendor vendor){
        Scanner input = new Scanner(System.in);
        ArrayList<Order> orderHistory = vendor.getOrderHistory();

        System.out.println("--------------------------------------------------------");
        System.out.println("|                ORDER HISTORY & REVIEWS               |");
        System.out.println("--------------------------------------------------------");
        System.out.println();
        System.out.println("Please select an option:");
        System.out.println("1. Check order history by date");
        System.out.println("2. Check order history by month");
        System.out.println("3. Check order history by year");
        System.out.println("4. Check customer reviews");

        System.out.print("Enter your choice (1-4): ");
        int choice = 0;
        while (choice < 1 || choice > 4) {
            try {
                choice = input.nextInt();
                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    System.out.print("Enter your choice (1-4): ");
                }
            } catch (InputMismatchException e) {
                input.nextInt(); // Clear the invalid input
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                System.out.print("Enter your choice (1-4): ");
            }
        }
        System.out.println();
        switch (choice) {
            case 1:
                displayOrderHistoryDayByDay(vendor);
                break;
            case 2:
                displayOrderHistoryMonthly(vendor);
                break;
            case 3:
                displayOrderHistoryYearly(vendor);
                break;
            case 4:
                checkReview(orderHistory);
                break;
            default:
                break;
        }

        input.nextLine();
        System.out.println();
        System.out.print("(Press enter to exit.)");
        String exit = input.nextLine();
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

    private static void displayOrderHistoryDayByDay(Vendor vendor) {
        Set<LocalDate> uniqueDates = new HashSet<>();
        for (Order order : vendor.getOrderHistory()) {
            LocalDate orderDate = LocalDate.parse(order.getCurrentDate()); // Parse the date string
            uniqueDates.add(orderDate);
        }

        // Loop through each unique date and display orders for that date
        for (LocalDate date : uniqueDates) {
            System.out.println("Orders for " + date + ":");
            System.out.println();

            ArrayList<Order> ordersForDate = vendor.getOrderHistoryByDate(date);

            System.out.println("----------------------------------------------");
            System.out.println("|                ORDER HISTORY               |");
            System.out.println("----------------------------------------------");
            System.out.println();
            System.out.println("Number of orders: "+ordersForDate.size());
            System.out.println();
            System.out.println("==================================================================================================================================================================================================================");
            System.out.println(String.format("%-30s", "ORDER ID") + String.format("%-30s", "CUSTOMER") + String.format("%-30s", "SERVING METHOD") + String.format("%-30s", "STATUS") + String.format("%-30s", "ITEM")+String.format("%-30s", "QUANTITY") + String.format("%-30s", "TOTAL PRICE"));
            System.out.println("==================================================================================================================================================================================================================");

            if (!ordersForDate.isEmpty()) {
                int counter = 1;
                for (Order order : ordersForDate) {
                    for(Cart item:order.getShoppingCart()){
                        if(counter == 1) {
                            if(order.getOrderType()==1)
                                System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Dine-In") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                            else if(order.getOrderType()==2)
                                System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Take Away") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                            else
                                System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Delivery") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                        }else{
                            System.out.println(String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", ""));
                        }
                        counter++;
                    }
                    counter = 1;
                }
            } else {
                System.out.println("(No orders for this date.)");
            }
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    private static void displayOrderHistoryMonthly(Vendor vendor) {
        Set<YearMonth> uniqueMonths = new HashSet<>();
        for (Order order : vendor.getOrderHistory()) {
            LocalDate orderDate = LocalDate.parse(order.getCurrentDate()); // Parse the date string
            uniqueMonths.add(YearMonth.from(orderDate));
        }

        // Loop through each unique month and display orders for that month
        for (YearMonth month : uniqueMonths) {
            System.out.println("Orders for " + month + ":");
            System.out.println();

            ArrayList<Order> ordersForMonth = vendor.getOrderHistoryByMonth(month);

            System.out.println("----------------------------------------------");
            System.out.println("|                ORDER HISTORY               |");
            System.out.println("----------------------------------------------");
            System.out.println();
            System.out.println("Number of orders: " + ordersForMonth.size());
            System.out.println();
            System.out.println("==================================================================================================================================================================================================================");
            System.out.println(String.format("%-30s", "ORDER ID") + String.format("%-30s", "CUSTOMER") + String.format("%-30s", "SERVING METHOD") + String.format("%-30s", "STATUS") + String.format("%-30s", "ITEM") + String.format("%-30s", "QUANTITY") + String.format("%-30s", "TOTAL PRICE"));
            System.out.println("==================================================================================================================================================================================================================");

            if (!ordersForMonth.isEmpty()) {
                int counter = 1;
                for (Order order : ordersForMonth) {
                    for (Cart item : order.getShoppingCart()) {
                        if (counter == 1) {
                            if(order.getOrderType()==1)
                                System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Dine-In") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                            else if(order.getOrderType()==2)
                                System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Take Away") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                            else
                                System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Delivery") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                        } else {
                            System.out.println(String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", ""));
                        }
                        counter++;
                    }
                    counter = 1;
                }
            } else {
                System.out.println("(No orders for this month.)");
            }
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    private static void displayOrderHistoryYearly(Vendor vendor) {
        Set<Integer> uniqueYears = new HashSet<>();
        for (Order order : vendor.getOrderHistory()) {
            LocalDate orderDate = LocalDate.parse(order.getCurrentDate()); // Parse the date string
            uniqueYears.add(orderDate.getYear());
        }

        // Loop through each unique year and display orders for that year
        for (int year : uniqueYears) {
            System.out.println("Orders for Year " + year + ":");
            System.out.println();

            ArrayList<Order> ordersForYear = vendor.getOrderHistoryByYear(year);

            System.out.println("----------------------------------------------");
            System.out.println("|                ORDER HISTORY               |");
            System.out.println("----------------------------------------------");
            System.out.println();
            System.out.println("Number of orders: " + ordersForYear.size());
            System.out.println();
            System.out.println("==================================================================================================================================================================================================================");
            System.out.println(String.format("%-30s", "ORDER ID") + String.format("%-30s", "CUSTOMER") + String.format("%-30s", "SERVING METHOD") + String.format("%-30s", "STATUS") + String.format("%-30s", "ITEM") + String.format("%-30s", "QUANTITY") + String.format("%-30s", "TOTAL PRICE"));
            System.out.println("==================================================================================================================================================================================================================");

            if (!ordersForYear.isEmpty()) {
                int counter = 1;
                for (Order order : ordersForYear) {
                    for (Cart item : order.getShoppingCart()) {
                        if (counter == 1) {
                            if(order.getOrderType()==1)
                                System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Dine-In") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                            else if(order.getOrderType()==2)
                                System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Take Away") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                            else
                                System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", "Delivery") + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getTotalPrice()));
                        } else {
                            System.out.println(String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", ""));
                        }
                        counter++;
                    }
                    counter = 1;
                }
            } else {
                System.out.println("(No orders for this year.)");
            }
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    private static void checkReview(ArrayList<Order> orderHistory){
        System.out.println("-------------------------------------------------");
        System.out.println("|                CUSTOMER REVIEWS               |");
        System.out.println("-------------------------------------------------");
        System.out.println();

        boolean reviewFound = false;
        for (Order order : orderHistory) {
            if (order.getReview(1) != null) {
                reviewFound = true;
                System.out.println();
                System.out.println("Review from " + "*****"+order.getCustomer().getName().substring(order.getCustomer().getName().length()-3) + ": " + order.getReview(1));
            }
        }
        if (!reviewFound) {
            System.out.println();
            System.out.println("No reviews found for any orders in the order history.");
        }
    }

    public static void checkNotification(Vendor vendor){
        Scanner input = new Scanner(System.in);
        int counter=1;
        System.out.println("-------------------------------------------------------");
        System.out.println("|                     NOTIFICATIONS                   |");
        System.out.println("|-----------------------------------------------------|");
        if(!vendor.getNotifications().isEmpty()) {
            for (VendorNotification notification : vendor.getNotifications()) {
                System.out.println("|" + String.format("%-3s", counter) + String.format("%-50s", notification.getMessage()) + "|");
                counter++;
            }
            System.out.println("-------------------------------------------------------");
            System.out.println();
            System.out.print("Enter 1 to check notification detail / Enter any other value to exit:");
            try {
                int proceed = input.nextInt();
                input.nextLine();
                if(proceed == 1){
                    int repeat = 1;
                    while (repeat == 1) {
                        System.out.println();
                        System.out.print("Which notification you would like to access? Enter the number:");
                        try {
                            int number = input.nextInt();
                            input.nextLine();
                            if(number<1 || number>vendor.getNotifications().size()){
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
                                VendorNotification notification = vendor.getNotifications().get(number-1);
                                int code = notification.getCode();
                                Order order = new Order(notification.getObjectID());
                                if(code == 1){
                                    int orderStatus = notification.notifyComingOrder(order);
                                    if(vendor.updateOrder(order,orderStatus)){
                                        if (orderStatus == 1 && order.getOrderType() == 3) {
                                            OrderTask orderTask = new OrderTask();
                                            orderTask.allocateRunner(order);
                                        } else if (orderStatus == 2) {
                                            Credit credit = new Credit(order.getCustomer());
                                            credit.addAmount(order.getTotalPrice(), 2);
                                        }
                                        System.out.println();
                                        System.out.println("The order has been updated and notified the customer.");
                                    }else{
                                        System.out.println();
                                        System.out.println("Unable to update the order. Customer has canceled the order.");
                                    }
                                    System.out.println();
                                    System.out.print("Enter any key to exit.");
                                    String exit = input.nextLine();
                                    notification.deleteNotification();
                                }else if(code == 2 || code == 3){
                                    if(code == 2)
                                        notification.notifyCancelOrder(order);
                                    if(code == 3)
                                        notification.notifyOrderChange(order);
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
                    }
                }
            }catch(InputMismatchException e){
                input.nextLine();
            }
        }else {
            System.out.println("|" + String.format("%-53s", "(No Notification.)") + "|");
            System.out.println("-------------------------------------------------------");
            System.out.println();
            System.out.print("(Press Enter to exit.)");
            String proceed = input.nextLine();
        }
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

    public static void accessDashboard(Vendor vendor){
        Scanner input = new Scanner(System.in);
        VendorDashboard dashboard = new VendorDashboard(vendor.getOrderHistory());

        System.out.println("------------------------------------------");
        System.out.println("|                DASHBOARD                |");
        System.out.println("------------------------------------------");
        System.out.println();
        System.out.println("Welcome! Please select the dashboard you'd like to view:");
        System.out.println("1. Sales Dashboard");
        System.out.println("2. Revenue Dashboard");
        System.out.println();
        System.out.println("(Enter any other key to exit.)");
        System.out.println();

        System.out.print("Enter the number of your choice: ");
        try {
            int choice = input.nextInt();
            System.out.println();
            if(choice == 1){
                dashboard.countCompletedOrders();
                System.out.println();
                System.out.println("------------------------------------------------------------");
                System.out.println(String.format("%-30s", "ITEM")+String.format("%-30s", "QUANTITY SOLD"));
                System.out.println("------------------------------------------------------------");
                dashboard.printItemsBySales();
                System.out.println();
                System.out.print("Would you like to view another dashboard? (Enter 'yes' to continue or any other key to exit): ");
                input.nextLine();
                String continueChoice = input.nextLine().trim().toLowerCase();
                if (continueChoice.equals("yes")) {
                    choice = 2;
                    System.out.println();
                }else{
                    System.out.println();
                    System.out.print("(Press Enter to exit.)");
                    String proceed = input.nextLine();
                }
            }
            if(choice == 2){
                dashboard.calculateRevenue();
                System.out.println();
                System.out.println("Please choose the type of revenue report:");
                System.out.println("1. Daily");
                System.out.println("2. Monthly");
                System.out.println("3. Yearly");
                System.out.println();
                System.out.println("(Enter any other key to exit.)");
                System.out.println();
                System.out.print("Enter the number of your choice: ");
                try {
                    int revenueChoice = input.nextInt();
                    input.nextLine();
                    String proceed;

                    switch (revenueChoice) {
                        case 1:
                            System.out.println("------------------------------------------------------------");
                            System.out.println(String.format("%-30s", "DATE")+String.format("%-30s", "REVENUE (RM)"));
                            System.out.println("------------------------------------------------------------");
                            dashboard.printRevenueByDate();
                            System.out.println();
                            System.out.print("(Press Enter to exit.)");
                            proceed = input.nextLine();
                            break;
                        case 2:
                            System.out.println("------------------------------------------------------------");
                            System.out.println(String.format("%-30s", "MONTH")+String.format("%-30s", "REVENUE (RM)"));
                            System.out.println("------------------------------------------------------------");
                            dashboard.printRevenueByMonth();
                            System.out.println();
                            System.out.print("(Press Enter to exit.)");
                            proceed = input.nextLine();
                            break;
                        case 3:
                            System.out.println("------------------------------------------------------------");
                            System.out.println(String.format("%-30s", "YEAR")+String.format("%-30s", "REVENUE (RM)"));
                            System.out.println("------------------------------------------------------------");
                            dashboard.printRevenueByYear();
                            System.out.println();
                            System.out.print("(Press Enter to exit.)");
                            proceed = input.nextLine();
                            break;
                        default:
                            System.out.println("Exiting the dashboard. Thank you!");
                            break;
                    }
                } catch (InputMismatchException e) {
                    input.nextLine(); // Clear the invalid input
                }
            }else {
                System.out.println("Exiting the dashboard. Thank you!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter an integer input.");
        }
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

}
