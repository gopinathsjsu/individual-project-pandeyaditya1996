public class CreditCardFactory {

    public static CreditCard getCreditCard(String cardNumber, String expirationDate, String cardHolderName) 
    {
        if (cardNumber.startsWith("4") && (cardNumber.length() == 13 || cardNumber.length() == 16)) 
        {
            return new Visa(cardNumber, expirationDate, cardHolderName);
        } 
        else if (cardNumber.startsWith("5") && cardNumber.length() == 16) 
        {
            return new MasterCard(cardNumber, expirationDate, cardHolderName);
        } 
        else if ((cardNumber.startsWith("34") || cardNumber.startsWith("37")) && cardNumber.length() == 15) 
        {
            return new AmericanExpress(cardNumber, expirationDate, cardHolderName);
        } 
        else if (cardNumber.startsWith("6011") && cardNumber.length() == 16) 
        {
            return new Discover(cardNumber, expirationDate, cardHolderName);
        } 
        else 
        {
            return null; 
        }
    }
}