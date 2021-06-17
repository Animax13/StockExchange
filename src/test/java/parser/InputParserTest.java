package parser;

import entities.Order;
import entities.enums.Stock;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    @Test
    void parseInput_handlesNull() {
        try {
            List<Order> response = InputParser.parseInput(null);
            assertTrue(response.isEmpty());
        } catch (Exception e) {
            fail("Exception is supposed to be handled");
        }
    }

    @Test
    void parseInput_correctInput() {
        //Arrange
        List<String> input = Collections.singletonList("#5 09:48 BAC sell 220 241.50");

        //Act
        List<Order> parsedInput = InputParser.parseInput(input);

        //Assert
        assertEquals(1, parsedInput.size());
        assertEquals(Stock.BAC, parsedInput.get(0).getStockName());
    }

    @Test
    void parseInput_inCorrectInputFormat() {
        //Arrange
        List<String> input = Collections.singletonList("#5 09:48 BAC 220 241.50");

        //Act
        List<Order> parsedInput = InputParser.parseInput(input);

        //Assert
        assertEquals(0, parsedInput.size());
    }

    @Test
    void parseInput_inCorrectTime() {
        //Arrange
        List<String> input = Collections.singletonList("#5 09:48y BAC sell 220 241.50");

        //Act
        List<Order> parsedInput = InputParser.parseInput(input);

        //Assert
        assertEquals(0, parsedInput.size());
    }

    @Test
    void parseInput_inCorrectStock() {
        //Arrange
        List<String> input = Collections.singletonList("#5 09:48 BAQ sell 220 241.50");

        //Act
        List<Order> parsedInput = InputParser.parseInput(input);

        //Assert
        assertEquals(0, parsedInput.size());
    }

    @Test
    void parseInput_inCorrectAction() {
        //Arrange
        List<String> input = Collections.singletonList("#5 09:48 BAC hold 220 241.50");

        //Act
        List<Order> parsedInput = InputParser.parseInput(input);

        //Assert
        assertEquals(0, parsedInput.size());
    }

    @Test
    void parseInput_inCorrectQuantity() {
        //Arrange
        List<String> input = Collections.singletonList("#5 09:48 BAC buy 0 241.50");

        //Act
        List<Order> parsedInput = InputParser.parseInput(input);

        //Assert
        assertEquals(0, parsedInput.size());
    }

    @Test
    void parseInput_inCorrectPrice() {
        //Arrange
        List<String> input = Collections.singletonList("#5 09:48 BAC buy 1 -7.56");

        //Act
        List<Order> parsedInput = InputParser.parseInput(input);

        //Assert
        assertEquals(0, parsedInput.size());
    }
}