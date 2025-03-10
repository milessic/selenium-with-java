package utils;

import utils.InvoiceItem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

public class JsonDataReader {
    private JsonNode rootNode;

    public JsonDataReader(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        rootNode = objectMapper.readTree(new File(filePath));
    }

    public Map<String, String> getHeaders() {
        return extractFields(rootNode.get("headers"));
    }

    public Map<String, String> getFrom() {
        return extractFields(rootNode.get("recipients").get("from"));
    }

    public Map<String, String> getTo() {
        return extractFields(rootNode.get("recipients").get("to"));
    }

    public Map<String, String> getFooter() {
        return extractFields(rootNode.get("footer"));
    }

    private Map<String, String> extractFields(JsonNode node) {
        Map<String, String> data = new HashMap<>();
        if (node != null) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                data.put(entry.getKey(), entry.getValue().asText());
            }
        }
        return data;
    }

    public ArrayList<InvoiceItem> getItems() {
        JsonNode data = rootNode.get("invoiceItems");
		ArrayList returnData = new ArrayList<InvoiceItem>();
		for (int i = 0; i < data.size(); i++){
			returnData.add(
				new InvoiceItem(
					data.get(i).get("itemDescription").asText(),
					data.get(i).get("pkwiuCode").asText(), 
					data.get(i).get("quantity").asText(),
					data.get(i).get("unit").asText(),
					data.get(i).get("unitNetPrice").asText(),
					data.get(i).get("vatRatePercent").asText()
				)
			);
		}
		return returnData;
    }
}
