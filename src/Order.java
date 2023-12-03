import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order {

    enum Status{
        PendingAccepted,
        VendorAccepted,
        VendorRejected,
        VendorIsReady,
        Completed,
        PendingRunner,
        Delivering,
        Cancelled
    }
    private String ID;
    String currentDate;
    private Customer customer;
    private Vendor vendor;
    private int orderType;// 1- Dine in,2- Take Away, 3- Delivery
    private Status status;
    private OrderReview orderReview;
    private OrderTask orderTask;
    private ArrayList<Cart> shoppingCart;

    public Order(String ID, Customer customer, Vendor vendor, int type, ArrayList<Cart> shoppingCart){
        this.ID = ID;
        this.customer = customer;
        this.vendor = vendor;
        this.shoppingCart = shoppingCart;
        this.orderType = type;
        this.status = Status.PendingAccepted;
        currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public Order(String ID){
        FileOperation file = new FileOperation("CusOrder.txt");
        ArrayList<String> orderData = file.search(ID);
        if (orderData.size() == 1) {
            String[] item = orderData.get(0).split(";");
            this.ID = item[0];
            this.currentDate = item[1];
            this.customer = new Customer(item[2]);
            this.vendor = new Vendor(item[3]);
            this.orderType = Integer.parseInt(item[4]);
            this.status = Status.valueOf(item[5]);
            this.shoppingCart = new ArrayList<>();
            String[] cartItem = item[6].split(",");
            for(int i=0;i<cartItem.length;i+=2){
                shoppingCart.add(new Cart(new MenuItem(cartItem[i]),Integer.parseInt(cartItem[i+1])));
            }
            this.orderReview = new OrderReview("OrderReview.txt");
            if (orderType==3)
                this.orderTask = new OrderTask(this);
        }
    }

    public void setStatus(Status status){this.status = status;}

    public void setOrderType(int orderType) {this.orderType = orderType;}

    public String getID(){return ID;}

    public String getCurrentDate() {return currentDate;}

    public Customer getCustomer(){
        return customer;
    }

    public Vendor getVendor(){
        return vendor;
    }

    public int getOrderType(){return orderType;}

    public Status getStatus(){return status;}

    public ArrayList<Cart> getShoppingCart(){
        return shoppingCart;
    }

    public double getDeliveryFee(){return 5.00;}

    public void setReview(int rate,String review,int type) {
        orderReview.setReview(this,rate,review,type);
    }

    public String getReview(int type) {
        return orderReview.getReview(this,type);
    }

    public boolean getOrderTaskStatus(){return orderTask.getAcceptStatus();}

    public String toString(){
        StringBuilder items = new StringBuilder();
        if (!shoppingCart.isEmpty()) {
            for (Cart item : shoppingCart) {
                items.append(item.getItem().getItemName()).append(",").append(item.getQuantity()).append(",");
            }
            items.setLength(items.length() - 1); // Remove the last comma
        }

        return String.format("%s;%s;%s;%s;%s;%s;%s", ID, currentDate, customer.getID(), vendor.getID(), orderType, status, items.toString());
    }

    public double getTotalPrice(){
        double totalPrice = 0;
        for(Cart item:shoppingCart){
            totalPrice += item.getItem().getItemPrice()* item.getQuantity();
        }
        if(orderType==3){
            totalPrice+=5.00;
        }
        return totalPrice;
    }
}
