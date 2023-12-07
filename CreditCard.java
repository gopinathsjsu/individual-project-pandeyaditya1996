// package java-individual-project;

public abstract class CreditCard {
    protected String cardNumber;

    // Constructor
    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    // Getters
    public String getCardNumber() {
        return cardNumber;
    }

    // Setters
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
class VisaCC extends CreditCard {
    // Visa specific logic
    public VisaCC(String cardNumber) {
        super(cardNumber);
    }

    // Additional Visa-specific methods
    // ...
}

class MasterCC extends CreditCard {
    // MasterCard specific logic
    public MasterCC(String cardNumber) {
        super(cardNumber);
    }

    // Additional MasterCard-specific methods
    // ...
}

class AmExCC extends CreditCard {
    // American Express specific logic
    public AmExCC(String cardNumber) {
        super(cardNumber);
    }

}

class DiscoverCC extends CreditCard {
    // American Express specific logic
    public DiscoverclearCC(String cardNumber) {
        super(cardNumber);
    }

}


