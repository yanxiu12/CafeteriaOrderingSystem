import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RunnerDashboard extends Dashboard{
    public RunnerDashboard(ArrayList<Order> orders) {
        super(orders);
    }

    private Map<LocalDate, Double> generateRevenueByDate() {
        Map<LocalDate, Double> revenueByDate = new HashMap<>();
        for (Order order : orders) {
            if (order.getStatus() == Order.Status.Completed) {
                LocalDate orderDate = LocalDate.parse(order.getCurrentDate());
                revenueByDate.merge(orderDate, order.getDeliveryFee(), Double::sum);
            }
        }
        return revenueByDate;
    }

    public void printRevenueByDate() {
        Map<LocalDate, Double> revenueByDate = generateRevenueByDate();
        if(!revenueByDate.isEmpty()) {
            for (Map.Entry<LocalDate, Double> entry : revenueByDate.entrySet()) {
                LocalDate date = entry.getKey();
                double revenue = entry.getValue();
                System.out.println(String.format("%-30s", date) + String.format("%-30s",revenue));
            }
        }else
            System.out.println("(No completed order currently. Unable to generate dashboard.)");
    }

    public void printRevenueByMonth() {
        Map<LocalDate, Double> revenueByDate = generateRevenueByDate();
        if(!revenueByDate.isEmpty()) {
            Map<Month, Double> revenueByMonth = new HashMap<>();
            for (Map.Entry<LocalDate, Double> entry : revenueByDate.entrySet()) {
                LocalDate date = entry.getKey();
                Month month = date.getMonth();
                double revenue = entry.getValue();
                revenueByMonth.merge(month, revenue, Double::sum);
            }
            for (Map.Entry<Month, Double> entry : revenueByMonth.entrySet()) {
                Month month = entry.getKey();
                double revenue = entry.getValue();
                System.out.println(String.format("%-30s", month) + String.format("%-30s", revenue));
            }
        }else
            System.out.println("(No completed order currently. Unable to generate dashboard.)");
    }

    public void printRevenueByYear() {
        Map<LocalDate, Double> revenueByDate = generateRevenueByDate();
        if(!revenueByDate.isEmpty()) {
            Map<Integer, Double> revenueByYear = new HashMap<>();
            for (Map.Entry<LocalDate, Double> entry : revenueByDate.entrySet()) {
                LocalDate date = entry.getKey();
                int year = date.getYear();
                double revenue = entry.getValue();
                revenueByYear.merge(year, revenue, Double::sum);
            }
            for (Map.Entry<Integer, Double> entry : revenueByYear.entrySet()) {
                int year = entry.getKey();
                double revenue = entry.getValue();
                System.out.println(String.format("%-30s",year) + String.format("%-30s",revenue));
            }
        }else
            System.out.println("(No completed order currently. Unable to generate dashboard.)");
    }
}
