import jdk.jfr.Category;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class Vendor implements Serializable {
    private String ID, vendorName, category, password,address;
    private transient ArrayList<Order> receivedOrders;
    private transient ArrayList<VendorNotification> notifications;

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
        this.receivedOrders = new ArrayList<>();
        this.notifications = new ArrayList<>();
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
        this.notifications = new ArrayList<>();
        FileOperation file = new FileOperation("VendorNotification.txt");
        ArrayList<String> foundRecords = file.search(ID);
        for(String record:foundRecords){
            String[] part = record.split(";");
            notifications.add(new VendorNotification(part[0],this,part[2],Integer.parseInt(part[3]),part[4]));
        }
        return notifications;
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
                if (!part[5].equals(String.valueOf(Order.Status.Completed)) && !part[5].equals(String.valueOf(Order.Status.Cancelled)) && !part[5].equals(String.valueOf(Order.Status.VendorRejected))) {
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
            String[] part = record.split(";");
            orderHistory.add(new Order(part[0]));
        }
        return orderHistory;
    }

    public ArrayList<Order> getOrderHistoryByDate(LocalDate date) {
        ArrayList<Order> ordersByDate = new ArrayList<>();
        FileOperation file = new FileOperation("CusOrder.txt");
        ArrayList<String> foundRecords = file.search(ID);

        for (String record : foundRecords) {
            String[] part = record.split(";");
            Order order = new Order(part[0]); // Assuming part[0] is the order ID

            // Assuming currentDate is a string representation of the date in the format "yyyy-MM-dd"
            LocalDate orderDate = LocalDate.parse(order.getCurrentDate()); // Parse the date string

            if (orderDate.equals(date)) {
                // Add this order to the history if it matches the specified date
                ordersByDate.add(order);
            }
        }
        return ordersByDate;
    }

    public ArrayList<Order> getOrderHistoryByMonth(YearMonth month) {
        ArrayList<Order> ordersByMonth = new ArrayList<>();
        FileOperation file = new FileOperation("CusOrder.txt");
        ArrayList<String> foundRecords = file.search(ID);

        for (String record : foundRecords) {
            String[] part = record.split(";");
            Order order = new Order(part[0]);

            LocalDate orderDate = LocalDate.parse(order.getCurrentDate()); // Parse the date string
            YearMonth orderYearMonth = YearMonth.from(orderDate);
            if (orderYearMonth.equals(month)) {
                ordersByMonth.add(order);
            }
        }
        return ordersByMonth;
    }

    public ArrayList<Order> getOrderHistoryByYear(int year) {
        ArrayList<Order> ordersByYear = new ArrayList<>();
        FileOperation file = new FileOperation("CusOrder.txt");
        ArrayList<String> foundRecords = file.search(ID);

        for (String record : foundRecords) {
            String[] part = record.split(";");
            Order order = new Order(part[0]);
            LocalDate orderDate = LocalDate.parse(order.getCurrentDate()); // Parse the date string
            if (orderDate.getYear() == year) {
                ordersByYear.add(order);
            }
        }
        return ordersByYear;
    }

//    public void cancelOrder(Order order){
//        receivedOrders.remove(order);
//        System.out.println("Order "+order.getID()+" ordered by "+order.getCustomer().getName()+" has been canceled.");
//    }

//    public void cancelCustomerOrder(Customer customer) {
//        ArrayList<Order> customerOrders = customer.getOrders();
//
//        for (Order customerOrder : customerOrders) {
//            if (receivedOrders.contains(customerOrder)) {
//                receivedOrders.remove(customerOrder);
//                customer.cancelOrder(customerOrder);
//                System.out.println("Customer's order canceled successfully.");
//            }
//        }
//    }

    public boolean updateOrder(Order order,int status){
        if(order.getStatus()!=Order.Status.Cancelled) {
            switch (status) {
                case 1://accept order
                    order.setStatus(Order.Status.VendorAccepted);
                    break;
                case 2://reject order
                    order.setStatus(Order.Status.VendorRejected);
                    break;
                case 3:
                    if(order.getOrderType()==3){
                        if(order.getOrderTaskStatus())
                            order.setStatus(Order.Status.Delivering);
                        else
                            order.setStatus(Order.Status.PendingRunner);
                    }else
                        order.setStatus(Order.Status.VendorIsReady);
                    break;
                case 4://complete order
                    order.setStatus(Order.Status.Completed);
                    break;
            }
            CustomerNotification notification;
            if (order.getStatus() == Order.Status.VendorRejected) {
                notification = new CustomerNotification("Your order [" + order.getID() + "] has been rejected!", order.getCustomer(), 4, order.getID());
            } else if(order.getStatus() == Order.Status.VendorAccepted){
                notification = new CustomerNotification("Order is Accepted!", order.getCustomer(), 8, order.getID());
            } else if(order.getStatus() == Order.Status.Delivering) {
                notification = new CustomerNotification("Order is Delivering!", order.getCustomer(), 6, order.getID());
            }else if(order.getStatus() == Order.Status.VendorIsReady){
                notification = new CustomerNotification("Order is Ready!", order.getCustomer(), 2, order.getID());
            }else if(order.getStatus() == Order.Status.PendingRunner){
                notification = new CustomerNotification("Order is Pending Runner!", order.getCustomer(), 7, order.getID());
            }else
                notification = new CustomerNotification("Order is Completed!", order.getCustomer(), 9, order.getID());
            notification.saveNotification();
            FileOperation file = new FileOperation("CusOrder.txt");
            file.modifyFile(order.getID(), order.toString());
            return true;
        }else
            return false;
    }
}
