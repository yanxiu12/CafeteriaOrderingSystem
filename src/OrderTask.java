import java.util.ArrayList;

public class OrderTask {

    private Order order;
    private Runner runner;
    private int runnerCounter;
    private boolean acceptStatus = false;
    private ArrayList<Runner> runnerList;

    public OrderTask() {
        Runner runner = new Runner();
        runnerList = runner.getAvailableRunner();
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
        int numberOfRunners = runnerList.size();
        runnerCounter=0;

        if(numberOfRunners>0) {
            FileOperation file = new FileOperation("RunnerTask.txt");
            ArrayList<String> foundTask = file.search(order.getID());
            if (!foundTask.isEmpty()) {
                String[] taskPart = foundTask.get(0).split(";");
                int counter = Integer.parseInt(taskPart[2]);
                runnerCounter = counter+1;
            }

            Runner onHoldRunner;
            for (; runnerCounter < numberOfRunners; runnerCounter++) {
                onHoldRunner = runnerList.get(runnerCounter);
                if (!onHoldRunner.getStatus()) {
                    continue;
                }
                break;
            }
        }

        if (runnerCounter < numberOfRunners) {
            this.runner = runnerList.get(runnerCounter);
            RunnerNotification notification = new RunnerNotification("You have a new delivery task!", runner, 1, order.getID());
            notification.saveNotification();
            if (runnerCounter == 0) {
                write2file(this.toString());
            } else {
                modifyFile(order.getID(), this.toString());
            }
        } else {
            CustomerNotification notification = new CustomerNotification("Failed to get runner!", order.getCustomer(), 1, order.getID());
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
