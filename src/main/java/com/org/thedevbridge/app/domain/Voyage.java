package com.org.thedevbridge.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Voyage.
 */
@Entity
@Table(name = "voyage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Voyage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "ville_depart")
    private String ville_depart;

    @Column(name = "ville_arrivee")
    private String ville_arrivee;

    @Column(name = "heure_depart")
    private String heure_depart;

    @Column(name = "heure_arrivee")
    private String heure_arrivee;

    @Column(name = "nombus")
    private String nombus;

    @Column(name = "nom_chauffeur")
    private String nom_chauffeur;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVille_depart() {
        return ville_depart;
    }

    public void setVille_depart(String ville_depart) {
        this.ville_depart = ville_depart;
    }

    public String getVille_arrivee() {
        return ville_arrivee;
    }

    public void setVille_arrivee(String ville_arrivee) {
        this.ville_arrivee = ville_arrivee;
    }

    public String getHeure_depart() {
        return heure_depart;
    }

    public void setHeure_depart(String heure_depart) {
        this.heure_depart = heure_depart;
    }

    public String getHeure_arrivee() {
        return heure_arrivee;
    }

    public void setHeure_arrivee(String heure_arrivee) {
        this.heure_arrivee = heure_arrivee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNom_bus() {
        return nombus;
    }

    public void setNom_bus(String nombus) {
        this.nombus = nombus;
    }

    public String getNom_chauffeur() {
        return nom_chauffeur;
    }

    public void setNom_chauffeur(String nom_chauffeur) {
        this.nom_chauffeur = nom_chauffeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Voyage voyage = (Voyage) o;

        if ( ! Objects.equals(id, voyage.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Voyage{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", ville_depart='" + ville_depart + "'" +
            ", ville_arrivee='" + ville_arrivee + "'" +
            ", heure_depart='" + heure_depart + "'" +
            ", heure_arrivee='" + heure_arrivee + "'" +
            '}';
    }
}
