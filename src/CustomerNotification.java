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
