import java.util.Scanner;

public class VendorNotification extends Notification{

    private String ID;
    private Vendor vendor;
    private int code;

    public VendorNotification(String message,Vendor vendor,int code){
        super(message);
        this.vendor = vendor;
        IDGenerator generator = new IDGenerator("VendorNotification","VN");
        this.ID = generator.generateID();
    }

    public Vendor getVendor(){
        return vendor;
    }

    public String notifyComingOrder(Order order){//code 1
        Scanner input = new Scanner(System.in);
        System.out.println("There is an upcoming order by "+order.getCustomer().getName()+".");
        System.out.println("Order item:");
        for(Cart item:order.getShoppingCart()){
            System.out.println(item.getItem().getItemName()+" x "+item.getQuantity());
        }
        System.out.println();
        System.out.println("Please choose 1. Accept / 2. Reject.");
        System.out.print("Enter the number: ");
        return input.nextLine();
    }

    public String toString() {
        return String.format("%s,%s,%s,%s", ID, vendor.getID(),message,code);
    }

    @Override
    public void saveNotification() {
        FileOperation file = new FileOperation("VendorNotification.txt");
        file.writeToFile(this.toString());
    }
}
