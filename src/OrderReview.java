import java.util.ArrayList;

public class OrderReview {
    private String review;
    private int rate,type;//type 1=vendor 2=runner
    private String reviewFileName;
    private Order order;

    public OrderReview(String reviewFileName) {
        this.reviewFileName = reviewFileName;
    }

    public void setReview(Order order,int rate,String review, int type) {
        this.review = review;
        this.rate = rate;
        this.order = order;
        this.type = type;
        writeReviewToFile(order,review);
    }

    public String getReview(Order order,int type) {
        if (review == null) {
            return readReviewFromFile(order,type);
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
        if(type == 1) {
            return String.format("%s;%s;%s;%s;%s;%s", order.getID(), order.getVendor().getID(), order.getCustomer().getName(), rate, review, type);
        }else if(type == 2){
            return String.format("%s;%s;%s;%s;%s;%s", order.getID(), order.getVendor().getID(), order.getCustomer().getName(), rate, review, type);
        }
        return null;
    }

    private String readReviewFromFile(Order order,int type) {
        FileOperation file = new FileOperation(reviewFileName);
        ArrayList<String> foundReview = file.search(order.getID());
        if(foundReview.size() == 1){
            String[] reviewRecord = foundReview.get(0).split(";");
            if(Integer.parseInt(reviewRecord[5])==type)
                return "\nRating = "+reviewRecord[3]+"\nFeedback = "+reviewRecord[4];
        }else if(foundReview.size() == 2){
            for(String record: foundReview){
                String[] reviewRecord = record.split(";");
                if(Integer.parseInt(reviewRecord[5])==type){
                    return "\nRating = "+reviewRecord[3]+"\nFeedback = "+reviewRecord[4];
                }
            }
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
                if(Integer.parseInt(reviewRecord[5])==1)
                    reviews.add(String.format("%-20s", reviewRecord[2]) +  String.format("%-10s",("| "+reviewRecord[3])) +  "| "+reviewRecord[4]);
            }
        }
        return reviews;
    }

}
