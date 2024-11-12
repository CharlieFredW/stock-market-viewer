package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.ExchangeInfo;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.TradeValue;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.Volume;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.repository.ExchangeInfoRepository;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.repository.TradeValueRepository;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.repository.VolumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Service
@EnableScheduling
public class APICallerService {

    private final RestTemplate restTemplate;
    private final TradeValueRepository tradeValueRepository;
    private final VolumeRepository volumeRepository;
    private final ExchangeInfoRepository exchangeInfoRepository;

    private final CacheManager cacheManager;

    @Autowired
    public APICallerService(TradeValueRepository tradeValueRepository,
                            VolumeRepository volumeRepository, ExchangeInfoRepository exchangeInfoRepository, CacheManager cacheManager) {
        this.restTemplate = new RestTemplate();
        this.tradeValueRepository = tradeValueRepository;
        this.volumeRepository = volumeRepository;
        this.exchangeInfoRepository = exchangeInfoRepository;
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedRate = 300000)
    public void callApiAndPersist() {
        String apiUrl = "Insert here";
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                for (JsonNode objectNode : jsonNode) {
                    JsonNode priceDataNode = objectNode.get("priceData");
                    String ticker = objectNode.get("ticker").asText();
                    if (priceDataNode != null && priceDataNode.isArray() && !priceDataNode.isEmpty()) {
                        JsonNode lastObject = priceDataNode.get(priceDataNode.size() - 1);
                        persistData(lastObject, ticker);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("API failed with status code: " + response.getBody());
        }
    }

    private void persistData(JsonNode cryptoJSON, String ticker) {
        TradeValue tradeValue = tradeValueRepository.save(new TradeValue(
                cryptoJSON.get("high").asDouble(),
                calculateMid(cryptoJSON.get("high").asDouble(), cryptoJSON.get("low").asDouble()),
                cryptoJSON.get("low").asDouble(),
                cryptoJSON.get("open").asDouble(),
                cryptoJSON.get("close").asDouble()
        ));

        Volume volume = volumeRepository.save(new Volume(
                cryptoJSON.get("volume").asDouble(),
                cryptoJSON.get("volumeNotional").asDouble(),
                cryptoJSON.get("tradesDone").asDouble()
        ));

        ExchangeInfo exchangeInfo = new ExchangeInfo();
        exchangeInfo.setTradeValuesId(tradeValue.getId());
        exchangeInfo.setVolumeId(volume.getId());
        exchangeInfo.setTicker(ticker);
        exchangeInfo.setTradeTime(new Timestamp(System.currentTimeMillis()));

        exchangeInfoRepository.save(exchangeInfo);

        cacheManager.getCache("cryptoObjects").clear();

        System.out.println("Crypto Data inserted successfully");
    }

    private double calculateMid(double a, double b) {
        return (a + b) / 2;
    }
}
