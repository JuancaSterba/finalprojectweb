package com.itec1.finalprojectweb.repository;

import com.itec1.finalprojectweb.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILocationRepository extends JpaRepository<Location, Long> {
}