import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    private String ID,name,dob,contact,password,address;
    private double walletBalance;

    private ArrayList<Notification> notifications;
    private ArrayList<Order>orders;
    private ArrayList<Cart>cartItems;
    private static int runnerCounter=0;

    public Customer(String userID){
        setDetails(userID);
    }//for credit use (Customer CA = new Customer("CA1"); Credit userCredit = new Credit(CA); userCredit.addAmount();CA.write2file();userCredit.write2file();

    public Customer(String userID,String password){
        FileOperation file = new FileOperation("Customer.txt");
        if(file.checkUserCredential(userID,password)){
            setDetails(userID);
        }
        this.orders = new ArrayList<>();
        this.cartItems = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }//for login

    public Customer(String ID,String password,String name,String dob,String contact,String address,String walletBalance){
        setID(ID);setPassword(password);setName(name);setDob(dob);setContact(contact);setAddress(address);setWalletBalance(walletBalance);
    }//for register //obj.write2file(obj.toString());

    public String getID() {return ID;}

    public void setID(String ID) {this.ID = ID;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDob() {return dob;}

    public void setDob(String dob) {this.dob = dob;}

    public String getContact() {return contact;}

    public void setContact(String contact) {this.contact = contact;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public double getWalletBalance() {return walletBalance;}

    public void setWalletBalance(String walletBalance) {this.walletBalance = Double.parseDouble(walletBalance);}

    public ArrayList<Order> getOrders() {return orders;}

    public void setDetails(String userID){
        FileOperation file = new FileOperation("Customer.txt");
        ArrayList<String> userData = file.search(userID);
        if (userData.size() == 1) {
            String[] item = userData.get(0).split(";");
            setID(item[0]);
            setPassword(item[1]);
            setName(item[2]);
            setDob(item[3]);
            setContact(item[4]);
            setAddress(item[5]);
            setWalletBalance(item[6]);
        }else
            System.out.println("User not found!");
    }

    public String toString(){
        return String.format("%s;%s;%s;%s;%s;%s", ID, password, name, dob, contact,address,walletBalance);
    }

    public void write2file(Customer customer){
        SerializationOperation operation = new SerializationOperation("Customer.ser");
        operation.addObject(customer);
    }

    public void write2OrderFile(String input){
        FileOperation file = new FileOperation("CusOrder.txt");
        file.writeToFile(input);
    }

    public void addToCart(MenuItem item,int quantity){
        for(Cart cartItem:cartItems){
            if(cartItem.equals(item)){
                cartItem.setQuantity(cartItem.getQuantity()+quantity);
                return;
            }
        }
        Cart items = new Cart(item,quantity);
        cartItems.add(items);
    }

    public void removeFromCart(MenuItem item,int quantity){
        for(Cart cartItem:cartItems){
            if(cartItem.equals(item)){
                if(quantity>= cartItem.getQuantity()) {
                    cartItems.remove(cartItem);
                }else{
                    cartItem.setQuantity(cartItem.getQuantity()-quantity);
                }
                return;
            }
        }
    }

    public void placeOrder(Vendor vendor, int type){
        runnerCounter=0;
        IDGenerator generator = new IDGenerator("CusOrder.txt","CO");
        String orderID = generator.generateID();
        Order ord = new Order(orderID,this,vendor,type,cartItems);
        orders.add(ord);
        VendorNotification notification = new VendorNotification("You have new order!",ord.getVendor(),1);
        notification.saveNotification();
        write2OrderFile(ord.toString());
    }

    public void printCart(){
        int counter = 0;
        double totalPrice = 0;
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println(String.format("%-5s", "No.")+String.format("%-30s", "Item Name")+String.format("%-30s", "Price")+String.format("%-30s", "Quantity"));
        System.out.println("------------------------------------------------------------------------------------------");
        for(Cart item:cartItems){
            counter++;
            System.out.println(String.format(String.format("%-5s", counter)+String.format("%-30s", item.getItem().getItemName())+String.format("%-30s", item.getItem().getItemPrice())+String.format("%-30s", item.getQuantity())));
            totalPrice+= (item.getItem().getItemPrice()*item.getQuantity());
        }
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Total Price (RM) : "+totalPrice);
    }

    public void cancelVendorOrder(Vendor vendor,Order order){
        vendor.cancelOrder(order);
        orders.remove(order);
    }

    public void cancelOrder(Order order){
        orders.remove(order);
        System.out.println("Your order "+order.getID()+" has been cancelled.");
    }

    public void allocateRunner(Order order){
        Runner runner = new Runner();
        ArrayList<Runner> runnerList = runner.getAvailableRunner();
        if (runnerList!=null){
            if(runnerCounter<runnerList.size()){
                RunnerNotification notification = new RunnerNotification("You have new task!",runnerList.get(runnerCounter),1);
                notification.saveNotification();
                runnerCounter++;
            }else {
                CustomerNotification notification = new CustomerNotification("Failed to get runner!", this,1);
                notifications.add(notification);
            }
        }
    }

}
