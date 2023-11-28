import jdk.jfr.Category;

import java.io.Serializable;
import java.util.ArrayList;

public class Vendor implements Serializable {
    private String ID, vendorName, category, password,address;
    private ArrayList<Order> receivedOrders;
    private ArrayList<VendorNotification> notifications;

    public Vendor(String userID){
        SerializationOperation operation = new SerializationOperation("Vendor.ser");
        ArrayList<Vendor> userData = operation.searchObjects(userID, Vendor.class);
        if (userData.size() == 1) {
            Vendor vendor = userData.get(0);
            ID = vendor.getID();
            password = vendor.getPassword();
            vendorName = vendor.getVendorName();
            category = vendor.getCategory();
            address = vendor.address;
        }else
            System.out.println("User not found!");
    }

//    public Vendor(String userID, String password){
//        FileOperation file = new FileOperation("Vendor.txt");
//        if(file.checkUserCredential(userID,password)){
//            setDetails(userID);
//            this.receivedOrders = new ArrayList<Order>();
//        }
//    }//for login

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

    public ArrayList<VendorNotification> getNotifications(){
        FileOperation file = new FileOperation("VendorNotification.txt");
        ArrayList<String> foundRecords = file.search(ID);
        for(String record:foundRecords){
            String[] part = record.split(";");
            notifications.add(new VendorNotification(part[0],this,part[2],Integer.parseInt(part[3]),part[4]));
        }
        return notifications;
    }

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
        SerializationOperation operation = new SerializationOperation("Vendor.ser");
        operation.addObject(vendor);
    }

    public ArrayList<Order> getReceivedOrders(){
        this.receivedOrders = new ArrayList<>();
        FileOperation file = new FileOperation("CusOrder.txt");
        ArrayList<String> foundOrder = file.search(ID);
        if(!foundOrder.isEmpty()) {
            for (String order : foundOrder) {
                String[] part = order.split(";");
                if (!part[5].equals(String.valueOf(Order.Status.Completed))) {
                    receivedOrders.add(new Order(part[0]));
                }
            }
        }
        return receivedOrders;
    }

    public ArrayList<Order> getOrderHistory(){
        ArrayList<Order> orderHistory = new ArrayList<>();
        FileOperation file = new FileOperation("CusOrder.txt");
        ArrayList<String> foundRecords = file.search(ID);
        for(String record:foundRecords){
            String[] part = record.split(",");
            orderHistory.add(new Order(part[0]));
        }
        return orderHistory;
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
        CustomerNotification notification = new CustomerNotification("Order Status Updated!",order.getCustomer(),2, order.getID());
        switch (status) {
            case 1://accept order
                order.setStatus(Order.Status.Accepted);
            case 2://reject order
                order.setStatus(Order.Status.Rejected);
            case 3:
                order.setStatus(Order.Status.Ready);
            case 4://complete order
                order.setStatus(Order.Status.Completed);
        }
        FileOperation file = new FileOperation("CusOrder.txt");
        file.modifyFile(order.getID(),order.toString());
    }
}
