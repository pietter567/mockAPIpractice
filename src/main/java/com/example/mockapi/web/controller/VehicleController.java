package com.example.mockapi.web.controller;

import com.example.mockapi.service.VehicleService;
import com.example.mockapi.web.model.Response;
import com.example.mockapi.web.model.starships.Starship;
import com.example.mockapi.web.model.starships.StarshipResponse;
import com.example.mockapi.web.model.vehicle.Vehicle;
import com.example.mockapi.web.model.vehicle.VehicleResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @GetMapping(path = "/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<VehicleResponse>> getAllVehicles(){

        List<Vehicle> vehicles = vehicleService.getALlVehicles();
        List<VehicleResponse> vehicleresponses = vehicles.stream().map(this::convertToResponse)
                .collect(Collectors.toList());

        return Response.<List<VehicleResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(vehicleresponses)
                .build();

    }

    @GetMapping(path = "/vehicles/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<VehicleResponse> getSpeciesById(@PathVariable("index") int index){
        Vehicle vehicle = vehicleService.getVehicleById(index);

        return Response.<VehicleResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(vehicle))
                .build();

    }

    public VehicleResponse convertToResponse(Vehicle vehicle){
        VehicleResponse vehicleResponse = new VehicleResponse();
        BeanUtils.copyProperties(vehicle, vehicleResponse);
        return vehicleResponse;
    }

}
