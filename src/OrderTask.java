import java.io.File;
import java.util.ArrayList;

public class OrderTask {

    private Order order;
    private Runner runner;
    private int runnerCounter;
    private boolean acceptStatus = false;
    private ArrayList<Runner> availableRunners;

    public OrderTask() {
        Runner runner = new Runner();
        availableRunners = runner.getAvailableRunner();
    }

    public OrderTask(Order order, Runner runner){
        this.order = order;
        this.runner = runner;
        FileOperation file = new FileOperation("RunnerTask.txt");
        ArrayList<String> foundTask=  file.search(order.getID());
        if(foundTask.size() == 1){
            String[] taskPart = foundTask.get(0).split(";");
            this.runnerCounter = Integer.parseInt(taskPart[2]);

        }
    }

    public OrderTask(Order order){
        this.order = order;
        FileOperation file = new FileOperation("RunnerTask.txt");
        ArrayList<String> foundTask=  file.search(order.getID());
        if(foundTask.size() == 1){
            String[] taskPart = foundTask.get(0).split(";");
            this.acceptStatus = Boolean.parseBoolean(taskPart[3]);
        }
    }

    public void setAccept(boolean acceptStatus){
        this.acceptStatus = acceptStatus;
    }

    public boolean getAcceptStatus(){return acceptStatus;}

    public void allocateRunner(Order order) {
        this.order = order;
        int numberOfRunners = availableRunners.size();

        FileOperation file = new FileOperation("RunnerTask.txt");
        ArrayList<String> foundTask=  file.search(order.getID());
        if(foundTask.size() == 1){
            String[] taskPart = foundTask.get(0).split(";");
            int counter = Integer.parseInt(taskPart[2]);
            this.runnerCounter = counter+1;
        }else if(foundTask.isEmpty()){
            this.runnerCounter = 0;
        }

        if (runnerCounter < numberOfRunners) {
            if(runnerCounter == 0){
                this.runner = availableRunners.get(runnerCounter);
                RunnerNotification notification = new RunnerNotification("You have a new delivery task!", runner, 1, order.getID());
                notification.saveNotification();
                write2file(this.toString());
            }else {
                this.runner = availableRunners.get(runnerCounter);
                RunnerNotification notification = new RunnerNotification("You have a new delivery task!", runner, 1, order.getID());
                notification.saveNotification();
                modifyFile(order.getID(), this.toString());
            }
        }

        if (runnerCounter >= numberOfRunners) {
            CustomerNotification notification = new CustomerNotification("Failed to get runner!", order.getCustomer(),1,order.getID());
            notification.saveNotification();
        }
    }

    public void write2file(String input){
        FileOperation file = new FileOperation("RunnerTask.txt");
        file.writeToFile(input);
    }

    public void modifyFile(String idToUpdate,String input){
        FileOperation file = new FileOperation("RunnerTask.txt");
        file.modifyFile(idToUpdate, input);
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s", order.getID(),runner.getID(), runnerCounter,acceptStatus);

    }
}
