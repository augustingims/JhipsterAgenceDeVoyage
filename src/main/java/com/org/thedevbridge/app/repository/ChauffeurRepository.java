package com.org.thedevbridge.app.repository;

import com.org.thedevbridge.app.domain.Chauffeur;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Chauffeur entity.
 */
public interface ChauffeurRepository extends JpaRepository<Chauffeur,Long> {

}
