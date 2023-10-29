package com.gerimedica.csvuploader.repository;

import com.gerimedica.csvuploader.entity.MedicalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalDataRepository extends JpaRepository<MedicalData, String> {
}
