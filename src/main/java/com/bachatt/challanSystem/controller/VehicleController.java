package com.bachatt.challanSystem.controller;

import com.bachatt.challanSystem.model.Vehicle;
import com.bachatt.challanSystem.repository.VehicleRepository;
import com.bachatt.challanSystem.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/add/vehicle")
    public Vehicle addVehicle(@Valid @RequestBody Vehicle vehicle) {
        return vehicleService.saveVehicle(vehicle);
    }

    @GetMapping("/vehicles")
    public List<Vehicle> getVehicles(
            @RequestHeader(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestHeader(value = "pageNo", defaultValue = "1") int pageNo) {

        var page = vehicleService.getVehiclesWithPagination(pageNo, pageSize);
        List<Vehicle> vehicles = page.getContent();
        return vehicles;
    }

    @GetMapping("/vehicle/{id}")
    public Vehicle getVehicle(@PathVariable String id) {
        return vehicleService.getVehicle(id);
    }

    @PutMapping("/vehicle/{id}")
    public Vehicle updateVehicle(@PathVariable String id, @RequestBody Vehicle vehicleUpdates) {
        return vehicleService.updateVehicle(id, vehicleUpdates);
    }

    @DeleteMapping("/vehicle/{id}")
    public Vehicle deleteVehicle(@PathVariable String id) {
        return vehicleService.deleteVehicle(id);
    }

    @GetMapping("/vehicle/search")
    public List<Vehicle> searchVehicles(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String fuel) {

        if (Stream.of(city, state, fuel).filter(Objects::nonNull).count() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Exactly one search parameter (city, state, or fuel) must be provided");
        }

        return vehicleService.searchVehicles(city, state, fuel);
    }
}
