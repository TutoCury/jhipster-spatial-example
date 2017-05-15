package com.tuto.spatial.repository;

import com.tuto.spatial.domain.Place;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Place entity.
 */
@SuppressWarnings("unused")
public interface PlaceRepository extends JpaRepository<Place,Long> {

}
