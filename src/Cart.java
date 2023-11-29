public class Cart {
    private MenuItem item;
    private int quantity;

    public Cart(MenuItem item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem(){
        return item;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){this.quantity=quantity;}

    @Override
    public String toString() {
        return super.toString();
    }
}
