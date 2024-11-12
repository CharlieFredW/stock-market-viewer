package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@CrossOrigin
public class GeneralController {

    @GetMapping
    public String getApiPage() {
        return "index";
    }


}
