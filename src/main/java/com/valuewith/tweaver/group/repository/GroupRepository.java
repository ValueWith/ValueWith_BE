package com.valuewith.tweaver.group.repository;

import com.valuewith.tweaver.group.entity.TripGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<TripGroup, Long> {
}