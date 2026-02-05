package com.example.smart_service.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookingResponse {
    private Long id;
    private LocalDate bookingDate;
    private String startTime;
    private String endTime;
    private String status;
    private String serviceTitle;
    private AuthResponse user;


    public BookingResponse(Long id, LocalDate bookingDate, String startTime, String endTime, String status, String serviceTitle, AuthResponse user) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.serviceTitle = serviceTitle;
        this.user = user;
        
    }
}
