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
 * A Caissiere.
 */
@Entity
@Table(name = "caissiere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Caissiere implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom_caissiere")
    private String nom_caissiere;

    @Column(name = "prenom_caissiere")
    private String prenom_caissiere;

    @Column(name = "tel_caissiere")
    private Long tel_caissiere;

    @Column(name = "adresse_caissiere")
    private String adresse_caissiere;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom_caissiere() {
        return nom_caissiere;
    }

    public void setNom_caissiere(String nom_caissiere) {
        this.nom_caissiere = nom_caissiere;
    }

    public String getPrenom_caissiere() {
        return prenom_caissiere;
    }

    public void setPrenom_caissiere(String prenom_caissiere) {
        this.prenom_caissiere = prenom_caissiere;
    }

    public Long getTel_caissiere() {
        return tel_caissiere;
    }

    public void setTel_caissiere(Long tel_caissiere) {
        this.tel_caissiere = tel_caissiere;
    }

    public String getAdresse_caissiere() {
        return adresse_caissiere;
    }

    public void setAdresse_caissiere(String adresse_caissiere) {
        this.adresse_caissiere = adresse_caissiere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Caissiere caissiere = (Caissiere) o;

        if ( ! Objects.equals(id, caissiere.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Caissiere{" +
            "id=" + id +
            ", nom_caissiere='" + nom_caissiere + "'" +
            ", prenom_caissiere='" + prenom_caissiere + "'" +
            ", tel_caissiere='" + tel_caissiere + "'" +
            ", adresse_caissiere='" + adresse_caissiere + "'" +
            '}';
    }
}
