package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.graphs;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GraphContext implements GraphContextService{

    private final Map<String, GraphStrategyPattern> graphPatterns;

    @Autowired
    public GraphContext(List<GraphStrategyPattern> strategyList) {
        graphPatterns = strategyList.stream().collect(Collectors.toMap(GraphStrategyPattern::getType, strategy -> strategy));
    }

    @Override
    public JsonNode generateGraph(String type, int timeInterval) {
        GraphStrategyPattern strategy = graphPatterns.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("Wrong graph type");
        }
        return strategy.generateGraph(timeInterval);
    }
}

