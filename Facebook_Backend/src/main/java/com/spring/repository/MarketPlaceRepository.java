package com.spring.repository;

import com.spring.entities.MarketPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketPlaceRepository extends JpaRepository<MarketPlace, Integer> {
}
