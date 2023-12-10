import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLAdapter implements Adapter {

    @Override
    public List<CreditCard> readCardsFromFile(String filename) throws Exception {
        List<CreditCard> cards = new ArrayList<>();

        // Create a DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Parse the XML file
        Document document = builder.parse(new File(filename));
        NodeList cardNodes = document.getElementsByTagName("CARD");

        for (int i = 0; i < cardNodes.getLength(); i++) {
            Element cardElement = (Element) cardNodes.item(i);
            String cardNumber = cardElement.getElementsByTagName("CARD_NUMBER").item(0).getTextContent();
            String expirationDate = cardElement.getElementsByTagName("EXPIRATION_DATE").item(0).getTextContent();
            String cardHolderName = cardElement.getElementsByTagName("CARD_HOLDER_NAME").item(0).getTextContent();

            // Determine the card type and handle invalid card numbers
            String errorMessage = determineErrorMessage(cardNumber);
            CreditCard card;
            if (!errorMessage.isEmpty()) {
                card = new InvalidCreditCard(cardNumber, expirationDate, cardHolderName, errorMessage);
            } else {
                card = CreditCardFactory.getCreditCard(cardNumber, expirationDate, cardHolderName);
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
        // Create a DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
    
        // Create a new XML document
        Document document = builder.newDocument();
        Element rootElement = document.createElement("CARDS");
        document.appendChild(rootElement);
    
        for (CreditCard card : cards) {
            Element cardElement = document.createElement("CARD");
    
            Element cardNumberElement = document.createElement("CARD_NUMBER");
            cardNumberElement.setTextContent(card.getCardNumber());
            cardElement.appendChild(cardNumberElement);
    
            Element cardTypeElement = document.createElement("CARD_TYPE");
            if (card instanceof InvalidCreditCard) {
                cardTypeElement.setTextContent(((InvalidCreditCard) card).getErrorMessage());
            } else {
                // Set the card type based on the class name or a specific method
                cardTypeElement.setTextContent(getCardType(card));
            }
            cardElement.appendChild(cardTypeElement);
    
            rootElement.appendChild(cardElement);
        }
    
        // Configure formatting for proper indentation
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty("indent", "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    
        // Write the XML document to a file
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(source, result);
    }
    
    private String getCardType(CreditCard card) {
        // Implement the logic to determine the card type based on the card instance.
        // This could be as simple as returning the class name, or more complex logic based on card properties.
        // Example:
        return card.getClass().getSimpleName().replace("CC", "");
    }
    
}
