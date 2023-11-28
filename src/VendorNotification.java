import java.util.InputMismatchException;
import java.util.Scanner;

public class VendorNotification extends Notification{

    private String ID,objectID;
    private Vendor vendor;
    private int code;

    public VendorNotification(String message,Vendor vendor,int code,String objectID){
        super(message);
        this.vendor = vendor;
        this.code = code;
        this.objectID = objectID;
        IDGenerator generator = new IDGenerator("VendorNotification","VN");
        this.ID = generator.generateID();
    }

    public VendorNotification(String ID,Vendor vendor,String message,int code,String objectID){
        super(message);
        this.vendor = vendor;
        this.ID = ID;
        this.code = code;
        this.objectID = objectID;
    }

    public Vendor getVendor(){
        return vendor;
    }

    public int getCode() {return code;}

    public String getObjectID() {return objectID;}

    public int notifyComingOrder(Order order){//code 1
        Scanner input = new Scanner(System.in);
        System.out.println("There is an upcoming order by "+order.getCustomer().getName()+".");
        System.out.println("Order item:");
        for(Cart item:order.getShoppingCart()){
            System.out.println(item.getItem().getItemName()+" x "+item.getQuantity());
        }
        System.out.println();
        System.out.println("Please choose 1. Accept / 2. Reject.");
        System.out.print("Enter the number: ");

        int choice = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter the number: ");
            try {
                choice = input.nextInt();
                if (choice == 1 || choice == 2) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter either 1 (Accept) or 2 (Reject).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
            }
        }
        return choice;
    }

    public void notifyCancelOrder(Order order){//code 2
        System.out.println("Dear vendor,");
        System.out.println();
        System.out.println("The order "+order.getID()+ " has been canceled by "+order.getCustomer().getName()+".");
        System.out.println("Order item:");
        for(Cart item:order.getShoppingCart()){
            System.out.println(item.getItem().getItemName()+" x "+item.getQuantity());
        }
        System.out.println();
    }

    public void notifyOrderChange(Order order){//code 3
        System.out.println("Dear vendor,");
        System.out.println();
        System.out.println("The serving method of order "+order.getID()+ " has been changed to "+order.getOrderType()+".");
        System.out.println("Order item:");
        for(Cart item:order.getShoppingCart()){
            System.out.println(item.getItem().getItemName()+" x "+item.getQuantity());
        }
        System.out.println();
    }

    public String toString() {
        return String.format("%s,%s,%s,%s", ID, vendor.getID(),message,code);
    }

    @Override
    public void saveNotification() {
        FileOperation file = new FileOperation("VendorNotification.txt");
        file.writeToFile(this.toString());
    }

    @Override
    public void deleteNotification() {

    }
}
