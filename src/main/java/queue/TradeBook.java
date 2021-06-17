package queue;

import entities.Trade;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class TradeBook {

    private final LinkedBlockingQueue<Trade> trades;

    public TradeBook() {
        trades = new LinkedBlockingQueue<>();
    }

    public void addTrade(Trade trade) {
        trades.add(trade);
    }

    public Trade removeTransaction () {
        return trades.poll();
    }

    public List<Trade> getAllTrades() {
        return new ArrayList<>(trades);
    }
}
