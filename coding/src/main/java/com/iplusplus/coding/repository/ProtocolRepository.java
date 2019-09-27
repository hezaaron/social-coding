package com.iplusplus.coding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iplusplus.coding.entity.Protocol;

public interface ProtocolRepository extends JpaRepository<Protocol, Long> {
}