package com.example.spaceservice.controller;


import com.example.spaceservice.dto.SpaceAvailableForDocDto;
import com.example.spaceservice.dto.SpaceAvailableForLoadDto;
import com.example.spaceservice.dto.SpaceNeedeForDocDto;
import com.example.spaceservice.dto.SpaceNeededForLoadDto;
import com.example.spaceservice.model.Space;
import com.example.spaceservice.service.SpaceService;
import com.example.spaceservice.utils.SpaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/space")
public class SpaceController {
    @Autowired
    SpaceService spaceService;

    @PostMapping("/doc-space-available")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<?> createDocumentSpaceAvailable(@RequestBody SpaceAvailableForDocDto request) {
        Space space = SpaceMapper.from(request);
        return ResponseEntity.ok(spaceService.addSpace(space));
    }

    @PostMapping("/doc-space-needed")
    public ResponseEntity<?> createDocumentSpaceNeeded(@RequestBody SpaceNeedeForDocDto request) {
        Space space = SpaceMapper.from(request);
        return ResponseEntity.ok(spaceService.addSpace(space));
    }

    @PostMapping("/load-space-needed")
    public ResponseEntity<?> createLoadSpaceNeeded(@RequestBody SpaceNeededForLoadDto request) {
        Space space = SpaceMapper.from(request);
        return ResponseEntity.ok(spaceService.addSpace(space));
    }

    @PostMapping("/load-space-available")
    public ResponseEntity<?> createLoadSpaceAvailable(@RequestBody SpaceAvailableForLoadDto request) {
        Space space = SpaceMapper.from(request);
        return ResponseEntity.ok(spaceService.addSpace(space));
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllSpaces(){
        return ResponseEntity.ok(spaceService.getAllSpaces());
    }

}
