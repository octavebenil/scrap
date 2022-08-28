package com.sbosoft.scrap.persistances.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "magic_cards")
public class MagicCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCard;

    @NotNull
    @Column(unique = true)
    private int multiverseId;

    @NotNull
    @NotBlank
    private String nom;

    @Column(nullable = true)
    private String prenoms;

    @Column(nullable = true)
    private String cout;

    @Column(nullable = true)
    private String image;

    @OneToMany(mappedBy = "magicCard")
    private Set<MagicCardRulling> magicCardRullings;

    public MagicCard() {
        super();
    }

    public MagicCard(int multiverseId, String nom) {
        this.multiverseId = multiverseId;
        this.nom = nom;
    }

    public MagicCard(int multiverseId, String nom, String prenoms, String cout, String image) {
        this.multiverseId = multiverseId;
        this.nom = nom;
        this.prenoms = prenoms;
        this.cout = cout;
        this.image = image;
    }

    public long getIdCard() {
        return idCard;
    }

    public void setIdCard(long idCard) {
        this.idCard = idCard;
    }

    public int getMultiverseId() {
        return multiverseId;
    }

    public void setMultiverseId(int multiverseId) {
        this.multiverseId = multiverseId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getCout() {
        return cout;
    }

    public void setCout(String cout) {
        this.cout = cout;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<MagicCardRulling> getMagicCardRullings() {
        return magicCardRullings;
    }

    public void setMagicCardRullings(Set<MagicCardRulling> magicCardRullings) {
        this.magicCardRullings = magicCardRullings;
    }

    @Override
    public String toString() {
        return "MagicCard{" +
                "idCard=" + idCard +
                ", multiverseId=" + multiverseId +
                ", nom='" + nom + '\'' +
                ", prenoms='" + prenoms + '\'' +
                ", cout='" + cout + '\'' +
                ", image='" + image + '\'' +
                ", magicCardRullings=" + magicCardRullings +
                '}';
    }
}
