package com.gerimedica.csvuploader.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * CsvMedicalData is a POJO class that represents the CSV file data.
 */
@Data
@Builder
public class MedicalDataResponseWrapper {

    List<MedicalDataDto> medicalData;

    private int size;
}
