package com.bachatt.challanSystem.repository;
import java.util.*;
import com.bachatt.challanSystem.model.Vehicle;
import java.util.Optional;
import org.springframework.data.mongodb.repository.*;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    Optional<Vehicle> findByRegistrationNo(String registrationNo);
    boolean existsByRegistrationNo(String registrationNo);
    List<Vehicle> findByFuelTypeIgnoreCase(String fuelType);
    List<Vehicle> findByStateIgnoreCase(String state);
    List<Vehicle> findByCityIgnoreCase(String city);
}
