package com.energy.data.collection.controller;

import com.energy.core.entity.EnergyData;
import com.energy.data.collection.service.DataCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataCollectionController {

    @Autowired
    private DataCollectionService dataCollectionService;

    @PostMapping("/collect")
    public ResponseEntity<EnergyData> collectEnergyData(@RequestBody EnergyData energyData) {
        EnergyData collectedData = dataCollectionService.collectEnergyData(energyData);
        return ResponseEntity.ok(collectedData);
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<EnergyData>> getEnergyDataByDeviceId(@PathVariable Long deviceId) {
        List<EnergyData> energyDataList = dataCollectionService.getEnergyDataByDeviceId(deviceId);
        return ResponseEntity.ok(energyDataList);
    }

    @GetMapping("/type/{deviceType}")
    public ResponseEntity<List<EnergyData>> getEnergyDataByDeviceType(@PathVariable String deviceType) {
        List<EnergyData> energyDataList = dataCollectionService.getEnergyDataByDeviceType(deviceType);
        return ResponseEntity.ok(energyDataList);
    }

    @GetMapping("/time-range")
    public ResponseEntity<List<EnergyData>> getEnergyDataByTimeRange(
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime) {
        List<EnergyData> energyDataList = dataCollectionService.getEnergyDataByTimeRange(startTime, endTime);
        return ResponseEntity.ok(energyDataList);
    }

    @GetMapping
    public ResponseEntity<List<EnergyData>> getAllEnergyData() {
        List<EnergyData> energyDataList = dataCollectionService.getAllEnergyData();
        return ResponseEntity.ok(energyDataList);
    }
}