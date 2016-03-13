package com.org.thedevbridge.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ticket.
 */
@Entity
@Table(name = "imprime")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Imprime implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "client")
    private String client;

    @Column(name = "nature")
    private String nature;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "date")
    private String date;

    @Column(name = "ville_depart")
    private String ville_depart;

    @Column(name = "ville_arrivee")
    private String ville_arrivee;

    @Column(name = "heure_depart")
    private String heure_depart;

    @Column(name = "nom_bus")
    private String nom_bus;

    @Column(name = "impression")
    private boolean impression;

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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getNom_bus() {
        return nom_bus;
    }

    public void setNom_bus(String nom_bus) {
        this.nom_bus = nom_bus;
    }

    public boolean isImpression() {
        return impression;
    }

    public void setImpression(boolean impression) {
        this.impression = impression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Imprime imprime = (Imprime) o;

        if ( ! Objects.equals(id, imprime.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Imprime{" +
            "id=" + id +
            ", nature='" + nature + '\'' +
            ", client='" + client + '\'' +
            ", prix=" + prix +
            ", numero=" + numero +
            ", date='" + date + '\'' +
            ", ville_depart='" + ville_depart + '\'' +
            ", ville_arrivee='" + ville_arrivee + '\'' +
            ", heure_depart='" + heure_depart + '\'' +
            ", nom_bus='" + nom_bus + '\'' +
            '}';
    }
}
