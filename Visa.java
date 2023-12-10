public class Visa extends CreditCard {
    
    public Visa(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        // Visa card validation logic
        // Visa cards start with 4 and are either 13 or 16 digits long
        return cardNumber.startsWith("4") && (cardNumber.length() == 13 || cardNumber.length() == 16);
    }
}
