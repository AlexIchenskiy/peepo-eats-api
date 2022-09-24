package com.peepoeats.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExceptionDetailedResponse {

    private String message;
    private List<String> details;

}
