package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.service.cryptoobject.CryptoObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CryptoObjectController {

    private final CryptoObjectService cryptoObjectService;

    @Autowired
    public CryptoObjectController(CryptoObjectService cryptoObjectService) {
        this.cryptoObjectService = cryptoObjectService;
    }

    @GetMapping("/cryptoObjects")
    @CrossOrigin
    public JsonNode getData() {
        return cryptoObjectService.SQLToJson();
    }
}

