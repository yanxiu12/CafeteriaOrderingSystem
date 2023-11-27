import java.util.ArrayList;

public class OrderReview {
    private String review;
    private String reviewFileName;
    private Order order;

    public OrderReview(String reviewFileName) {
        this.reviewFileName = reviewFileName;
    }

    public void setReview(Order order,String review) {
        this.review = review;
        this.order = order;
        writeReviewToFile(order,review);
    }

    public String getReview(Order order) {
        if (review == null) {
            return readReviewFromFile(order);
        }
        return review;
    }

    public ArrayList<String> getReviewByVendor(Vendor vendor){
        return readReviewFromFile(vendor);
    }

    private void writeReviewToFile(Order order, String review) {
        FileOperation file = new FileOperation(reviewFileName);
        file.writeToFile(this.toString());
    }

    public String toString(){
        return String.format("%s;%s;%s;%s", order.getID(), order.getVendor().getID(), order.getCustomer().getName(), review);
    }

    private String readReviewFromFile(Order order) {
        FileOperation file = new FileOperation(reviewFileName);
        ArrayList<String> foundReview = file.search(order.getID());
        if(foundReview.size() == 1){
            String[] reviewRecord = foundReview.get(0).split(";");
            return reviewRecord[3];
        }
        return null;
    }

    private ArrayList<String> readReviewFromFile(Vendor vendor){
        ArrayList<String> reviews= new ArrayList<>();
        FileOperation file = new FileOperation(reviewFileName);
        ArrayList<String> foundReview = file.search(vendor.getID());
        if(!foundReview.isEmpty()){
            for(String review:foundReview){
                String[] reviewRecord = review.split(";");
                reviews.add(String.format("%-5s", reviewRecord[2]) +  "| "+reviewRecord[3]);
            }
            return reviews;
        }
        return null;
    }
}
