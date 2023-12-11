public abstract class CreditCard {
    protected String cardNumber;
    protected String expirationDate;
    protected String cardHolderName;

    public CreditCard(String cardNumber, String expirationDate, String cardHolderName) {
        this.expirationDate = expirationDate;
        this.cardHolderName = cardHolderName;        
        this.cardNumber = cardNumber;
    }

    public abstract boolean isValid();

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

}