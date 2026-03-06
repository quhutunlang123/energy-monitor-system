package com.energy.alert.controller;

import com.energy.core.entity.Alert;
import com.energy.alert.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @PostMapping
    public ResponseEntity<Alert> createAlert(@RequestBody Alert alert) {
        Alert createdAlert = alertService.createAlert(alert);
        return ResponseEntity.ok(createdAlert);
    }

    @PutMapping("/{id}/handle")
    public ResponseEntity<Alert> handleAlert(@PathVariable Long id, @RequestParam String handler) {
        Alert handledAlert = alertService.handleAlert(id, handler);
        return handledAlert != null ? ResponseEntity.ok(handledAlert) : ResponseEntity.notFound().build();
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<Alert>> getAlertsByDeviceId(@PathVariable Long deviceId) {
        List<Alert> alerts = alertService.getAlertsByDeviceId(deviceId);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/level/{alertLevel}")
    public ResponseEntity<List<Alert>> getAlertsByAlertLevel(@PathVariable String alertLevel) {
        List<Alert> alerts = alertService.getAlertsByAlertLevel(alertLevel);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Alert>> getAlertsByStatus(@PathVariable Integer status) {
        List<Alert> alerts = alertService.getAlertsByStatus(status);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/time-range")
    public ResponseEntity<List<Alert>> getAlertsByTimeRange(
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime) {
        List<Alert> alerts = alertService.getAlertsByTimeRange(startTime, endTime);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping
    public ResponseEntity<List<Alert>> getAllAlerts() {
        List<Alert> alerts = alertService.getAllAlerts();
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> getAlertCountByStatus(@PathVariable Integer status) {
        long count = alertService.getAlertCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/level/{alertLevel}")
    public ResponseEntity<Long> getAlertCountByAlertLevel(@PathVariable String alertLevel) {
        long count = alertService.getAlertCountByAlertLevel(alertLevel);
        return ResponseEntity.ok(count);
    }
}