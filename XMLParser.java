
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;
import javax.xml.parsers.*;

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
