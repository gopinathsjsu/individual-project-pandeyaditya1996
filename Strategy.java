import java.util.List;

public abstract class Strategy {
    abstract List<CreditCard> readCardsFromFile(String filename) throws Exception;
    abstract void writeCardsToFile(List<CreditCard> cards, String filename) throws Exception;

    public String determineErrorMessage(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return "Invalid: empty/null card number";
        }
        if (cardNumber.length() > 19) {
            return "Invalid: more than 19 digits";
        }
        if (!cardNumber.matches("\\d+")) {
            return "Invalid: non numeric characters";
        }
        // Add a condition to check for 'not a possible card number'
        if (!isPossibleCardNumber(cardNumber)) {
            return "Invalid: not a possible card number";
        }
        return "";
    }
    
    public boolean isPossibleCardNumber(String cardNumber) {
        // Example logic: Check if the card number starts with certain digits
        // and has a valid length. Adjust this logic according to your specific rules.
        if (cardNumber.startsWith("4") && (cardNumber.length() == 13 || cardNumber.length() == 16)) {
            return true; // Visa
        }
        if (cardNumber.startsWith("5") && cardNumber.length() == 16) {
            return true; // MasterCard
        }
        if ((cardNumber.startsWith("34") || cardNumber.startsWith("37")) && cardNumber.length() == 15) {
            return true; // American Express
        }
        if (cardNumber.startsWith("6011") && cardNumber.length() == 16) {
            return true; // Discover
        }
        return false; // Not a plausible card number
    }
        
}
