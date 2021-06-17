package queue;

import entities.Order;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class OrderQueue {

    protected PriorityBlockingQueue<Order> orders;

    public OrderQueue() {
        orders = new PriorityBlockingQueue<>(11, getComparator());
    }

    protected abstract Comparator<Order> getComparator();

    public void add (Order order) {
        orders.offer(order);
    }

    public Order getBestOrder() {
        return orders.peek();
    }

    public Order removeBestOrder() {
        return orders.poll();
    }
}
