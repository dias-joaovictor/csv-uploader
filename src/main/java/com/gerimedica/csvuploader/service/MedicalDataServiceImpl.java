package com.gerimedica.csvuploader.service;

import com.gerimedica.csvuploader.converter.MedicalDataConverter;
import com.gerimedica.csvuploader.csv.MedicalDataDto;
import com.gerimedica.csvuploader.csv.MedicalDataResponseWrapper;
import com.gerimedica.csvuploader.repository.MedicalDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicalDataServiceImpl implements MedicalDataService {

    private final CsvParserService csvParserService;

    private final MedicalDataConverter medicalDataConverter;

    private final MedicalDataRepository medicalDataRepository;

    @Override
    public void processCsvFile(InputStream inputStream) {
        medicalDataConverter.toMedicalData(csvParserService.parseCsvFile(inputStream, MedicalDataDto.class))
                .forEach(medicalDataRepository::save);
    }

    @Override
    public MedicalDataResponseWrapper findAll() {
        var data = medicalDataConverter.toDto(medicalDataRepository
                .findAll(Sort.by("sortingPriority", "code")));
        return MedicalDataResponseWrapper.builder()
                .medicalData(data)
                .size(data.size())
                .build();
    }

    @Override
    public MedicalDataResponseWrapper findById(String code) {
        medicalDataRepository.getReferenceById(code);
        if(StringUtils.isBlank(code)) {
            return MedicalDataResponseWrapper.builder()
                    .medicalData(List.of())
                    .size(0)
                    .build();
        }
        var data = medicalDataRepository.findById(code)
                .map(List::of)
                .map(medicalDataConverter::toDto)
                .orElse(List.of());

        return MedicalDataResponseWrapper.builder()
                .medicalData(data)
                .size(data.size())
                .build();
    }

    @Override
    public void cleanUp() {
        medicalDataRepository.deleteAll();
    }

}
