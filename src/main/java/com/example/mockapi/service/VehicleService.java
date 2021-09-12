package com.example.mockapi.service;

import com.example.mockapi.web.model.species.Species;
import com.example.mockapi.web.model.vehicle.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> getALlVehicles();
    Vehicle getVehicleById(int index);
}
