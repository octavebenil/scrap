package com.sbosoft.scrap.persistances.dao;

import com.sbosoft.scrap.persistances.models.MagicCard;
import com.sbosoft.scrap.persistances.models.MagicCardRulling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface MagicCardRepository extends JpaRepository<MagicCard, Long> {

    @Query("select m from MagicCard m where m.idCard = ?1")
    MagicCard findByIdCard(long idCard);

    @Query("select m from MagicCard m where m.multiverseId = ?1")
    MagicCard findByMultiversId(int multiverseId);
}
