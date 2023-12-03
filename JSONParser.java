
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class JSONParser implements FileParser {

    @Override
    public List<CreditCard> parseInputFile(String filePath) {
        List<CreditCard> cards = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String cardNumber = jsonObject.getString("cardNumber");
                CreditCard card = CreditCardFactory.createCreditCard(cardNumber);
                if (card != null) {
                    cards.add(card);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public void writeOutputFile(String filePath, List<CreditCard> cards) {
        // Implement writing logic
    }
}
