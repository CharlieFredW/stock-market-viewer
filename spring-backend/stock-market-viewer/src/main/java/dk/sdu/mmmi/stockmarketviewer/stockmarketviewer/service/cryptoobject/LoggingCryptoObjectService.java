package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.cryptoobject;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

@Service
public class LoggingCryptoObjectService extends CryptoObjectServiceDecorator {

    public LoggingCryptoObjectService(CryptoObjectService cryptoObjectDecorator) {
        super(cryptoObjectDecorator);
    }

    @Override
    public JsonNode SQLToJson() {
        System.out.println("Fetching crypto object data");
        JsonNode result = super.SQLToJson();
        System.out.println("Fetched data: " + result);
        return result;
    }
}

