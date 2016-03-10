package com.org.thedevbridge.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nature")
    private String nature;

    @Column(name = "type")
    private String type;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "nom_caissiere")
    private String nom_caissiere;

    @OneToOne    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getNom_caissiere() {
        return nom_caissiere;
    }

    public void setNom_caissiere(String nom_caissiere) {
        this.nom_caissiere = nom_caissiere;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Ticket ticket = (Ticket) o;

        if ( ! Objects.equals(id, ticket.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ticket{" +
            "nom_caissiere='" + nom_caissiere + '\'' +
            ", numero=" + numero +
            ", prix=" + prix +
            ", type='" + type + '\'' +
            ", nature='" + nature + '\'' +
            ", id=" + id +
            '}';
    }
}
