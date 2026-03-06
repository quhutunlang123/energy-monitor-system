package com.energy.data.storage.controller;

import com.energy.core.entity.EnergyData;
import com.energy.data.storage.service.DataStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/data/storage")
public class DataStorageController {

    @Autowired
    private DataStorageService dataStorageService;

    @PostMapping
    public ResponseEntity<EnergyData> saveEnergyData(@RequestBody EnergyData energyData) {
        EnergyData savedData = dataStorageService.saveEnergyData(energyData);
        return ResponseEntity.ok(savedData);
    }

    @PutMapping
    public ResponseEntity<EnergyData> updateEnergyData(@RequestBody EnergyData energyData) {
        EnergyData updatedData = dataStorageService.updateEnergyData(energyData);
        return ResponseEntity.ok(updatedData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnergyData(@PathVariable Long id) {
        dataStorageService.deleteEnergyData(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnergyData> getEnergyDataById(@PathVariable Long id) {
        EnergyData energyData = dataStorageService.getEnergyDataById(id);
        return energyData != null ? ResponseEntity.ok(energyData) : ResponseEntity.notFound().build();
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<EnergyData>> getEnergyDataByDeviceId(
            @PathVariable Long deviceId,
            @RequestParam(defaultValue = "10") int limit) {
        List<EnergyData> energyDataList = dataStorageService.getEnergyDataByDeviceId(deviceId, limit);
        return ResponseEntity.ok(energyDataList);
    }

    @GetMapping("/time-range")
    public ResponseEntity<List<EnergyData>> getEnergyDataByTimeRange(
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime) {
        List<EnergyData> energyDataList = dataStorageService.getEnergyDataByTimeRange(startTime, endTime);
        return ResponseEntity.ok(energyDataList);
    }

    @GetMapping
    public ResponseEntity<List<EnergyData>> getAllEnergyData(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<EnergyData> energyDataList = dataStorageService.getAllEnergyData(page, size);
        return ResponseEntity.ok(energyDataList);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getEnergyDataCount() {
        long count = dataStorageService.getEnergyDataCount();
        return ResponseEntity.ok(count);
    }
}