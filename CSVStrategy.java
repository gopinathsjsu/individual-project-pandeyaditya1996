import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CSVStrategy extends Strategy {

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