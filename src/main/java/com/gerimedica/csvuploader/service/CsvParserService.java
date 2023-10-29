package com.gerimedica.csvuploader.service;

import com.gerimedica.csvuploader.csv.CsvData;

import java.io.InputStream;
import java.util.List;

public interface CsvParserService {

    <T extends CsvData> List<T> parseCsvFile(final InputStream inputStream, Class<T> clazz);

}
