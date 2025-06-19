package com.example.spaceservice.repository;



import com.example.spaceservice.model.Space;
import com.example.spaceservice.model.SpaceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SpaceRepo extends JpaRepository<Space, String> {
    List<Space> findSpacesByType(SpaceType type);
    void deleteSpacesByAvailableToBefore(LocalDateTime time);
}
