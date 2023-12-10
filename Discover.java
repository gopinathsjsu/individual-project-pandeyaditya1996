public class Discover extends CreditCard {
    
    public Discover(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        // Discover card validation logic
        // Discover cards start with 6011 and are 16 digits long
        return cardNumber.startsWith("6011") && cardNumber.length() == 16;
    }
}
