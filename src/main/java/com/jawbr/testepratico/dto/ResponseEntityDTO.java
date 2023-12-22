package com.jawbr.testepratico.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseEntityDTO(
        Object result
) {
}
