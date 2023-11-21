import java.lang.reflect.Array;
import java.util.ArrayList;

public class Runner {

    private String ID, runnerName, contact, password;
    private boolean status = false;
    private ArrayList<Order> receivedOrders;
    private ArrayList<Notification> notifications;

    public Runner(){};//for task usage

    public Runner(String userID, String password){
        FileOperation file = new FileOperation("Customer.txt");
        if(file.checkUserCredential(userID,password)){
            setDetails(userID);
            status=true;
        }
        this.receivedOrders = new ArrayList<Order>();
    }//for login

    public Runner(String ID,String password,String runnerName,String contact){
        setID(ID);setPassword(password);setRunnerName(runnerName);setContact(contact);
    }//for register //obj.write2file(obj.toString());

    public String getID() {return ID;}

    public void setID(String ID) {this.ID = ID;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getRunnerName() {return runnerName;}

    public void setRunnerName(String runnerName) {this.runnerName = runnerName;}

    public String getContact() {return contact;}

    public void setContact(String contact) {this.contact = contact;}

    public boolean isStatus() {return status;}

    public void setStatus(boolean status) {this.status = status;}

    public void addNotification(RunnerNotification notification){notifications.add(notification);}

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
        FileOperation file = new FileOperation("Runner.txt");
        ArrayList<Runner> runnerFound = new ArrayList<>();
        for(String line:file.search("true")){
            String[] field = line.split(",");
            runnerFound.add(new Runner(field[0],field[1]));
        }
        return runnerFound;
    }

    public void receiveOrder(Order order){
        receivedOrders.add(order);
        write2TaskFile(ID+","+order.toString());
    }

    public ArrayList<Order> getReceivedOrders(){
        return receivedOrders;
    }

    public void rejectOrder(Order order){
        order.getCustomer().allocateRunner(order);
    }

    public String toString(){
        return String.format("%s;%s;%s;%s;%s", ID, password,runnerName,contact,status);
    }

    public void write2file(String input){
        FileOperation file = new FileOperation("Runner.txt");
        file.writeToFile(input);
    }

    public void write2TaskFile(String input){
        FileOperation file = new FileOperation("RunnerTask.txt");
        file.writeToFile(input);
    }
}
