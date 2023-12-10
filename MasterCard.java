public class MasterCard extends CreditCard {
    
    public MasterCard(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        // MasterCard validation logic
        // MasterCard starts with 5 and the second digit is 1 through 5. Length must be 16 digits.
        if (cardNumber.length() != 16 || cardNumber.charAt(0) != '5') {
            return false;
        }
        char secondDigit = cardNumber.charAt(1);
        return secondDigit >= '1' && secondDigit <= '5';
    }
}
