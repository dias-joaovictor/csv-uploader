package com.gerimedica.csvuploader.converter;

import com.gerimedica.csvuploader.csv.MedicalDataDto;
import com.gerimedica.csvuploader.entity.MedicalData;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MedicalDataConverterTest {

    private static final String CODE = "1";
    private static final String SOURCE = "Source";
    private static final String CODE_LIST_CODE = "Code List Code";
    private static final String LONG_DESCRIPTION = "Long description";
    private static final LocalDate FROM_DATE = LocalDate.of(2023, 10, 28);
    private static final LocalDate TO_DATE = LocalDate.of(2023, 10, 29);
    private static final int SORTING_PRIORITY = 10;
    private final MedicalDataConverter addressConverter = Mappers.getMapper(MedicalDataConverter.class);

    @Test
    void toMedicalData_givenValidDto_converted() {
        // arrange
        var dto = MedicalDataDto.builder()
                .code(CODE)
                .source(SOURCE)
                .codeListCode(CODE_LIST_CODE)
                .longDescription(LONG_DESCRIPTION)
                .fromDate(FROM_DATE)
                .toDate(TO_DATE)
                .sortingPriority(SORTING_PRIORITY)
                .build();

        // act
        var result = addressConverter.toMedicalData(List.of(dto));

        // assert
        assertThat(result)
                .as("Successfully converted")
                .extracting("code", "source", "codeListCode", "longDescription", "fromDate", "toDate", "sortingPriority")
                .containsExactlyInAnyOrder(Tuple.tuple(
                        CODE, SOURCE, CODE_LIST_CODE, LONG_DESCRIPTION, FROM_DATE, TO_DATE, SORTING_PRIORITY));
    }


    @Test
    void toMedicalData_givenValidEntity_convertedToDto() {
        // arrange
        var entity = MedicalData.builder()
                .code(CODE)
                .source(SOURCE)
                .codeListCode(CODE_LIST_CODE)
                .longDescription(LONG_DESCRIPTION)
                .fromDate(FROM_DATE)
                .toDate(TO_DATE)
                .sortingPriority(SORTING_PRIORITY)
                .build();

        // act
        var result = addressConverter.toDto(List.of(entity));

        // assert
        assertThat(result)
                .as("Successfully converted")
                .extracting("code", "source", "codeListCode", "longDescription", "fromDate", "toDate", "sortingPriority")
                .containsExactlyInAnyOrder(Tuple.tuple(
                        CODE, SOURCE, CODE_LIST_CODE, LONG_DESCRIPTION, FROM_DATE, TO_DATE, SORTING_PRIORITY));
    }


}