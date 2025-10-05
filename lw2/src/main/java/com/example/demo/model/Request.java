package com.example.demo.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @NotBlank(message = "uid не может быть пустым")
    @Size(max = 32, message = "uid не может превышать 32 символа")
    private String uid;

    @NotBlank(message = "operationUid не может быть пустым")
    @Size(max = 32, message = "operationUid не может превышать 32 символа")
    private String operationUid;

    private String systemName;
    private String systemTime;
    private String source;

    @Min(value = 1, message = "communicationId должен быть >= 1")
    @Max(value = 100000, message = "communicationId должен быть <= 100000")
    private int communicationId;

    private int templateId;
    private int productCode;
    private int smsCode;
}
