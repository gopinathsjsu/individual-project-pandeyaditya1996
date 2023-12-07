import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.json.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public abstract class FileParser {
    List<CreditCard> parseInputFile(String filePath);
    void writeOutputFile(String filePath, List<CreditCard> cards);
        

    public String getCardType(CreditCard card) {
        String cardNumber = card.getCardNumber();

        // Visa: First digit is 4. Length is either 13 or 16 digits.
        if (cardNumber.startsWith("4") && (cardNumber.length() == 13 || cardNumber.length() == 16)) {
            return "Visa";
        }

        // MasterCard: First digit is 5, second digit is in range 1 through 5 inclusive. Length is 16 digits.
        else if (cardNumber.startsWith("5") && cardNumber.substring(1, 2).matches("[1-5]") && cardNumber.length() == 16) {
            return "MasterCard";
        }

        // American Express: First digit is 3 and second digit is 4 or 7. Length is 15 digits.
        else if (cardNumber.startsWith("3") && (cardNumber.substring(1, 2).equals("4") || cardNumber.substring(1, 2).equals("7")) && cardNumber.length() == 15) {
            return "AmericanExpress";
        }

        // Discover: First four digits are 6011. Length is 16 digits.
        else if (cardNumber.startsWith("6011") && cardNumber.length() == 16) {
            return "Discover";
        }

        // Check for invalid or unsupported card type
        else {
            return "Invalid: Unsupported card type";
        }
    }
}


class CSVParser extends FileParser {

    @Override
    public List<CreditCard> parseInputFile(String filePath) {
        List<CreditCard> cards = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                CreditCard card = CreditCardFactory.createCreditCard(values[0]);
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("cardNumber,cardType\n");
            for (CreditCard card : cards) {
                String cardType = getCardType(card); // Assume getCardType returns the card type or error message
                bw.write(card.getCardNumber() + "," + cardType + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class JSONParser extends FileParser {

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
        JSONObject root = new JSONObject();
        JSONArray cardArray = new JSONArray();
        for (CreditCard card : cards) {
            JSONObject cardObject = new JSONObject();
            cardObject.put("cardNumber", card.getCardNumber());
            cardObject.put("cardType", getCardType(card)); // Assume getCardType returns the card type or error message
            cardArray.put(cardObject);
        }
        root.put("cards", cardArray);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(root.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class XMLParser extends FileParser {

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
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("CARDS");
            document.appendChild(root);

            for (CreditCard card : cards) {
                Element cardElement = document.createElement("CARD");
                root.appendChild(cardElement);

                Element numberElement = document.createElement("CARD_NUMBER");
                numberElement.appendChild(document.createTextNode(card.getCardNumber()));
                cardElement.appendChild(numberElement);

                Element typeElement = document.createElement("CARD_TYPE");
                typeElement.appendChild(document.createTextNode(getCardType(card)));
                cardElement.appendChild(typeElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException | IOException e) {
            e.printStackTrace();
        }
    }
}


