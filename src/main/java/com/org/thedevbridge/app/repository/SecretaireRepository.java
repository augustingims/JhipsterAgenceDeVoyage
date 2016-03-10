package com.org.thedevbridge.app.repository;

import com.org.thedevbridge.app.domain.Secretaire;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Secretaire entity.
 */
public interface SecretaireRepository extends JpaRepository<Secretaire,Long> {

}
