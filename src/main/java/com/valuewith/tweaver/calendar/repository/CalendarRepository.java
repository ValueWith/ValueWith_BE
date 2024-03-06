package com.valuewith.tweaver.calendar.repository;

import com.valuewith.tweaver.group.entity.TripGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<TripGroup, Long>, CalendarDsl{

}
