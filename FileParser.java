public interface FileParser {
            List<CreditCard> parseInputFile(String filePath);
            void writeOutputFile(String filePath, List<CreditCard> cards);
        }

        public class CSVParser implements FileParser {
            // CSV parsing logic
        }

        public class JSONParser implements FileParser {
            // JSON parsing logic
        }

        public class XMLParser implements FileParser {
            // XML parsing logic
        }