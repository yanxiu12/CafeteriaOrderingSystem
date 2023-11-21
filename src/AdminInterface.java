import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminInterface extends MainInterface {

    public AdminInterface() {
    }

    public boolean login() {
        String usernameInput = null, passwordInput = null;
        boolean status = true;
        boolean login = false;
        Scanner input = new Scanner(System.in);

        System.out.println("--------------------------------------");
        System.out.println("|                LOGIN               |");
        System.out.println("--------------------------------------");
        System.out.println();
        while (login = true) {
            while (status = true) {
                System.out.print("User ID: ");
                if (input.nextLine() != null) {
                    usernameInput = input.nextLine();
                    status = false;
                } else
                    System.out.println("Username cannot be empty.");
            }
            while (status = true) {
                System.out.print("Password: ");
                if (input.nextLine() != null) {
                    passwordInput = input.nextLine();
                    status = false;
                } else
                    System.out.println("Password cannot be empty.");
            }
            System.out.println();
            System.out.println("Checking user credential......");
            System.out.println();
            if ((!usernameInput.equals("admin")) && (!passwordInput.equals("123123"))) {
                System.out.print("Incorrect username and password. Enter 1 to try again.");
                if (input.nextInt() != 1)
                    login = false;
                System.out.println();
            }
        }
        return status;
    }

    public void AdminMain() {
        System.out.println("------------------------------------------");
        System.out.println("|                MAIN MENU               |");
        System.out.println("------------------------------------------");
        System.out.println();
        System.out.println("Welcome, admin.");
        System.out.println();
        System.out.println("Please choose which you would like to proceed.");
        System.out.println();
        System.out.println(String.format("%-15s", "1. Access Customer") + String.format("%-15s", "2. Access Vendor") + String.format("%-15s", "3. Access Runner"));
        System.out.println();
    }

    public void accessCustomer() {
        System.out.println("------------------------------------------------");
        System.out.println("|                ACCESS CUSTOMER               |");
        System.out.println("------------------------------------------------");
        System.out.println();
        SerializationOperation operation = new SerializationOperation("Customer.ser");
        ArrayList<Customer> customerData = operation.readAllObjects(Customer.class);
        System.out.println("====================================================================================================================================================================================");
        System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "DATE OF BIRTH") + String.format("%-30s", "CONTACT") + String.format("%-30s", "PASSWORD") + String.format("%-30s", "ADDRESS"));
        System.out.println("====================================================================================================================================================================================");
        if (customerData != null) {
            for (Customer customer : customerData) {
                System.out.println(String.format("%-30s", customer.getID()) + String.format("%-30s", customer.getName()) + String.format("%-30s", customer.getDob()) + String.format("%-30s", customer.getContact()) + String.format("%-30s", customer.getPassword()) + String.format("%-30s", customer.getAddress()));
            }
        } else {
            System.out.println("(No Customer Record Exist.)");
        }
        System.out.println();
        System.out.println("Please choose which you would like to proceed.");
        System.out.println();
        System.out.println(String.format("%-15s", "1. Create") + String.format("%-15s", "2. Update") + String.format("%-15s", "3. Delete") + String.format("%-15s", "4. Top-Up Credit"));
        System.out.println();
    }

    public void createCustomer() {
        Scanner input = new Scanner(System.in);
        boolean status = true;
        String id=null,name=null,dob=null,contact=null,password=null,address=null;

        System.out.println("------------------------------------------------");
        System.out.println("|                CREATE CUSTOMER               |");
        System.out.println("------------------------------------------------");
        System.out.println();
        IDGenerator generator = new IDGenerator("Customer.ser","CA");
        id = generator.generateID();
        while(status = true){
            System.out.print("Customer Name:");
            name = input.nextLine();
            if(name.matches("[a-zA-Z\\s]+")){
                status = false;
            }else{
                System.out.println("Please enter a valid name.");
            }
        }
        while(status = true){
            System.out.print("Customer Date of Birth:");
            dob = input.nextLine();
            if(dob.isEmpty())
                status = false;
            else
                System.out.println("Please enter a valid date.");
        }
        while(status = true){
            System.out.print("Customer Contact:");
            contact = input.nextLine();
            if(contact.matches("(^01\\d{8}$|^011\\d{8}$)"))
                status = false;
            else
                System.out.println("Please enter a valid contact.");
        }
        while(status = true){
            System.out.print("Customer Address:");
            address = input.nextLine();
            if(address.isEmpty())
                status = false;
            else
                System.out.println("Please enter a valid address.");
        }
        while(status = true){
            System.out.print("Customer Password:");
            password = input.nextLine();
            if(password.isEmpty())
                status = false;
            else
                System.out.println("Please enter a valid password.");
        }
        Customer newCustomer = new Customer(id,password,name,dob,contact,address,"0");
        newCustomer.write2file(newCustomer);
        System.out.println();
        System.out.println("Successfully created the user.");
        System.out.println();
    }

    public void updateCustomer(){
        Scanner input = new Scanner(System.in);
        boolean status = true,repeat = true;
        int choice=0;
        String idToUpdate=null;
        String id=null,name=null,dob=null,contact=null,password=null,address=null,wallet=null;

        System.out.println("------------------------------------------------");
        System.out.println("|                UPDATE CUSTOMER               |");
        System.out.println("------------------------------------------------");
        System.out.println();
        while(status = true){
            System.out.print("Enter Customer ID:");
            idToUpdate = input.nextLine();
            if(idToUpdate.isEmpty())
                status = false;
            else
                System.out.println("Input cannot be null.");
        }
        System.out.println();
        SerializationOperation operation = new SerializationOperation("Customer.ser");
        ArrayList<Customer> foundCustomer = operation.searchObjects(id,Customer.class);
        System.out.println("====================================================================================================================================================================================");
        System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "DATE OF BIRTH") + String.format("%-30s", "CONTACT") + String.format("%-30s", "PASSWORD") + String.format("%-30s", "ADDRESS"));
        System.out.println("====================================================================================================================================================================================");
        if (foundCustomer != null) {
            for (Customer customer : foundCustomer) {
                System.out.println(String.format("%-30s", customer.getID()) + String.format("%-30s", customer.getName()) + String.format("%-30s", customer.getDob()) + String.format("%-30s", customer.getContact()) + String.format("%-30s", customer.getPassword()) + String.format("%-30s", customer.getAddress()));
                id= customer.getID();name=customer.getName();dob= customer.getDob();contact= customer.getContact();address= customer.getAddress();password=customer.getPassword();wallet=String.valueOf(customer.getWalletBalance());
            }
            System.out.println();
            while(repeat = true){
                System.out.println("Which would you like to update?");
                System.out.println();
                System.out.println(String.format("%-20s", "1. Name")+String.format("%-20s", "2. Date of Birth"));
                System.out.println(String.format("%-20s", "3. Contact")+String.format("%-20s", "4. Address"));
                System.out.println(String.format("%-20s", "5. Password"));
                System.out.println();
                while(status = true){
                    System.out.print("Enter the number:");
                    try {
                        choice = input.nextInt();
                        if(choice==1 || choice==2 || choice==3 || choice==4 || choice==5)
                            status = false;
                        else {
                            System.out.println("Please enter a valid input.");
                            status = true;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid input.");
                        status = true;
                        input.nextLine();//clear the scanner's buffer
                    }
                }
                System.out.println();
                switch(choice){
                    case 1:
                        while(status = true){
                            System.out.print("Customer Name:");
                            name = input.nextLine();
                            if(name.matches("[a-zA-Z\\s]+")){
                                status = false;
                            }else{
                                System.out.println("Please enter a valid name.");
                            }
                        }
                    case 2:
                        while(status = true){
                            System.out.print("Customer Date of Birth:");
                            dob = input.nextLine();
                            if(dob.isEmpty())
                                status = false;
                            else
                                System.out.println("Please enter a valid date.");
                        }
                    case 3:
                        while(status = true){
                            System.out.print("Customer Contact:");
                            contact = input.nextLine();
                            if(contact.matches("(^01\\d{8}$|^011\\d{8}$)"))
                                status = false;
                            else
                                System.out.println("Please enter a valid contact.");
                        }
                    case 4:
                        while(status = true){
                            System.out.print("Customer Address:");
                            address = input.nextLine();
                            if(address.isEmpty())
                                status = false;
                            else
                                System.out.println("Please enter a valid address.");
                        }
                    case 5:
                        while(status = true){
                            System.out.print("Customer Password:");
                            password = input.nextLine();
                            if(password.isEmpty())
                                status = false;
                            else
                                System.out.println("Please enter a valid password.");
                        }
                }
                System.out.println();
                System.out.println("Do you have anything more to update? Enter 1 to proceed.");
                try {
                    int more = input.nextInt();
                    if(more == 1)
                        repeat = true;
                } catch (InputMismatchException e) {
                    repeat = true;
                    input.nextLine();//clear the scanner's buffer
                }
            }
            operation.updateObject(idToUpdate,new Customer(id,password, name, dob, contact,address,wallet));
            System.out.println("Successfully updated the user.");
        } else {
            System.out.println("(No Customer Record Exist.)");
        }
        System.out.println();
        System.out.println("Back to Main Menu......");
        System.out.println();
    }

    public void deleteCustomer(){
        Scanner input = new Scanner(System.in);
        boolean status = true;
        int choice=0;
        String idToDelete=null;

        System.out.println("------------------------------------------------");
        System.out.println("|                DELETE CUSTOMER               |");
        System.out.println("------------------------------------------------");
        System.out.println();
        while(status = true){
            System.out.print("Enter Customer ID to delete:");
            idToDelete = input.nextLine();
            if(idToDelete.isEmpty())
                status = false;
            else
                System.out.println("Input cannot be null.");
        }
        System.out.println();
        SerializationOperation operation = new SerializationOperation("Customer.ser");
        ArrayList<Customer> foundCustomer = operation.searchObjects(idToDelete,Customer.class);
        System.out.println("====================================================================================================================================================================================");
        System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "DATE OF BIRTH") + String.format("%-30s", "CONTACT") + String.format("%-30s", "PASSWORD") + String.format("%-30s", "ADDRESS"));
        System.out.println("====================================================================================================================================================================================");
        if (foundCustomer != null) {
            for (Customer customer : foundCustomer) {
                System.out.println(String.format("%-30s", customer.getID()) + String.format("%-30s", customer.getName()) + String.format("%-30s", customer.getDob()) + String.format("%-30s", customer.getContact()) + String.format("%-30s", customer.getPassword()) + String.format("%-30s", customer.getAddress()));
            }
            System.out.println();
            System.out.println("Are you sure you want to delete?");
            System.out.println("1. Yes\n2. No");
            System.out.println();
            while(status = true){
                System.out.print("Enter the number:");
                try {
                    choice = input.nextInt();
                    if(choice==1){
                        status = false;
                        operation.deleteObject(idToDelete,Customer.class);
                        System.out.println("Successfully deleted the user.");
                    }
                    else if(choice==2){
                        status = false;
                    }
                    else {
                        System.out.println("Please enter a valid input.");
                        status = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid input.");
                    status = true;
                    input.nextLine();//clear the scanner's buffer
                }
            }
        }else
            System.out.println("(No Customer Record Exist.)");
        System.out.println();
        System.out.println("Back to Main Menu......");
        System.out.println();
    }
}