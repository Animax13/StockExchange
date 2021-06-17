package queue;

import entities.Order;

import java.util.Comparator;

public class BuyerOrderQueue extends OrderQueue {

    @Override
    protected Comparator<Order> getComparator() {
        return Comparator.comparing(Order::getPrice).reversed().thenComparing(Order::getTimeStamp);
    }
}
