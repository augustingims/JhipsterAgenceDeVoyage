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
 * A Responsable.
 */
@Entity
@Table(name = "responsable")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Responsable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom_responsable")
    private String nom_responsable;

    @Column(name = "prenom_responsable")
    private String prenom_responsable;

    @Column(name = "tel_responsable")
    private Long tel_responsable;

    @Column(name = "adresse_responsable")
    private String adresse_responsable;

    @OneToMany(mappedBy = "responsable")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Secretaire> secretaires = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom_responsable() {
        return nom_responsable;
    }

    public void setNom_responsable(String nom_responsable) {
        this.nom_responsable = nom_responsable;
    }

    public String getPrenom_responsable() {
        return prenom_responsable;
    }

    public void setPrenom_responsable(String prenom_responsable) {
        this.prenom_responsable = prenom_responsable;
    }

    public Long getTel_responsable() {
        return tel_responsable;
    }

    public void setTel_responsable(Long tel_responsable) {
        this.tel_responsable = tel_responsable;
    }

    public String getAdresse_responsable() {
        return adresse_responsable;
    }

    public void setAdresse_responsable(String adresse_responsable) {
        this.adresse_responsable = adresse_responsable;
    }

    public Set<Secretaire> getSecretaires() {
        return secretaires;
    }

    public void setSecretaires(Set<Secretaire> secretaires) {
        this.secretaires = secretaires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Responsable responsable = (Responsable) o;

        if ( ! Objects.equals(id, responsable.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Responsable{" +
            "id=" + id +
            ", nom_responsable='" + nom_responsable + "'" +
            ", prenom_responsable='" + prenom_responsable + "'" +
            ", tel_responsable='" + tel_responsable + "'" +
            ", adresse_responsable='" + adresse_responsable + "'" +
            '}';
    }
}
