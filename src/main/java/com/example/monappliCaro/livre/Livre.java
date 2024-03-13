package com.example.monappliCaro.livre;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity  //for the table in the DataBase
@Table  //for Hibernate

public class Livre {

    @Id
    @SequenceGenerator(
            name = "sequence_livre",
            sequenceName = "sequence_livre",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_livre"
    )
    private Long id;
    private String proprio;
    private String titre;
    private String auteur;
    private LocalDate date_edition;

    public Livre() {
    }

    public Livre(Long id,
                 String proprio,
                 String titre,
                 String auteur,
                 LocalDate date_edition) {
        this.id = id;
        this.proprio = proprio;
        this.titre = titre;
        this.auteur = auteur;
        this.date_edition = date_edition;
    }

    public Livre(String proprio,
                 String titre,
                 String auteur,
                 LocalDate date_edition) {
        this.proprio = proprio;
        this.titre = titre;
        this.auteur = auteur;
        this.date_edition = date_edition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProprio() {
        return proprio;
    }

    public void setProprio(String proprio) {
        this.proprio = proprio;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public LocalDate getDate_edition() {
        return date_edition;
    }

    public void setDate_edition(LocalDate date_edition) {
        this.date_edition = date_edition;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", proprio='" + proprio + '\'' +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", date_edition=" + date_edition +
                '}';
    }
}
