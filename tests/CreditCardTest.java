
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CreditCardTest {

    CreditCard creditCard;

    @Before
    public void setUp() {
        creditCard = new CreditCard("1234567890123456", "12/25", "John Doe") {
            @Override
            public boolean isValid() {
                return true; // Stub implementation for testing
            }
        };
    }

    @Test
    public void testCardNumber() {
        assertEquals("1234567890123456", creditCard.getCardNumber());
    }

    @Test
    public void testCardHolderName() {
        assertEquals("John Doe", creditCard.getCardHolderName());
    }

    @Test
    public void testExpirationDate() {
        assertEquals("12/25", creditCard.getExpirationDate());
    }
}
