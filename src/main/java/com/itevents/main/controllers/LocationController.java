package com.itevents.main.controllers;

import com.itevents.main.models.LocationModel;
import com.itevents.main.services.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<LocationModel> getAll() {
        return locationService.getAllLocations();
    }

    @PostMapping
    public LocationModel create(@RequestBody LocationModel location) {
        return locationService.saveLocation(location);
    }

    @PutMapping("/{id}")
    public Optional<LocationModel> update(@PathVariable Long id, @RequestBody LocationModel updated) {
        return locationService.getById(id).map(location -> {
            location.setName(updated.getName());
            return locationService.saveLocation(location);
        });
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }
}
