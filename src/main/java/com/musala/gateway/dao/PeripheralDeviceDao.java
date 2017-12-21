package com.musala.gateway.dao;

import com.musala.gateway.entities.PeripheralDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeripheralDeviceDao extends JpaRepository<PeripheralDevice, Integer> {
    List<PeripheralDevice> findAll();

    PeripheralDevice findByUid(int uid);
}
