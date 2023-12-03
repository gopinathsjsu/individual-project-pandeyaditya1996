import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVParser implements FileParser {

    @Override
    public List<CreditCard> parseInputFile(String filePath) {
        List<CreditCard> cards = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                CreditCard card = CreditCardFactory.createCreditCard(values[0]);
                // Assuming the first value is the card number
                // Additional logic can be added to handle other fields
                if (card != null) {
                    cards.add(card);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public void writeOutputFile(String filePath, List<CreditCard> cards) {
        // Implement writing logic
    }
}
