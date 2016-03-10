package com.org.thedevbridge.app.repository;

import com.org.thedevbridge.app.domain.Bus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bus entity.
 */
public interface BusRepository extends JpaRepository<Bus,Long> {

}
