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
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "num_cni")
    private Long num_cni;

    @Column(name = "nom_client")
    private String nom_client;

    @Column(name = "prenom_client")
    private String prenom_client;

    @Column(name = "profession")
    private String profession;

    @Column(name = "date_delivrance")
    private String date_delivrance;

    @Column(name = "login")
    private String login;

    @Column(name = "ticket")
    private boolean ticket;

    @Column(name = "demande")
    private boolean demande;

    @Column(name = "reserver")
    private boolean reserver;

    @OneToOne    private Secretaire secretaire;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ticket> tickets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNum_cni() {
        return num_cni;
    }

    public void setNum_cni(Long num_cni) {
        this.num_cni = num_cni;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public String getProfession() {
        return profession;
    }

    public String getDate_delivrance() {
        return date_delivrance;
    }

    public void setDate_delivrance(String date_delivrance) {
        this.date_delivrance = date_delivrance;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Secretaire getSecretaire() {
        return secretaire;
    }

    public void setSecretaire(Secretaire secretaire) {
        this.secretaire = secretaire;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public boolean isTicket() {
        return ticket;
    }

    public void setTicket(boolean ticket) {
        this.ticket = ticket;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isReserver() {
        return reserver;
    }

    public void setReserver(boolean reserver) {
        this.reserver = reserver;
    }

    public boolean isDemande() {
        return demande;
    }

    public void setDemande(boolean demande) {
        this.demande = demande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Client client = (Client) o;

        if ( ! Objects.equals(id, client.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", num_cni='" + num_cni + "'" +
            ", nom_client='" + nom_client + "'" +
            ", prenom_client='" + prenom_client + "'" +
            ", profession='" + profession + "'" +
            ", date_delivrance='" + date_delivrance + "'" +
            '}';
    }
}
