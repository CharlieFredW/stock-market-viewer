package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.cryptoobject;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class CryptoObjectServiceDecorator implements CryptoObjectService {
    protected final CryptoObjectService cryptoObjectDecorator;

    public CryptoObjectServiceDecorator(CryptoObjectService cryptoObjectDecorator) {
        this.cryptoObjectDecorator = cryptoObjectDecorator;
    }

    @Override
    public JsonNode SQLToJson() {
        return cryptoObjectDecorator.SQLToJson();
    }
}

