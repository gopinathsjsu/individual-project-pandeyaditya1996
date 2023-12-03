
import static org.junit.Assert.*;
import org.junit.Test;

public class CreditCardFactoryTest {

    @Test
    public void testCreateMasterCard() {
        CreditCard card = CreditCardFactory.createCreditCard("5123456789012345");
        assertTrue(card instanceof MasterCC);
    }

    @Test
    public void testCreateVisaCard() {
        CreditCard card = CreditCardFactory.createCreditCard("4123456789012");
        assertTrue(card instanceof VisaCC);
    }

    @Test
    public void testCreateAmExCard() {
        CreditCard card = CreditCardFactory.createCreditCard("341234567890123");
        assertTrue(card instanceof AmExCC);
    }

    @Test
    public void testCreateInvalidCard() {
        CreditCard card = CreditCardFactory.createCreditCard("1234567890123456");
        assertNull(card);
    }
}
