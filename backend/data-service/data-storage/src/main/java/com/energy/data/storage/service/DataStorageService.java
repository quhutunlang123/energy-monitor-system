package com.energy.data.storage.service;

import com.energy.core.entity.EnergyData;
import com.energy.core.mapper.EnergyDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataStorageService {

    @Autowired
    private EnergyDataMapper energyDataMapper;

    public EnergyData saveEnergyData(EnergyData energyData) {
        energyData.setCreateTime(LocalDateTime.now());
        energyData.setUpdateTime(LocalDateTime.now());
        energyDataMapper.insert(energyData);
        return energyData;
    }

    public EnergyData updateEnergyData(EnergyData energyData) {
        energyData.setUpdateTime(LocalDateTime.now());
        energyDataMapper.updateById(energyData);
        return energyData;
    }

    public void deleteEnergyData(Long id) {
        energyDataMapper.deleteById(id);
    }

    public EnergyData getEnergyDataById(Long id) {
        return energyDataMapper.selectById(id);
    }

    public List<EnergyData> getEnergyDataByDeviceId(Long deviceId, int limit) {
        return energyDataMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EnergyData>().lambda()
                        .eq(EnergyData::getDeviceId, deviceId)
                        .orderByDesc(EnergyData::getTimestamp)
                        .last("LIMIT " + limit)
        );
    }

    public List<EnergyData> getEnergyDataByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return energyDataMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EnergyData>().lambda()
                        .between(EnergyData::getTimestamp, startTime, endTime)
                        .orderByDesc(EnergyData::getTimestamp)
        );
    }

    public List<EnergyData> getAllEnergyData(int page, int size) {
        int offset = (page - 1) * size;
        return energyDataMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EnergyData>().lambda()
                        .orderByDesc(EnergyData::getTimestamp)
                        .last("LIMIT " + size + " OFFSET " + offset)
        );
    }

    public long getEnergyDataCount() {
        return energyDataMapper.selectCount(null);
    }
}