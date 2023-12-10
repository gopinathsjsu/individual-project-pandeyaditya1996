import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONStrategy extends Strategy {

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

            String errorMessage = determineErrorMessage(cardNumber);
            CreditCard card;
            if (errorMessage.isEmpty()) {
                card = CreditCardFactory.getCreditCard(cardNumber, expirationDate, cardHolderName);
            } else {
                card = new InvalidCreditCard(cardNumber, expirationDate, cardHolderName, errorMessage);
            }
            
            if (card != null) {
                cards.add(card);
            }

        }

        return cards;
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