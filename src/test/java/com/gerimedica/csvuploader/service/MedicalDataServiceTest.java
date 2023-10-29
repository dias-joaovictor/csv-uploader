package com.gerimedica.csvuploader.service;

import com.gerimedica.csvuploader.converter.MedicalDataConverter;
import com.gerimedica.csvuploader.csv.MedicalDataDto;
import com.gerimedica.csvuploader.csv.MedicalDataResponseWrapper;
import com.gerimedica.csvuploader.entity.MedicalData;
import com.gerimedica.csvuploader.repository.MedicalDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalDataServiceTest {

    @InjectMocks
    private MedicalDataServiceImpl medicalDataService;

    @Mock
    private CsvParserService csvParserService;

    @Mock
    private MedicalDataConverter medicalDataConverter;

    @Mock
    private MedicalDataRepository medicalDataRepository;

    @Test
    void processCsvFile_givenValidInputStream_saveMethodIsCalled() {
        // arrange
        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        List<MedicalDataDto> medicalDataDtos = List.of(MedicalDataDto.builder()
                        .code("1")
                        .build(),
                MedicalDataDto.builder()
                        .code("2")
                        .build());
        when(csvParserService.parseCsvFile(inputStream, MedicalDataDto.class))
                .thenReturn(medicalDataDtos);
        when(medicalDataConverter.toMedicalData(medicalDataDtos))
                .thenReturn(List.of(new MedicalData(), new MedicalData()));

        // act
        medicalDataService.processCsvFile(inputStream);

        // assert
        verify(medicalDataRepository, times(2)).save(Mockito.any(MedicalData.class));
    }

    @Test
    void processCsvFile_givenValidInputStreamButAnError_saveMethodIsNotCalled() {
        // arrange
        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        when(csvParserService.parseCsvFile(inputStream, MedicalDataDto.class))
                .thenThrow(new RuntimeException("Error occurred"));

        // act
        assertThrows(RuntimeException.class, () -> medicalDataService.processCsvFile(inputStream));

        // assert
        verifyNoInteractions(medicalDataRepository);
        verifyNoInteractions(medicalDataConverter);
    }

    @Test
    void findAll_databaseHasElements_dataIsReturned() {
        // arrange
        List<MedicalData> medicalDummyData = List.of(new MedicalData(), new MedicalData());
        when(medicalDataRepository.findAll(Sort.by("sortingPriority", "code")))
                .thenReturn(medicalDummyData);

        when(medicalDataConverter.toDto(medicalDummyData))
                .thenReturn(List.of(MedicalDataDto.builder()
                                .code("1")
                                .build(),
                        MedicalDataDto.builder()
                                .code("2")
                                .build()));
        // act
        MedicalDataResponseWrapper response = medicalDataService.findAll();

        // assert
        assertThat(response.getMedicalData())
                .as("My response is correct")
                .extracting("code")
                .containsExactlyInAnyOrder("1", "2");

        assertThat(response.getSize())
                .as("My response size is correct")
                .isEqualTo(2);
    }


    @Test
    void findById_givinValidCode_itemFound() {
        // arrange
        when(medicalDataRepository.findById("1"))
                .thenReturn(java.util.Optional.of(new MedicalData()));
        when(medicalDataConverter.toDto(Mockito.anyList()))
                .thenReturn(List.of(MedicalDataDto.builder()
                        .code("1")
                        .build()));

        // act
        MedicalDataResponseWrapper response = medicalDataService.findById("1");

        // assert
        assertThat(response.getMedicalData())
                .as("My response is correct")
                .extracting("code")
                .containsExactlyInAnyOrder("1");

        assertThat(response.getSize())
                .as("My response size is correct")
                .isEqualTo(1);
    }

    @Test
    void findById_givinBlankCode_itemNotFound() {
        // arrange

        // act
        MedicalDataResponseWrapper response = medicalDataService.findById(" ");

        // assert
        assertThat(response.getMedicalData())
                .as("My response is correct")
                .isEmpty();

        assertThat(response.getSize())
                .as("My response size is correct")
                .isEqualTo(0);
        verifyNoInteractions(medicalDataConverter);
        verifyNoInteractions(medicalDataRepository);
    }

    @Test
    void findById_givinNullCode_itemNotFound() {
        // arrange

        // act
        MedicalDataResponseWrapper response = medicalDataService.findById(null);

        // assert
        assertThat(response.getMedicalData())
                .as("My response is correct")
                .isEmpty();

        assertThat(response.getSize())
                .as("My response size is correct")
                .isEqualTo(0);
        verifyNoInteractions(medicalDataConverter);
        verifyNoInteractions(medicalDataRepository);
    }

    @Test
    void cleanUp_databaseHasElements_dataIsDeleted() {
        // arrange

        // act
        medicalDataService.cleanUp();

        // assert
        verify(medicalDataRepository).deleteAll();
    }

}