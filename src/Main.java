
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

    //IDGenerator g = new IDGenerator("Customer.ser","CA");
    //System.out.println(g.generateSerializedID());
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
                                                break;
                                            default:
                                                System.out.println("Invalid choice. Please try again");
                                                break;
                                        }
                                        break;
                                    case 3:
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
                                                break;
                                            default:
                                                System.out.println("Invalid choice. Please try again");
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