package entities;

public class Trade {

    private final String sellerOrderID;
    private final int quantity;
    private final double sellingPrice;
    private final String buyOrderID;

    public Trade(String sellerOrderID, int quantity, double sellingPrice, String buyOrderID) {
        this.sellerOrderID = sellerOrderID;
        this.quantity = quantity;
        this.sellingPrice = sellingPrice;
        this.buyOrderID = buyOrderID;
    }

    public String getSellerOrderID() {
        return sellerOrderID;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public String getBuyOrderID() {
        return buyOrderID;
    }
}
