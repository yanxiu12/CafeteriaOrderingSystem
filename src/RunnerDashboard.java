import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RunnerDashboard extends Dashboard{
    public RunnerDashboard(ArrayList<Order> orders) {
        super(orders);
    }

    public Map<LocalDate, Double> generateRevenueByDate() {
        Map<LocalDate, Double> revenueByDate = new HashMap<>();
        for (Order order : orders) {
            if (order.getOrderType() == 2 && order.getStatus() == Order.Status.Completed) {
                LocalDate orderDate = LocalDate.parse(order.getCurrentDate());
                revenueByDate.merge(orderDate, order.getDeliveryFee(), Double::sum);
            }
        }
        return revenueByDate;
    }

    private void printRevenueByDate() {
        Map<LocalDate, Double> revenueByDate = generateRevenueByDate();
        for (Map.Entry<LocalDate, Double> entry : revenueByDate.entrySet()) {
            LocalDate date = entry.getKey();
            double revenue = entry.getValue();
            System.out.println("Date: " + date + " - Revenue: " + revenue);
        }
    }

    private void printRevenueByMonth() {
        Map<LocalDate, Double> revenueByDate = generateRevenueByDate();
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
            System.out.println("Month: " + month + " - Revenue: " + revenue);
        }
    }

    private void printRevenueByYear() {
        Map<LocalDate, Double> revenueByDate = generateRevenueByDate();
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
            System.out.println("Year: " + year + " - Revenue: " + revenue);
        }
    }
}
