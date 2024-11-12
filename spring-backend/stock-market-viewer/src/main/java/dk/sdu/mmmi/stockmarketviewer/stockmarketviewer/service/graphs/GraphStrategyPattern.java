package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.graphs;

import com.fasterxml.jackson.databind.JsonNode;

public interface GraphStrategyPattern {
    JsonNode generateGraph(int timeInterval);
    String getType();
}
