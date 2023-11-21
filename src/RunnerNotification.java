import java.util.Scanner;

public class RunnerNotification extends Notification{

    private String ID;
    private Runner runner;
    private int code;

    public RunnerNotification(String message,Runner runner,int code){
        super(message);
        this.runner = runner;
        this.code = code;
        IDGenerator generator = new IDGenerator("CustomerNotification","CN");
        this.ID = generator.generateID();
    }

    public Runner getRunner(){
        return runner;
    }

    public String notifyComingTask(Order order){//code 1
        Scanner input = new Scanner(System.in);
        System.out.println("There is an upcoming task by "+order.getCustomer().getName()+".");
        System.out.println("Order item:");
        for(Cart item:order.getShoppingCart()){
            System.out.println(item.getItem().getItemName()+" x "+item.getQuantity());
        }
        System.out.println();
        System.out.println("Vendor detail:");
        System.out.println(order.getVendor().getVendorName());
        System.out.println(order.getVendor().getAddress());
        System.out.println("Please choose 1. Accept / 2. Reject.");
        System.out.print("Enter the number: ");
        return input.nextLine();
    }

    public String toString() {
        return String.format("%s,%s,%s,%s", ID, runner.getID(),message,code);
    }

    @Override
    public void saveNotification() {
        FileOperation file = new FileOperation("RunnerNotification.txt");
        file.writeToFile(this.toString());
    }
}
