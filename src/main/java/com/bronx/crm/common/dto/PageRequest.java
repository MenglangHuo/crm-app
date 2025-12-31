package com.bronx.crm.common.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
    @Min(value = 0, message = "Page number cannot be less than 0")
    private int page = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size cannot exceed 100")
    private int size = 10;

    private String sortBy = "createdAt";

    private String sortDirection = "DESC";
}
