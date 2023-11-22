
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

    IDGenerator g = new IDGenerator("Customer.ser","CA");
    System.out.println(g.generateSerializedID());
        try {
            while (true) {
                int choice;
                MainInterface.mainMenu();
                choice = MainInterface.userInput();

                switch (choice) {
                    case 1://admin
                        if (AdminInterface.login()) {

                            int access;
                            AdminInterface.AdminMain();
                            access = AdminInterface.userInput();

                            switch (access){
                                case 1://access customer
                                    int func;
                                    AdminInterface.accessCustomer();
                                    func = AdminInterface.userInput();
                                    switch(func){
                                        case 1:
                                            AdminInterface.createCustomer();
                                            return;
                                        case 2:
                                            AdminInterface.updateCustomer();
                                            return;
                                        case 3:
                                            AdminInterface.deleteCustomer();
                                            return;
                                        case 4:
                                            AdminInterface.topUpCustomer();
                                            return;
                                        case 0:
                                            return;
                                    }
                                case 2:
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