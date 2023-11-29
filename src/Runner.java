import java.io.Serializable;
import java.util.ArrayList;

public class Runner implements Serializable {

    private String ID, runnerName, contact, password;
    private boolean status = false;
    private ArrayList<Order> receivedOrders;
    private ArrayList<RunnerNotification> notifications;

    public Runner(){};//for task usage

//    public Runner(String userID, String password){
//        FileOperation file = new FileOperation("Customer.txt");
//        if(file.checkUserCredential(userID,password)){
//            setDetails(userID);
//            status=true;
//        }
//        this.receivedOrders = new ArrayList<>();
//    }//for login

    public Runner(String ID,String password,String runnerName,String contact){
        setID(ID);setPassword(password);setRunnerName(runnerName);setContact(contact);
        this.notifications = new ArrayList<>();
    }//for register //obj.write2file(obj.toString());

    public String getID() {return ID;}

    public void setID(String ID) {this.ID = ID;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getRunnerName() {return runnerName;}

    public void setRunnerName(String runnerName) {this.runnerName = runnerName;}

    public String getContact() {return contact;}

    public void setContact(String contact) {this.contact = contact;}

    public boolean getStatus() {return status;}

    public void setStatus(boolean status) {this.status = status;}

    public ArrayList<RunnerNotification> getNotifications(){
        FileOperation file = new FileOperation("RunnerNotification.txt");
        ArrayList<String> foundRecords = file.search(ID);
        for(String record:foundRecords){
            String[] part = record.split(";");
            notifications.add(new RunnerNotification(part[0],part[1],this,Integer.parseInt(part[3]),part[4]));
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
            setRunnerName(item[2]);
            setContact(item[3]);
            setStatus(Boolean.parseBoolean(item[4]));
        }
    }

    public ArrayList<Runner> getAvailableRunner(){
        SerializationOperation operation = new SerializationOperation("Runner.ser");
        ArrayList<Runner> runnerFound = new ArrayList<>();
        ArrayList<Runner> allRunner = operation.readAllObjects(Runner.class);
        for(Runner runner:allRunner){
            if(runner.getStatus())
                runnerFound.add(runner);
        }
        return runnerFound;
    }

    public void receiveOrder(Order order){
        receivedOrders.add(order);
        write2TaskFile(ID+","+order.toString());
        status=false;
        modifyFile();
        CustomerNotification notification = new CustomerNotification("We have found you a runner!",order.getCustomer(),5,order.getID(),contact);
        notification.saveNotification();
    }

    public ArrayList<Order> getReceivedOrders(){
        if(receivedOrders.isEmpty()){
            FileOperation file = new FileOperation("RunnerTask.txt");
            ArrayList<String> foundTask = file.search(ID);
            for(String task:foundTask){
                String[] part = task.split(";");
                if (!part[5].equals(String.valueOf(Order.Status.Completed))) {
                    receivedOrders.add(new Order(part[1]));
                }
            }
        }
        return receivedOrders;
    }

    public ArrayList<Order> getTaskHistory(){
        ArrayList<Order> taskHistory = new ArrayList<>();
        FileOperation file = new FileOperation("RunnerTask.txt");
        ArrayList<String> foundTask = file.search(ID);
        for(String task:foundTask){
            String[] part = task.split(";");
            receivedOrders.add(new Order(part[1]));
        }
        return taskHistory;
    }

    public void rejectOrder(Order order){
        order.getCustomer().allocateRunner(order);
    }

    public void updateOrder(Order order,int status){
        switch (status) {
            case 1://accept order
                order.setStatus(Order.Status.Delivering);
            case 2://reject order
                order.setStatus(Order.Status.PendingRunner);
            case 3://complete order
                order.setStatus(Order.Status.Completed);
                this.status=true;
                modifyFile();
        }
        CustomerNotification notification = new CustomerNotification("Order Status Updated!",order.getCustomer(),2, order.getID());
        notification.saveNotification();
        FileOperation file = new FileOperation("CusOrder.txt");
        file.modifyFile(order.getID(),order.toString());
    }

    public String toString(){
        return String.format("%s;%s;%s;%s;%s", ID, password,runnerName,contact,status);
    }

    public void write2file(){
        SerializationOperation operation = new SerializationOperation("Runner.ser");
        operation.addObject(this);
    }

    public void write2TaskFile(String input){
        FileOperation file = new FileOperation("RunnerTask.txt");
        file.writeToFile(input);
    }

    public void modifyFile(){
        SerializationOperation operation = new SerializationOperation("Customer.ser");
        operation.updateObject(ID,Runner.class);
    }
}
