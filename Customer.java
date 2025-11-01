import java.util.Random;

public class Customer {
    public enum OrderType { BURGER, HOTDOG, FRIES, SODA }

    private double patience = 100.0;
    private boolean isAngry = false;
    private String orderText;
    private double orderPrice;
    private int imageIndex;
    private OrderType orderType;

    public Customer() {
        Random rand = new Random();
        this.imageIndex = rand.nextInt(5); // We have 5 different customer images
        generateOrder(rand);
    }

    private void generateOrder(Random rand) {
        // Randomly pick one of the 4 menu items
        int pick = rand.nextInt(4);
        switch (pick) {
            case 0:
                orderType = OrderType.BURGER;
                orderText = "Burger";
                orderPrice = 100.00;
                break;
            case 1:
                orderType = OrderType.HOTDOG;
                orderText = "Hotdog";
                orderPrice = 80.00;
                break;
            case 2:
                orderType = OrderType.FRIES;
                orderText = "Fries";
                orderPrice = 60.00;
                break;
            default:
                orderType = OrderType.SODA;
                orderText = "Soda";
                orderPrice = 30.00;
                break;
        }
    }

    public void decreasePatience() {
        patience -= 2; // Decrease by 2% every tick
        if (patience <= 50 && !isAngry) {
            isAngry = true;
        }
        if (patience < 0) patience = 0;
    }

    public double getPatience() {
        return patience;
    }

    public boolean isAngry() {
        return isAngry;
    }

    public String getOrder() {
        return orderText;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public OrderType getOrderType() {
        return orderType;
    }
}