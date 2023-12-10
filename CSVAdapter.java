import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CSVAdapter implements Adapter {

    @Override
    public List<CreditCard> readCardsFromFile(String filename) throws Exception {
        List<CreditCard> cards = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Skip header line
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String cardNumber = values.length > 0 ? values[0] : "";
                String expirationDate = values.length > 1 ? values[1] : "";
                String cardHolderName = values.length > 2 ? values[2] : "";

                String errorMessage = determineErrorMessage(cardNumber);
                CreditCard card = errorMessage.isEmpty() ?
                    CreditCardFactory.getCreditCard(cardNumber, expirationDate, cardHolderName) :
                    new InvalidCreditCard(cardNumber, expirationDate, cardHolderName, errorMessage);

                if (card != null && card.isValid()) {
                    cards.add(card);
                } else {
                    cards.add(card);
                }
            }
        }
        return cards;
    }

    private String determineErrorMessage(String cardNumber) {
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
    
    private boolean isPossibleCardNumber(String cardNumber) {
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
    
    

    @Override
    public void writeCardsToFile(List<CreditCard> cards, String filename) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write the header line
            writer.write("cardNumber,cardType");
            writer.newLine();
    
            // Write the card details
            for (CreditCard card : cards) {
                if (card == null) continue;  // Skip null cards
    
                String cardNumber = card.getCardNumber() != null ? card.getCardNumber() : "null";
                String cardType = card instanceof InvalidCreditCard ?
                    ((InvalidCreditCard) card).getErrorMessage() :
                    card.getClass().getSimpleName().replace("CC", "");
                writer.write(cardNumber + "," + cardType);
                writer.newLine();
            }
        }
    }
    
}