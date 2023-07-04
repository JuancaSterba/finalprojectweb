package com.itec1.finalprojectweb.repository;

import com.itec1.finalprojectweb.entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrackingRepository extends JpaRepository<Tracking, Long> {
}
