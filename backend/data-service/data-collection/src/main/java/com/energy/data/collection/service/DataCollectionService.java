package com.energy.data.collection.service;

import com.energy.core.entity.EnergyData;
import com.energy.core.mapper.EnergyDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataCollectionService {

    @Autowired
    private EnergyDataMapper energyDataMapper;

    public EnergyData collectEnergyData(EnergyData energyData) {
        energyData.setTimestamp(LocalDateTime.now());
        energyData.setCreateTime(LocalDateTime.now());
        energyData.setUpdateTime(LocalDateTime.now());
        energyDataMapper.insert(energyData);
        return energyData;
    }

    public List<EnergyData> getEnergyDataByDeviceId(Long deviceId) {
        return energyDataMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EnergyData>().lambda()
                        .eq(EnergyData::getDeviceId, deviceId)
                        .orderByDesc(EnergyData::getTimestamp)
        );
    }

    public List<EnergyData> getEnergyDataByDeviceType(String deviceType) {
        return energyDataMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EnergyData>().lambda()
                        .eq(EnergyData::getDeviceType, deviceType)
                        .orderByDesc(EnergyData::getTimestamp)
        );
    }

    public List<EnergyData> getEnergyDataByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return energyDataMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EnergyData>().lambda()
                        .between(EnergyData::getTimestamp, startTime, endTime)
                        .orderByDesc(EnergyData::getTimestamp)
        );
    }

    public List<EnergyData> getAllEnergyData() {
        return energyDataMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EnergyData>().lambda()
                        .orderByDesc(EnergyData::getTimestamp)
        );
    }
}