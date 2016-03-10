package com.org.thedevbridge.app.repository;

import com.org.thedevbridge.app.domain.Client;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Client entity.
 */
public interface ClientRepository extends JpaRepository<Client,Long> {

    List<Client> findByTicket(boolean ticket);
   List<Client> findByDemande(boolean demande);
    List<Client> findByReserver(boolean reserver);
    Client findById(Long id);
   long countByDemande(boolean demande);
    long countByReserver(boolean reserver);

}
