package com.org.thedevbridge.app.repository;

import com.org.thedevbridge.app.domain.Voyage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Voyage entity.
 */
public interface VoyageRepository extends JpaRepository<Voyage,Long> {

    long countByNombusLike(String nombus);
}
