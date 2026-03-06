package com.energy.data.process.service;

import com.energy.core.entity.EnergyData;
import com.energy.core.mapper.EnergyDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataProcessService {

    @Autowired
    private EnergyDataMapper energyDataMapper;

    public double calculateAverageConsumption(Long deviceId, LocalDateTime startTime, LocalDateTime endTime) {
        List<EnergyData> energyDataList = energyDataMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EnergyData>().lambda()
                        .eq(EnergyData::getDeviceId, deviceId)
                        .between(EnergyData::getTimestamp, startTime, endTime)
        );

        if (energyDataList.isEmpty()) {
            return 0.0;
        }

        double totalConsumption = energyDataList.stream()
                .mapToDouble(EnergyData::getCurrentValue)
                .sum();

        return totalConsumption / energyDataList.size();
    }

    public double calculateTotalConsumption(Long deviceId, LocalDateTime startTime, LocalDateTime endTime) {
        List<EnergyData> energyDataList = energyDataMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EnergyData>().lambda()
                        .eq(EnergyData::getDeviceId, deviceId)
                        .between(EnergyData::getTimestamp, startTime, endTime)
        );

        return energyDataList.stream()
                .mapToDouble(EnergyData::getCurrentValue)
                .sum();
    }

    public EnergyData getMaxConsumption(Long deviceId, LocalDateTime startTime, LocalDateTime endTime) {
        List<EnergyData> energyDataList = energyDataMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EnergyData>().lambda()
                        .eq(EnergyData::getDeviceId, deviceId)
                        .between(EnergyData::getTimestamp, startTime, endTime)
                        .orderByDesc(EnergyData::getCurrentValue)
        );

        return energyDataList.isEmpty() ? null : energyDataList.get(0);
    }

    public EnergyData getMinConsumption(Long deviceId, LocalDateTime startTime, LocalDateTime endTime) {
        List<EnergyData> energyDataList = energyDataMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EnergyData>().lambda()
                        .eq(EnergyData::getDeviceId, deviceId)
                        .between(EnergyData::getTimestamp, startTime, endTime)
                        .orderByAsc(EnergyData::getCurrentValue)
        );

        return energyDataList.isEmpty() ? null : energyDataList.get(0);
    }

    public double calculateDailyAverageConsumption(Long deviceId, LocalDate date) {
        LocalDateTime startTime = date.atStartOfDay();
        LocalDateTime endTime = date.plusDays(1).atStartOfDay().minusSeconds(1);
        return calculateAverageConsumption(deviceId, startTime, endTime);
    }

    public double calculateMonthlyAverageConsumption(Long deviceId, int year, int month) {
        LocalDateTime startTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endTime = startTime.plusMonths(1).minusSeconds(1);
        return calculateAverageConsumption(deviceId, startTime, endTime);
    }
}