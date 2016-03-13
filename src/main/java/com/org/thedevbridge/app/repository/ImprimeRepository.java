package com.org.thedevbridge.app.repository;

import com.org.thedevbridge.app.domain.Imprime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Ticket entity.
 */
public interface ImprimeRepository extends JpaRepository<Imprime,Long> {

    Imprime findById(Long id);

}
