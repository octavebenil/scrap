package com.sbosoft.scrap.web.controllers;

import com.sbosoft.scrap.interfaces.IScrapingService;
import com.sbosoft.scrap.persistances.models.MagicCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/magic")
public class MagicCardRestController {

    @Autowired
    private IScrapingService scrapingService;

    @GetMapping("card/detail")
    public ResponseEntity<MagicCard> getDetailsOneCard(
            @RequestParam("multiverseid") int multiverseId
    ){
        MagicCard magicCard = scrapingService.scrapMagicCardByMultiverseId(multiverseId);
        return new ResponseEntity(magicCard, HttpStatus.OK);
    }

    @GetMapping("card/range")
    public ResponseEntity<List<MagicCard>> getDetailsRangeCard(
            @RequestParam("start_multiverseid") int startMultiverseId,
            @RequestParam("end_multiverseid") int endMultiverseId
    ){
        List<MagicCard> magicCards = scrapingService.scrapMagicCardByRangeOfMultiverseId(startMultiverseId, endMultiverseId);

        return new ResponseEntity(magicCards, HttpStatus.OK);
    }
}
