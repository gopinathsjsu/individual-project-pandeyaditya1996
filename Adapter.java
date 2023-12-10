import java.util.List;

public interface Adapter {
    List<CreditCard> readCardsFromFile(String filename) throws Exception;
    void writeCardsToFile(List<CreditCard> cards, String filename) throws Exception;
}
