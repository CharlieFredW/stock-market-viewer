package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.graphs.GraphContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class GraphController {

    private final GraphContextService graphService;

    @Autowired
    public GraphController(GraphContextService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/cryptoGraph")
    public JsonNode getCryptoGraph(@RequestParam String type, @RequestParam int timeInterval) {
        return graphService.generateGraph(type, timeInterval);
    }
}


