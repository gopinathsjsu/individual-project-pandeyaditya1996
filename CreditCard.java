// package java-individual-project;

public abstract class CreditCard {
            protected String cardNumber;
            protected String expirationDate;
            protected String cardHolderName;

            // Constructor, getters, setters
        }

        public class VisaCC extends CreditCard {
            // Visa specific logic
        }

        public class MasterCC extends CreditCard {
            // MasterCard specific logic
        }

        public class AmExCC extends CreditCard {
            // American Express specific logic
        }

        // Additional subclasses for other card types