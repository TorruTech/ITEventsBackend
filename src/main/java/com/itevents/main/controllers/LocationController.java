package com.itevents.main.controllers;

import com.itevents.main.models.LocationModel;
import com.itevents.main.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Location", description = "Endpoints para gestionar ubicaciones")
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Devuelve todas las ubicaciones")
    @GetMapping
    public List<LocationModel> getAll() {
        return locationService.getAllLocations();
    }

    @Operation(summary = "Devuelve una ubicacion por id")
    @PostMapping
    public LocationModel create(@RequestBody LocationModel location) {
        return locationService.saveLocation(location);
    }

    @Operation(summary = "Actualiza una ubicacion por id")
    @PutMapping("/{id}")
    public Optional<LocationModel> update(@PathVariable Long id, @RequestBody LocationModel updated) {
        return locationService.getById(id).map(location -> {
            location.setName(updated.getName());
            return locationService.saveLocation(location);
        });
    }

    @Operation(summary = "Elimina una ubicacion por id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }
}
