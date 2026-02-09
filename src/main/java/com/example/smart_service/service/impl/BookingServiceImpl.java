package com.example.smart_service.service.impl;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.smart_service.dto.request.BookingRequest;
import com.example.smart_service.dto.response.AuthResponse;
import com.example.smart_service.dto.response.BookingResponse;
import com.example.smart_service.entity.BookingEntity;
import com.example.smart_service.entity.ServiceEntity;
import com.example.smart_service.entity.UserEntity;
import com.example.smart_service.exception.ResourceNotFoundException;
import com.example.smart_service.repository.BookingRepository;
import com.example.smart_service.repository.ServiceRepository;
import com.example.smart_service.repository.UserRepository;
import com.example.smart_service.service.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    
    @Override
    public BookingResponse postBooking(Long serviceId, BookingRequest bookingRequest) {

        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        
        BookingEntity booking = new BookingEntity();
        booking.setBookingDate(bookingRequest.getBookingDate());
        booking.setStartTime(bookingRequest.getStartTime());
        booking.setEndTime(bookingRequest.getEndTime());
        booking.setUser(user);
        booking.setService(service);


        BookingEntity savedBooking = bookingRepository.save(booking);

         AuthResponse userResponse = new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getPrifileImage(),
                user.getStatus(),
                null,
                null
        );

        return new BookingResponse(
                savedBooking.getId(),
                savedBooking.getBookingDate(),
                savedBooking.getStartTime(),
                savedBooking.getEndTime(),
                savedBooking.getStatus(),
                service.getTitle(),
                userResponse
        );
    }

    @Override
    public List<BookingResponse> getAllBookings(Long serviceId) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        return bookingRepository.findByService(service).stream().map(booking -> {
            AuthResponse userResponse = new AuthResponse(
                    booking.getUser().getId(),
                    booking.getUser().getUsername(),
                    booking.getUser().getEmail(),
                    booking.getUser().getPassword(),
                    booking.getUser().getPhone(),
                    booking.getUser().getPrifileImage(),
                    booking.getUser().getStatus(),
                    null,
                    null
            );
            return new BookingResponse(
                    booking.getId(),
                    booking.getBookingDate(),
                    booking.getStartTime(),
                    booking.getEndTime(),
                    booking.getStatus(),
                    service.getTitle(),
                    userResponse
            );
        }).toList();
     }
}