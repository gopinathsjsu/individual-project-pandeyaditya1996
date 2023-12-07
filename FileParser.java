import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.json.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public interface FileParser {
            List<CreditCard> parseInputFile(String filePath);
            void writeOutputFile(String filePath, List<CreditCard> cards);
        }

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

public class XMLParser implements FileParser {

    @Override
    public List<CreditCard> parseInputFile(String filePath) {
        List<CreditCard> cards = new ArrayList<>();
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("CreditCard");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String cardNumber = eElement.getElementsByTagName("cardNumber").item(0).getTextContent();
                    CreditCard card = CreditCardFactory.createCreditCard(cardNumber);
                    if (card != null) {
                        cards.add(card);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public void writeOutputFile(String filePath, List<CreditCard> cards) {
        // Implement writing logic
    }
}

