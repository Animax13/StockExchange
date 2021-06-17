package queue;

import entities.Order;

import java.util.Comparator;

public class SellerOrderQueue extends OrderQueue {

    @Override
    protected Comparator<Order> getComparator() {
        return Comparator.comparing(Order::getPrice).thenComparing(Order::getTimeStamp);
    }
}
