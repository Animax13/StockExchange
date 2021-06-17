package service;

import entities.Order;
import entities.Trade;
import entities.enums.OrderAction;
import entities.enums.Stock;
import queue.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class StockExchangeService {

    private final Map<Stock, BuyerOrderQueue> buyerOrderBook;
    private final Map<Stock, SellerOrderQueue> sellerOrderBook;
    private final TradeBook tradeBook;

    public StockExchangeService() {
        buyerOrderBook = new ConcurrentHashMap<>();
        sellerOrderBook = new ConcurrentHashMap<>();
        tradeBook = new TradeBook();
    }

    // PUBLIC METHODS
    public List<Trade> process (List<Order> orders) {
        List<Trade> trades = null;
        if (orders != null && !orders.isEmpty()) {
            orders = orders.stream().sorted(Comparator.comparing(Order::getTimeStamp)).collect(Collectors.toList());
            orders.forEach(this::processOrder);
            trades = tradeBook.getAllTrades();
        }
        return trades;
    }

    //PRIVATE METHODS
    private void processOrder (Order order) {
        Stock stock = order.getStockName();
        BuyerOrderQueue buyerOrderQueue = buyerOrderBook.getOrDefault(stock, new BuyerOrderQueue());
        SellerOrderQueue sellerOrderQueue = sellerOrderBook.getOrDefault(stock, new SellerOrderQueue());
        if (OrderAction.BUY.equals(order.getAction()))
            buyerOrderQueue.add(order);
        else
            sellerOrderQueue.add(order);
        processTransaction(buyerOrderQueue, sellerOrderQueue);
        buyerOrderBook.put(stock, buyerOrderQueue);
        sellerOrderBook.put(stock, sellerOrderQueue);
    }

    private void processTransaction(BuyerOrderQueue buyerOrderQueue, SellerOrderQueue sellerOrderQueue) {
        while (areOrdersMatching(buyerOrderQueue.getBestOrder(), sellerOrderQueue.getBestOrder())) {
            Order buyOrder = buyerOrderQueue.removeBestOrder();
            Order sellOrder = sellerOrderQueue.removeBestOrder();
            if (buyOrder.getQuantity() >= sellOrder.getQuantity()) {
                tradeBook.addTrade(new Trade(sellOrder.getId(), sellOrder.getQuantity(), sellOrder.getPrice(), buyOrder.getId()));
                buyOrder.setQuantity(buyOrder.getQuantity() - sellOrder.getQuantity());
                if (buyOrder.getQuantity() > 0)
                    buyerOrderQueue.add(buyOrder);
            } else {
                tradeBook.addTrade(new Trade(sellOrder.getId(), buyOrder.getQuantity(), sellOrder.getPrice(), buyOrder.getId()));
                sellOrder.setQuantity(sellOrder.getQuantity() - buyOrder.getQuantity());
                sellerOrderQueue.add(sellOrder);
            }
        }
    }

    private boolean areOrdersMatching(Order buyOrder, Order sellOrder) {
        return  buyOrder != null &&
                sellOrder != null &&
                (buyOrder.getPrice() >= sellOrder.getPrice());
    }
}
