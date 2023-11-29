import java.util.ArrayList;

public class Dashboard {
    protected ArrayList<Order> orders;

    public Dashboard(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void calculateRevenue() {
        double totalRevenue = 0;
        for (Order order : orders) {
            if (order.getStatus() == Order.Status.Completed) {
                totalRevenue += order.getTotalPrice();
            }
        }
        System.out.println("Total Revenue : RM "+String.format("%.2f", totalRevenue));
    }

    public void countCompletedOrders() {
        int deliveredCount = 0;
        for (Order order : orders) {
            if (order.getStatus() == Order.Status.Completed) {
                deliveredCount++;
            }
        }
        System.out.println("Total Orders Completed: RM "+deliveredCount);
    }
}
