package com.example.smart_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smart_service.dto.request.BookingRequest;
import com.example.smart_service.dto.response.ApiResponse;
import com.example.smart_service.dto.response.BookingResponse;
import com.example.smart_service.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("api/bookings")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Booking API", description = "Endpoints for managing bookings")
public class BookingController {

    private final BookingService service;

    @Operation(summary = "Create Booking", description = "Allows a customer to create a booking for a service")
    @PostMapping("/{serviceId}")
    public ApiResponse<BookingResponse> postbooking(@PathVariable Long serviceId, @RequestBody BookingRequest bookingRequest) {
        BookingResponse response =  service.postBooking(serviceId, bookingRequest);//service.postBooking(serviceId, bookingRequest);
        return ApiResponse.success("Booking created successfully", response);
    }

    @Operation(summary = "Get All Bookings for a Service", description = "Retrieve all bookings associated with a specific service")
    @GetMapping("/{serviceId}")
    public ApiResponse<List<BookingResponse>> getBookings(@PathVariable Long serviceId) {
        List<BookingResponse> response = service.getAllBookings(serviceId);
        return ApiResponse.success("Bookings retrieved successfully", response);
    }
    
    

}
