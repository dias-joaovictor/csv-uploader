package com.gerimedica.csvuploader;

import com.gerimedica.csvuploader.csv.MedicalDataDto;
import com.gerimedica.csvuploader.entity.MedicalData;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalDataConverter {

    List<MedicalData> toMedicalData(List<MedicalDataDto> medicalData);

    List<MedicalDataDto> toDto(List<MedicalData> medicalData);

}
