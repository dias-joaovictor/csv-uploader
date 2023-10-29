package com.gerimedica.csvuploader.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(of = "code")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalData {

    @Id
    private String code;

    private String source;

    private String codeListCode;

    private String displayValue;

    private String longDescription;

    private LocalDate fromDate;

    private LocalDate toDate;

    private int sortingPriority;
}
