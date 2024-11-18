import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JsonToCsv {
    public static void main(String[] args) {
        String jsonFilePath = "data.json"; 
        String csvFilePath = "output.csv"; 

        try {
            System.out.println("start printing");
            // Parse JSON file
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(jsonFilePath));

            // Write to CSV file
            FileWriter csvWriter = new FileWriter(csvFilePath);

            if (rootNode.isArray()) {
                // Extract headers from the first JSON object
                JsonNode firstObject = rootNode.get(0);
                Iterator<String> fieldNames = firstObject.fieldNames();
                while (fieldNames.hasNext()) {
                    csvWriter.append(fieldNames.next()).append(",");
                }
                csvWriter.append("\n");

                // Write rows
                for (JsonNode node : rootNode) {
                    Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
                    while (fields.hasNext()) {
                        Map.Entry<String, JsonNode> field = fields.next();
                        csvWriter.append(field.getValue().asText()).append(",");
                    }
                    csvWriter.append("\n");
                }
            }

            csvWriter.flush();
            csvWriter.close();

            System.out.println("CSV file created successfully at: " + csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
