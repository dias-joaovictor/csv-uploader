package com.gerimedica.csvuploader.endpoint;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MultipartDataFile {
    private String name;
    private MultipartFile file;
}
