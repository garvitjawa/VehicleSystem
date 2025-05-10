package com.bachatt.challanSystem.controller;

import com.bachatt.challanSystem.model.Vehicle;
import com.bachatt.challanSystem.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.bachatt.challanSystem.service.VehicleService;
import jakarta.validation.constraints.NotBlank;
import java.util.stream.Stream;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/add/vehicle")
    public ResponseEntity<?> addVehicle(@Valid @RequestBody  Vehicle vehicle){
        Vehicle saved=vehicleService.saveVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/vehicles")
    public List<Vehicle> getVehicles(){
        return vehicleService.getVehicles();
    }

    @GetMapping("/vehicle/{id}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String id) {
            return ResponseEntity.ok(vehicleService.getVehicle(id));
    }
    @PutMapping("/vehicle/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable String id,
            @RequestBody Vehicle vehicleUpdates) {

        Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicleUpdates);
        return ResponseEntity.ok(updatedVehicle);
    }
    @DeleteMapping("/vehicle/{id}")
    public ResponseEntity<Vehicle> deleteVehicle(
            @PathVariable  String id
    ){
        Vehicle vehicle = vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(vehicle);
    }
    @GetMapping("/vehicle/search")
    public ResponseEntity<List<Vehicle>> searchVehicles(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String fuel) {

        // Validate that exactly one parameter is provided
        if (Stream.of(city, state, fuel).filter(Objects::nonNull).count() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Exactly one search parameter (city, state, or fuel) must be provided");
        }

        return ResponseEntity.ok(vehicleService.searchVehicles(city, state, fuel));
    }
}
