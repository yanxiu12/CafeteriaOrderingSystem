import jdk.jfr.Category;

import java.util.ArrayList;

public class Vendor {
    private String ID, vendorName, category, password,address;
    private ArrayList<Order> receivedOrders;
    private ArrayList<Notification> notifications;

    public Vendor(String userID){
        FileOperation file = new FileOperation("Vendor.txt");
        ArrayList<String> userData = file.search(userID);
        if (userData.size() == 1) {
            String[] item = userData.get(0).split(";");
            ID = item[0];
            password = item[1];
            vendorName = item[2];
            category = item[3];
            address = item[4];
        }else
            System.out.println("User not found!");
    }

    public Vendor(String userID, String password){
        FileOperation file = new FileOperation("Vendor.txt");
        if(file.checkUserCredential(userID,password)){
            setDetails(userID);
            this.receivedOrders = new ArrayList<Order>();
        }
    }//for login

    public Vendor(String ID,String password,String vendorName,String category,String address){
        setID(ID);setPassword(password);setVendorName(vendorName);setCategory(category);setAddress(address);
    }//for register //obj.write2file(obj.toString());

    public String getID(){return ID;}

    public void setID(String ID){this.ID = ID;}

    public String getVendorName(){return vendorName;}

    public void setVendorName(String vendorName){this.vendorName = vendorName;}

    public String getCategory(){return category;}

    public void setCategory(String category){this.category = category;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

    public void setAddress(String address){this.address = address;}

    public String getAddress(){return address;}

    public void addNotification(VendorNotification notification){notifications.add(notification);}

    public void setDetails(String userID){
        FileOperation file = new FileOperation("Vendor.txt");
        ArrayList<String> userData = file.search(userID);
        if (userData.size() == 1) {
            String[] item = userData.get(0).split(",");
            setID(item[0]);
            setPassword(item[1]);
            setVendorName(item[2]);
            setCategory(item[3]);
            setAddress(item[4]);
        }
    }

    public String toString(){
        return String.format("%s;%s;%s;%s;%s", ID, password, vendorName,category,address);
    }

    public void write2file(Vendor vendor){
        SerializationOperation operatioin = new SerializationOperation("Vendor.ser");
        operatioin.addObject(vendor);
    }

    public void receiveOrder(Order order) {
        receivedOrders.add(order);
    }

    public ArrayList<Order> getReceivedOrders(){
        return receivedOrders;
    }

    public void cancelOrder(Order order){
        receivedOrders.remove(order);
        System.out.println("Order "+order.getID()+" ordered by "+order.getCustomer().getName()+" has been canceled.");
    }

    public void cancelCustomerOrder(Customer customer) {
        ArrayList<Order> customerOrders = customer.getOrders();

        for (Order customerOrder : customerOrders) {
            if (receivedOrders.contains(customerOrder)) {
                receivedOrders.remove(customerOrder);
                customer.cancelOrder(customerOrder);
                System.out.println("Customer's order canceled successfully.");
            }
        }
    }

    public void updateOrder(Order order,int status){
        CustomerNotification notification = new CustomerNotification("Order Status Updated!",order.getCustomer(),2);
        switch (status) {
            case 1://accept order
                order.setStatus(Order.Status.Accepted);
            case 2://reject order
                order.setStatus(Order.Status.Rejected);
            case 3://complete order
                order.setStatus(Order.Status.Completed);
        }
        FileOperation file = new FileOperation("CusOrder.txt");
        file.modifyFile(order.getID(),order.toString());
    }
}
