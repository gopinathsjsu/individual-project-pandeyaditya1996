
import static org.junit.Assert.*;
import org.junit.Test;

public class InvalidCreditCardTest {

    @Test
    public void testIsValid() {
        CreditCard card = new InvalidCreditCard("valid_card_number", "12/25", "John Doe");
        assertTrue(card.isValid());

        card = new InvalidCreditCard("invalid_card_number", "12/25", "John Doe");
        assertFalse(card.isValid());
    }
}
