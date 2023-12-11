
import static org.junit.Assert.*;
import org.junit.Test;

public class MasterCardTest {

    @Test
    public void testIsValid() {
        CreditCard card = new MasterCard("valid_card_number", "12/25", "John Doe");
        assertTrue(card.isValid());

        card = new MasterCard("invalid_card_number", "12/25", "John Doe");
        assertFalse(card.isValid());
    }
}
