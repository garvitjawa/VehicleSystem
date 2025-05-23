package com.bachatt.challanSystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
@Data
@Document(collection = "vehicles")
public class Vehicle {

    @Id
    private String id;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    private String name;

    @NotBlank(message = "Fuel type is required")
    @Pattern(regexp = "^(Petrol|Diesel|Electric|Hybrid)$", message = "Fuel type must be Petrol, Diesel, Electric, or Hybrid")
    private String fuelType;

    @NotBlank(message = "Registration number is required")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "Registration number must contain only uppercase letters, numbers, and hyphens")
    private String registrationNo;

    @NotBlank(message = "Owner name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Owner name must contain only letters and spaces")
    private String ownerName;

    @NotBlank(message = "Owner address is required")
    private String ownerAddress;

    @NotBlank(message = "City is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "City must contain only letters and spaces")
    private String city;

    @NotBlank(message = "State is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "State must contain only letters and spaces")
    private String state;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
}
