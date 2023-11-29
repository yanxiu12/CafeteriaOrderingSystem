import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Credit {
    private Customer customer;
    private String ID,currentDate,transaction;
    private String transactionType;

    public Credit(Customer user){
        this.customer = user;
        this.currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public Credit(String ID, Customer customer, String currentDate, String transaction,String transactionType){
        this.ID = ID;
        this.customer = customer;
        this.currentDate = currentDate;
        this.transaction = transaction;
        this.transactionType = transactionType;
    }

    public Customer getCustomer() {return customer;}

    public String getTransaction(){return transaction;}

    public String getID(){return ID;}

    public String getCurrentDate(){return currentDate;}

    public String getTransactionType(){return transactionType;}

    public void addAmount(double amount,int type){//1=topUp 2=refund
        IDGenerator generator = new IDGenerator("CustomerCredit.txt", "CT");
        ID = generator.generateID();
        transaction = "+"+String.format("%.2f",amount);
        if(type==1)
            transactionType = "Top-Up";
        else if (type == 2)
            transactionType = "Refund";
        customer.setWalletBalance(String.format("%.2f",customer.getWalletBalance()+amount));
        write2file();
        customer.modifyFile(customer);
    }

    public void deductAmount(double amount){
        IDGenerator generator = new IDGenerator("CustomerCredit.txt", "CT");
        ID = generator.generateID();
        transaction = "-"+String.format("%.2f",amount);

        customer.setWalletBalance(String.format("%.2f",customer.getWalletBalance()-amount));
        customer.modifyFile(customer);
        write2file();
    }

    public boolean isBalanceEnough(double amount){
        return !(customer.getWalletBalance() < amount);
    }

    public String toString(){
        return String.format("%s,%s,%s,%s,%s", ID, customer.getID(), currentDate, transaction,transactionType);
    }

    public void write2file(){
        FileOperation file = new FileOperation("CustomerCredit.txt");
        file.writeToFile(this.toString());
    }

}
