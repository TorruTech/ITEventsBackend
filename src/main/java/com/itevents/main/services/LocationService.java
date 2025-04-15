package com.itevents.main.services;

import com.itevents.main.models.LocationModel;
import com.itevents.main.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationModel> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<LocationModel> getById(Long id) {
        return locationRepository.findById(id);
    }

    public LocationModel saveLocation(LocationModel location) {
        return locationRepository.save(location);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
