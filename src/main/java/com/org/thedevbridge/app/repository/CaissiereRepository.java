package com.org.thedevbridge.app.repository;

import com.org.thedevbridge.app.domain.Caissiere;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Caissiere entity.
 */
public interface CaissiereRepository extends JpaRepository<Caissiere,Long> {

}
