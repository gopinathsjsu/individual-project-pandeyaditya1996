public class AmericanExpress extends CreditCard {
    
    public AmericanExpress(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        // American Express card validation logic
        // American Express cards start with 34 or 37 and are 15 digits long
        return (cardNumber.startsWith("34") || cardNumber.startsWith("37")) && cardNumber.length() == 15;
    }
}
