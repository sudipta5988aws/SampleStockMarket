package com.jpmc.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO used in ControllerAdvice to return ErrorResponseDTO from controllers
 * in case any exception occurred
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ErrorResponseDTO {
    private boolean error =false;
    private String errorCode;
    private String errorMessage;
}
