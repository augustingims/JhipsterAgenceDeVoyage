package com.org.thedevbridge.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Chauffeur.
 */
@Entity
@Table(name = "chauffeur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chauffeur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom_chauffeur")
    private String nom_chauffeur;

    @Column(name = "prenom_chauffeur")
    private String prenom_chauffeur;

    @Column(name = "tel_chauffeur")
    private Long tel_chauffeur;

    @Column(name = "adresse_chauffeur")
    private String adresse_chauffeur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom_chauffeur() {
        return nom_chauffeur;
    }

    public void setNom_chauffeur(String nom_chauffeur) {
        this.nom_chauffeur = nom_chauffeur;
    }

    public String getPrenom_chauffeur() {
        return prenom_chauffeur;
    }

    public void setPrenom_chauffeur(String prenom_chauffeur) {
        this.prenom_chauffeur = prenom_chauffeur;
    }

    public Long getTel_chauffeur() {
        return tel_chauffeur;
    }

    public void setTel_chauffeur(Long tel_chauffeur) {
        this.tel_chauffeur = tel_chauffeur;
    }

    public String getAdresse_chauffeur() {
        return adresse_chauffeur;
    }

    public void setAdresse_chauffeur(String adresse_chauffeur) {
        this.adresse_chauffeur = adresse_chauffeur;
    }

    public Chauffeur(String nom_chauffeur, String prenom_chauffeur) {
        this.nom_chauffeur = nom_chauffeur;
        this.prenom_chauffeur = prenom_chauffeur;
    }

    public Chauffeur() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Chauffeur chauffeur = (Chauffeur) o;

        if ( ! Objects.equals(id, chauffeur.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Chauffeur{" +
            "id=" + id +
            ", nom_chauffeur='" + nom_chauffeur + "'" +
            ", prenom_chauffeur='" + prenom_chauffeur + "'" +
            ", tel_chauffeur='" + tel_chauffeur + "'" +
            ", adresse_chauffeur='" + adresse_chauffeur + "'" +
            '}';
    }
}
