package com.gerimedica.csvuploader.endpoint;

import com.gerimedica.csvuploader.csv.MedicalDataDto;
import com.gerimedica.csvuploader.csv.MedicalDataResponseWrapper;
import com.gerimedica.csvuploader.service.MedicalDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gerimedica.csvuploader.repository.MedicalDataRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/medical-data")
@RestController
@RequiredArgsConstructor
public class MedicalDataEndpoint {

    private final MedicalDataService medicalDataService;

    @GetMapping
    public MedicalDataResponseWrapper getAll() {
        return medicalDataService.findAll();
    }

    @GetMapping("/by-code/{code}")
    public MedicalDataResponseWrapper getById(@PathVariable String code) {
        return medicalDataService.findById(code);
    }

    @PutMapping
    public void uploadMedicalData(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("Starting to upload file: {}", file.getOriginalFilename());
        medicalDataService.processCsvFile(file.getInputStream());
        log.info("Finishing to upload file: {}", file.getOriginalFilename());
    }

    @DeleteMapping
    public void deleteAll() {
        medicalDataService.cleanUp();
    }

}
