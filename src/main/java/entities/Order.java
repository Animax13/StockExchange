package entities;

import entities.enums.OrderAction;
import entities.enums.Stock;

public class Order {

    private String id;
    private long timeStamp;
    private Stock stockName;
    private OrderAction action;
    private int quantity; // validation
    private double price; // price

    public Order(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public Stock getStockName() {
        return stockName;
    }

    public OrderAction getAction() {
        return action;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setStockName(Stock stockName) {
        this.stockName = stockName;
    }

    public void setAction(OrderAction action) {
        this.action = action;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
