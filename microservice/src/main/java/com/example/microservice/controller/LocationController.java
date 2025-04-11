package com.example.microservice.controller;

import com.example.microservice.service.LocationService;
import com.example.microservice.service.LocationService.SavedLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/coordinates")
    public ResponseEntity<Map<String, Double>> getCoordinates(@RequestParam String address) {
        Map<String, Double> coordinates = locationService.getCoordinates(address);
        if (coordinates != null) {
            return ResponseEntity.ok(coordinates);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/distance")
    public ResponseEntity<Map<String, Object>> calculateDistance(
            @RequestParam String address1,
            @RequestParam String address2) {
        
        Map<String, Double> point1 = locationService.getCoordinates(address1);
        Map<String, Double> point2 = locationService.getCoordinates(address2);
        
        if (point1 != null && point2 != null) {
            double distance = locationService.calculateDistance(point1, point2);
            return ResponseEntity.ok(Map.of(
                "distance", distance,
                "unit", "kilometers",
                "from", Map.of(
                    "address", address1,
                    "coordinates", point1
                ),
                "to", Map.of(
                    "address", address2,
                    "coordinates", point2
                )
            ));
        }
        
        return ResponseEntity.badRequest().body(Map.of(
            "error", "Could not find coordinates for one or both addresses"
        ));
    }

    @PostMapping("/saved")
    public ResponseEntity<SavedLocation> saveLocation(
            @RequestParam String name,
            @RequestParam String address) {
        try {
            SavedLocation location = locationService.saveLocation(name, address);
            return ResponseEntity.ok(location);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/saved")
    public ResponseEntity<List<SavedLocation>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/saved/{id}")
    public ResponseEntity<SavedLocation> getLocation(@PathVariable String id) {
        SavedLocation location = locationService.getLocation(id);
        if (location != null) {
            return ResponseEntity.ok(location);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/saved/{id}")
    public ResponseEntity<SavedLocation> updateLocation(
            @PathVariable String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address) {
        try {
            SavedLocation location = locationService.updateLocation(id, name, address);
            return ResponseEntity.ok(location);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/saved/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable String id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 