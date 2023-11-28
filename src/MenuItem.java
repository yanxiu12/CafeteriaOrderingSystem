import java.io.File;
import java.util.ArrayList;

public class MenuItem {
    private String itemID, itemName,itemPrice;
    private Vendor vendor;
    private ArrayList<MenuItem> vendorItems;

    public MenuItem(String itemID, String itemName,String itemPrice, Vendor vendor){
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.vendor = vendor;
    }

    public MenuItem(String itemID){
        FileOperation file = new FileOperation("MenuItem.txt");
        ArrayList<String> orderData = file.search(itemID);
        if (orderData.size() == 1) {
            String[] itemDetail = orderData.get(0).split(";");
            this.itemID = itemDetail[0];
            this.itemName = itemDetail[1];
            itemPrice = itemDetail[2];
            vendor = new Vendor(itemDetail[3]);
        }
    }

    public MenuItem(Vendor vendor){
        setMenu(vendor);
    }//to view specify vendor's menu

    public String getItemID(){
        return itemID;
    }
    public String getItemName(){
        return itemName;
    }

    public double getItemPrice(){
        return Double.parseDouble(itemPrice);
    }

    public void setItemName(String itemName) {this.itemName = itemName;}

    public void setItemPrice(double itemPrice) {this.itemPrice = String.format("%.2f", itemPrice);}

    public Vendor getVendor(){
        return vendor;
    }

    public ArrayList<MenuItem> getVendorItems(){return vendorItems;}

    public MenuItem getDetail(int row){
        int index = row-1;
        return vendorItems.get(index);
    }

    public String toString(){
        return String.format("%s;%s;%s;%s", itemID, itemName, itemPrice,vendor.getID());
    }

    public void write2file(String input){
        FileOperation file = new FileOperation("MenuItem.txt");
        file.writeToFile(input);
    }

    public void modifyFile(String id,String input){
        FileOperation file = new FileOperation("MenuItem.txt");
        file.modifyFile(id,input);
    }

    public void setMenu(Vendor vendor){
        vendorItems = new ArrayList<>();
        FileOperation file = new FileOperation("MenuItem.txt");
        ArrayList<String> items = file.search(vendor.getID());
        for(String line:items) {
            String[] itemDetail = line.split(",");
            MenuItem item = new MenuItem(itemDetail[0], itemDetail[1], itemDetail[2], vendor);
            vendorItems.add(item);
        }
    }

    public void printMenu(){
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println(String.format("%-30s", "ITEM ID")+String.format("%-30s", "ITEM NAME")+String.format("%-30s", "ITEM PRICE (RM)"));
        System.out.println("------------------------------------------------------------------------------------------");
        for(MenuItem item:vendorItems){
            System.out.println(String.format("%-30s", item.getItemID())+String.format("%-30s", item.getItemName())+String.format("%-30s", item.getItemPrice()));
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }
}
