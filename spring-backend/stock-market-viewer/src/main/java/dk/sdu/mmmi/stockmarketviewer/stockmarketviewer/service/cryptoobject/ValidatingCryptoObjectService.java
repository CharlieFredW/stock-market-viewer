package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.cryptoobject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

@Service
public class ValidatingCryptoObjectService extends CryptoObjectServiceDecorator {

    public ValidatingCryptoObjectService(CryptoObjectService cryptoObjectDecorator) {
        super(cryptoObjectDecorator);
    }

    @Override
    public JsonNode SQLToJson() {
        JsonNode jsonData = super.SQLToJson();
        if (!validateJsonData(jsonData)) {
            return createErrorJsonNode();
        }

        return jsonData;
    }

    private boolean validateJsonData(JsonNode jsonData) {
        if (jsonData != null && jsonData.isArray()) {
            for (JsonNode dataNode : jsonData) {
                double open = dataNode.path("open").asDouble();
                double high = dataNode.path("high").asDouble();
                double low = dataNode.path("low").asDouble();
                double close = dataNode.path("close").asDouble();
                double mid = dataNode.path("mid").asDouble();
                double volume = dataNode.path("volume").asDouble();
                double volumeNotional = dataNode.path("volumeNotional").asDouble();
                double tradesDone = dataNode.path("tradesDone").asDouble();

                if (open < 0 || high < 0 || low < 0 || close < 0 || mid < 0 || volume < 0 || volumeNotional < 0 || tradesDone < 0) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private JsonNode createErrorJsonNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("error", "Validation failed: Negative values are not allowed.");
        return errorNode;
    }
}