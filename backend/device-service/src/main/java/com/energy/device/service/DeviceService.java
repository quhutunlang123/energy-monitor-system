package com.energy.device.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.energy.core.entity.Device;
import com.energy.core.mapper.DeviceMapper;
import com.energy.device.dto.DeviceDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    public Device createDevice(DeviceDTO deviceDTO) {
        Device device = new Device();
        BeanUtils.copyProperties(deviceDTO, device);
        deviceMapper.insert(device);
        return device;
    }

    public Optional<Device> getDeviceById(Long id) {
        return Optional.ofNullable(deviceMapper.selectById(id));
    }

    public List<Device> getDevicesByUserId(Long userId) {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return deviceMapper.selectList(wrapper);
    }

    public List<Device> getAllDevices() {
        return deviceMapper.selectList(null);
    }

    public Device updateDevice(Long id, DeviceDTO deviceDTO) {
        Device device = deviceMapper.selectById(id);
        if (device != null) {
            BeanUtils.copyProperties(deviceDTO, device);
            deviceMapper.updateById(device);
        }
        return device;
    }

    public void deleteDevice(Long id) {
        deviceMapper.deleteById(id);
    }

    public List<Device> getDevicesByType(String deviceType) {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("device_type", deviceType);
        return deviceMapper.selectList(wrapper);
    }

    public List<Device> getDevicesByStatus(Integer status) {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        return deviceMapper.selectList(wrapper);
    }
}