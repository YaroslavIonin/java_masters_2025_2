package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
//    @Pattern(regexp = "success|failed", message = "code должно быть 'success' или 'failed'")
    private String code;

    @NotBlank(message = "errorCode не может быть пустым")
//    @Pattern(
//            regexp = "UnsupportedCodeException|ValidationException|UnknownException",
//            message = "errorCode должно быть одним из: UnsupportedCodeException, ValidationException, UnknownException"
//    )
    private String errorCode;

    @NotBlank(message = "errorMessage не может быть пустым")
//    @Pattern(
//            regexp = "Не поддерживаемая ошибка|Ошибка валидации|Произошла непредвиденная ошибка",
//            message = "errorMessage должно быть одним из: Не поддерживаемая ошибка, Ошибка валидации, Произошла непредвиденная ошибка"
//    )
    private String errorMessage;
}
