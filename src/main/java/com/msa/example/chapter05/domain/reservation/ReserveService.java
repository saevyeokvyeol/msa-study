package com.msa.example.chapter05.domain.reservation;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReserveService {
    public Long reserveHotelRoom(Long hotelId, String roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        return 1L;
    }
}
