package com.sbosoft.scrap.persistances.dao;

import com.sbosoft.scrap.persistances.models.MagicCard;
import com.sbosoft.scrap.persistances.models.MagicCardRulling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface MagicCardRullingRepository extends JpaRepository<MagicCardRulling, Long> {

    @Query("select m from MagicCardRulling m where m.idMagicCardRulling = ?1")
    MagicCardRulling findByIdMagicCardRulling(long idCard);
}
