import java.util.List;

public class CreditCardProcessor {

    private static Adapter getAdapter(String filename) {
        if (filename.endsWith(".json")) {
            return new JSONAdapter();
        } else if (filename.endsWith(".csv")) {
            return new CSVAdapter();
        } else if (filename.endsWith(".xml")) {
            return new XMLAdapter();
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }

    public static void processFile(String inputFilename, String outputFilename) {
        try {
            Adapter adapter = getAdapter(inputFilename);

            List<CreditCard> creditCards = adapter.readCardsFromFile(inputFilename);
            adapter.writeCardsToFile(creditCards, outputFilename);

            System.out.println("Processing completed successfully.");
        } catch (Exception e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java CreditCardProcessor <inputFile> <outputFile>");
            return;
        }

        processFile(args[0], args[1]);
    }
}
