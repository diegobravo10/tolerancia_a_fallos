package com.example.inventory_service.config;

import com.example.inventory_service.entity.Seat;
import com.example.inventory_service.repository.SeatRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private final SeatRepository repository;

    public DataLoader(SeatRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void loadData() {
        repository.save(new Seat("A1", 1L, true));
        repository.save(new Seat("A2", 1L, true));
        repository.save(new Seat("A3", 1L, true));
    }
}
