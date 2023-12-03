import java.io.Serializable;
import java.util.ArrayList;

public class Runner implements Serializable {

    private String ID, runnerName, contact, password;
    private boolean status = true;
    private transient ArrayList<Runner> availableRunners;
    private transient ArrayList<Order> receivedOrders;
    private transient ArrayList<RunnerNotification> notifications;

    public Runner(){availableRunners = new ArrayList<>();}//for task usage

//    public Runner(String userID, String password){
//        FileOperation file = new FileOperation("Customer.txt");
//        if(file.checkUserCredential(userID,password)){
//            setDetails(userID);
//            status=true;
//        }
//        this.receivedOrders = new ArrayList<>();
//    }//for login

    public Runner(String ID,String password,String runnerName,String contact,boolean status){
        setID(ID);setPassword(password);setRunnerName(runnerName);setContact(contact);setStatus(status);
        this.notifications = new ArrayList<>();
        this.receivedOrders = new ArrayList<>();
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
        this.notifications = new ArrayList<>();
        FileOperation file = new FileOperation("RunnerNotification.txt");
        ArrayList<String> foundRecords = file.search(ID);
        for(String record:foundRecords){
            String[] part = record.split(";");
            notifications.add(new RunnerNotification(part[0],this,part[2],Integer.parseInt(part[3]),part[4]));
        }
        return notifications;
    }

    public ArrayList<Runner> getAvailableRunner(){
        SerializationOperation operation = new SerializationOperation("Runner.ser");
        ArrayList<Runner> allRunner = operation.readAllObjects(Runner.class);
        if(!allRunner.isEmpty()) {
            for (Runner runner : allRunner) {
                if (runner.getStatus())
                    availableRunners.add(runner);
            }
        }
        return availableRunners;
    }

    public void receiveOrder(Order order){
        receivedOrders.add(order);
        OrderTask task = new OrderTask(order,this);
        task.setAccept(true);
        task.modifyFile(order.getID(),task.toString());
        status=false;
        modifyFile();
        System.out.println("Contact:"+contact);
        CustomerNotification notification = new CustomerNotification("We have found you a runner!",order.getCustomer(),5,order.getID(),contact);
        notification.saveNotification();
    }

    public ArrayList<Order> getReceivedOrders(){
        this.receivedOrders = new ArrayList<>();
        FileOperation file = new FileOperation("RunnerTask.txt");
        ArrayList<String> foundTask = file.search(ID);
        if(!foundTask.isEmpty()) {
            for (String task : foundTask) {
                String[] part = task.split(";");
                Order order = new Order(part[0]);
                if (!(order.getStatus() == Order.Status.Completed)) {
                    receivedOrders.add(order);
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
            taskHistory.add(new Order(part[0]));
        }
        return taskHistory;
    }


    public void updateOrder(Order order,int status){
        boolean statusChange = false;
        switch (status) {
            case 1://accept order
                if(order.getStatus() == Order.Status.Ready) {
                    order.setStatus(Order.Status.Delivering);
                    statusChange = true;
                }
                break;
            case 2://reject order
                if(order.getStatus() == Order.Status.Ready) {
                    order.setStatus(Order.Status.PendingRunner);
                    statusChange = true;
                }
                break;
            case 3://complete order
                order.setStatus(Order.Status.Completed);
                this.status=true;
                modifyFile();
                statusChange = true;
                break;
        }
        if(statusChange) {
            CustomerNotification notification = new CustomerNotification("Order Status Updated!", order.getCustomer(), 2, order.getID());
            notification.saveNotification();
            FileOperation file = new FileOperation("CusOrder.txt");
            file.modifyFile(order.getID(), order.toString());
        }
    }

    public String toString(){
        return String.format("%s;%s;%s;%s;%s", ID, password,runnerName,contact,status);
    }

    public void write2file(){
        SerializationOperation operation = new SerializationOperation("Runner.ser");
        operation.addObject(this);
    }


    public void modifyFile(){
        SerializationOperation operation = new SerializationOperation("Runner.ser");
        operation.updateObject(ID, this);
    }
}
