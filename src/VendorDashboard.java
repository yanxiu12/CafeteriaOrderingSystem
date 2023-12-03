import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VendorDashboard extends Dashboard{

    public VendorDashboard(ArrayList<Order> orders) {
        super(orders);
    }

    private Map<LocalDate, Double> generateRevenueByDate() {
        Map<LocalDate, Double> revenueByDate = new HashMap<>();
        for (Order order : orders) {
            if (order.getStatus() == Order.Status.Completed) {
                LocalDate orderDate = LocalDate.parse(order.getCurrentDate());
                revenueByDate.merge(orderDate, order.getTotalPrice(), Double::sum);
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
                System.out.println(String.format("%-30s", date) + String.format("%-30s", String.format("%.2f",revenue)));
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
                System.out.println(String.format("%-30s", month) + String.format("%-30s", String.format("%.2f",revenue)));
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
                System.out.println(String.format("%-30s", year) + String.format("%-30s", String.format("%.2f",revenue)));
            }
        }else
            System.out.println("(No completed order currently. Unable to generate dashboard.)");
    }

    public void printItemsBySales() {
        if(!orders.isEmpty()) {
            Map<String, Integer> itemSales = new HashMap<>();

            for (Order order : orders) {
                for (Cart item : order.getShoppingCart()) {
                    if (order.getStatus() == Order.Status.Completed) {
                        String itemName = item.getItem().getItemName();
                        int quantity = item.getQuantity();
                        itemSales.put(itemName, itemSales.getOrDefault(itemName, 0) + quantity);
                    }
                }
            }

            ArrayList<Map.Entry<String, Integer>> sortedList = new ArrayList<>(itemSales.entrySet());
            sortedList.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

            for (Map.Entry<String, Integer> entry : sortedList) {
                String itemName = entry.getKey();
                int quantitySold = entry.getValue();
                System.out.println(String.format("%-30s", itemName) + String.format("%-30s", quantitySold));
            }
        }else
            System.out.println("(No completed order currently. Unable to generate dashboard.)");
    }
}
