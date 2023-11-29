import java.util.InputMismatchException;
import java.util.Scanner;

public class RunnerNotification extends Notification{

    private String ID,objectID;
    private Runner runner;
    private int code;

    public RunnerNotification(String message,Runner runner,int code,String objectID){
        super(message);
        this.runner = runner;
        this.code = code;
        this.objectID = objectID;
        IDGenerator generator = new IDGenerator("CustomerNotification","CN");
        this.ID = generator.generateID();
    }

    public RunnerNotification(String id,String message,Runner runner,int code,String objectID){
        super(message);
        this.runner = runner;
        this.code = code;
        this.objectID = objectID;
        this.ID = id;
    }

    public Runner getRunner(){
        return runner;
    }

    public int getCode() {return code;}

    public String getObjectID() {return objectID;}

    public int notifyComingTask(Order order){//code 1
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

    public String toString() {
        return String.format("%s;%s;%s;%s;%s", ID, runner.getID(),message,code,objectID);
    }

    @Override
    public void saveNotification() {
        FileOperation file = new FileOperation("RunnerNotification.txt");
        file.writeToFile(this.toString());
    }

    @Override
    public void deleteNotification() {
        FileOperation file = new FileOperation("RunnerNotification.txt");
        file.delete(ID);
    }
}
