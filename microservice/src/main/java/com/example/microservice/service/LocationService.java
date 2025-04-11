package com.example.microservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private final RestTemplate restTemplate;
    private static final String NOMINATIM_API = "https://nominatim.openstreetmap.org";
    private final ConcurrentHashMap<String, SavedLocation> savedLocations = new ConcurrentHashMap<>();

    public static class SavedLocation {
        private String id;
        private String name;
        private String address;
        private Map<String, Double> coordinates;

        public SavedLocation(String name, String address, Map<String, Double> coordinates) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
            this.address = address;
            this.coordinates = coordinates;
        }

        // Getters
        public String getId() { return id; }
        public String getName() { return name; }
        public String getAddress() { return address; }
        public Map<String, Double> getCoordinates() { return coordinates; }

        // Setters
        public void setName(String name) { this.name = name; }
        public void setAddress(String address) { this.address = address; }
        public void setCoordinates(Map<String, Double> coordinates) { this.coordinates = coordinates; }
    }

    public Map<String, Double> getCoordinates(String address) {
        try {
            log.info("Getting coordinates for address: {}", address);
            
            // Set up headers with a user agent (required by Nominatim)
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "InsuranceRecommendationService/1.0");
            HttpEntity<?> entity = new HttpEntity<>(headers);

            // Make the API call
            String url = NOMINATIM_API + "/search?q=" + address.replace(" ", "+") + "&format=json&limit=1";
            JsonNode[] response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode[].class).getBody();

            if (response != null && response.length > 0) {
                double lat = response[0].get("lat").asDouble();
                double lon = response[0].get("lon").asDouble();
                log.info("Found coordinates for {}: ({}, {})", address, lat, lon);
                return Map.of("lat", lat, "lon", lon);
            } else {
                log.warn("No results found for address: {}", address);
                return null;
            }
        } catch (Exception e) {
            log.error("Error getting coordinates for address: {}", address, e);
            throw new RuntimeException("Error getting coordinates for address: " + address, e);
        }
    }

    public SavedLocation saveLocation(String name, String address) {
        Map<String, Double> coordinates = getCoordinates(address);
        if (coordinates == null) {
            throw new RuntimeException("Could not find coordinates for address: " + address);
        }
        
        SavedLocation location = new SavedLocation(name, address, coordinates);
        savedLocations.put(location.getId(), location);
        return location;
    }

    public List<SavedLocation> getAllLocations() {
        return new ArrayList<>(savedLocations.values());
    }

    public SavedLocation getLocation(String id) {
        return savedLocations.get(id);
    }

    public SavedLocation updateLocation(String id, String name, String address) {
        SavedLocation location = savedLocations.get(id);
        if (location == null) {
            throw new RuntimeException("Location not found with id: " + id);
        }

        if (address != null && !address.equals(location.getAddress())) {
            Map<String, Double> coordinates = getCoordinates(address);
            if (coordinates == null) {
                throw new RuntimeException("Could not find coordinates for address: " + address);
            }
            location.setAddress(address);
            location.setCoordinates(coordinates);
        }

        if (name != null) {
            location.setName(name);
        }

        return location;
    }

    public void deleteLocation(String id) {
        if (savedLocations.remove(id) == null) {
            throw new RuntimeException("Location not found with id: " + id);
        }
    }

    public double calculateDistance(Map<String, Double> point1, Map<String, Double> point2) {
        if (point1 == null || point2 == null) {
            throw new IllegalArgumentException("Both points must be non-null");
        }

        log.info("Calculating distance between ({}, {}) and ({}, {})", 
                point1.get("lat"), point1.get("lon"), 
                point2.get("lat"), point2.get("lon"));

        final int R = 6371; // Earth's radius in kilometers

        double lat1 = Math.toRadians(point1.get("lat"));
        double lat2 = Math.toRadians(point2.get("lat"));
        double dLat = Math.toRadians(point2.get("lat") - point1.get("lat"));
        double dLon = Math.toRadians(point2.get("lon") - point1.get("lon"));

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1) * Math.cos(lat2) *
                Math.sin(dLon/2) * Math.sin(dLon/2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c;
        
        log.info("Calculated distance: {} km", distance);
        return distance;
    }
} 