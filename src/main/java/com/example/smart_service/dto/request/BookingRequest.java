package com.example.smart_service.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookingRequest {
    private LocalDate bookingDate;
    private String startTime;
    private String endTime;
}
