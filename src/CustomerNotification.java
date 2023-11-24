public class CustomerNotification extends Notification{

    private String ID;
    private Customer customer;
    private int code;
    private String details;

    public CustomerNotification(String message, Customer customer, int code) {
        super(message);
        this.customer = customer;
        this.code = code;
        IDGenerator generator = new IDGenerator("CustomerNotification","CN");
        this.ID = generator.generateID();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void notifyRunnerNotAvailable(Order order){//code 1
        System.out.println("Dear "+customer.getName()+", there is currently no available runner for your order: "+order.getID());
        System.out.println("You may change your order to 'Dine-in' or 'Take Away'. ");
        System.out.println("Please proceed to your order for the change.");
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
        return String.format("%s,%s,%s,%s", ID, customer.getID(),message,code);
    }

    @Override
    public void saveNotification() {
        FileOperation file = new FileOperation("CustomerNotification.txt");
        file.writeToFile(this.toString());
    }
}
