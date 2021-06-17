import entities.Order;
import entities.Trade;
import parser.InputParser;
import parser.OutputParser;
import service.StockExchangeService;

import java.util.Arrays;
import java.util.List;

public class StockExchange {

    private static List<String> executeOrders (List<String> orders) {
        List<Order> parsedOrders = InputParser.parseInput(orders);
        List<Trade> executedTrades = new StockExchangeService().process(parsedOrders);
        return OutputParser.parseOutput(executedTrades);
    }

    public static void main(String[] args) {
        List<String> orders = Arrays.asList(
                "#1 09:45 BAC sell 100 240.10",
                "#2 09:45 BAC sell 90 237.45",
                "#3 09:47 BAC buy 80 238.10",
                "#5 09:48 BAC sell 220 241.50",
                "#6 09:49 BAC buy 50 238.50",
                "#7 09:52 TCS buy 10 1001.10",
                "#8 10:01 BAC sell 20 240.10",
                "#9 10:02 BAC buy 150 242.70"
        );

        List<String> executedOrders = executeOrders(orders);
        System.out.println("EXECUTED ORDERS : ");
        executedOrders.forEach(System.out::println);
    }
}
