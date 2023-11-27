
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        try {
            while (true) {
                int choice;
                MainInterface.mainMenu();
                choice = MainInterface.userInput();

                switch (choice) {
                    case 1://admin
                        if (AdminInterface.login()) {
                            boolean returnToMainMenu = false;
                            while(true) {
                                int access;
                                AdminInterface.AdminMain();
                                access = AdminInterface.userInput();

                                switch (access) {
                                    case 1://access customer
                                        boolean returnToCustomer = false;
                                        while(true) {
                                            int cus;
                                            AdminInterface.accessCustomer();
                                            cus = AdminInterface.userInput();
                                            switch (cus) {
                                                case 1:
                                                    AdminInterface.createCustomer();
                                                    break;
                                                case 2:
                                                    AdminInterface.updateCustomer();
                                                    break;
                                                case 3:
                                                    AdminInterface.deleteCustomer();
                                                    break;
                                                case 4:
                                                    AdminInterface.topUpCustomer();
                                                    break;
                                                case 0:
                                                    returnToCustomer = true;
                                                    break;
                                                default:
                                                    System.out.println("Invalid choice. Please try again");
                                                    break;
                                            }
                                            if (returnToCustomer)
                                                break;
                                        }
                                        break;
                                    case 2://access vendor
                                        boolean returnToVendor = false;
                                        while(true) {
                                            int vend;
                                            AdminInterface.accessVendor();
                                            vend = AdminInterface.userInput();
                                            switch (vend) {
                                                case 1:
                                                    AdminInterface.createVendor();
                                                    break;
                                                case 2:
                                                    AdminInterface.updateVendor();
                                                    break;
                                                case 3:
                                                    AdminInterface.deleteVendor();
                                                    break;
                                                case 0:
                                                    returnToVendor = true;
                                                    break;
                                                default:
                                                    System.out.println("Invalid choice. Please try again");
                                                    break;
                                            }
                                            if (returnToVendor)
                                                break;
                                        }
                                        break;
                                    case 3:
                                        boolean returnToRunner = false;
                                        while(true) {
                                            int run;
                                            AdminInterface.accessRunner();
                                            run = AdminInterface.userInput();
                                            switch (run) {
                                                case 1:
                                                    AdminInterface.createRunner();
                                                    break;
                                                case 2:
                                                    AdminInterface.updateRunner();
                                                    break;
                                                case 3:
                                                    AdminInterface.deleteRunner();
                                                    break;
                                                case 0:
                                                    returnToRunner = true;
                                                    break;
                                                default:
                                                    System.out.println("Invalid choice. Please try again");
                                                    break;
                                            }
                                            if (returnToRunner)
                                                break;
                                        }
                                        break;
                                    case 0:
                                        returnToMainMenu = true;
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again");
                                        break;
                                }
                                if (returnToMainMenu) {
                                    break; // Break the inner while loop
                                }
                            }
                        }
                        break;
                    case 2://customer
                        Customer customer = CustomerInterface.login();
                        if (!(customer ==null)) {
                            boolean returnToMainMenu = false;
                            while(true) {
                                int access;
                                CustomerInterface.CustomerMain(customer);
                                access = CustomerInterface.userInput();

                                switch (access) {
                                    case 1://View Profile
                                        CustomerInterface.accessProfile(customer);
                                        break;
                                    case 2://View Menu & Order
                                        CustomerInterface.accessMenu(customer);
                                        break;
                                    case 3://Order History
                                        CustomerInterface.accessOrderHistory(customer);
                                        break;
                                    case 4://Transaction History
                                        CustomerInterface.accessTransactionHistory(customer);
                                        break;
                                    case 5://Check Active Order Status
                                        CustomerInterface.checkActiveOrderStatus(customer);
                                        break;
                                    case 6://Check Notification
                                        CustomerInterface.checkNotification(customer);
                                        break;
                                    case 0:
                                        returnToMainMenu = true;
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again");
                                        break;
                                }
                                if (returnToMainMenu) {
                                    break; // Break the inner while loop
                                }
                            }
                        }
                        break;
                    case 3://vendor
                        break;
                    case 4://runner
                        break;
                    case 0://exit application
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again");
                        break;
                }
            }
        }finally {
            MainInterface.exitApplication();
        }
    }
}