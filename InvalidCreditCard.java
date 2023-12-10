public class InvalidCreditCard extends CreditCard {
    private final String errorMessage;

    public InvalidCreditCard(String cardNumber, String expirationDate, String cardHolderName, String errorMessage) {
        super(cardNumber, expirationDate, cardHolderName);
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}