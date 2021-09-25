package com.springboot.logistics.domain.repository;

import java.util.Optional;

import com.springboot.logistics.domain.model.City;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByName(String name);

}