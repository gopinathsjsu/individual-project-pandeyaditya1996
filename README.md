[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/MrnpBhWc)

Commands to run on MAC -->

1) javac -cp ".:lib/*" *.java  
2) java -cp '.:lib/*' CreditCardProcessor input_file.xml output_file.xml

Class diagrams will be present in REPO also

**The Primary Problem**

The primary issue I'm addressing is identifying the type of card represented by a record that includes a credit card number, expiration date, and cardholder's name. The main challenge is to read these records, validate the credit card number, determine who issued the card, and create an object of the appropriate credit card class.

**Secondary Problems**

Allowing various file extensions: The system should be able to handle different input file formats (CSV, JSON, XML) and also accept new formats in the future.
Credit Card Validation: Each credit card number must be validated to match if it's a legitimate number and identify the card issuer company.

**Design Pattern:**

**-Strategy Pattern**

In this implementation, I employed the Strategy Pattern. The method isValid() is established in the foundational class CreditCard, with the responsibility for its execution passed on to the specific subclasses (AmExCC, DiscoverCC, MasterCC, VisaCC). This approach empowers each type of credit card to implement its unique validation strategy, thereby facilitating the expansion of the system to incorporate new card types without necessitating changes to the pre-existing code. Additionally, the InvalidCreditCard class adheres to this pattern as well, offering a distinct strategy to manage invalid credit cards.

**Consequences of the Strategy method**

1)As the variety of credit card types expands, there could be an increase in the number of subclasses (like AmexCC, VisaCC etc), which might complicate the management of the system.
2)Employing the Strategy Pattern can add complexity, particularly when handling a multitude of interchangeable algorithms or strategies.
3)Dynamically changing between various strategies during runtime could lead to a minor increase in runtime overhead.

**Secondary Problem**

The secondary problem handled here involves creating the required objects for accommodating different types of input files (e.g., CSV, XML, or JSON).

**-Factory Pattern**

The design pattern I implemented is the Factory Method Pattern, as demonstrated in my CreditCardFactory class. This class is responsible for the creation of CreditCard object instances. It utilizes a method that receives inputs such as cardNumber, expirationDate, and cardHolderName and determines which specific CreditCard subclass to create based on certain criteria. This method employs conditional checks on the cardNumber to identify the credit card type. Depending on these checks, it either instantiates and returns an object of a relevant subclass (such as VisaCC, MasterCC, etc.) or returns null if the card number does not align with any recognized patterns. In this setup, the client code acquires a CreditCard instance through the factory method (getCreditCard), with the actual class of the resulting object varying based on the criteria defined within the factory method.


**Consequences of the Factory method**

1)The complexity of the system might escalate with the introduction of new types of credit cards or the addition of further conditions to the factory method.
2)The direct instantiation of specific classes (MasterCC,VisaCC etc) results in a close interdependence between the Credi class and these particular implementations.
3)There's a potential breach of the open-closed principle should modifications become necessary in the current factory method logic to integrate new card types.


