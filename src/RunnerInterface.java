import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RunnerInterface extends MainInterface{

    public static Runner login() {
        String usernameInput = null, passwordInput = null;
        boolean status;
        Runner runner=null;
        Runner loginRunner=null;
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
            SerializationOperation operation = new SerializationOperation("Runner.ser");
            ArrayList<Runner> foundRunner = operation.searchObjects(usernameInput,Runner.class);
            if(foundRunner.size() == 1){
                runner = foundRunner.get(0);
            }
            if (runner == null) {
                System.out.print("User ID not exist. Enter 1 to try again / Enter other value to exit.");
                try {
                    repeat = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid integer.");
                    input.nextLine();
                }
                System.out.println();
            }else if(!usernameInput.equals(runner.getID()) || !passwordInput.equals(runner.getPassword())){
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
                loginRunner = new Runner(runner.getID(),runner.getPassword(),runner.getRunnerName(),runner.getContact(),runner.getStatus());
                repeat = 0;
            }
        }
        return loginRunner;
    }

    public static void RunnerMain(Runner runner) {
        System.out.println("------------------------------------------");
        System.out.println("|                MAIN MENU               |");
        System.out.println("------------------------------------------");
        System.out.println();
        System.out.println("Welcome, "+runner.getRunnerName()+".");
        System.out.println("(You have currently "+runner.getNotifications().size()+" notification(s).)");
        System.out.println();
        System.out.println("Please choose which you would like to proceed.");
        System.out.println();
        System.out.println(String.format("%-30s", "1. View Profile") + String.format("%-30s", "2. Access Active Task"));
        System.out.println(String.format("%-30s", "3. Check Task History")+String.format("%-30s", "4. Check Notification"));
        System.out.println(String.format("%-30s", "5. Revenue Dashboard"));
        System.out.println();
        System.out.println("(Enter 0 to log out.)");
        System.out.println();
    }

    public static void accessProfile(Runner runner){
        Scanner input = new Scanner(System.in);

        System.out.println("------------------------------------------------");
        System.out.println("|                PROFILE DETAILS               |");
        System.out.println("------------------------------------------------");//48
        System.out.println();
        System.out.println(String.format("%-24s", "Runner ID") + String.format("%24s", runner.getID()));
        System.out.println(String.format("%-24s", "Runner Name") + String.format("%24s", runner.getRunnerName()));
        System.out.println(String.format("%-24s", "Runner Contact") + String.format("%24s", runner.getContact()));
        System.out.println(String.format("%-24s", "Runner Password") + String.format("%24s", "*****"+runner.getPassword().substring(runner.getPassword().length()-3)));
        System.out.println(String.format("%-24s", "Runner Status") + String.format("%24s", runner.getStatus()));
        System.out.println();
        System.out.println("(To modify the details, please proceed to admin.)");
        System.out.println();
        System.out.print("(Press Enter to exit.)");
        String proceed = input.nextLine();
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

    public static void checkActiveTaskStatus(Runner runner) {
        Scanner input = new Scanner(System.in);

        System.out.println("-----------------------------------------------------");
        System.out.println("|                  ACTIVE TASK STATUS               |");
        System.out.println("-----------------------------------------------------");
        System.out.println();
        ArrayList<Order> orders = runner.getReceivedOrders();
        System.out.println("================================================================================================================================================================================================================================================");
        System.out.println(String.format("%-30s", "ORDER ID") + String.format("%-30s", "CUSTOMER") + String.format("%-30s", "CUSTOMER ADDRESS") + String.format("%-30s", "VENDOR")  + String.format("%-30s", "VENDOR ADDRESS") + String.format("%-30s", "STATUS") + String.format("%-30s", "ITEM") + String.format("%-30s", "QUANTITY"));
        System.out.println("================================================================================================================================================================================================================================================");
        if (!orders.isEmpty()) {
            int counter = 1;
            for (Order order : orders) {
                for (Cart item : order.getShoppingCart()) {
                    if (counter == 1) {
                        System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName())  + String.format("%-30s", order.getCustomer().getAddress()) + String.format("%-30s", order.getVendor().getVendorName())  + String.format("%-30s", order.getVendor().getAddress()) + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()));
                    } else {
                        System.out.println(String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()));
                    }
                    counter++;
                }
                counter=1;
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
                        if (validOrderID) {
                            System.out.println();
                            System.out.println("Current Order Status: " + order.getStatus());
                            if (order.getStatus().equals(Order.Status.Delivering)) {
                                System.out.println("|");
                                System.out.println("v");
                                System.out.println("New Order Status: " + Order.Status.Completed);
                                System.out.println();

                                System.out.print("Is the information correct? (Enter 'yes' to proceed): ");
                                String confirmation = input.nextLine().trim().toLowerCase();

                                if (confirmation.equals("yes")) {
                                    runner.updateOrder(order,3);
                                } else {
                                    System.out.println("Update canceled. Please review the information and try again.");
                                }
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

    public static void checkTaskHistory(Runner runner){
        Scanner input = new Scanner(System.in);
        ArrayList<Order> taskHistory = runner.getTaskHistory();

        System.out.println("----------------------------------------------");
        System.out.println("|                 TASK HISTORY               |");
        System.out.println("----------------------------------------------");
        System.out.println();
        System.out.println("Number of tasks: "+taskHistory.size());
        System.out.println();
        System.out.println("====================================================================================================================================================================================");
        System.out.println(String.format("%-30s", "ORDER ID") + String.format("%-30s", "CUSTOMER") + String.format("%-30s", "VENDOR") + String.format("%-30s", "STATUS") + String.format("%-30s", "ITEM")+String.format("%-30s", "QUANTITY") + String.format("%-30s", "DELIVERY FEE"));
        System.out.println("====================================================================================================================================================================================");
        if (!taskHistory.isEmpty()) {
            int counter = 1;
            for (Order order : taskHistory) {
                for(Cart item:order.getShoppingCart()){
                    if(counter == 1) {
                        System.out.println(String.format("%-30s", order.getID()) + String.format("%-30s", order.getCustomer().getName()) + String.format("%-30s", order.getVendor().getVendorName()) + String.format("%-30s", order.getStatus()) + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", order.getDeliveryFee()));

                    }else{
                        System.out.println(String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") + String.format("%-30s", "") +  String.format("%-30s", "") + String.format("%-30s", item.getItem().getItemName()) + String.format("%-30s", item.getQuantity()) + String.format("%-30s", ""));
                    }
                    counter++;
                }
                counter=1;
            }
            System.out.println();
            System.out.print("Would you like to take any action from your history? Enter 1 to check customer reviews / Enter any other value to exit:");
            try{
                int proceed = input.nextInt();
                if(proceed == 1){
                    boolean reviewFound = false;
                    for (Order order : taskHistory) {
                        if (order.getReview(2) != null) {
                            reviewFound = true;
                            System.out.println();
                            System.out.println("Review from " + "*****"+order.getCustomer().getName().substring(order.getCustomer().getName().length()-3) + ": " + order.getReview(2));
                        }
                    }
                    if (!reviewFound) {
                        System.out.println();
                        System.out.println("No reviews found for any tasks in the task history.");
                    }
                }
            }catch(InputMismatchException e){
                input.nextLine();
            }
        }else{
            System.out.println("(No Task History Exist.)");
        }
        input.nextLine();
        System.out.println();
        System.out.print("(Press enter to exit.)");
        String exit = input.nextLine();
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

    public static void checkNotification(Runner runner){
        Scanner input = new Scanner(System.in);
        int counter=1;
        System.out.println("-------------------------------------------------------");
        System.out.println("|                     NOTIFICATIONS                   |");
        System.out.println("|-----------------------------------------------------|");
        if(!runner.getNotifications().isEmpty()) {
            for (RunnerNotification notification : runner.getNotifications()) {
                System.out.println("|" + String.format("%-3s", counter) + String.format("%-50s", notification.getMessage()) + "|");
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
                            if(number<1 || number>runner.getNotifications().size()){
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
                                RunnerNotification notification = runner.getNotifications().get(number-1);
                                int code = notification.getCode();
                                Order order = new Order(notification.getObjectID());
                                if(code == 1){
                                    System.out.println();
                                    int acceptStatus = notification.notifyComingTask(order);
                                    if(acceptStatus ==1){
                                        if(runner.getStatus()){
                                            runner.updateOrder(order,acceptStatus);
                                            runner.receiveOrder(order);
                                            System.out.println("Successfully accepted the task. Thank you!");
                                        }else{
                                            runner.updateOrder(order,2);
                                            System.out.println("Unable to accept the task. You have an active task on hand.");
                                            OrderTask orderTask = new OrderTask();
                                            orderTask.allocateRunner(order);
                                        }
                                    }else if(acceptStatus==2){
                                        runner.updateOrder(order,acceptStatus);
                                        OrderTask orderTask = new OrderTask();
                                        orderTask.allocateRunner(order);
                                    }
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
            System.out.print("(Press Enter to exit.)");
            String proceed = input.nextLine();
        }
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }

    public static void accessDashboard(Runner runner){
        Scanner input = new Scanner(System.in);
        RunnerDashboard dashboard = new RunnerDashboard(runner.getTaskHistory());

        System.out.println("------------------------------------------");
        System.out.println("|                DASHBOARD                |");
        System.out.println("------------------------------------------");
        System.out.println();
        System.out.println("Welcome! Please select the dashboard you'd like to view:");
        System.out.println("1. Revenue Dashboard");
        System.out.println();
        System.out.println("(Enter any other key to exit.)");
        System.out.println();
        while(true) {
            System.out.print("Enter the number of your choice: ");
            try {
                int choice = input.nextInt();
                System.out.println();
                if(choice == 1){
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

                        switch (revenueChoice) {
                            case 1:
                                System.out.println("------------------------------------------------------------");
                                System.out.println(String.format("%-30s", "DATE")+String.format("%-30s", "REVENUE (RM)"));
                                System.out.println("------------------------------------------------------------");
                                dashboard.printRevenueByDate();
                                break;
                            case 2:
                                System.out.println("------------------------------------------------------------");
                                System.out.println(String.format("%-30s", "MONTH")+String.format("%-30s", "REVENUE (RM)"));
                                System.out.println("------------------------------------------------------------");
                                dashboard.printRevenueByMonth();
                                break;
                            case 3:
                                System.out.println("------------------------------------------------------------");
                                System.out.println(String.format("%-30s", "YEAR")+String.format("%-30s", "REVENUE (RM)"));
                                System.out.println("------------------------------------------------------------");
                                dashboard.printRevenueByYear();
                                break;
                            default:
                                System.out.println("Exiting the dashboard. Thank you!");
                                break;
                        }
                        break;
                    } catch (InputMismatchException e) {
                        input.nextLine(); // Clear the invalid input
                    }
                }else {
                    System.out.println("Exiting the dashboard. Thank you!");
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer input.");
            }
        }
        System.out.println();
        System.out.print("(Press enter to exit.)");
        String exit = input.nextLine();
        System.out.println();
        System.out.println("Proceeding to main menu......");
        System.out.println();
    }
}
