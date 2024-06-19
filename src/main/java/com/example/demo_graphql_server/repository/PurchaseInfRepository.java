package com.example.demo_graphql_server.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_graphql_server.entity.PurchaseInfEntity;

@Repository
public interface PurchaseInfRepository extends JpaRepository<PurchaseInfEntity, Long>{
	
	List<PurchaseInfEntity> findByDatetimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
