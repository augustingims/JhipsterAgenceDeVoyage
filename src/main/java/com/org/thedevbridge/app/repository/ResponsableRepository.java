package com.org.thedevbridge.app.repository;

import com.org.thedevbridge.app.domain.Responsable;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Responsable entity.
 */
public interface ResponsableRepository extends JpaRepository<Responsable,Long> {

}
