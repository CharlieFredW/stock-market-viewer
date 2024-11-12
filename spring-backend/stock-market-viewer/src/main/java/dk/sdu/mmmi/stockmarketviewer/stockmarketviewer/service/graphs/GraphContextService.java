package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.graphs;

import com.fasterxml.jackson.databind.JsonNode;

public interface GraphContextService {
    JsonNode generateGraph(String type, int timeInterval);
}
