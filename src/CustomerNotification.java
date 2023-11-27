import java.util.Scanner;

public class CustomerNotification extends Notification{

    private String ID;
    private Customer customer;
    private int code;
    private String objectID;

    public CustomerNotification(String message, Customer customer, int code, String objectID) {
        super(message);
        this.customer = customer;
        this.code = code;
        this.objectID = objectID;
        IDGenerator generator = new IDGenerator("CustomerNotification","CN");
        this.ID = generator.generateID();
    }

    public CustomerNotification(String ID, Customer customer, String message, int code, String objectID){
        super(message);
        this.ID = ID;
        this.customer = customer;
        this.code = code;
        this.objectID = objectID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getCode() {return code;}

    public String getObjectID() {return objectID;}

    public int notifyRunnerNotAvailable(Order order){//code 1
        Scanner input = new Scanner(System.in);
        String method;
        System.out.println("Dear "+customer.getName()+", there is currently no available runner for your order: "+order.getID());
        System.out.println("You may change your order to 'Dine-in' or 'Take Away'. ");
        System.out.println("Please proceed for the change.");
        while(true){
            System.out.println();
            System.out.print("Enter 'Dine-in' or 'Take Away':");
            method = input.nextLine().toLowerCase();
            if(method.equals("dine-in")){
                return 1;
            }else if (method.equals("take away")) {
                return 2;
            }else {
                System.out.println("Please enter the input properly.");
            }
        }
    }

    public void notifyOrderStatusUpdate(Order order){//code 2
        System.out.println("Dear "+customer.getName()+", your order status has been updated!");
        System.out.println("Current status: "+order.getStatus());
    }

    public void transactionReceipt(Credit credit){//code 3
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("|"+String.format("%-29s", "")+"TRANSACTION RECEIPT"+String.format("%-29s", "")+"|");
        System.out.println("|"+String.format("%-29s", "")+"-------------------"+String.format("%-29s", "")+"|");
        System.out.println("|"+String.format("%-77s", "")+"|");
        System.out.println("|"+String.format("%39s", (credit.getTransaction().charAt(0)+"RM "))+String.format("%-38s", credit.getTransaction().substring(1))+"|");
        System.out.println("|"+String.format("%-77s", "")+"|");
        System.out.println("|"+String.format("%-39s", "CUSTOMER NAME")+String.format("%38s", credit.getCustomer().getName())+"|");
        System.out.println("|"+String.format("%-39s", "CUSTOMER ID")+String.format("%38s", credit.getCustomer().getID())+"|");
        System.out.println("|"+String.format("%-39s", "TRANSACTION TYPE")+String.format("%38s", credit.getTransactionType())+"|");
        System.out.println("|"+String.format("%-39s", "DATE")+String.format("%38s", credit.getCurrentDate())+"|");
        System.out.println("|"+String.format("%-39s", "TRANSACTION ID")+String.format("%38s", credit.getID())+"|");
        System.out.println("|"+String.format("%-77s", "")+"|");
        System.out.println("-------------------------------------------------------------------------------");
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s", ID, customer.getID(),message,code);
    }

    @Override
    public void saveNotification() {
        FileOperation file = new FileOperation("CustomerNotification.txt");
        file.writeToFile(this.toString());
    }

    @Override
    public void deleteNotification() {
        FileOperation file = new FileOperation("CustomerNotification.txt");
        file.delete(ID);
    }
}
