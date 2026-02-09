package com.example.smart_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.smart_service.dto.request.BookingRequest;
import com.example.smart_service.dto.response.BookingResponse;

@Service
public interface BookingService {
    BookingResponse postBooking(Long serviceId,BookingRequest bookingRequest);
    List<BookingResponse> getAllBookings(Long serviceId);
}
