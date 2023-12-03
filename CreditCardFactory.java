public class CreditCardFactory {

    public static CreditCard createCreditCard(String cardNumber) {
        // Determine the type of credit card based on the number
        if (cardNumber.startsWith("5") && (cardNumber.length() == 16)) {
            return new MasterCC(cardNumber);  // Assuming a constructor that takes cardNumber
        } else if (cardNumber.startsWith("4") && (cardNumber.length() == 13 || cardNumber.length() == 16)) {
            return new VisaCC(cardNumber);
        } else if (cardNumber.startsWith("3") && (cardNumber.charAt(1) == '4' || cardNumber.charAt(1) == '7') && cardNumber.length() == 15) {
            return new AmExCC(cardNumber);
        } else if (cardNumber.startsWith("6011") && cardNumber.length() == 16) {
            return new DiscoverCC(cardNumber);  // Assuming a DiscoverCC class is to be created
        } else {
            return null;  // Invalid or unsupported card type
        }
    }
}
