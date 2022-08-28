package com.sbosoft.scrap.interfaces;

import com.sbosoft.scrap.persistances.models.MagicCard;

import java.util.List;

public interface IScrapingService {
    MagicCard scrapMagicCardByMultiverseId(int multiverseId);
    List<MagicCard> scrapMagicCardByRangeOfMultiverseId(int startMultiverseId, int endMultiverseId);
}
