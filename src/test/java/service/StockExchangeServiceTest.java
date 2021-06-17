package service;

import entities.Order;
import entities.Trade;
import entities.enums.OrderAction;
import entities.enums.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockExchangeServiceTest {

    private StockExchangeService target;

    @BeforeEach
    void setUp() {
        target = new StockExchangeService();
    }

    private Order getDummyOrder() {
        Order order = new Order("dummy_id");
        order.setStockName(Stock.BAC);
        order.setQuantity(100);
        order.setAction(OrderAction.BUY);
        order.setPrice(100.0);
        order.setTimeStamp(new Date().getTime());
        return order;
    }

    @Test
    public void process_handlesNull() {
        try {
            List<Trade> response = target.process(null);
            assertNull(response);
        } catch (Exception e) {
            fail("Exception is supposed to be handled");
        }
    }

    @Test
    public void process_singleOrder_noMatch() {
        //Arrange
        Order order = getDummyOrder();

        //Act
        List<Trade> response = target.process(Collections.singletonList(order));

        //Assert
        assertTrue(response.isEmpty());
    }

    @Test
    public void process_orders_match() {
        //Arrange
        Order buyOrder = getDummyOrder();
        Order sellOrder = getDummyOrder();
        sellOrder.setAction(OrderAction.SELL);

        //Act
        List<Trade> response = target.process(Arrays.asList(buyOrder, sellOrder));

        //Assert
        assertEquals(1, response.size());
    }

    @Test
    public void process_orders_misMatch() {
        //Arrange
        Order buyOrder = getDummyOrder();
        buyOrder.setStockName(Stock.TCS);
        Order sellOrder = getDummyOrder();
        sellOrder.setAction(OrderAction.SELL);

        //Act
        List<Trade> response = target.process(Arrays.asList(buyOrder, sellOrder));

        //Assert
        assertEquals(0, response.size());
    }

    @Test
    public void process_orders_match_cheaperSellOrderExecutes() {
        //Arrange
        Order buyOrder = getDummyOrder();

        Order sellOrder1 = getDummyOrder();
        sellOrder1.setAction(OrderAction.SELL);
        sellOrder1.setPrice(97.56);

        Order sellOrder2 = getDummyOrder();
        sellOrder2.setAction(OrderAction.SELL);
        sellOrder2.setPrice(93.57);

        //Act
        List<Trade> response = target.process(Arrays.asList(sellOrder1, sellOrder2, buyOrder));

        //Assert
        assertEquals(response.get(0).getSellingPrice(), 93.57);
    }

    @Test
    public void process_orders_match_expensiveSellOrderExecutesIfItComesBefore() {
        //Arrange
        Order buyOrder = getDummyOrder();

        Order sellOrder1 = getDummyOrder();
        sellOrder1.setAction(OrderAction.SELL);
        sellOrder1.setPrice(97.56);

        Order sellOrder2 = getDummyOrder();
        sellOrder2.setAction(OrderAction.SELL);
        sellOrder2.setPrice(93.57);

        //Act
        List<Trade> response = target.process(Arrays.asList(sellOrder1, buyOrder, sellOrder2));

        //Assert
        assertEquals(response.get(0).getSellingPrice(), 97.56);
    }

    @Test
    public void process_orders_match_expensiveBuyOrderGetsPriority() {
        //Arrange
        Order sellOrder = getDummyOrder();
        sellOrder.setAction(OrderAction.SELL);

        Order buyOrder1 = getDummyOrder();
        buyOrder1.setQuantity(70);
        buyOrder1.setPrice(101.56);
        buyOrder1.setId("b1");

        Order buyOrder2 = getDummyOrder();
        buyOrder2.setQuantity(70);
        buyOrder2.setPrice(103.57);
        buyOrder2.setId("b2");

        //Act
        List<Trade> response = target.process(Arrays.asList(buyOrder1, buyOrder2, sellOrder));

        //Assert
        assertEquals(response.get(0).getBuyOrderID(), "b2");
        assertEquals(response.get(0).getQuantity(), 70);
        assertEquals(response.get(1).getBuyOrderID(), "b1");
        assertEquals(response.get(1).getQuantity(), 30);
    }

    @Test
    public void process_orders_match_expensiveBuyOrderGetsLessPriorityBasedOnOrder() {
        //Arrange
        Order sellOrder = getDummyOrder();
        sellOrder.setAction(OrderAction.SELL);
        sellOrder.setTimeStamp(1);

        Order buyOrder1 = getDummyOrder();
        buyOrder1.setQuantity(70);
        buyOrder1.setPrice(101.56);
        buyOrder1.setId("b1");
        buyOrder1.setTimeStamp(1);

        Order buyOrder2 = getDummyOrder();
        buyOrder2.setQuantity(70);
        buyOrder2.setPrice(103.57);
        buyOrder2.setId("b2");
        buyOrder2.setTimeStamp(2);

        //Act
        List<Trade> response = target.process(Arrays.asList(buyOrder1, buyOrder2, sellOrder));

        //Assert
        assertEquals(response.get(0).getBuyOrderID(), "b1");
        assertEquals(response.get(0).getQuantity(), 70);
        assertEquals(response.get(1).getBuyOrderID(), "b2");
        assertEquals(response.get(1).getQuantity(), 30);
    }
}