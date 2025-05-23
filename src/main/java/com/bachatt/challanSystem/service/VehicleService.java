package com.bachatt.challanSystem.service;

import com.bachatt.challanSystem.model.Vehicle;
import com.bachatt.challanSystem.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;
import com.bachatt.challanSystem.exception.DuplicateVehicleException;
import org.springframework.web.server.ResponseStatusException;
import com.bachatt.challanSystem.exception.ResourceNotFoundException;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle saveVehicle(Vehicle vehicle) {
        if (vehicleRepository.existsByRegistrationNo(vehicle.getRegistrationNo())) {
            throw new DuplicateVehicleException(
                    "Vehicle with registration number " + vehicle.getRegistrationNo() + " already exists"
            );
        }
        return vehicleRepository.save(vehicle);

    }
    public List<Vehicle> getVehicles(){
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicle(String id){
        return vehicleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Vehicle not found with id: " + id
        ));
    }

    public Vehicle updateVehicle(String id, Vehicle vehicleUpdates){
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", id));
        if (vehicleUpdates.getOwnerName() != null) {
            existingVehicle.setOwnerName(vehicleUpdates.getOwnerName());
        }
        if (vehicleUpdates.getOwnerAddress() != null) {
            existingVehicle.setOwnerAddress(vehicleUpdates.getOwnerAddress());
        }
        if (vehicleUpdates.getCity() != null) {
            existingVehicle.setCity(vehicleUpdates.getCity());
        }
        if (vehicleUpdates.getState() != null) {
            existingVehicle.setState(vehicleUpdates.getState());
        }
        return vehicleRepository.save(existingVehicle);

    }
    public Vehicle deleteVehicle(String id){
        Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vehicle",id));
        vehicleRepository.deleteById(id);
        return vehicle;
    }
    public List<Vehicle> searchVehicles(String city, String state, String fuel) {
        if (city != null) {
            return getByCity(city);
        } else if (state != null) {
            return getByState(state);
        } else if (fuel != null) {
            return getByFuel(fuel);
        }
        throw new IllegalArgumentException("No search parameter provided");
    }

    public List<Vehicle> getByCity(String city) {
        List<Vehicle> vehicles = vehicleRepository.findByCityIgnoreCase(city);
        if(vehicles.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No vehicles found in city: " + city);
        }
        return vehicles;
    }

    public List<Vehicle> getByState(String state) {
        List<Vehicle> vehicles = vehicleRepository.findByStateIgnoreCase(state);
        if(vehicles.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No vehicles found in state: " + state);
        }
        return vehicles;
    }

    public List<Vehicle> getByFuel(String fuel) {
        List<Vehicle> vehicles = vehicleRepository.findByFuelTypeIgnoreCase(fuel);
        if(vehicles.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No vehicles found with fuel type: " + fuel);
        }
        return vehicles;
    }

    public Page<Vehicle> getVehiclesWithPagination(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return vehicleRepository.findAll(pageable);
    }
}
