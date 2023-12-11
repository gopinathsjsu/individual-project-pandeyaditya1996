
import static org.junit.Assert.*;
import org.junit.Test;

public class AmericanExpressTest {

    @Test
    public void testIsValid() {
        CreditCard card = new AmericanExpress("valid_card_number", "12/25", "John Doe");
        assertTrue(card.isValid());

        card = new AmericanExpress("invalid_card_number", "12/25", "John Doe");
        assertFalse(card.isValid());
    }
}
