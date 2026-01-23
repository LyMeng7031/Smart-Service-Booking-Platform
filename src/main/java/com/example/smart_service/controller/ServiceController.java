package com.example.smart_service.controller;

import com.example.smart_service.dto.request.ServiceRequest;
import com.example.smart_service.dto.response.ServiceResponse;
import com.example.smart_service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService service;

    @PostMapping("/create")
    public ServiceResponse create(@RequestBody ServiceRequest req, @RequestHeader("Authorization") String auth) {
        return service.createService(req, auth);
    }
}