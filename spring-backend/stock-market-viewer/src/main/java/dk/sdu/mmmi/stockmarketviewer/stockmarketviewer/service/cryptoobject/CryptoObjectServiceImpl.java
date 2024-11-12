package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.cryptoobject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.ExchangeInfo;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.TradeValue;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.Volume;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.repository.ExchangeInfoRepository;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.repository.TradeValueRepository;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.repository.VolumeRepository;


@Service
public class CryptoObjectServiceImpl implements CryptoObjectService {

    private final TradeValueRepository tradeValueRepository;
    private final VolumeRepository volumeRepository;
    private final ExchangeInfoRepository exchangeInfoRepository;

    @Autowired
    public CryptoObjectServiceImpl(TradeValueRepository tradeValueRepository, VolumeRepository volumeRepository, ExchangeInfoRepository exchangeInfoRepository) {
        this.tradeValueRepository = tradeValueRepository;
        this.volumeRepository = volumeRepository;
        this.exchangeInfoRepository = exchangeInfoRepository;
    }

    @Override
    public JsonNode SQLToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode dataArray = objectMapper.createArrayNode();

        TradeValue latestTradeValue = tradeValueRepository.findTopByOrderByIdDesc();
        Volume latestVolume = volumeRepository.findTopByOrderByIdDesc();
        ExchangeInfo latestExchangeInfo = exchangeInfoRepository.findTopByOrderByIdDesc();

        if (latestTradeValue != null && latestVolume != null && latestExchangeInfo != null) {
            ObjectNode dataNode = objectMapper.createObjectNode();
            dataNode.put("open", latestTradeValue.getOpenValue());
            dataNode.put("high", latestTradeValue.getHighValue());
            dataNode.put("low", latestTradeValue.getLowValue());
            dataNode.put("close", latestTradeValue.getCloseValue());
            dataNode.put("mid", latestTradeValue.getMidValue());
            dataNode.put("volume", latestVolume.getVolume());
            dataNode.put("volumeNotional", latestVolume.getVolumeNotional());
            dataNode.put("tradesDone", latestVolume.getTradesDone());
            dataNode.put("date", latestExchangeInfo.getTradeTime().toString());
            dataNode.put("ticker", latestExchangeInfo.getTicker());

            dataArray.add(dataNode);
        }

        return dataArray;
    }

}
