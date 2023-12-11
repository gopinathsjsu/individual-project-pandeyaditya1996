public class Visa extends CreditCard {
    
    public Visa(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {

        return cardNumber.startsWith("4") && (cardNumber.length() == 13 || cardNumber.length() == 16);
    }
}
