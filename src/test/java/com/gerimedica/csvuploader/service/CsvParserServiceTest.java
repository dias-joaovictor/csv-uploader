package com.gerimedica.csvuploader.service;

import com.gerimedica.csvuploader.csv.MedicalDataDto;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CsvParserServiceTest {

    @InjectMocks
    private CsvParserServiceImpl csvParserService;

    private static final String CSV_TEXT_COLUMN_CORRECT_ORDER = """
            "source","codeListCode","code","displayValue","longDescription","fromDate","toDate","sortingPriority"
            "ZIB","ZIB001","271636001","Polsslag regelmatig","The long description is necessary","01-01-2019","","1"
            "ZIB","ZIB001","61086009","Polsslag onregelmatig","","01-01-2019","","2"
            "ZIB","ZIB001","Type 1","Losse harde keutels, zoals noten","","01-01-2019","",""
            """;

    private static final String CSV_TEXT_COLUMN_OTHER_ORDER = """
            "codeListCode","source","code","displayValue","longDescription","fromDate","toDate","sortingPriority"
            "ZIB001","ZIB","271636001","Polsslag regelmatig","The long description is necessary","01-01-2019","","1"
            "ZIB001","ZIB","61086009","Polsslag onregelmatig","","01-01-2019","","2"
            "ZIB001","ZIB","Type 1","Losse harde keutels, zoals noten","","01-01-2019","",""
                """;

    @Test
    void parseCsvFile_givenInputStreamWithColumnsInAnyOrder_shouldReturnListOfCsvData() {
        // arrange

        InputStream inputStream = new ByteArrayInputStream(CSV_TEXT_COLUMN_OTHER_ORDER.getBytes());

        // act
        var medicalDataList = csvParserService.parseCsvFile(inputStream, MedicalDataDto.class);

        // assert
        assertThat(medicalDataList)
                .as("Columns in any order are parsed still")
                .extracting("code", "source", "codeListCode", "displayValue", "longDescription",
                        "fromDate", "toDate", "sortingPriority")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("271636001", "ZIB", "ZIB001", "Polsslag regelmatig",
                                "The long description is necessary", LocalDate.parse("2019-01-01"), null, 1),
                        Tuple.tuple("61086009", "ZIB", "ZIB001", "Polsslag onregelmatig", "", LocalDate.parse("2019-01-01"), null, 2),
                        Tuple.tuple("Type 1", "ZIB", "ZIB001", "Losse harde keutels, zoals noten", "", LocalDate.parse("2019-01-01"), null, 0));

    }

    @Test
    void parseCsvFile_givenInputStreamWithColumnsInCorrectOrder_shouldReturnListOfCsvData() {
        // arrange

        InputStream inputStream = new ByteArrayInputStream(CSV_TEXT_COLUMN_CORRECT_ORDER.getBytes());

        // act
        var medicalDataList = csvParserService.parseCsvFile(inputStream, MedicalDataDto.class);

        // assert
        assertThat(medicalDataList)
                .as("Columns in any order are parsed still")
                .extracting("code", "source", "codeListCode", "displayValue", "longDescription",
                        "fromDate", "toDate", "sortingPriority")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("271636001", "ZIB", "ZIB001", "Polsslag regelmatig",
                                "The long description is necessary", LocalDate.parse("2019-01-01"), null, 1),
                        Tuple.tuple("61086009", "ZIB", "ZIB001", "Polsslag onregelmatig", "", LocalDate.parse("2019-01-01"), null, 2),
                        Tuple.tuple("Type 1", "ZIB", "ZIB001", "Losse harde keutels, zoals noten", "", LocalDate.parse("2019-01-01"), null, 0));

    }

}