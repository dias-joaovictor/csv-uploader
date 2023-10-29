package com.gerimedica.csvuploader.service;

import com.gerimedica.csvuploader.csv.CsvData;
import com.gerimedica.csvuploader.exception.FileParseException;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CsvParserServiceImpl implements CsvParserService{

    @Override
    public <T extends CsvData> List<T> parseCsvFile(final InputStream inputStream, Class<T> clazz) {
        try {
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);

            return new CsvToBeanBuilder<T>(new InputStreamReader(inputStream))
                    .withMappingStrategy(strategy)
                    .build()
                    .parse();

        } catch (Exception e) {
            throw new FileParseException("Error occurred while parsing CSV file", e);
        }
    }

}
