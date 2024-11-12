package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.graphs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.ExchangeInfo;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.Volume;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.repository.ExchangeInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class TradesGraphStrategyPattern implements GraphStrategyPattern {

    private final ExchangeInfoRepository exchangeInfoRepository;

    @Autowired
    public TradesGraphStrategyPattern(ExchangeInfoRepository exchangeInfoRepository) {
        this.exchangeInfoRepository = exchangeInfoRepository;
    }

    @Override
    public JsonNode generateGraph(int timeInterval) {
        LocalDateTime localDateTime;
        Timestamp timestamp = switch (timeInterval) {
            case 1 -> Timestamp.valueOf(LocalDateTime.now().minusDays(1));
            case 2 -> Timestamp.valueOf(LocalDateTime.now().minusDays(7));
            case 3 -> Timestamp.valueOf(LocalDateTime.now().minusMonths(1));
            default -> throw new IllegalArgumentException("Invalid time interval");
        };

        List<Object[]> resultList = exchangeInfoRepository.fetchVolume(timestamp);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode dataArray = objectMapper.createArrayNode();
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");

        for (Object[] row : resultList) {
            ExchangeInfo exchangeInfo = (ExchangeInfo) row[0];
            Volume volume = (Volume) row[1];

            ObjectNode dataNode = objectMapper.createObjectNode();
            dataNode.put("trades", volume.getTradesDone());
            Timestamp tradeTime = exchangeInfo.getTradeTime();

            String formattedTime;
            if (timeInterval == 1) {
                formattedTime = timeFormat.format(tradeTime);
            } else {
                formattedTime = dateTimeFormat.format(tradeTime);
            }
            dataNode.put("time", formattedTime);
            dataArray.add(dataNode);
        }

        return dataArray;
    }

    @Override
    public String getType() {
        return "trades";
    }
}
