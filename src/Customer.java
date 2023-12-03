import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    private String ID,name,dob,contact,password,address;
    private double walletBalance;

    private transient ArrayList<CustomerNotification> notifications;
    private transient ArrayList<Order>orders;
    private transient ArrayList<Cart>cartItems;

    public Customer(String userID){
        SerializationOperation operation = new SerializationOperation("Customer.ser");
        ArrayList<Customer> foundCustomer = operation.searchObjects(userID,Customer.class);
        if(foundCustomer.size()==1) {
            Customer found = foundCustomer.get(0);
            setID(found.getID());setPassword(found.getPassword());setName(found.getName());setDob(found.getDob());setContact(found.getContact());setAddress(found.getAddress());

        }
    }

//    public Customer(String userID,String password){
//        FileOperation file = new FileOperation("Customer.txt");
//        if(file.checkUserCredential(userID,password)){
//            setDetails(userID);
//        }
//        this.orders = new ArrayList<>();
//        this.cartItems = new ArrayList<>();
//        this.notifications = new ArrayList<>();
//    }//for login

    public Customer(String ID,String password,String name,String dob,String contact,String address,String walletBalance){
        setID(ID);setPassword(password);setName(name);setDob(dob);setContact(contact);setAddress(address);setWalletBalance(walletBalance);
        this.notifications = new ArrayList<>();
        this.cartItems = new ArrayList<>();
        this.orders = new ArrayList<>();
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

    public ArrayList<Order> getOrders() {
        this.orders = new ArrayList<>();
        FileOperation file = new FileOperation("CusOrder.txt");
        ArrayList<String> foundOrder = file.search(ID);
        if(!foundOrder.isEmpty()) {
            for (String order : foundOrder) {
                String[] part = order.split(";");
                if (!part[5].equals(String.valueOf(Order.Status.Completed)) && !part[5].equals(String.valueOf(Order.Status.Cancelled)) && !part[5].equals(String.valueOf(Order.Status.Rejected))) {
                    orders.add(new Order(part[0]));
                }
            }
        }
        return orders;
    }

    public ArrayList<Order> getOrderHistory(){
        ArrayList<Order> orderHistory = new ArrayList<>();
        FileOperation file = new FileOperation("CusOrder.txt");
        ArrayList<String> foundRecords = file.search(ID);
        for(String record:foundRecords){
            String[] part = record.split(";");
            orderHistory.add(new Order(part[0]));
        }
        return orderHistory;
    }

    public void resetCartItems(){this.cartItems = new ArrayList<>();}

    public ArrayList<Cart> getCartItems() {return cartItems;}

    public ArrayList<Credit> getCreditRecords(){
        ArrayList<Credit> creditRecords = new ArrayList<>();
        FileOperation file = new FileOperation("CustomerCredit.txt");
        ArrayList<String> foundRecords = file.search(ID);
        for(String record:foundRecords){
            String[] part = record.split(";");
            creditRecords.add(new Credit(part[0],this,part[2],part[3],part[4]));
        }
        return creditRecords;
    }

    public ArrayList<CustomerNotification> getNotifications(){
        this.notifications = new ArrayList<>();
        FileOperation file = new FileOperation("CustomerNotification.txt");
        ArrayList<String> foundRecords = file.search(ID);
        for(String record:foundRecords){
            String[] part = record.split(";");
            notifications.add(new CustomerNotification(part[0],this,part[2],Integer.parseInt(part[3]),part[4],part[5]));
        }
        return notifications;
    }

   /* public void setDetails(String userID){
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
    }*/

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

    public void modifyOrderFile(String id,String input){
        FileOperation file = new FileOperation("CusOrder.txt");
        file.modifyFile(id,input);
    }

    public void modifyFile(Customer customer){
        SerializationOperation operation = new SerializationOperation("Customer.ser");
        operation.updateObject(customer.getID(),this);
    }

    public void addToCart(MenuItem item,int quantity){
        for(Cart cartItem:cartItems){
            if(cartItem.getItem().getItemID().equals(item.getItemID())){
                cartItem.setQuantity(cartItem.getQuantity()+quantity);
                return;
            }
        }
        Cart items = new Cart(item,quantity);
        cartItems.add(items);
    }

    public void removeFromCart(MenuItem item,int quantity){
        for(Cart cartItem:cartItems){
            if(cartItem.getItem().getItemID().equals(item.getItemID())){
                if(cartItem.getQuantity()+quantity<=0) {
                    cartItems.remove(cartItem);
                }else{
                    cartItem.setQuantity(cartItem.getQuantity()+quantity);
                }
                return;
            }
        }
    }

    public void placeOrder(Vendor vendor, int method){
        String orderID = IDGenerator.generateIDForOrder();
        Order ord = new Order(orderID,this,vendor,method,cartItems);
        orders.add(ord);
        VendorNotification notification = new VendorNotification("You have new order!",ord.getVendor(),1,ord.getID());
        notification.saveNotification();
        write2OrderFile(ord.toString());
    }

    public double printCart(int method){
        int counter = 0;
        double totalPrice = 0;
        double deliveryFee=0;
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println(String.format("%-5s", "No.")+String.format("%-30s", "Item Name")+String.format("%-30s", "Price")+String.format("%-30s", "Quantity"));
        System.out.println("-----------------------------------------------------------------------------------------------");
        for(Cart item:cartItems){
            counter++;
            System.out.println((String.format("%-5s", counter)+String.format("%-30s", item.getItem().getItemName())+String.format("%-30s", item.getItem().getItemPrice())+String.format("%-30s", item.getQuantity())));
            totalPrice+= (item.getItem().getItemPrice()*item.getQuantity());
        }
        System.out.println("-----------------------------------------------------------------------------------------------");
        if(method == 3) {
            deliveryFee=5.00;
            System.out.println("Subtotal (RM) : "+String.format("%.2f",totalPrice));
            System.out.println("Delivery Fee (RM) :"+String.format("%.2f",deliveryFee));
        }
        System.out.println("Total Price (RM) : "+String.format("%.2f",(totalPrice+deliveryFee)));
        System.out.println();
        return totalPrice+deliveryFee;
    }

    public void cancelVendorOrder(Order order){
        orders.remove(order);
        order.setStatus(Order.Status.Cancelled);
        modifyOrderFile(order.getID(),order.toString());
        VendorNotification notification = new VendorNotification("An order has been canceled! ", order.getVendor(),2,order.getID());
        notification.saveNotification();
    }

//    public void allocateRunner(Order order){
//        Runner runner = new Runner();
//        ArrayList<Runner> runnerList = runner.getAvailableRunner();
//        if (runnerList!=null){
//            if(runnerCounter<runnerList.size()){
//                RunnerNotification notification = new RunnerNotification("You have new task!",runnerList.get(runnerCounter),1,order.getID());
//                notification.saveNotification();
//                runnerCounter++;
//            }else {
//                CustomerNotification notification = new CustomerNotification("Failed to get runner!", this,1,order.getID());
//                notification.saveNotification();
//            }
//        }
//    }

}
