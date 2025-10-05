package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    @NotBlank(message = "uid не может быть пустым")
    private String uid;

    @NotBlank(message = "operationUid не может быть пустым")
    private String operationUid;

    @NotBlank(message = "systemTime не может быть пустым")
    private String systemTime;

    @NotBlank(message = "code не может быть пустым")
    private String code;

    @NotBlank(message = "errorCode не может быть пустым")
    private String errorCode;

    @NotBlank(message = "errorMessage не может быть пустым")
    private String errorMessage;
}
