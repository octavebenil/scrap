package com.sbosoft.scrap.persistances.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "magic_card_rulling")
public class MagicCardRulling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMagicCardRulling;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String rulling;

    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_card", nullable = false)
    private MagicCard magicCard;

    public MagicCardRulling(){super();}

    public MagicCardRulling(String rulling, LocalDate date, MagicCard magicCard) {
        this.rulling = rulling;
        this.date = date;
        this.magicCard = magicCard;
    }

    public long getIdMagicCardRulling() {
        return idMagicCardRulling;
    }

    public void setIdMagicCardRulling(long idMagicCardRulling) {
        this.idMagicCardRulling = idMagicCardRulling;
    }

    public String getRulling() {
        return rulling;
    }

    public void setRulling(String rulling) {
        this.rulling = rulling;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MagicCard getMagicCard() {
        return magicCard;
    }

    public void setMagicCard(MagicCard magicCard) {
        this.magicCard = magicCard;
    }

    @Override
    public String toString() {
        return "MagicCardRulling{" +
                "idMagicCardRulling=" + idMagicCardRulling +
                ", rulling='" + rulling + '\'' +
                ", date=" + date +
                ", magicCard=" + magicCard +
                '}';
    }
}
