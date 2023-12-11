public class Discover extends CreditCard {
    
    public Discover(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {

        return cardNumber.length() == 16 && cardNumber.startsWith("6011");
    }
}
