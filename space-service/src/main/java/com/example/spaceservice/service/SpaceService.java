package com.example.spaceservice.service;

import com.example.spaceservice.model.Space;
import com.example.spaceservice.model.SpaceType;
import com.example.spaceservice.repository.SpaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceService {
    @Autowired
    SpaceRepo spaceRepo;


    public List<Space> getAllSpaces(){
        return spaceRepo.findAll();
    }

    public List<Space> getAllFreeSpaces() {
        List<Space> result = new ArrayList<>();
        result.addAll(spaceRepo.findSpacesByType(SpaceType.LOAD_AVAILABLE));
        result.addAll(spaceRepo.findSpacesByType(SpaceType.DOCUMENT_AVAILABLE));
        return result;
    }

    public List<Space> getAllAvailableSpacesForDocs() {
      return  spaceRepo.findSpacesByType(SpaceType.DOCUMENT_AVAILABLE);
    }

    public List<Space> getAllAvailableSpacesForLoad() {
        return  spaceRepo.findSpacesByType(SpaceType.LOAD_AVAILABLE);
    }

    public List<Space> getAllNeededSpaces() {
        List<Space> result = new ArrayList<>();
        result.addAll(spaceRepo.findSpacesByType(SpaceType.LOAD_NEEDED));
        result.addAll(spaceRepo.findSpacesByType(SpaceType.DOCUMENT_NEEDED));
        return result;
    }

    public List<Space> getAllNeededSpacesForDocs() {
        return  spaceRepo.findSpacesByType(SpaceType.DOCUMENT_NEEDED);

    }

    public List<Space> getAllNeededSpacesForLoad() {
        return  spaceRepo.findSpacesByType(SpaceType.LOAD_NEEDED);

    }

    public String addSpace(Space space){
       var s =  spaceRepo.save(space);
       return "Added space "+s.toString();
    }


    // Runs every day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeExpiredSpaces() {
        spaceRepo.deleteSpacesByAvailableToBefore(LocalDateTime.now());
    }

}
