package com.sbosoft.scrap.services;

import com.sbosoft.scrap.configurations.AppConfig;
import com.sbosoft.scrap.interfaces.IScrapingService;
import com.sbosoft.scrap.persistances.dao.MagicCardRepository;
import com.sbosoft.scrap.persistances.dao.MagicCardRullingRepository;
import com.sbosoft.scrap.persistances.models.MagicCard;
import com.sbosoft.scrap.persistances.models.MagicCardRulling;
import com.sbosoft.scrap.utils.DownloadImage;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ScrapingService implements IScrapingService {
    private static final Logger log = LoggerFactory.getLogger(ScrapingService.class);

    @Autowired
    private MagicCardRepository magicCardRepository;

    @Autowired
    private MagicCardRullingRepository magicCardRullingRepository;

    @Override
    public MagicCard scrapMagicCardByMultiverseId(int multiverseId) {
        return scrapCardDetail(multiverseId);
    }

    @Override
    public List<MagicCard> scrapMagicCardByRangeOfMultiverseId(int startMultiverseId, int endMultiverseId) {
        List<MagicCard> magicCards = new ArrayList<>();

        for(int k=startMultiverseId; k <= endMultiverseId; k++ ){
            magicCards.add(scrapCardDetail(k));
        }
        return magicCards;
    }

    private MagicCard scrapCardDetail(int multiverseId){
        MagicCard magicCard = null;

        String currentUrl = AppConfig.MAGIC_DETAILS_URL + "?multiverseid="+multiverseId;
        log.info("currentUrl " + currentUrl);
        try {
            Document document = Jsoup.connect(currentUrl).validateTLSCertificates(false).get();

            String image = AppConfig.MAGIC_URL+"/Handlers/Image.ashx?multiverseid="+multiverseId+"&type=card";

            log.warn("Imga " + image);

            Element manaNameElement = document.getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_nameRow");
            Element manaCostElement = document.getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_cmcRow");


            String name = null;
            String cout = null;

            for(Element el: manaNameElement.children()){
                if(el.hasClass("value")){
                    name = el.text();
                    log.info("Nom " + name);
                }
            }

            for(Element el: manaCostElement.children()){
                if(el.hasClass("value")){
                    cout = el.text();
                    log.info("Cout " + cout);
                }
            }

            if(name != null && name.isEmpty() == false){

                //télécharge et enregistre l'image
                String imgName = multiverseId+".jpg";

                DownloadImage.storeImageIntoFS(image, imgName, "src/main/resources/static/images/");

                magicCard = new MagicCard(multiverseId, name, "", cout, "images/"+imgName);

                //rullings
                List<MagicCardRulling> magicCardRullings = new ArrayList<>();

                DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .parseLenient()
                        .appendPattern("[dd/MM/yyyy]")
                        .appendPattern("[dd/M/yyyy]")

                        .appendPattern("[dd/MM/yy]")
                        .appendPattern("[dd/M/yy]")
                        .appendPattern("[d/M/yy]")
                        .appendPattern("[d/MM/yy]")

                        .appendPattern("[d/M/yyyy]")
                        .appendPattern("[d/MM/yyyy]");

                DateTimeFormatter formatter = formatterBuilder.toFormatter(Locale.FRANCE);

                Elements rullingTables = document.getElementsByClass("rulingsTable");

                for(Element el: rullingTables){
                    Elements rows = el.select("tr");
                    for(Element row : rows){
                        Elements columns = row.select("td");
                        //rulling contient deux colonne date et détails
                        if(columns.size() == 2){
                            String date = columns.get(0).text();
                            String text = columns.get(1).text();

                            //on ignore le date null
                            log.warn("Date "+date);
                            try{
                                if(date.isEmpty() == false && date != null){
                                    LocalDate dateRulling = LocalDate.parse(date, formatter);

                                    MagicCardRulling magicCardRulling = new MagicCardRulling(text, dateRulling, magicCard);

                                    magicCardRullings.add(magicCardRulling);
                                }
                            } catch (Exception e){
                                log.warn("Error " + e.getMessage());
                            }
                        }
                    }
                }

                //Persiste les données dans la bd
                //on evite le doublon
                if(magicCardRepository.findByMultiversId(multiverseId)  == null){
                    magicCardRepository.save(magicCard);
                    magicCardRullingRepository.saveAll(magicCardRullings);
                }
            }


        } catch (IOException e) {
            log.warn("err " + e.getMessage());
            magicCard = new MagicCard();
        }

        return magicCard;
    }
}
