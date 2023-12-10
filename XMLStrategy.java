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

public class XMLStrategy extends Strategy {

    @Override
    public List<CreditCard> readCardsFromFile(String filename) throws Exception {
        List<CreditCard> cards = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File(filename));
        NodeList cardNodes = document.getElementsByTagName("CARD");

        for (int i = 0; i < cardNodes.getLength(); i++) {
            Element cardElement = (Element) cardNodes.item(i);
            String cardNumber = cardElement.getElementsByTagName("CARD_NUMBER").item(0).getTextContent();
            String expirationDate = cardElement.getElementsByTagName("EXPIRATION_DATE").item(0).getTextContent();
            String cardHolderName = cardElement.getElementsByTagName("CARD_HOLDER_NAME").item(0).getTextContent();

            String errorMessage = determineErrorMessage(cardNumber);
            CreditCard card;
            if (!errorMessage.isEmpty()) {
                card = new InvalidCreditCard(cardNumber, expirationDate, cardHolderName, errorMessage);
            } else {
                card = CreditCardFactory.getCreditCard(cardNumber, expirationDate, cardHolderName);
            }
            
            if (card != null) {
                cards.add(card);
            }
        }

        return cards;
    }

    @Override
    public void writeCardsToFile(List<CreditCard> cards, String filename) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
    
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

                cardTypeElement.setTextContent(getCardType(card));
            }
            cardElement.appendChild(cardTypeElement);
    
            rootElement.appendChild(cardElement);
        }
    
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty("indent", "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(source, result);
    }
    
    private String getCardType(CreditCard card) {

        return card.getClass().getSimpleName().replace("CC", "");
    }
    
}
