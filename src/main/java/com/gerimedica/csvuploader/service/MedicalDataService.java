package com.gerimedica.csvuploader.service;

import com.gerimedica.csvuploader.csv.MedicalDataDto;
import com.gerimedica.csvuploader.csv.MedicalDataResponseWrapper;

import java.io.InputStream;
import java.util.List;

public interface MedicalDataService {

    void processCsvFile(InputStream inputStream);

    MedicalDataResponseWrapper findAll();

    MedicalDataResponseWrapper findById(String code);

    void cleanUp();

}
