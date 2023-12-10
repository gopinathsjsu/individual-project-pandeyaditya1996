public abstract class CreditCard {
    protected String cardNumber;
    protected String expirationDate;
    protected String cardHolderName;

    public CreditCard(String cardNumber, String expirationDate, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cardHolderName = cardHolderName;
    }

    // Abstract method to validate card number
    public abstract boolean isValid();

    // Getters
    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    // You can add other common methods here if needed
}