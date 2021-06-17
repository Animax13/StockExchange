package parser;

import entities.Order;
import entities.enums.OrderAction;
import entities.enums.Stock;
import utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class InputParser {

    public static List<Order> parseInput (List<String> input) {
        List<Order> validOrders = new ArrayList<>();
        if (input != null && !input.isEmpty()) {
            for (String in : input) {
                List<String> components = Arrays.stream(in.split(" ")).collect(Collectors.toList());
                if (components.size() != 6) {
                    System.out.println("Order: " + in + " not in correct format");
                } else {
                    Order order = new Order(components.get(0));
                    if (populateOrderInputIfValid(order, components, in))
                        validOrders.add(order);
                }
            }
        }
        return validOrders;
    }

    private static boolean populateOrderInputIfValid (Order order, List<String> components, String orderInput) {
        return populateTimeIfValid(components.get(1), order, orderInput) &&
                populateStockNameIfValid(components.get(2), order, orderInput) &&
                populateOrderActionIfValid(components.get(3), order, orderInput) &&
                populateQuantityIfValid(components.get(4), order, orderInput) &&
                populatePriceIfValid(components.get(5), order, orderInput);

    }

    private static boolean populateTimeIfValid (String time, Order order, String orderInput) {
        boolean isValid = false;
        Long timeStamp = DateTimeUtil.parseTime(time);
        if (timeStamp != null) {
            order.setTimeStamp(timeStamp);
            isValid = true;
        } else
            System.out.println("Time not valid in Order : " + orderInput);
        return isValid;
    }

    private static boolean populateStockNameIfValid (String stock, Order order, String orderInput) {
        boolean isValid = false;
        try {
            order.setStockName(Enum.valueOf(Stock.class, stock.toUpperCase()));
            isValid = true;
        } catch (Exception ex) {
            System.out.println("Stock name not valid for Order: " +  orderInput);
        }
        return isValid;
    }

    private static boolean populateOrderActionIfValid (String action, Order order, String orderInput) {
        boolean isValid = false;
        try {
            order.setAction(Enum.valueOf(OrderAction.class, action.toUpperCase()));
            isValid = true;
        } catch (Exception ex) {
            System.out.println("Action not valid for Order: " +  orderInput);
        }
        return isValid;
    }

    private static boolean populateQuantityIfValid (String quantityValue, Order order, String orderInput) {
        boolean isValid = false;
        try {
            int quantity = Integer.parseInt(quantityValue);
            if (quantity <= 0)
                throw new RuntimeException();
            order.setQuantity(quantity);
            isValid = true;
        } catch (Exception ex) {
            System.out.println("Quantity not valid for Order: " +  orderInput);
        }
        return isValid;
    }

    private static boolean populatePriceIfValid (String priceValue, Order order, String orderInput) {
        boolean isValid = false;
        try {
            double price = Double.parseDouble(priceValue);
            if (price <= 0)
                throw new RuntimeException();
            order.setPrice(price);
            isValid = true;
        } catch (Exception ex) {
            System.out.println("Price not valid for Order: " +  orderInput);
        }
        return isValid;
    }
}
