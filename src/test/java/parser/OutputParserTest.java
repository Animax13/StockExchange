package parser;

import entities.Trade;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OutputParserTest {

    @Test
    void parseOutput_handlesNull() {
        try {
            List<String> response = OutputParser.parseOutput(null);
            assertNull(response);
        } catch (Exception e) {
            fail("Exception is supposed to be handled");
        }
    }

    @Test
    void parseOutput_correctOutput() {
        //Arrange
        List<Trade> trades = Collections.singletonList(new Trade("dummy_seller_id", 100, 100.0, "dummy_buyer_id"));

        //Act
        List<String> response = OutputParser.parseOutput(trades);

        //Assert
        assertEquals(1, response.size());
        assertEquals("dummy_seller_id 100 100.0 dummy_buyer_id", response.get(0));
    }
}