package com.gerimedica.csvuploader.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * CsvMedicalData is a POJO class that represents the CSV file data.
 */
@Data
@Builder
public class MedicalDataDto implements CsvData {

    @CsvBindByName
    private String code;

    @CsvBindByName
    private String source;

    @CsvBindByName
    private String codeListCode;

    @CsvBindByName
    private String displayValue;

    @CsvBindByName
    private String longDescription;

    @CsvBindByName
    @CsvDate("dd-MM-yyyy")
    private LocalDate fromDate;

    @CsvBindByName
    @CsvDate("dd-MM-yyyy")
    private LocalDate toDate;

    @CsvBindByName
    private int sortingPriority;
}
