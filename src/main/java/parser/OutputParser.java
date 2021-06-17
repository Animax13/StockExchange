package parser;

import entities.Trade;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OutputParser {

    private static final String SPACE = " ";

    public static List<String> parseOutput (List<Trade> trades) {
        List<String> parsedOutput = null;
        if (trades!=null && !trades.isEmpty())
            parsedOutput =  trades.stream().map(parse).collect(Collectors.toList());
        return parsedOutput;
    }

    private static final Function<Trade, String> parse = trade -> trade.getSellerOrderID() + SPACE
            + trade.getQuantity() + SPACE
            + trade.getSellingPrice() + SPACE
            + trade.getBuyOrderID();
}
