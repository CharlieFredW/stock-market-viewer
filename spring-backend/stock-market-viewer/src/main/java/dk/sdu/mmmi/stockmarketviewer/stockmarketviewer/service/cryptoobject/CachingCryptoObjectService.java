package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.cryptoobject;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachingCryptoObjectService extends CryptoObjectServiceDecorator {

    public CachingCryptoObjectService(CryptoObjectService cryptoObjectDecorator) {
        super(cryptoObjectDecorator);
    }

    @Override
    @Cacheable("cryptoObjects")
    public JsonNode SQLToJson() {
        return super.SQLToJson();
    }
}