package com.energy.data.process.controller;

import com.energy.core.entity.EnergyData;
import com.energy.data.process.service.DataProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/data/process")
public class DataProcessController {

    @Autowired
    private DataProcessService dataProcessService;

    @GetMapping("/average/{deviceId}")
    public ResponseEntity<Double> calculateAverageConsumption(
            @PathVariable Long deviceId,
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime) {
        double averageConsumption = dataProcessService.calculateAverageConsumption(deviceId, startTime, endTime);
        return ResponseEntity.ok(averageConsumption);
    }

    @GetMapping("/total/{deviceId}")
    public ResponseEntity<Double> calculateTotalConsumption(
            @PathVariable Long deviceId,
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime) {
        double totalConsumption = dataProcessService.calculateTotalConsumption(deviceId, startTime, endTime);
        return ResponseEntity.ok(totalConsumption);
    }

    @GetMapping("/max/{deviceId}")
    public ResponseEntity<EnergyData> getMaxConsumption(
            @PathVariable Long deviceId,
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime) {
        EnergyData maxConsumption = dataProcessService.getMaxConsumption(deviceId, startTime, endTime);
        return maxConsumption != null ? ResponseEntity.ok(maxConsumption) : ResponseEntity.notFound().build();
    }

    @GetMapping("/min/{deviceId}")
    public ResponseEntity<EnergyData> getMinConsumption(
            @PathVariable Long deviceId,
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime) {
        EnergyData minConsumption = dataProcessService.getMinConsumption(deviceId, startTime, endTime);
        return minConsumption != null ? ResponseEntity.ok(minConsumption) : ResponseEntity.notFound().build();
    }

    @GetMapping("/daily-average/{deviceId}")
    public ResponseEntity<Double> calculateDailyAverageConsumption(
            @PathVariable Long deviceId,
            @RequestParam("date") LocalDate date) {
        double dailyAverageConsumption = dataProcessService.calculateDailyAverageConsumption(deviceId, date);
        return ResponseEntity.ok(dailyAverageConsumption);
    }

    @GetMapping("/monthly-average/{deviceId}")
    public ResponseEntity<Double> calculateMonthlyAverageConsumption(
            @PathVariable Long deviceId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        double monthlyAverageConsumption = dataProcessService.calculateMonthlyAverageConsumption(deviceId, year, month);
        return ResponseEntity.ok(monthlyAverageConsumption);
    }
}