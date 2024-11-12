package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.cryptoobject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CryptoObjectServiceConfig {

    @Bean
    @Primary
    public CryptoObjectService cryptoObjectService(CryptoObjectServiceImpl impl) {
        CryptoObjectService service = impl;

        service = new ValidatingCryptoObjectService(service);

        service = new LoggingCryptoObjectService(service);

        service = new CachingCryptoObjectService(service);

        return service;
    }
}
