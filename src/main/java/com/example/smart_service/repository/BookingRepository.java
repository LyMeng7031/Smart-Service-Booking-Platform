package com.example.smart_service.repository;

import com.example.smart_service.entity.BookingEntity;
import com.example.smart_service.entity.ServiceEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findByService(ServiceEntity service);

}
