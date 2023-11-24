import java.text.SimpleDateFormat;
import java.util.Date;

public class Credit {
    private Customer customer;
    private String ID,currentDate,transaction;
    private String transactionType;

    public Credit(Customer user){
        this.customer = user;
        this.currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public void addAmount(double amount,int type){//1=topUp 2=refund
        IDGenerator generator = new IDGenerator("creditRecord.txt", "CT");
        ID = generator.generateID();
        transaction = "+"+amount;
        if(type==1)
            transactionType = "Top-Up";
        else if (type == 2)
            transactionType = "Refund";
        customer.setWalletBalance(String.valueOf(customer.getWalletBalance()+amount));
        write2file();
        customer.modifyFile(customer);
    }

    public void deductAmount(double amount){
        IDGenerator generator = new IDGenerator("creditRecord.txt", "CT");
        ID = generator.generateID();
        transaction = "+"+amount;

        customer.setWalletBalance(String.valueOf(customer.getWalletBalance()-amount));
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
