import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONAdapter implements Adapter {

    @Override
    public List<CreditCard> readCardsFromFile(String filename) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        JSONObject jsonObject = new JSONObject(content);
        JSONArray cardsArray = jsonObject.getJSONArray("cards");
        List<CreditCard> cards = new ArrayList<>();

        for (int i = 0; i < cardsArray.length(); i++) {
            JSONObject cardObject = cardsArray.getJSONObject(i);
            String cardNumber = cardObject.optString("cardNumber", null);
            String expirationDate = cardObject.optString("expirationDate", null);
            String cardHolderName = cardObject.optString("cardHolderName", null);

            // Inside the readCardsFromFile method...
            String errorMessage = determineErrorMessage(cardNumber);
            CreditCard card;
            if (errorMessage.isEmpty()) {
                card = CreditCardFactory.getCreditCard(cardNumber, expirationDate, cardHolderName);
            } else {
                card = new InvalidCreditCard(cardNumber, expirationDate, cardHolderName, errorMessage);
            }
            
            // Add this null check
            if (card != null) {
                cards.add(card);
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
        JSONObject jsonObject = new JSONObject();
        JSONArray cardsArray = new JSONArray();

        for (CreditCard card : cards) {
            JSONObject cardObject = new JSONObject();
            cardObject.put("cardNumber", card.getCardNumber() != null ? card.getCardNumber() : JSONObject.NULL);
            cardObject.put("cardType", card instanceof InvalidCreditCard ?
                ((InvalidCreditCard) card).getErrorMessage() :
                card.getClass().getSimpleName().replace("CC", ""));
            cardsArray.put(cardObject);
        }

        jsonObject.put("cards", cardsArray);
        Files.write(Paths.get(filename), jsonObject.toString(4).getBytes());
    }
}