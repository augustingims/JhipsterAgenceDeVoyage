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
 * A Secretaire.
 */
@Entity
@Table(name = "secretaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Secretaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom_secretaire")
    private String nom_secretaire;

    @Column(name = "prenom_secretaire")
    private String prenom_secretaire;

    @Column(name = "tel_secretaire")
    private Long tel_secretaire;

    @Column(name = "adresse_secretaire")
    private String adresse_secretaire;

    @OneToOne    private Responsable responsable;

    @OneToMany(mappedBy = "secretaire")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Client> clients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom_secretaire() {
        return nom_secretaire;
    }

    public void setNom_secretaire(String nom_secretaire) {
        this.nom_secretaire = nom_secretaire;
    }

    public String getPrenom_secretaire() {
        return prenom_secretaire;
    }

    public void setPrenom_secretaire(String prenom_secretaire) {
        this.prenom_secretaire = prenom_secretaire;
    }

    public Long getTel_secretaire() {
        return tel_secretaire;
    }

    public void setTel_secretaire(Long tel_secretaire) {
        this.tel_secretaire = tel_secretaire;
    }

    public String getAdresse_secretaire() {
        return adresse_secretaire;
    }

    public void setAdresse_secretaire(String adresse_secretaire) {
        this.adresse_secretaire = adresse_secretaire;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Secretaire secretaire = (Secretaire) o;

        if ( ! Objects.equals(id, secretaire.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Secretaire{" +
            "id=" + id +
            ", nom_secretaire='" + nom_secretaire + "'" +
            ", prenom_secretaire='" + prenom_secretaire + "'" +
            ", tel_secretaire='" + tel_secretaire + "'" +
            ", adresse_secretaire='" + adresse_secretaire + "'" +
            '}';
    }
}
