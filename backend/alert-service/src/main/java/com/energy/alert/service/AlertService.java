package com.energy.alert.service;

import com.energy.core.entity.Alert;
import com.energy.core.mapper.AlertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertMapper alertMapper;

    public Alert createAlert(Alert alert) {
        alert.setAlertTime(LocalDateTime.now());
        alert.setStatus(0); // 0: 未处理
        alert.setCreateTime(LocalDateTime.now());
        alert.setUpdateTime(LocalDateTime.now());
        alertMapper.insert(alert);
        return alert;
    }

    public Alert handleAlert(Long id, String handler) {
        Alert alert = alertMapper.selectById(id);
        if (alert != null) {
            alert.setStatus(1); // 1: 已处理
            alert.setHandleTime(LocalDateTime.now());
            alert.setHandler(handler);
            alert.setUpdateTime(LocalDateTime.now());
            alertMapper.updateById(alert);
        }
        return alert;
    }

    public List<Alert> getAlertsByDeviceId(Long deviceId) {
        return alertMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Alert>().lambda()
                        .eq(Alert::getDeviceId, deviceId)
                        .orderByDesc(Alert::getAlertTime)
        );
    }

    public List<Alert> getAlertsByAlertLevel(String alertLevel) {
        return alertMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Alert>().lambda()
                        .eq(Alert::getAlertLevel, alertLevel)
                        .orderByDesc(Alert::getAlertTime)
        );
    }

    public List<Alert> getAlertsByStatus(Integer status) {
        return alertMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Alert>().lambda()
                        .eq(Alert::getStatus, status)
                        .orderByDesc(Alert::getAlertTime)
        );
    }

    public List<Alert> getAlertsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return alertMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Alert>().lambda()
                        .between(Alert::getAlertTime, startTime, endTime)
                        .orderByDesc(Alert::getAlertTime)
        );
    }

    public List<Alert> getAllAlerts() {
        return alertMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Alert>().lambda()
                        .orderByDesc(Alert::getAlertTime)
        );
    }

    public long getAlertCountByStatus(Integer status) {
        return alertMapper.selectCount(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Alert>().lambda()
                        .eq(Alert::getStatus, status)
        );
    }

    public long getAlertCountByAlertLevel(String alertLevel) {
        return alertMapper.selectCount(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Alert>().lambda()
                        .eq(Alert::getAlertLevel, alertLevel)
        );
    }
}