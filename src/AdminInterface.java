import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminInterface extends MainInterface {

    public AdminInterface() {
    }

    public static boolean login() {
        String usernameInput = null, passwordInput = null;
        boolean status;
        boolean validate = false;
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
            if ((!usernameInput.equals("admin")) || (!passwordInput.equals("123123"))) {
                System.out.print("Incorrect username and password. Enter 1 to try again.");
                try {
                    repeat = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid integer.");
                    input.nextLine();
                }
                System.out.println();
            }else {
                repeat = 0;
                validate = true;
            }
        }
        return validate;
    }

    public static void AdminMain() {
        System.out.println("------------------------------------------");
        System.out.println("|                MAIN MENU               |");
        System.out.println("------------------------------------------");
        System.out.println();
        System.out.println("Welcome, admin.");
        System.out.println();
        System.out.println("Please choose which you would like to proceed.");
        System.out.println();
        System.out.println(String.format("%-30s", "1. Access Customer") + String.format("%-30s", "2. Access Vendor") + String.format("%-30s", "3. Access Runner"));
        System.out.println();
        System.out.println("(Enter 0 to log out.)");
        System.out.println();
    }

    public static void accessCustomer() {
        System.out.println("------------------------------------------------");
        System.out.println("|                ACCESS CUSTOMER               |");
        System.out.println("------------------------------------------------");
        System.out.println();
        SerializationOperation operation = new SerializationOperation("Customer.ser");
        ArrayList<Customer> customerData = operation.readAllObjects(Customer.class);
        System.out.println("==================================================================================================================================================================================================================");
        System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "DATE OF BIRTH") + String.format("%-30s", "CONTACT") + String.format("%-30s", "PASSWORD") + String.format("%-30s", "ADDRESS")+String.format("%-30s", "WALLET BALANCE"));
        System.out.println("==================================================================================================================================================================================================================");
        if (!customerData.isEmpty()) {
            for (Customer customer : customerData) {
                System.out.println(String.format("%-30s", customer.getID()) + String.format("%-30s", customer.getName()) + String.format("%-30s", customer.getDob()) + String.format("%-30s", customer.getContact()) + String.format("%-30s", customer.getPassword()) + String.format("%-30s", customer.getAddress())+String.format("%-30s", customer.getWalletBalance()));
            }
        } else {
            System.out.println("(No Customer Record Exist.)");
        }
        System.out.println();
        System.out.println("Please choose which you would like to proceed.");
        System.out.println();
        System.out.println(String.format("%-15s", "1. Create") + String.format("%-15s", "2. Update") + String.format("%-15s", "3. Delete") + String.format("%-15s", "4. Top-Up Credit"));
        System.out.println();
        System.out.println("(Enter 0 to Main Menu.)");
        System.out.println();
    }

    public static void createCustomer() {
        Scanner input = new Scanner(System.in);
        boolean status;
        String id,name=null,dob=null,contact=null,password=null,address=null;

        System.out.println("------------------------------------------------");
        System.out.println("|                CREATE CUSTOMER               |");
        System.out.println("------------------------------------------------");
        System.out.println();
        IDGenerator generator = new IDGenerator("Customer.ser","CA");
        id = generator.generateSerializedID();
        System.out.println("Customer ID:"+id);
        status = true;
        while(status){
            System.out.print("Customer Name:");
            name = input.nextLine();
            if(name.matches("[a-zA-Z\\s]+")){
                status = false;
            }else{
                System.out.println("Please enter a valid name.");
            }
        }
        status = true;
        while(status){
            System.out.print("Customer Date of Birth (yyyy-MM-dd):");
            dob = input.nextLine();
            if(dob.matches("\\d{4}-\\d{2}-\\d{2}"))
                status = false;
            else
                System.out.println("Please enter a valid date.");
        }
        status = true;
        while(status){
            System.out.print("Customer Contact:");
            contact = input.nextLine();
            if(contact.matches("(^01\\d{8}$|^011\\d{8}$)"))
                status = false;
            else
                System.out.println("Please enter a valid contact without any symbol.");
        }
        status = true;
        while(status){
            System.out.print("Customer Address:");
            address = input.nextLine();
            if(!address.isEmpty())
                status = false;
            else
                System.out.println("Please enter a valid address.");
        }
        status = true;
        while(status){
            System.out.print("Customer Password:");
            password = input.nextLine();
            if(!password.isEmpty())
                status = false;
            else
                System.out.println("Please enter a valid password.");
        }
        Customer newCustomer = new Customer(id,password,name,dob,contact,address,"0");
        newCustomer.write2file(newCustomer);
        System.out.println();
        System.out.println("Successfully created the user.");
        System.out.println();
        System.out.println("Back to previous page......");
        System.out.println();
    }

    public static void updateCustomer(){
        Scanner input = new Scanner(System.in);
        boolean status,repeat,update=true;
        int choice=0;
        String idToUpdate;
        String id=null,name=null,dob=null,contact=null,password=null,address=null,wallet=null;

        System.out.println("------------------------------------------------");
        System.out.println("|                UPDATE CUSTOMER               |");
        System.out.println("------------------------------------------------");
        System.out.println();
        while(update){
            System.out.print("Enter Customer ID:");
            idToUpdate = input.nextLine();
            if(idToUpdate.isEmpty())
                System.out.println("Input cannot be null.");
            else {
                System.out.println();
                SerializationOperation operation = new SerializationOperation("Customer.ser");
                ArrayList<Customer> foundCustomer = operation.searchObjects(idToUpdate,Customer.class);
                System.out.println("====================================================================================================================================================================================");
                System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "DATE OF BIRTH") + String.format("%-30s", "CONTACT") + String.format("%-30s", "PASSWORD") + String.format("%-30s", "ADDRESS"));
                System.out.println("====================================================================================================================================================================================");
                if (!foundCustomer.isEmpty()) {
                    for (Customer customer : foundCustomer) {
                        System.out.println(String.format("%-30s", customer.getID()) + String.format("%-30s", customer.getName()) + String.format("%-30s", customer.getDob()) + String.format("%-30s", customer.getContact()) + String.format("%-30s", customer.getPassword()) + String.format("%-30s", customer.getAddress()));
                        id= customer.getID();name=customer.getName();dob= customer.getDob();contact= customer.getContact();address= customer.getAddress();password=customer.getPassword();wallet=String.valueOf(customer.getWalletBalance());
                    }
                    System.out.println();
                    repeat = true;
                    while(repeat){
                        System.out.println("Which would you like to update?");
                        System.out.println();
                        System.out.println(String.format("%-20s", "1. Name")+String.format("%-20s", "2. Date of Birth"));
                        System.out.println(String.format("%-20s", "3. Contact")+String.format("%-20s", "4. Address"));
                        System.out.println("5. Password");
                        System.out.println();
                        status=true;
                        while(status){
                            System.out.print("Enter the number:");
                            try {
                                choice = input.nextInt();
                                if(choice==1 || choice==2 || choice==3 || choice==4 || choice==5) {
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
                                    System.out.println("Customer Name:"+name);
                                    System.out.print("New Customer Name:");
                                    String newName = input.nextLine();
                                    if (newName.isEmpty()) {
                                        System.out.println("Input cannot be empty.");
                                    }else if (newName.equals(name)) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    }else if (newName.matches("[a-zA-Z\\s]+")) {
                                        status = false; // Exit loop if name format is valid
                                        name = newName;
                                        System.out.println("The updated information has been stored.");
                                    }else {
                                        System.out.println("Please enter a valid name.");
                                    }
                                }
                                break;
                            case 2:
                                status = true;
                                while(status){
                                    System.out.println("Customer Date of Birth (yyyy-MM-dd):"+dob);
                                    System.out.print("New Customer Date of Birth (yyyy-MM-dd):");
                                    String newDob = input.nextLine();
                                    if (newDob.equals(dob)) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    }else if(newDob.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                        status = false;
                                        dob = newDob;
                                        System.out.println("The updated information has been stored.");
                                    }else
                                        System.out.println("Please enter a valid date.");
                                }
                                break;
                            case 3:
                                status = true;
                                while(status){
                                    System.out.println("Customer Contact:"+contact);
                                    System.out.print("New Customer Contact:");
                                    String newContact = input.nextLine();
                                    if (newContact.equals(contact)){
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    }else if(newContact.matches("(^01\\d{8}$|^011\\d{8}$)")) {
                                        status = false;
                                        contact = newContact;
                                        System.out.println("The updated information has been stored.");
                                    } else
                                        System.out.println("Please enter a valid contact.");
                                }
                                break;
                            case 4:
                                status = true;
                                while(status){
                                    System.out.println("Customer Address:"+address);
                                    System.out.print("New Customer Address:");
                                    String newAddress = input.nextLine();
                                    if(newAddress.isEmpty())
                                        System.out.println("Please enter a valid address.");
                                    else if (newAddress.equals(address)) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    }else {
                                        status = false;
                                        address = newAddress;
                                        System.out.println("The updated information has been stored.");
                                    }
                                }
                                break;
                            case 5:
                                status = true;
                                while(status){
                                    System.out.println("Customer Password:"+password);
                                    System.out.print("New Customer Password:");
                                    String newPassword = input.nextLine();
                                    if(newPassword.isEmpty())
                                        System.out.println("Please enter a valid password.");
                                    else if (newPassword.equals(password)) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    }else {
                                        status = false;
                                        password = newPassword;
                                        System.out.println("The updated information has been stored.");
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
                    operation.updateObject(idToUpdate,new Customer(id,password, name, dob, contact,address,wallet));
                    System.out.println();
                    System.out.println("Successfully updated the user.");
                    update=false;
                } else {
                    System.out.println("(No Customer Record Exist.)");
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
        System.out.println();
        System.out.println("Back to previous page......");
        System.out.println();
    }

    public static void deleteCustomer(){
        Scanner input = new Scanner(System.in);
        boolean status,repeat;
        int choice;
        String idToDelete;

        System.out.println("------------------------------------------------");
        System.out.println("|                DELETE CUSTOMER               |");
        System.out.println("------------------------------------------------");
        System.out.println();
        repeat = true;
        while(repeat){
            System.out.print("Enter Customer ID to delete:");
            idToDelete = input.nextLine();
            if(idToDelete.isEmpty())
                System.out.println("Input cannot be null.");
            else{
                System.out.println();
                SerializationOperation operation = new SerializationOperation("Customer.ser");
                ArrayList<Customer> foundCustomer = operation.searchObjects(idToDelete,Customer.class);
                System.out.println("====================================================================================================================================================================================");
                System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "DATE OF BIRTH") + String.format("%-30s", "CONTACT") + String.format("%-30s", "PASSWORD") + String.format("%-30s", "ADDRESS"));
                System.out.println("====================================================================================================================================================================================");
                if (!foundCustomer.isEmpty()) {
                    for (Customer customer : foundCustomer) {
                        System.out.println(String.format("%-30s", customer.getID()) + String.format("%-30s", customer.getName()) + String.format("%-30s", customer.getDob()) + String.format("%-30s", customer.getContact()) + String.format("%-30s", customer.getPassword()) + String.format("%-30s", customer.getAddress()));
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
                                operation.deleteObject(idToDelete,Customer.class);
                                System.out.println("Successfully deleted the user.");
                                repeat=false;
                            }
                            else if(choice==2){
                                status = false;
                                System.out.print("Do you want to delete other customer? Enter 1 to re-try.");
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
                    System.out.println("(No Customer Record Exist.)");
                    System.out.println();
                    System.out.print("Do you want to delete other customer? Enter 1 to re-try.");
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
        System.out.println();
        System.out.println("Back to previous page......");
        System.out.println();
    }

    public static void topUpCustomer(){
        Scanner input = new Scanner(System.in);
        boolean status,repeat;
        double amount;
        String idToReload;
        Customer cus = null;

        System.out.println("------------------------------------------------");
        System.out.println("|                 TOP-UP CREDIT                |");
        System.out.println("------------------------------------------------");
        System.out.println();
        repeat=true;
        while(repeat){
            System.out.print("Enter Customer ID to top-up:");
            idToReload = input.nextLine();
            if(idToReload.isEmpty())
                System.out.println("Input cannot be null.");
            else {
                System.out.println();
                SerializationOperation operation = new SerializationOperation("Customer.ser");
                ArrayList<Customer> foundCustomer = operation.searchObjects(idToReload,Customer.class);
                System.out.println("==========================================================================================");
                System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "WALLET BALANCE"));
                System.out.println("==========================================================================================");
                if (!foundCustomer.isEmpty()) {
                    for (Customer customer : foundCustomer) {
                        System.out.println(String.format("%-30s", customer.getID()) + String.format("%-30s", customer.getName()) + String.format("%-30s", customer.getWalletBalance()) );
                        cus = customer;
                    }
                    System.out.println();
                    status = true;
                    while(status){
                        System.out.print("Enter the amount to top-up:");
                        try {
                            amount = input.nextDouble();
                            System.out.println();
                            Credit credit = new Credit(cus);
                            credit.addAmount(amount,1);
                            CustomerNotification notification = new CustomerNotification("Successfully top up credit!",cus,3);
                            notification.saveNotification();
                            status = false;
                            System.out.println("Successfully top-up the amount. \nThe transaction receipt has been sent to customer account.");
                            repeat = false;
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid input.");
                            status = true;
                            input.nextLine();//clear the scanner's buffer
                        }
                    }
                }else {
                    System.out.println("(No Customer Record Exist.)");
                    System.out.println();
                    System.out.print("Enter 1 to re-try again.");
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
        System.out.println();
        System.out.println("Back to previous page......");
        System.out.println();
    }

    public static void accessVendor() {
        System.out.println("------------------------------------------------");
        System.out.println("|                 ACCESS VENDOR                |");
        System.out.println("------------------------------------------------");
        System.out.println();
        SerializationOperation operation = new SerializationOperation("Vendor.ser");
        ArrayList<Vendor> vendorData = operation.readAllObjects(Vendor.class);
        System.out.println("======================================================================================================================================================");
        System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "CATEGORY") + String.format("%-30s", "PASSWORD") + String.format("%-30s", "ADDRESS"));
        System.out.println("======================================================================================================================================================");
        if (!vendorData.isEmpty()) {
            for (Vendor vendor : vendorData) {
                System.out.println(String.format("%-30s", vendor.getID()) + String.format("%-30s", vendor.getVendorName()) + String.format("%-30s", vendor.getCategory()) +  String.format("%-30s", vendor.getPassword()) + String.format("%-30s", vendor.getAddress()));
            }
        } else {
            System.out.println("(No Vendor Record Exist.)");
        }
        System.out.println();
        System.out.println("Please choose which you would like to proceed.");
        System.out.println();
        System.out.println(String.format("%-15s", "1. Create") + String.format("%-15s", "2. Update") + String.format("%-15s", "3. Delete"));
        System.out.println();
        System.out.println("(Enter 0 to Main Menu.)");
        System.out.println();
    }

    public static void createVendor() {
        Scanner input = new Scanner(System.in);
        boolean status = true;
        String id,name=null,category=null,password=null,address=null;

        System.out.println("------------------------------------------------");
        System.out.println("|                 CREATE VENDOR                |");
        System.out.println("------------------------------------------------");
        System.out.println();
        IDGenerator generator = new IDGenerator("Vendor.ser","VA");
        id = generator.generateSerializedID();
        System.out.println("Vendor ID:"+id);
        while(status){
            System.out.print("Vendor Name:");
            name = input.nextLine();
            if(!name.isEmpty()){
                status = false;
            }else{
                System.out.println("Please enter a valid name.");
            }
        }
        status = true;
        while(status){
            System.out.print("Vendor Category:");
            category = input.nextLine();
            if(category.matches("[a-zA-Z\\s]+"))
                status = false;
            else
                System.out.println("Please enter a valid category.");
        }
        status = true;
        while(status){
            System.out.print("Vendor Address:");
            address = input.nextLine();
            if(!address.isEmpty())
                status = false;
            else
                System.out.println("Please enter a valid address.");
        }
        status = true;
        while(status){
            System.out.print("Vendor Password:");
            password = input.nextLine();
            if(!password.isEmpty())
                status = false;
            else
                System.out.println("Please enter a valid password.");
        }
        Vendor newVendor = new Vendor(id,password,name,category,address);
        newVendor.write2file(newVendor);
        System.out.println();
        System.out.println("Successfully created the user.");
        System.out.println();
        System.out.println("Back to previous page......");
        System.out.println();
    }

    public static void updateVendor(){
        Scanner input = new Scanner(System.in);
        boolean status ,repeat, update = true;
        int choice=0;
        String idToUpdate;
        String id=null,name=null,category=null,password=null,address=null;

        System.out.println("------------------------------------------------");
        System.out.println("|                 UPDATE VENDOR                |");
        System.out.println("------------------------------------------------");
        System.out.println();
        while(update){
            System.out.print("Enter Vendor ID:");
            idToUpdate = input.nextLine();
            if(idToUpdate.isEmpty())
                System.out.println("Input cannot be null.");
            else{
                System.out.println();
                SerializationOperation operation = new SerializationOperation("Vendor.ser");
                ArrayList<Vendor> foundVendor = operation.searchObjects(idToUpdate,Vendor.class);
                System.out.println("======================================================================================================================================================");
                System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "CATEGORY") + String.format("%-30s", "PASSWORD") + String.format("%-30s", "ADDRESS"));
                System.out.println("======================================================================================================================================================");
                if (!foundVendor.isEmpty()) {
                    for (Vendor vendor : foundVendor) {
                        System.out.println(String.format("%-30s", vendor.getID()) + String.format("%-30s", vendor.getVendorName()) + String.format("%-30s", vendor.getCategory()) +  String.format("%-30s", vendor.getPassword()) + String.format("%-30s", vendor.getAddress()));
                        id= vendor.getID();name=vendor.getVendorName();category=vendor.getCategory();address= vendor.getAddress();password=vendor.getPassword();
                    }
                    System.out.println();
                    repeat = true;
                    while(repeat){
                        System.out.println("Which would you like to update?");
                        System.out.println();
                        System.out.println(String.format("%-20s", "1. Name")+String.format("%-20s", "2. Category"));
                        System.out.println(String.format("%-20s", "3. Password")+String.format("%-20s", "4. Address"));
                        System.out.println();
                        status = true;
                        while(status){
                            System.out.print("Enter the number:");
                            try {
                                choice = input.nextInt();
                                if(choice==1 || choice==2 || choice==3 || choice==4)
                                    status = false;
                                else {
                                    System.out.println("Please enter a valid input.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Please enter a valid input.");
                            }
                            input.nextLine();//clear the scanner's buffer
                        }
                        System.out.println();
                        switch(choice){
                            case 1:
                                status = true;
                                while(status){
                                    System.out.println("Vendor Name:"+name);
                                    System.out.print("New Vendor Name:");
                                    String newName = input.nextLine();
                                    if (newName.equals(name)) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    } else if(!newName.isEmpty()){
                                        status = false;
                                        name = newName;
                                        System.out.println("The updated information has been stored.");
                                    } else{
                                        System.out.println("Please enter a valid name.");
                                    }
                                }
                                break;
                            case 2:
                                status = true;
                                while(status){
                                    System.out.println("Vendor Category:"+category);
                                    System.out.print("New Vendor Category:");
                                    String newCategory = input.nextLine();
                                    if (newCategory.equals(category)) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    }else if(newCategory.matches("[a-zA-Z\\s]+")) {
                                        status = false;
                                        category = newCategory;
                                        System.out.println("The updated information has been stored.");
                                    }else
                                        System.out.println("Please enter a valid category.");
                                }
                                break;
                            case 3:
                                status = true;
                                while(status){
                                    System.out.println("Vendor Password:"+password);
                                    System.out.print("New Vendor Password:");
                                    String newPassword = input.nextLine();
                                    if (newPassword.equals(password)) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    } else if(!newPassword.isEmpty()){
                                        status = false;
                                        password = newPassword;
                                        System.out.println("The updated information has been stored.");
                                    } else
                                        System.out.println("Please enter a valid password.");
                                }
                                break;
                            case 4:
                                status = true;
                                while(status){
                                    System.out.println("Vendor Address:"+address);
                                    System.out.print("New Vendor Address:");
                                    String newAddress = input.nextLine();
                                    if (newAddress.equals(address)) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    } else if(!newAddress.isEmpty()) {
                                        status = false;
                                        address = newAddress;
                                        System.out.println("The updated information has been stored.");
                                    }else
                                        System.out.println("Please enter a valid address.");
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
                    }
                    operation.updateObject(idToUpdate,new Vendor(id,password,name,category,address));
                    System.out.println();
                    System.out.println("Successfully updated the user.");
                    update = false;
                } else {
                    System.out.println("(No Vendor Record Exist.)");
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
        System.out.println();
        System.out.println("Back to previous page......");
        System.out.println();
    }

    public static void deleteVendor(){
        Scanner input = new Scanner(System.in);
        boolean status = true,repeat = true;
        int choice;
        String idToDelete;

        System.out.println("------------------------------------------------");
        System.out.println("|                 DELETE VENDOR                |");
        System.out.println("------------------------------------------------");
        System.out.println();
        while(repeat){
            System.out.print("Enter Vendor ID to delete:");
            idToDelete = input.nextLine();
            if(idToDelete.isEmpty())
                System.out.println("Input cannot be null.");
            else{
                System.out.println();
                SerializationOperation operation = new SerializationOperation("Vendor.ser");
                ArrayList<Vendor> foundVendor = operation.searchObjects(idToDelete,Vendor.class);
                System.out.println("======================================================================================================================================================");
                System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "CATEGORY") + String.format("%-30s", "PASSWORD") + String.format("%-30s", "ADDRESS"));
                System.out.println("======================================================================================================================================================");
                if (!foundVendor.isEmpty()) {
                    for (Vendor vendor : foundVendor) {
                        System.out.println(String.format("%-30s", vendor.getID()) + String.format("%-30s", vendor.getVendorName()) + String.format("%-30s", vendor.getCategory()) +  String.format("%-30s", vendor.getPassword()) + String.format("%-30s", vendor.getAddress()));
                    }
                    System.out.println();
                    System.out.println("Are you sure you want to delete?");
                    System.out.println("1. Yes\n2. No");
                    System.out.println();
                    while(status){
                        System.out.print("Enter the number:");
                        try {
                            choice = input.nextInt();
                            if(choice==1){
                                status = false;
                                operation.deleteObject(idToDelete,Vendor.class);
                                System.out.println("Successfully deleted the user.");
                                repeat = false;
                            }
                            else if(choice==2){
                                status = false;
                                System.out.print("Do you want to delete other vendors? Enter 1 to re-try.");
                                try {
                                    int retry = input.nextInt();
                                    if (retry!=1)
                                        repeat=false;
                                    input.nextLine();
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
                    System.out.println("(No Vendor Record Exist.)");
                    System.out.print("Do you want to delete other vendors? Enter 1 to re-try.");
                    try {
                        int retry=input.nextInt();
                        if (retry!=1)
                            repeat=false;
                    } catch (InputMismatchException e) {
                        repeat = false;
                    }
                    input.nextLine();
                }
            }
        }
        System.out.println();
        System.out.println("Back to previous page......");
        System.out.println();
    }

    public static void accessRunner() {
        System.out.println("------------------------------------------------");
        System.out.println("|                 ACCESS RUNNER                |");
        System.out.println("------------------------------------------------");
        System.out.println();
        SerializationOperation operation = new SerializationOperation("Runner.ser");
        ArrayList<Runner> runnerData = operation.readAllObjects(Runner.class);
        System.out.println("========================================================================================================================");
        System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "CONTACT") + String.format("%-30s", "PASSWORD"));
        System.out.println("========================================================================================================================");
        if (!runnerData.isEmpty()) {
            for (Runner runner : runnerData) {
                System.out.println(String.format("%-30s", runner.getID()) + String.format("%-30s", runner.getRunnerName()) + String.format("%-30s", runner.getContact()) +  String.format("%-30s", runner.getPassword()));
            }
        } else {
            System.out.println("(No Runner Record Exist.)");
        }
        System.out.println();
        System.out.println("Please choose which you would like to proceed.");
        System.out.println();
        System.out.println(String.format("%-15s", "1. Create") + String.format("%-15s", "2. Update") + String.format("%-15s", "3. Delete"));
        System.out.println();
        System.out.println("(Enter 0 to Main Menu.)");
        System.out.println();
    }

    public static void createRunner() {
        Scanner input = new Scanner(System.in);
        boolean status = true;
        String id,name=null,contact=null,password=null;

        System.out.println("------------------------------------------------");
        System.out.println("|                 CREATE RUNNER                |");
        System.out.println("------------------------------------------------");
        System.out.println();
        IDGenerator generator = new IDGenerator("Runner.ser","RA");
        id = generator.generateSerializedID();
        System.out.println("Runner ID: "+id);
        while(status){
            System.out.print("Runner Name:");
            name = input.nextLine();
            if(name.matches("[a-zA-Z\\s]+")){
                status = false;
            }else{
                System.out.println("Please enter a valid name.");
            }
        }
        status = true;
        while(status){
            System.out.print("Runner Contact:");
            contact = input.nextLine();
            if(contact.matches("(^01\\d{8}$|^011\\d{8}$)"))
                status = false;
            else
                System.out.println("Please enter a valid contact.");
        }
        status = true;
        while(status){
            System.out.print("Runner Password:");
            password = input.nextLine();
            if(!password.isEmpty())
                status = false;
            else
                System.out.println("Please enter a valid password.");
        }
        Runner newRunner = new Runner(id,password,name,contact);
        newRunner.write2file(newRunner);
        System.out.println();
        System.out.println("Successfully created the user.");
        System.out.println();
        System.out.println("Back to previous page......");
        System.out.println();
    }

    public static void updateRunner(){
        Scanner input = new Scanner(System.in);
        boolean status,repeat, update = true;
        int choice=0;
        String idToUpdate;
        String id=null,name=null,contact=null,password=null;

        System.out.println("------------------------------------------------");
        System.out.println("|                 UPDATE RUNNER                |");
        System.out.println("------------------------------------------------");
        System.out.println();
        while(update){
            System.out.print("Enter Runner ID:");
            idToUpdate = input.nextLine();
            if(idToUpdate.isEmpty())
                System.out.println("Input cannot be null.");

            else{
                System.out.println();
                SerializationOperation operation = new SerializationOperation("Runner.ser");
                ArrayList<Runner> foundRunner = operation.searchObjects(idToUpdate,Runner.class);
                System.out.println("========================================================================================================================");
                System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "CONTACT") + String.format("%-30s", "PASSWORD"));
                System.out.println("========================================================================================================================");
                if (!foundRunner.isEmpty()) {
                    for (Runner runner : foundRunner) {
                        System.out.println(String.format("%-30s", runner.getID()) + String.format("%-30s", runner.getRunnerName()) + String.format("%-30s", runner.getContact()) +  String.format("%-30s", runner.getPassword()));
                        id= runner.getID();name=runner.getRunnerName();contact=runner.getContact();password=runner.getPassword();
                    }
                    System.out.println();
                    repeat = true;
                    while(repeat){
                        System.out.println("Which would you like to update?");
                        System.out.println();
                        System.out.println(String.format("%-20s", "1. Name")+String.format("%-20s", "2. Contact"));
                        System.out.println("3. Password");
                        System.out.println();
                        status = true;
                        while(status){
                            System.out.print("Enter the number:");
                            try {
                                choice = input.nextInt();
                                if(choice==1 || choice==2 || choice==3)
                                    status = false;
                                else {
                                    System.out.println("Please enter a valid input.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Please enter a valid input.");
                            }
                            input.nextLine();//clear the scanner's buffer
                        }
                        System.out.println();
                        switch(choice){
                            case 1:
                                status = true;
                                while(status){
                                    System.out.println("Runner Name:"+name);
                                    System.out.print("New Runner Name:");
                                    String newName = input.nextLine();
                                    if (newName.equals(name)) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    }else if(newName.matches("[a-zA-Z\\s]+")){
                                        status = false;
                                        name = newName;
                                        System.out.println("The updated information has been stored.");
                                    }else{
                                        System.out.println("Please enter a valid name.");
                                    }
                                }
                                break;
                            case 2:
                                status = true;
                                while(status){
                                    System.out.println("Runner Contact:"+contact);
                                    System.out.print("New Runner Contact:");
                                    String newContact = input.nextLine();
                                    if (newContact.equals(contact)) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    }else if(contact.matches("(^01\\d{8}$|^011\\d{8}$)")) {
                                        status = false;
                                        contact = newContact;
                                        System.out.println("The updated information has been stored.");
                                    } else
                                        System.out.println("Please enter a valid contact.");
                                }
                                break;
                            case 3:
                                status = true;
                                while(status){
                                    System.out.println("Runner Password:"+password);
                                    System.out.print("New Runner Password:");
                                    String newPassword = input.nextLine();
                                    if (newPassword.equals(password)) {
                                        status = false;
                                        System.out.println("The update was not processed. The new value matches the existing value.");
                                    }else if(!password.isEmpty()) {
                                        status = false;
                                        password = newPassword;
                                        System.out.println("The updated information has been stored.");
                                    }else
                                        System.out.println("Please enter a valid password.");
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
                    }
                    operation.updateObject(idToUpdate,new Runner(id,password,name,contact));
                    System.out.println();
                    System.out.println("Successfully updated the user.");
                    update = false;
                } else {
                    System.out.println("(No Runner Record Exist.)");
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
        System.out.println();
        System.out.println("Back to previous page......");
        System.out.println();
    }

    public static void deleteRunner(){
        Scanner input = new Scanner(System.in);
        boolean status = true, repeat = true;
        int choice;
        String idToDelete;

        System.out.println("------------------------------------------------");
        System.out.println("|                 DELETE RUNNER                |");
        System.out.println("------------------------------------------------");
        System.out.println();
        while(repeat){
            System.out.print("Enter Runner ID to delete:");
            idToDelete = input.nextLine();
            if(idToDelete.isEmpty())
                System.out.println("Input cannot be null.");
            else{
                System.out.println();
                SerializationOperation operation = new SerializationOperation("Runner.ser");
                ArrayList<Runner> foundRunner = operation.searchObjects(idToDelete,Runner.class);
                System.out.println("========================================================================================================================");
                System.out.println(String.format("%-30s", "ID") + String.format("%-30s", "NAME") + String.format("%-30s", "CONTACT") + String.format("%-30s", "PASSWORD"));
                System.out.println("========================================================================================================================");
                if (!foundRunner.isEmpty()) {
                    for (Runner runner : foundRunner) {
                        System.out.println(String.format("%-30s", runner.getID()) + String.format("%-30s", runner.getRunnerName()) + String.format("%-30s", runner.getContact()) +  String.format("%-30s", runner.getPassword()));
                    }
                    System.out.println();
                    System.out.println("Are you sure you want to delete?");
                    System.out.println("1. Yes\n2. No");
                    System.out.println();
                    while(status){
                        System.out.print("Enter the number:");
                        try {
                            choice = input.nextInt();
                            if(choice==1){
                                status = false;
                                operation.deleteObject(idToDelete,Runner.class);
                                System.out.println("Successfully deleted the user.");
                                repeat = false;
                            }
                            else if(choice==2){
                                status = false;
                                System.out.print("Do you want to delete other vendors? Enter 1 to re-try.");
                                try {
                                    int retry = input.nextInt();
                                    if (retry!=1)
                                        repeat=false;
                                    input.nextLine();
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
                    System.out.println("(No Runner Record Exist.)");
                    System.out.print("Do you want to delete other vendors? Enter 1 to re-try.");
                    try {
                        int retry=input.nextInt();
                        if (retry!=1)
                            repeat=false;
                    } catch (InputMismatchException e) {
                        repeat = false;
                    }
                    input.nextLine();
                }
            }
        }
        System.out.println();
        System.out.println("Back to previous page......");
        System.out.println();
    }
}